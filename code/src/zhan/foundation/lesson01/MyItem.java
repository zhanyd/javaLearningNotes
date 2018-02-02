package zhan.foundation.lesson01;

public class MyItem {
	private byte type;
	private byte color;
	private byte price;
	
	public MyItem(){
		
	}
	
	public MyItem(byte type,byte color,byte price){
		this.setType(type);
		this.setColor(color);
		this.setPrice(price);
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getColor() {
		return color;
	}

	public void setColor(byte color) {
		this.color = color;
	}

	public byte getPrice() {
		return price;
	}

	public void setPrice(byte price) {
		this.price = price;
	}
	
	
}
