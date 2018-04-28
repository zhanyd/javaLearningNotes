package zhan.foundation.lesson07;

import java.util.concurrent.SynchronousQueue;

public class Exercises01 {

	public static void main(String[] args) throws InterruptedException {
		SynchronousQueue<String> queue=new SynchronousQueue();
		/*if(queue.offer("S1"))
		{
			System.out.println("scucess");
		}else
		{
			System.out.println("faield");
		}*/
		queue.put("s1");
		System.out.println("put s1");
	}

}
