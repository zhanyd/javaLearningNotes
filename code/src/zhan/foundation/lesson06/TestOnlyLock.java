package zhan.foundation.lesson06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOnlyLock {
    static Object lock=new Object();
    static ArrayList<String> datas=new  ArrayList<String>();
    static ReentrantLock reentrantLock = new ReentrantLock();
    static Condition condition =  reentrantLock.newCondition();

    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10).mapToObj(i->{if(i%2==0) {return new MThreadL("consumer "+i);} else return new NThreadL("producer "+i);}).filter(t->{t.start();return true;}).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}

class MThreadL extends Thread
{

    public MThreadL(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
        	TestOnlyLock.reentrantLock.lock();
        	try{
        		
        		while(TestOnlyLock.datas.isEmpty())
        		{
        			System.out.println(Thread.currentThread().getName()+" into wait ,because empty ");
        			try {
        				TestOnlyLock.condition.await();
        			} catch (InterruptedException e) {
        				break;
        				
        			}
        			System.out.println(Thread.currentThread().getName()+" wait finished ");
        		}
        		if(TestOnlyLock.datas.isEmpty())
        		{
        			
        			System.out.println("impossible empty !! "+Thread.currentThread().getName());
        			System.exit(-1);
        		}
        		System.out.println(Thread.currentThread().getName()+" consumer the data :");
        		TestOnlyLock.datas.forEach(s->System.out.println(s));
        		TestOnlyLock.datas.clear();
        		TestOnlyLock.condition.signalAll();
        		System.out.println(Thread.currentThread().getName()+" notifyAll");
        	}finally{
        		TestOnlyLock.reentrantLock.unlock();
        	}
            
        }
    }
}

class NThreadL extends Thread
{
    public NThreadL(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
        	TestOnlyLock.reentrantLock.lock();
            try
            {
                while(TestOnlyLock.datas.size()>0)
                {
                    System.out.println(Thread.currentThread().getName()+" into wait,because full ");
                    try {
                    	TestOnlyLock.condition.await();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+" wait finished ");
                }
                if(TestOnlyLock.datas.size()>0)
                {

                    System.out.println("impossible full !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i->TestOnlyLock.datas.add(i+" data"));
                System.out.println(Thread.currentThread().getName()+" produce the data");
                TestOnlyLock.condition.signalAll();
                System.out.println(Thread.currentThread().getName()+" notifyAll");
            }finally{
        		TestOnlyLock.reentrantLock.unlock();
        	}
            
        }
    }
}
