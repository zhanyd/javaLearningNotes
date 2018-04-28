package zhan.foundation.lesson07;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Exercises0101 {
	public static void main(String[] args){
		SynchronousQueue<Object> queue = new SynchronousQueue<Object>();
		
		List<Thread> threadListCustomer = IntStream.range(0,5)
				.mapToObj(t->{
					return new Thread(()->{
						System.out.println(Thread.currentThread().getName() + " take begin");
						Object item = null;
						try {
							while((item = queue.take()) != null){
								System.out.println(Thread.currentThread().getName() + " take " +  item.toString() + "item");
								System.out.println(Thread.currentThread().getName() + " take end");
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				})
				.collect(Collectors.toList());
		
		threadListCustomer.forEach(t->t.start());
		
		
		List<Thread> threadListProductor = IntStream.range(0,5)
				.mapToObj(t->{
					return new Thread(()->{
						System.out.println(Thread.currentThread().getName() + " offer begin");
						Object item = new Object();
						while(queue.offer(item)){
							System.out.println(Thread.currentThread().getName() + " offer " +  item.toString() + "item");
							System.out.println(Thread.currentThread().getName() + " offer end");
						}
					});
				})
				.collect(Collectors.toList());
		
		threadListProductor.forEach(t->t.start());
		
	}
	
}
