package zhan.foundation.lesson09;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioExample {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		InetSocketAddress address = new InetSocketAddress(9000);
		serverSocketChannel.socket().bind(address);
		System.out.println("started at " + address);
		
		SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true){
			int selectNum = selector.select();
			System.out.println("Selected Number is : " + selectNum);
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			while(iter.hasNext()){
				SelectionKey selectionKey = iter.next();
				if((selectionKey.readyOps() & selectionKey.OP_ACCEPT) == selectionKey.OP_ACCEPT){
					System.out.println("selectionKey.readyOps() = " + selectionKey.readyOps());
					ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
					SocketChannel socketChannel = serverChannel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, selectionKey.OP_READ);
					socketChannel.write(ByteBuffer.wrap("welcome to zhan server...\r\n".getBytes()));
				}
				else if((selectionKey.readyOps() & selectionKey.OP_READ) == selectionKey.OP_READ){
					System.out.println("selectionKey.readyOps() = " + selectionKey.readyOps());
					System.out.println("---------received read event-----------");
					SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
					ByteBuffer buffer = ByteBuffer.allocate(100);
					socketChannel.read(buffer);
					
					buffer = (ByteBuffer)selectionKey.attachment();
					if(buffer == null || !buffer.hasRemaining()){
						int writeBufferSize = socketChannel.socket().getSendBufferSize();
						System.out.println("send buffer size : " + writeBufferSize);
						int sendDataSize = writeBufferSize * 50 + 2;
						buffer = ByteBuffer.allocate(sendDataSize);
						for(int i = 0; i < buffer.capacity() - 2; i++){
							buffer.put((byte)('a' + i%25));
						}
						buffer.put("\r\n".getBytes());
						buffer.flip();
						System.out.println("send another huge block data : " + sendDataSize);
						
					}
					
					int writed = socketChannel.write(buffer);
					System.out.println("writed " + writed);
					if(buffer.hasRemaining()){
						System.out.println("not write finished,remains " + buffer.remaining());
						buffer = buffer.compact();
						selectionKey.attach(buffer);
						selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
					}else{
						System.out.println("block write finished");
						selectionKey.attach(null);
						selectionKey.interestOps(selectionKey.interestOps() &~ SelectionKey.OP_WRITE);
					}
				}else if((selectionKey.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE){
					System.out.println("--------received write event-----------");
					ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
					SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
					if(buffer != null){
						buffer.flip();
						int writed = socketChannel.write(buffer);
						System.out.println("writed " + writed);
						if(buffer.hasRemaining()){
							System.out.println(" not write finished,bind to session,remains " + buffer.remaining());
							buffer = buffer.compact();
							selectionKey.attach(buffer);
							selectionKey.interestOps(selectionKey.interestOps() | selectionKey.OP_WRITE);
						}else{
							System.out.println("block write finished");
							selectionKey.attach(null);
							selectionKey.interestOps(selectionKey.interestOps() &~ selectionKey.OP_WRITE);
						}
					}else{
						System.out.println("block write finished");
						selectionKey.attach(null);
						selectionKey.interestOps(selectionKey.interestOps() &~ selectionKey.OP_WRITE);
					}
				}
				
				iter.remove();
			}
		}
		

	}

}
