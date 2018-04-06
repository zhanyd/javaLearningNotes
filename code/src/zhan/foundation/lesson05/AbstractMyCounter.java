package zhan.foundation.lesson05;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2018/4/6 0006.
 */
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

        threadList.forEach(t->t.start());

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
