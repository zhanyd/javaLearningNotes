# 习题解答

## 1.解释下你所理解的Happens-before含义和JVM里的几个Happens-before约定


## 2.不依赖任何的同步机制（syncronized ,lock），有几种方式能实现多个线程共享变量之间的happens-before方式
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