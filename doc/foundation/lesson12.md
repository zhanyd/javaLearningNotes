# 习题解答

## 1.MyNIORector 这里的selector.select(500); 如果改为selector.select()，会是什么结果，结合socketChannel.register(selector, SelectionKey.OP_READ)这里的JavaDoc，研究说明问题所在

答：先运行语句selector.select(500);
	后运行注册感兴趣事件语句selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
	![](img/lesson12-001.png)

	如果改为selector.select()，程序在select()处阻塞，注册感兴趣事件无法刷新到selector中，程序会一直阻塞；
	如果是selector.select(500)，每过500毫秒会刷新感兴趣事件，读事件能被selector感知。


## 2.NIOAcceptor 这里的serverSocketChannel改为非阻塞的写法。

答：原来阻塞的写法是没有selector的，如果要改成非阻塞的写法，需要增加selector。
```
package zhan.foundation.lesson11.nio2NoBlock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class NIOAcceptor extends Thread{

	private final ServerSocketChannel serverSocketChannel;
	private final MyNIORector[] reactors;
	private Selector selector;
	
	public NIOAcceptor(int bindPort,MyNIORector[] reactors) throws IOException{
		this.reactors = reactors;
		serverSocketChannel = ServerSocketChannel.open();
		//serverSocketChannel.configureBlocking(true);
		serverSocketChannel.configureBlocking(false);
		InetSocketAddress address = new InetSocketAddress(bindPort);
		serverSocketChannel.socket().bind(address);
		System.out.println(Thread.currentThread().getName() + "started at " + address);
		
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
	}
	
	public void run(){
		while(true){
			try{
				selector.select();
				Set<SelectionKey> selectionKeySet = selector.selectedKeys();
				for(SelectionKey key : selectionKeySet){
					SocketChannel socketChannel = serverSocketChannel.accept();
					System.out.println("Connection Accepted "+socketChannel.getRemoteAddress());
					int nextReator = ThreadLocalRandom.current().nextInt(0,reactors.length);
					reactors[nextReator].registerNewClient(socketChannel);
					selectionKeySet.remove(key);
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
```

## 3.用NIO实现一个NIOAcceptor，即客户端非阻塞的方式连接服务器（课程里的Telnet Server）
      另外：
       用户连接上以后，输出命令选择界面：
        1: find keyword in files
        2: quit

       用户输入 "1  d:\mydata   java"表示执行第一个命令，在 d:\mydata目录下，寻找包括java关键字的统计(多线程并发查询方式，可以Forkjoin)	
       用户输入  2则 输出 Bye My Power boy ..断开连接


```
private void processCommand(String readedLine) throws IOException, InterruptedException, ExecutionException {
		byte[] data=null;
		if (readedLine.startsWith("dir")) {
			readedLine = "cmd  /c " + readedLine;
			data = (LocalCmandUtil.callCmdAndgetResult(readedLine)+"\r\nTelnet>").getBytes("GBK");
			this.writeData(data);
		}else if(readedLine.startsWith("1")){ 
			String[] strCommand = readedLine.split(" ");
			if(strCommand.length != 3){
				data = "输入参数有误".getBytes("GBK");
			}else {
				File[] files = new File(strCommand[1]).listFiles();
				ForkJoinPool forkJoinPool = new ForkJoinPool();
				Exercises0202 task = new Exercises0202(0,files.length - 1,files,strCommand[2]);
				Future<Integer> allSum = forkJoinPool.submit(task);

				System.out.println("strCommand : ");
				Arrays.stream(strCommand).forEach(f->System.out.print(" " + f));
				
				System.out.println(strCommand[2] + " 总共出现 " + allSum.get() + "次");
				data = (strCommand[2] + " 总共出现 " + allSum.get() + "次 \r\nTelnet>").getBytes("GBK");
				
			}
			this.writeData(data);
		}else if(readedLine.equals("2")){
			data = new byte[100];
			ByteBuffer tempBuf = ByteBuffer.wrap(data);
			tempBuf.put("Bye My Power boy ..".getBytes());
			this.writeData(data);
			this.socketChannel.close();
		}
		else {
			data=new byte[100];
			ByteBuffer tempBuf=ByteBuffer.wrap(data);
			for (int i = 0; i < tempBuf.capacity() - 10; i++) {
				tempBuf.put((byte) ('a' + i % 25));
			}
			tempBuf.put("\r\nTelnet>".getBytes());
			this.writeData(data);
		}

	}
```
