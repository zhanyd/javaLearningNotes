package zhan.foundation.lesson10.pool;

import java.nio.ByteBuffer;
import java.util.TreeSet;

public class DirectByteBufferPool {

	private static ByteBuffer largeBuffer;
	private static TreeSet<ByteBuffer> bufferSet;
	
	public static void main(String[] args) {
		init(100,10000);
		
		ByteBuffer byteBuffer = allocateBuffer(200);
		System.out.println("分配的 buffer capacity = " + byteBuffer.capacity());
	}
	
	/**
	 * 分配ByteBuffer池
	 * @param baseSize
	 * @param bufferLength
	 */
	public static void init(int baseSize,int bufferLength){
		bufferSet = new TreeSet<ByteBuffer>();
		largeBuffer = ByteBuffer.allocateDirect(bufferLength);
		
		int position = 0;
		int multiple = 1;
		while(bufferLength >= position + baseSize * multiple){
			largeBuffer.position(position);
			largeBuffer.limit(position + baseSize * multiple);
			bufferSet.add(largeBuffer.slice());
			position = largeBuffer.limit();
			//按2倍的大小增长
			multiple = multiple * 2;
		}
		
		bufferSet.stream().forEach(b->System.out.println("capacity = " + b.capacity()));
		System.out.println("还剩  " + (largeBuffer.capacity() - largeBuffer.limit()) + " 空间未分配");
	}
	
	
	/**
	 * 分配byteBuffer
	 * @param bufferSize
	 * @return
	 */
	public static ByteBuffer allocateBuffer(int bufferSize){
		ByteBuffer requiredBuffer = ByteBuffer.allocateDirect(bufferSize);
		ByteBuffer returnBuffer = bufferSet.ceiling(requiredBuffer);
		return returnBuffer;
	}
}
