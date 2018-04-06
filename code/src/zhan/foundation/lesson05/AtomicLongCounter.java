package zhan.foundation.lesson05;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class AtomicLongCounter extends AbstractMyCounter{

    private AtomicLong value = new AtomicLong();

    public void incr(){
        value.getAndIncrement();
    }

    public long getCurValue(){
        return value.get();
    }
}
