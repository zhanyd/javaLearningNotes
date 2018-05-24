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
					iter.remove();
				}
				/*else if(){
					
				}*/
			}
		}
		

	}

}
