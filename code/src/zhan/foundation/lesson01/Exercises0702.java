package zhan.foundation.lesson01;

public class Exercises0702 {
	
	private static int[] storeByteArry = new int[100];
	
	public static void main(String[] args) {
		MyItem myItem = new MyItem((byte)2,(byte)4,(byte)5);
		putMyItem(0,myItem);
		
		MyItem myItem2 = getMyItem(0);
		System.out.println(myItem2.getType() + " " + myItem2.getColor() + " " + myItem2.getPrice());

	}
	
	
	public static void putMyItem(int index,MyItem item){
		storeByteArry[index] = (item.getType()) + (item.getColor() << 8) + (item.getPrice() << 16);
		System.out.println(Integer.toBinaryString(storeByteArry[index]));
	}
	
	
	public static MyItem getMyItem(int index){
		MyItem myItem = new MyItem();
		myItem.setType((byte)(storeByteArry[index] & 0xff));
		myItem.setColor((byte)(storeByteArry[index] >> 8 & 0xff));
		myItem.setPrice((byte)(storeByteArry[index] >> 16 & 0xff));
		
		return myItem;
	}
}
