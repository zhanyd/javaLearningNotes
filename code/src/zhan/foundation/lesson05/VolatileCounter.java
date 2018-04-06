package zhan.foundation.lesson05;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class VolatileCounter extends AbstractMyCounter{

    private volatile long value = 0;

    public void incr(){
        value++;
    }

    public long getCurValue(){
        return value;
    }
}
