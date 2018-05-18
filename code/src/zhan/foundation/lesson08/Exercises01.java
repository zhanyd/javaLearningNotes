package zhan.foundation.lesson08;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Exercises01 {

	public static void main(String[] args) {
		
		try{
			ServerSocket serverSocket = new ServerSocket(8080);
			while(true){
				System.out.println("before serverSocket.accept()");
				Socket socket = serverSocket.accept();
				System.out.println("Request: " + socket.toString() + " cooonected");
				LineNumberReader in = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
				String lineInput;
				String requestPage = null;
				while((lineInput = in.readLine()) != null){
					System.out.println(lineInput);
					if(in.getLineNumber() == 1){
						requestPage = lineInput.substring(lineInput.indexOf("/") + 1,lineInput.lastIndexOf(" "));
						System.out.println("request page :" + requestPage);
					}else{
						if(lineInput.isEmpty()){
							System.out.println("header finished");
							doResponseGet(requestPage,socket);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	private static void doResponseGet(String requestPage, Socket socket) throws IOException{
		final String WEB_ROOT = "f:";
		File theFile = new File(WEB_ROOT,requestPage);
		OutputStream out = socket.getOutputStream();
		if(theFile.exists()){
			//从服务器根目录下找到用户请求的文件并发送回浏览器
			InputStream fileIn = new FileInputStream(theFile);
			byte[] buf = new byte[fileIn.available()];
			fileIn.read(buf);
			fileIn.close();
			
			String msg = new String(buf,"GBK");
			String response = "HTTP/1.1 200 ok\r\n";
			response += "Server: zhanyd Server/0.1 \r\n";
			response += "\r\n";
			response += msg;
			out.write(response.getBytes());
			out.flush();
			//socket.close();
			System.out.println("return file " + requestPage);
		}else{
			String msg = "Hello you connected me...";
			String response = "HTTP/1.1 200 ok\r\n";
			response += "Server: zhanyd Server/0.1 \r\n";
			response += "Content-Length: " + (msg.length()-5) + "\r\n";
			response += "\r\n";
			response += msg;
			out.write(response.getBytes());
			out.flush();
			System.out.println("Hello you connected me...");
		}
		
		//out.close();
	}

}
