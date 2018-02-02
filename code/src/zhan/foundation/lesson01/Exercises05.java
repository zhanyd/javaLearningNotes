package zhan.foundation.lesson01;

public class Exercises05 {
	
	public static void main(String[] args) {
		MyItem myItem1 = new MyItem((byte)0,(byte)1,(byte)2);
		MyItem myItem2 = new MyItem((byte)3,(byte)4,(byte)5);
		MyItem myItem3 = new MyItem((byte)6,(byte)7,(byte)8);
		
		ByteStore byteStore = new ByteStore();
		byteStore.putMyItem(0, myItem1);
		byteStore.putMyItem(1, myItem2);
		byteStore.putMyItem(2, myItem3);
		
		System.out.println(myItem1.equals(byteStore.getMyItem(0)));
		System.out.println(myItem2.equals(byteStore.getMyItem(1)));
		System.out.println(myItem3.equals(byteStore.getMyItem(2)));

	}
}
