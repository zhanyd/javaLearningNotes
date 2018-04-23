package zhan.foundation.lesson06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOnlyRWLock {
    static Object lock=new Object();
    static ArrayList<String> datas=new  ArrayList<String>();
    static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    static Condition condition =  reentrantLock.writeLock().newCondition();

    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10).mapToObj(i->{if(i%2==0) {return new MThreadLR("consumer "+i);} else return new NThreadLR("producer "+i);}).filter(t->{t.start();return true;}).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}

class MThreadLR extends Thread
{

    public MThreadLR(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
        	TestOnlyRWLock.reentrantLock.writeLock().lock();
        	try{
        		
        		while(TestOnlyRWLock.datas.isEmpty())
        		{
        			System.out.println(Thread.currentThread().getName()+" into wait ,because empty ");
        			try {
        				TestOnlyRWLock.condition.await();
        			} catch (InterruptedException e) {
        				break;
        				
        			}
        			System.out.println(Thread.currentThread().getName()+" wait finished ");
        		}
        		if(TestOnlyRWLock.datas.isEmpty())
        		{
        			
        			System.out.println("impossible empty !! "+Thread.currentThread().getName());
        			System.exit(-1);
        		}
        		System.out.println(Thread.currentThread().getName()+" consumer the data :");
        		TestOnlyRWLock.datas.forEach(s->System.out.println(s));
        		TestOnlyRWLock.datas.clear();
        		TestOnlyRWLock.condition.signalAll();
        		System.out.println(Thread.currentThread().getName()+" notifyAll");
        	}finally{
        		TestOnlyRWLock.reentrantLock.writeLock().unlock();
        	}
            
        }
    }
}

class NThreadLR extends Thread
{
    public NThreadLR(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
        	TestOnlyRWLock.reentrantLock.writeLock().lock();
            try
            {
                while(TestOnlyRWLock.datas.size()>0)
                {
                    System.out.println(Thread.currentThread().getName()+" into wait,because full ");
                    try {
                    	TestOnlyRWLock.condition.await();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+" wait finished ");
                }
                if(TestOnlyRWLock.datas.size()>0)
                {

                    System.out.println("impossible full !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i->TestOnlyRWLock.datas.add(i+" data"));
                System.out.println(Thread.currentThread().getName()+" produce the data");
                TestOnlyRWLock.condition.signalAll();
                System.out.println(Thread.currentThread().getName()+" notifyAll");
            }finally{
        		TestOnlyRWLock.reentrantLock.writeLock().unlock();
        	}
            
        }
    }
}
