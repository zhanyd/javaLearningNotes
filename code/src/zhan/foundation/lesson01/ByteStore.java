package zhan.foundation.lesson01;

public class ByteStore {
	
	public Byte[] storeByteArry = new Byte[3000];
	
	public void putMyItem(int index,MyItem item){
		storeByteArry[index*3] = item.getType();
		storeByteArry[index*3 + 1] = item.getColor();
		storeByteArry[index*3 + 2] = item.getPrice();
	}
	
	public MyItem getMyItem(int index){
		return new MyItem(storeByteArry[index*3],storeByteArry[index*3 + 1],storeByteArry[index*3 + 2]);
	}
}
