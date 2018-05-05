package zhan.foundation.lesson07;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Exercises03 {

	private static List<Map<String, long[]>> salaryList = new ArrayList<>();
	 
	public static void main(String[] args) throws Exception {
		long endWriteTime = System.currentTimeMillis();
		readFromBufferedReaderStreamByForkJoin("f:\\salaryWriterChannel.txt");
		System.out.println("read cost : " + (System.currentTimeMillis() - endWriteTime));
	}
	
	
	 public static void readFromBufferedReaderStreamByForkJoin(String filename) throws Exception {
	        List<String> allLinesList = Files.readAllLines(Paths.get(filename));
	        int size = allLinesList.size();
	        ForkJoinProduces task = new ForkJoinProduces(allLinesList,0,size);
	        ForkJoinPool forkJoinPool = new ForkJoinPool();
	        forkJoinPool.submit(task);
	        task.join();

	        //合并分段读取的文件
	        salaryList.stream()
	                .reduce((map1,map2)->{
	                    map2.entrySet().stream()
	                            .forEach(map->{
	                                map1.merge(map.getKey(),map.getValue(),(k,v)->{
	                                    k[0] += v[0];
	                                    k[1] += v[1];
	                                    return k;
	                                });
	                            });
	                    return map1;
	                })
	                .ifPresent(s->s.entrySet().stream()
	                        .sorted((a,b)->Long.compare(b.getValue()[0],a.getValue()[0]))
	                        .limit(10)
	                        .forEach(f->System.out.printf("%s,%s,%s\n",f.getKey(),f.getValue()[0],f.getValue()[1]))
	                );

	    }




	    public static class ForkJoinProduces extends RecursiveAction {
	        List<String> list;
	        int start;
	        int end;
	        public ForkJoinProduces( List<String> list,int start,int end){
	            this.list = list;
	            this.start = start;
	            this.end = end;
	        }

	        @Override
	        protected void compute() {
	            if(end - start <= 1000_000){
	                getSalaryList(list,start,end);
	            }else{
	                    int middle = (start + end) / 2;
	                    ForkJoinProduces leftTask = new ForkJoinProduces(list,start,middle);
	                    ForkJoinProduces rightTask = new ForkJoinProduces(list,middle + 1,end);
	                    invokeAll(leftTask,rightTask);
	                }
	            }
	    }
	    
	    
	    /**
	     * 分段读取文件
	     * @param list
	     * @param start
	     * @param end
	     */
	    public static void getSalaryList(List<String> list,int start,int end){
	        salaryList.add(
	                list.stream()
	                .skip(start)
	                .limit(end - start)
	                .map(s->s.split(","))
	                //.filter(s->Integer.parseInt(s[1]) > 10000)
	                .collect(Collectors.groupingBy(s->s[0].substring(0,2),
	                        Collector.of(()->new long[2],
	                                (a,sa)->{
	                                    a[0] += Integer.parseInt(sa[1]) + Integer.parseInt(sa[2]);
	                                    a[1] += 1L;
	                                },
	                                (a,b)->{
	                                    a[0] += b[0];
	                                    a[1] += b[1];
	                                    return a;
	                                }
	                        )
	                        )
	                )
	        );
	    }

}
