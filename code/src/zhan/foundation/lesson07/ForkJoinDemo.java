package zhan.foundation.lesson07;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2018/4/30 0030.
 */
public class ForkJoinDemo extends RecursiveTask<Integer> {

    static private int[] array;
    private int beg;
    private int end;

    public ForkJoinDemo(int[] array,int beg,int end){
        super();
        this.array = array;
        this.beg = beg;
        this.end = end;
    }

    @Override
    protected Integer compute(){
        int result = 0;
        if(end - beg > 1){
            int mid = (end + beg) / 2;
            ForkJoinDemo f1 = new ForkJoinDemo(array,beg,mid);
            ForkJoinDemo f2 = new ForkJoinDemo(array,mid,end);
            invokeAll(f1,f2);
           /* f1.fork();
            f2.fork();*/
            try {
                result = f1.join() + f2.join();
                System.out.println("f1.join() = " + f1.join() + ",f2.join() = " + f2.join() + ",result = " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            result = count(array[beg]);
            System.out.println("result = " + result);
        }
        return  result;
    }

    //统计一个整数中出现了几个1
    public int count(int num){
        String strNum = String.valueOf(num);
        System.out.println("strNum = " + strNum);
        return strNum.length() - strNum.replace("1","").length();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random random = new Random();
        array = new int[4];
        IntStream.range(0,4).forEach(i->array[i] = random.nextInt(10000));
        System.out.println("array.length = " + array.length);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinDemo demo = new ForkJoinDemo(array,0,array.length);

        Future<Integer> result = pool.submit(demo);
        System.out.println(result.get());

       /* Integer res = pool.invoke(demo);
        System.out.println(res);*/
    }
}
