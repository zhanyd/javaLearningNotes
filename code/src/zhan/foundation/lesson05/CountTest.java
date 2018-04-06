package zhan.foundation.lesson05;

/**
 * Created by Administrator on 2018/4/6 0006.
 */
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
