package zhan.foundation.lesson02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Exercises02_2 {
	public static void main(String[] args) throws IOException {
		int a = 10240;
		FileOutputStream fileOutput = new FileOutputStream("e://endian.txt");
		fileOutput.write(getByteLitEndian(a));
		FileInputStream fileInput = new FileInputStream("e://endian.txt");
		byte[] buffer = new byte[4];
		int count = 0;
		while((count = fileInput.read(buffer)) != -1){
			System.out.println(getIntLitEndian(buffer,0));
		}
		
		fileOutput.close();
		fileInput.close();
	}
	
	/**
	 * 获取大头byte数组
	 * @param a
	 * @return
	 */
	public static byte[] getByteLitEndian(int a){
		return new byte[]{
				(byte)(a & 0xff),
				(byte)((a >> 8) & 0xff),
				(byte)((a >> 16) &0xff),
				(byte)((a >> 24) & 0xff)
		};
	}
	
	/**
	 * 获取大头int
	 * @param b
	 * @param off
	 * @return
	 */
	public static int getIntLitEndian(byte[] b,int off){
		return (b[off] & 0xff) +
				((b[off + 1] & 0xff) << 8) +
				((b[off + 2] & 0xff) << 16) +
				((b[off + 3] & 0xff) << 24);
	}
}
