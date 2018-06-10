# 习题解答

## 1 实现一个简单的多线程能力的DirectByteBufferPool，里面存放DirectByteBuffer，结合TreeSet这种可以范围查询的数据结构，实现任意大小的ByteBuffer的分配复用能力，比如需要一个1024大小的ByteBuffer，则可以返回大于1024的某个最小的空闲ByteBuffer

```
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
```

## 2尝试采用FileChannel的transfer方法，完成大文件的传输（仅限于比较大的文本文件，模拟生成100M的大文件， 因为二进制文件需要Base 64编码，无法用transfer），即新增一个命令 download xxxx
```
package zhan.foundation.lesson10;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Base64;

public class IOHandler implements Runnable{

	private final SelectionKey selectionKey;
	private final SocketChannel socketChannel;
	private ByteBuffer writeBuffer;
	private ByteBuffer readBuffer;
	private int lastMessagePos;
	
	public IOHandler(final Selector selector,SocketChannel socketChannel) throws IOException {
		socketChannel.configureBlocking(false);
		this.socketChannel = socketChannel;
		selectionKey = socketChannel.register(selector, 0);
		selectionKey.interestOps(SelectionKey.OP_READ);
		writeBuffer = ByteBuffer.allocateDirect(100);
		readBuffer = ByteBuffer.allocateDirect(10);
		//绑定会话
		selectionKey.attach(this);
		writeBuffer.put("Welcome Leader.us Power Man Java Course ...\r\nTelnet>".getBytes());
		writeBuffer.flip();
		doWriteData();
		
	}
	
	public void run(){
		try{
			if(selectionKey.isReadable()){
				doReadData();
			}else if(selectionKey.isWritable()){
				doWriteData();
			}
		}catch(Exception e){
			e.printStackTrace();
			selectionKey.cancel();
			try{
				socketChannel.close();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
	}
	
	private void doWriteData() throws IOException{
		writeToChannel();
	}
	
	private void writeToChannel() throws IOException{
		int writed = socketChannel.write(writeBuffer);
		System.out.println("writed " + writed);
		if(writeBuffer.hasRemaining()){
			System.out.println("writed "+writed+" not write finished  so bind to session ,remains " + writeBuffer.remaining());
			selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
		}else{
			System.out.println("block write finished");
			writeBuffer.clear();
			selectionKey.interestOps(selectionKey.interestOps() &~ SelectionKey.OP_WRITE | SelectionKey.OP_READ);
			
		}
	}
	
	
	private void doReadData() throws IOException{
		System.out.println("readed ");
		socketChannel.read(readBuffer);
		int readEndPos = readBuffer.position();
		String readedLine = null;
		for(int i = lastMessagePos; i < readEndPos; i++){
			System.out.println(readBuffer.get(i));
			if(readBuffer.get(i) == 13){
				byte[] lineBytes = new byte[i - lastMessagePos];
				readBuffer.position(lastMessagePos);
				readBuffer.get(lineBytes);
				lastMessagePos = i;
				readedLine = new String(lineBytes);
				System.out.println("received line ,lenth:"+readedLine.length()+" value "+readedLine);
			    break;
			}
		}
		
		if(readedLine != null){
		    //不能取消读事件，取消了就没办法继续读了  added by zhan
            //取消读事件关注，因为要应答数据
            //selectionKey.interestOps(selectionKey.interestOps() &~SelectionKey.OP_READ);
			//处理指令
			processCommand(readedLine);
		}
		
		if(readBuffer.position() > readBuffer.capacity()/2){
			//清理前面读过的废弃空间
			System.out.println(" rewind read byte buffer ,get more space  "+readBuffer.position());
			//limit 不应该设置成readBuffer.position()，应为在for的if语句里position只是到上一个报文的结束，结束符后的报文会丢失。。。
            //readBuffer.limit(readBuffer.position());
			readBuffer.limit(readEndPos);
			readBuffer.position(lastMessagePos);
			readBuffer.compact();
			lastMessagePos = 0;
		}
	}
	
	
	private void processCommand(String readedLine) throws IOException{
		if(readedLine.startsWith("dir")){
			readedLine = "cmd /c " + readedLine;
			String result = LocalCmandUtil.callCmdAndgetResult(readedLine);
			writeBuffer.put(result.getBytes("GBK"));
			writeBuffer.put("\r\nTelnet>".getBytes());
			writeBuffer.flip();
			writeToChannel();
		}else if(readedLine.startsWith("copy")){
			RandomAccessFile fromFile = new RandomAccessFile("F://origin.txt","rw");
			FileChannel fromChannel = fromFile.getChannel();
			
			RandomAccessFile toFile = new RandomAccessFile("F://tatget.txt","rw");
			FileChannel toChannel = toFile.getChannel();
			
			int position = 0;
			long count = fromChannel.size();
			
			fromChannel.transferTo(position, count, toChannel);
			
			fromChannel.close();
			fromFile.close();
			toChannel.close();
			toFile.close();
		}else if(readedLine.startsWith("download")){
			RandomAccessFile fromFile = new RandomAccessFile("F://Sybase_ase157_winx86.zip","rw");
			FileChannel fromChannel = fromFile.getChannel();
			Base64.Encoder encoder = Base64.getEncoder();
			ByteBuffer encodBuffer;
			long size = fromChannel.size();
			long postion = 0;
			while(postion < size){
				int readnum = fromChannel.read(writeBuffer);
				postion += readnum;
				writeBuffer.flip();
				encodBuffer = encoder.encode(writeBuffer);
				socketChannel.write(encodBuffer);
				writeBuffer.clear();
			}
			fromChannel.close();
			fromFile.close();
		}
		else{
			for(int i = 0; i < writeBuffer.capacity() - 10; i++){
				writeBuffer.put((byte)('a' + i % 25));
			}
			writeBuffer.put("\r\nTelnet>".getBytes());
			writeBuffer.flip();
			writeToChannel();
		}
	}
	
	
}

```