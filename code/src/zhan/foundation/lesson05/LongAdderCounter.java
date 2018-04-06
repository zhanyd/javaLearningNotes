package zhan.foundation.lesson05;

import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class LongAdderCounter extends AbstractMyCounter{

    private LongAdder value = new LongAdder();

    public void incr(){
        value.increment();
    }

    public long getCurValue(){
        return value.sum();
    }
}
