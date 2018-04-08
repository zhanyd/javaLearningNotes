# 习题解答

## 1.解释下你所理解的Happens-before含义和JVM里的几个Happens-before约定
1. 如果一个操作happens-before另一个操作，那么第一个操作的执行结果将对第二个操作可见，而且第一个操作的执行顺序排在第二个操作之前。 
2. 两个操作之间存在happens-before关系，并不意味着一定要按照happens-before原则制定的顺序来执行。如果重排序之后的执行结果与按照happens-before关系来执行的结果一致，那么这种重排序并不非法。

## 2.不依赖任何的同步机制（syncronized ,lock），有几种方式能实现多个线程共享变量之间的happens-before方式
1. 程序次序规则：在一个线程内，按照代码顺序，书写在前的操作先行发生于书写在后的操作。准确的说，应该是控制流顺序而不是程序代码的顺序，因为要考虑分支、循环等结构。
2. 管程序锁定规则：一个unlock操作先行发生于后面对于同一个锁的lock操作。这里必须强调的是同一个锁，而“后面”是指时间上的先后顺序。
3. volatile变量规则：对一个volatile对象的写操作先行发生于后面对这个变量的读操作，这里的“后面”同样是指时间上的先后顺序。
4. 线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作。
5. 线程终止规则：线程中的所有操作都先行发生于对此线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值等手段检测到线程已经终止执行。
6. 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过Thread.interrupted()方法检测到是否有中断发生。
7. 对象终结规则：一个对象的初始化完成（构造函数执行结束）先行发生于它的funalize()方法的开始。
8. 传递性：如果操作A先行发生于操作B，操作B先行发生于操作C，那就可以得出操作A先行发生于操作C的结论。

## 3.编程验证normal var ,volaitle，synchronize,atomicLong ,LongAdder，这几种做法实现的计数器方法，在多线程情况下的性能，准确度

    class MyCounter
    {
          private long value;//根据需要进行替换
          public void incr();
          public long getCurValue();//得到最后结果
    }  
 启动10个线程一起执行，每个线程调用incr() 100万次，
所有线程结束后，打印 getCurValue()的结果，分析程序的结果 并作出解释。 用Stream和函数式编程实现则加分！

```
public abstract class AbstractMyCounter {

    public abstract void incr();
    public abstract long getCurValue();

    public long getCount(AbstractMyCounter counter){

        long beginTime = System.currentTimeMillis();
        List<Thread> threadList = IntStream.range(0,10)
                .mapToObj(m -> new Thread(()->{
                    IntStream.rangeClosed(1,100_0000)
                            .forEach(i->counter.incr());
                }))
                .collect(Collectors.toList());
		
		//先循环启动所有线程
        threadList.forEach(t->t.start());
		//等待所有线程运行结束
        threadList.forEach(t->{
            try{
                t.join();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        System.out.print("cost :" + (System.currentTimeMillis() - beginTime) + "  ");
        return counter.getCurValue();
    }
}


public class NormalCounter extends AbstractMyCounter{

    private long value = 0;

    public void incr(){
        value++;
    }
    public long getCurValue(){
        return value;
    }
}



public class VolatileCounter extends AbstractMyCounter{

    private volatile long value = 0;

    public void incr(){
        value++;
    }

    public long getCurValue(){
        return value;
    }
}


public class SynchronizedCounter extends AbstractMyCounter {

    private long value = 0;

    public synchronized void incr() {
        value++;
    }

    public long getCurValue(){
        return value;
    }
}



public class AtomicLongCounter extends AbstractMyCounter{

    private AtomicLong value = new AtomicLong();

    public void incr(){
        value.getAndIncrement();
    }

    public long getCurValue(){
        return value.get();
    }
}


public class LongAdderCounter extends AbstractMyCounter{

    private LongAdder value = new LongAdder();

    public void incr(){
        value.increment();
    }

    public long getCurValue(){
        return value.sum();
    }
}


public class CountTest {

    public static void main(String[] args) throws InterruptedException{
        AbstractMyCounter normalCounter = new NormalCounter();
        AbstractMyCounter volatileCounter = new VolatileCounter();
        AbstractMyCounter synchronizedCounter = new SynchronizedCounter();
        AbstractMyCounter atomicLongCounter = new AtomicLongCounter();
        AbstractMyCounter longAdderCounter = new LongAdderCounter();

        System.out.println("normalCounter value = " + normalCounter.getCount(normalCounter));
        System.out.println("volatileCounter value = " + volatileCounter.getCount(volatileCounter));
        System.out.println("synchronizedCounter value = " + synchronizedCounter.getCount(synchronizedCounter));
        System.out.println("atomicLongCounter value = " + atomicLongCounter.getCount(atomicLongCounter));
        System.out.println("longAdderCounter value = " + longAdderCounter.getCount(longAdderCounter));

    }
}
```

输出：
```
cost :78  normalCounter value = 8967348
cost :168  volatileCounter value = 7012860
cost :1281  synchronizedCounter value = 10000000
cost :204  atomicLongCounter value = 10000000
cost :36  longAdderCounter value = 10000000

```



# 选做题：
## 1.自己画出Java内存模型并解释各个区，以JDK8为例，每个区的控制参数也给出。
## 2.解释为什么会有数组的Atomic类型的对象