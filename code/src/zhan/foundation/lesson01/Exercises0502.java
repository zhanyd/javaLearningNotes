package zhan.foundation.lesson01;
import java.util.Random;

public class Exercises0502 {
	
public static void main(String[] args) {
		
		ByteStore byteStore = new ByteStore();
		Random random = new Random();
		for(int i = 0; i < 1000; i++){
			byteStore.putMyItem(i, new MyItem((byte)random.nextInt(127),(byte)random.nextInt(127),(byte)random.nextInt(127)));
		}
		
		
		quick(byteStore.storeByteArry,0,byteStore.storeByteArry.length - 3);
		
		for(int i = 0; i < 1000; i++){
			System.out.println(byteStore.storeByteArry[i*3] + " " + byteStore.storeByteArry[i*3+1] + " " + byteStore.storeByteArry[i*3+2]);
		}
	}
	
	
	//快速排序
    public static void quick(Byte[] storeByteArry,int low,int high){
        if(low < high){
            int middle = getMiddle(storeByteArry,low,high);
            quick(storeByteArry,low,middle-3);
            quick(storeByteArry,middle+3,high);
        }
    }
    
    public static int getMiddle(Byte[] storeByteArry,int low,int high){
        byte tmp1 = storeByteArry[low];
        byte tmp2 = storeByteArry[low + 1];
        byte tmp3 = storeByteArry[low + 2];
        while(low < high){
            while(low < high && storeByteArry[high + 2] >= tmp3){
                high = high - 3;
            }
            storeByteArry[low] = storeByteArry[high];
            storeByteArry[low + 1] = storeByteArry[high + 1];
            storeByteArry[low + 2] = storeByteArry[high + 2];

            while(low < high && storeByteArry[low + 2] < tmp3){
                low = low + 3;
            }
            storeByteArry[high] = storeByteArry[low];
            storeByteArry[high + 1] = storeByteArry[low + 1];
            storeByteArry[high + 2] = storeByteArry[low + 2];
        }
        storeByteArry[low] = tmp1;
        storeByteArry[low + 1] = tmp2;
        storeByteArry[low + 2] = tmp3;
        return low;
    }

}
