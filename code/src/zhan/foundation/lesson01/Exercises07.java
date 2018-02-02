package zhan.foundation.lesson01;

import java.util.Random;

public class Exercises07 {
	public static void main(String[] args) {
		getTopMaopao();
		getTopQuick();
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
	
	/**
	 * 冒泡排序
	 */
	public static void getTopMaopao(){
		long startTime = System.currentTimeMillis();
		Random random = new Random();
		int arrayLength = 10000;
		Salary[] salaryArray = new Salary[arrayLength];
		for(int i = 0; i < arrayLength; i++){
			salaryArray[i] = new Salary(getRomdomString(5),random.nextInt(950000) + 50000,random.nextInt(100000));
		}
		
		salaryArray = maopaoSalary(salaryArray);
		
		for(int i = salaryArray.length-1; i >= salaryArray.length-10; i--){
			System.out.println(salaryArray[i]);
		}
		
		System.out.println("用时：" + (System.currentTimeMillis() - startTime));
	}
	
	
	/**
	 * 快速排序
	 */
	public static void getTopQuick(){
		long startTime = System.currentTimeMillis();
		Random random = new Random();
		int arrayLength = 10000;
		Salary[] salaryArray = new Salary[arrayLength];
		for(int i = 0; i < arrayLength; i++){
			salaryArray[i] = new Salary(getRomdomString(5),random.nextInt(950000) + 50000,random.nextInt(100000));
		}
		
		quick(salaryArray,0,salaryArray.length-1);
		
		for(int i = 0; i < 10; i++){
			System.out.println(salaryArray[i]);
		}
		
		System.out.println("用时：" + (System.currentTimeMillis() - startTime));
	}
	
	
	
	 public static Salary[] maopaoSalary(Salary[] arry){
	        boolean noswap = true;
	        Salary temp;
	        for(int i=0; i<arry.length - 1; i++){
	            noswap = true;
	            for(int j=0; j<arry.length - 1 - i; j++){
	                if((arry[j].getBaseSalary()*13 + arry[j].getBonus()) <= (arry[j+1].getBaseSalary()*13 + arry[j+1].getBonus())){
	                    temp = arry[j];
	                    arry[j] = arry[j+1];
	                    arry[j+1] = temp;
	                    noswap = false;
	                }

	            }
	            if(noswap){
	                break;
	            }
	        }

	        return arry;
	    }
	 
	 
	 
	    public static void quick(Salary[] list,int low,int high){
	        if(low < high){
	            int middle = getMiddle(list,low,high);
	            quick(list,low,middle-1);
	            quick(list,middle+1,high);
	        }
	    }
	    
	    public static int getMiddle(Salary[] list,int low,int high){
	    	Salary tmp = list[low];
	        while(low < high){
	            while(low < high && (list[high].getBaseSalary()*13 + list[high].getBonus()) >= (tmp.getBaseSalary()*13 + tmp.getBonus())){
	                high--;
	            }
	            list[low] = list[high];

	            while(low < high && (list[low].getBaseSalary()*13 + list[low].getBonus()) < (tmp.getBaseSalary()*13 + tmp.getBonus())){
	                low++;
	            }
	           list[high] = list[low];
	        }
	        list[low] = tmp;
	        return low;
	    }
}
