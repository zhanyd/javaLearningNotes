package zhan.foundation.lesson04;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Exercises04 {

	public static void main(String[] args){
		//findOdd();
		range();
	}
	
	/**
	 * 查找不重复的奇数
	 */
	public static void findOdd(){
		Stream.of(1,4,65,7,4,2,7,8,9)
			.filter(f->f%2 == 1)
			.distinct()
			.forEach(System.out :: println);
	}
	
	public static void range(){
		IntStream.rangeClosed('0', '9')
			.forEach(c->System.out.println(c));
	}
	
}
