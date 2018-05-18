package zhan.foundation.lesson07;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by Administrator on 2018/4/30 0030.
 */
public class ExecutorServiceDemo {

    static ExecutorService excutor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception{
       /* List<Future<Long>> futureList =  Files.list(new File("D:/Downloads").toPath())
                .filter(s->!s.toFile().isDirectory())
                .map(s->new Callable<Long>() {
                    public Long call() throws Exception{
                        System.out.println(s);
                        return Files.size(s);
                    }
                })
                .map(s->excutor.submit(s))
                .collect(Collectors.toList());
        Supplier<LongStream> streamSupplier = () -> futureList.stream().map(f->{
            try {
                return f.get();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }).mapToLong(val->(Long)val);

        streamSupplier.get().forEach(System.out::println);
        System.out.println("total:" + streamSupplier.get().sum());
        //excutor.shutdown();
        System.out.println("end");*/
    }


}
