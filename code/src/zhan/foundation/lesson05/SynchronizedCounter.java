package zhan.foundation.lesson05;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class SynchronizedCounter extends AbstractMyCounter {

    private long value = 0;

    public synchronized void incr() {
        value++;
    }

    public long getCurValue(){
        return value;
    }
}
