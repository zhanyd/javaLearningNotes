# 习题解答

## 1.1 说说下面的几个方法，分别锁的是什么东西？

 public static synchronized void doIt(){xx};
 pubilc syncronzied void doIt() {xxx)
 pubilc void doIt(){ syncronized(myobj) ....}
 
1. public static synchronized void doIt(){xx}; 
   synchronized作用于静态方法上，作用于整个类，锁住了该类的所有对象
2. pubilc syncronzied void doIt() {xxx)  
   syncronzied作用于方法上，作用于当前对象，锁住了当前对象
3. pubilc void doIt(){ syncronized(myobj) ....}
   syncronized作用于方法内的一个代码块，锁住了当前对象的代码块，必须获得myobj的锁方能执行（可以是类或者对象）


synchronized是Java中的关键字，是一种同步锁。它修饰的对象有以下几种：
1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；
2. 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
3. 修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；
4. 修改一个类，其作用的范围是synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象。


## 2.说说为什么下面的代码是错误的 public void doIt() { syncronized(new ObjA()) {xxxx}
 
syncronized(new ObjA())加锁的对象是一个新new的对象，该对象永远也无法被引用，不是当前对象，所以当前对象的syncronized代码块无效。



## 3.说说下面的代码为什么是错误的 public void doIt() {synchrnized(myobj) { if(xxx) { myobjA.wait();}...} 
 
 While循环会在线程被唤醒的时候再次判断条件，如果条件还是不变的话就继续调用wait()，接着睡。
 如果If判断的话，在线程被唤醒的时候就不再判断条件了，直接执行下面的语句，醒来就跑。
 

## 4.将05作业-TestOnly.java 中的代码中的错误一一修订，并确保其永远执行成功


1.if(TestOnly.datas.isEmpty())  => while(TestOnly.datas.isEmpty())
2.添加TestOnly.lock.notifyAll();
```
package zhan.foundation.lesson06;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOnly {
    static Object lock=new Object();
    static ArrayList<String> datas=new  ArrayList<String>();

    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10).mapToObj(i->{if(i%2==0) {return new MThread("consumer "+i);} else return new NThread("producer "+i);}).filter(t->{t.start();return true;}).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}

class MThread extends Thread
{

    public MThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            synchronized(TestOnly.lock)
            {
                while(TestOnly.datas.isEmpty())
                {
                    System.out.println(Thread.currentThread().getName()+" into wait ,because empty ");
                    try {
                        TestOnly.lock.wait();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+" wait finished ");
                }
                if(TestOnly.datas.isEmpty())
                {

                    System.out.println("impossible empty !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                System.out.println(Thread.currentThread().getName()+" consumer the data :");
                TestOnly.datas.forEach(s->System.out.println(s));
                TestOnly.datas.clear();
                TestOnly.lock.notifyAll();
                System.out.println(Thread.currentThread().getName()+" notifyAll");
            }
        }
    }
}

class NThread extends Thread
{
    public NThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            synchronized(TestOnly.lock)
            {
                while(TestOnly.datas.size()>0)
                {
                    System.out.println(Thread.currentThread().getName()+" into wait,because full ");
                    try {
                        TestOnly.lock.wait();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+" wait finished ");
                }
                if(TestOnly.datas.size()>0)
                {

                    System.out.println("impossible full !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i->TestOnly.datas.add(i+" data"));
                System.out.println(Thread.currentThread().getName()+" produce the data");
                TestOnly.lock.notifyAll();
                System.out.println(Thread.currentThread().getName()+" notifyAll");
            }
        }
    }
}
```