package zhan.foundation.lesson01;
import java.util.Arrays;
import java.util.Random;

public class Exercises0502 {
	
	public static void main(String[] args) {
		
		ByteStore byteStore = new ByteStore();
		Random random = new Random();
		for(int i = 0; i < 1000; i++){
			byteStore.putMyItem(i, new MyItem((byte)random.nextInt(127),(byte)random.nextInt(127),(byte)random.nextInt(127)));
		}
		
		quick(byteStore.storeByteArry,0,byteStore.storeByteArry.length - 1);
		
		for(int i = 0; i < 3000; i++){
			System.out.println(byteStore.storeByteArry[i]);
		}

	}
	
	
	//快速排序
    public static void quick(Byte[] storeByteArry,int low,int high){
        if(low < high){
            int middle = getMiddle(storeByteArry,low,high);
            quick(storeByteArry,low,middle-1);
            quick(storeByteArry,middle+1,high);
        }
    }
    
    public static int getMiddle(Byte[] storeByteArry,int low,int high){
        byte tmp = storeByteArry[low];
        while(low < high){
            while(low < high && storeByteArry[high] >= tmp){
                high = high - 1;
            }
            storeByteArry[low] = storeByteArry[high];

            while(low < high && storeByteArry[low] < tmp){
                low = low + 1;
            }
            storeByteArry[high] = storeByteArry[low];
        }
        storeByteArry[low] = tmp;
        return low;
    }

}
