package zhan.foundation.lesson11.nio2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;


public class MyNIORector extends Thread{

	final Selector selector;
	final ExecutorService executor;
	
	public MyNIORector(ExecutorService executorService) throws IOException{
		selector = Selector.open();
		this.executor = executorService;
	}
	
	public void registerNewClient(SocketChannel socketChannel) throws IOException{
		System.out.println(" registered by actor " + this.getName());
		new TelnetIOHandler(selector,socketChannel);
	}
	
	public void run(){
		while(true){
			Set<SelectionKey> selectedKeys = null;
			try{
				selector.select(500);
				//selector.select();
				selectedKeys = selector.selectedKeys();
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			
			for(SelectionKey selectedKey : selectedKeys){
				IOHandler ioHandler = (IOHandler)selectedKey.attachment();
				this.executor.execute(ioHandler);
			}
			selectedKeys.clear();
		}
	}
	
}
