package zhan.foundation.lesson05;

/**
 * Created by Administrator on 2018/4/6 0006.
 */
public class NormalCounter extends AbstractMyCounter{

    private long value = 0;

    public void incr(){
        value++;
    }
    public long getCurValue(){
        return value;
    }
}
