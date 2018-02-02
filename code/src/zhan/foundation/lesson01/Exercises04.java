package zhan.foundation.lesson01;

import java.util.Arrays;
import java.util.Random;


public class Exercises04 {

	public static void main(String[] args) {
		System.out.println("getTopSalaryNormal():");
		getTopSalaryNormal();
		System.out.println("getTopSalaryStream():");
		getTopSalaryStream();
	}
	
	/**
	 * 普通方法
	 */
	public static void getTopSalaryNormal(){
		long startTime = System.currentTimeMillis();
		Random random = new Random();
		int arrayLength = 10000;
		Salary[] salaryArray = new Salary[arrayLength];
		for(int i = 0; i < arrayLength; i++){
			salaryArray[i] = new Salary(getRomdomString(5),random.nextInt(950000) + 50000,random.nextInt(100000));
		}
		
		Arrays.sort(salaryArray);
		
		for(int i = 0; i < 10; i++){
			System.out.println(salaryArray[i]);
		}
		
		System.out.println("用时：" + (System.currentTimeMillis() - startTime));
	}
	
	/**
	 * stream()方法
	 */
	public static void getTopSalaryStream(){
		long startTime = System.currentTimeMillis();
		Random random = new Random();
		int arrayLength = 10000;
		Salary[] salaryArray = new Salary[arrayLength];
		for(int i = 0; i < arrayLength; i++){
			salaryArray[i] = new Salary(getRomdomString(5),random.nextInt(950000) + 50000,random.nextInt(100000));
		}
		
		Arrays.stream(salaryArray).sorted().limit(10).forEach(System.out::println);
		
		System.out.println("用时：" + (System.currentTimeMillis() - startTime));
	}
	
	public static String getRomdomString(int length){
		String baseStr = "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer strBuff = new StringBuffer();
		for(int i = 0; i < length; i++){
			strBuff.append(baseStr.charAt(random.nextInt(baseStr.length())));
		}
		return strBuff.toString();
	}

}
