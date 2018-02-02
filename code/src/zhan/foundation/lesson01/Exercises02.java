package zhan.foundation.lesson01;

public class Exercises02 {

	public static void main(String[] args) {
		int a=-1024;
		
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(a>>1));
		System.out.println(Integer.toBinaryString(a>>>1));
		
		System.out.println("a>>1: " + (a>>1));
		System.out.println("a>>>1: " + (a>>>1));
		
		System.out.println(Integer.toBinaryString(1 << 13));
		System.out.println(1 << 13);
	}

}
