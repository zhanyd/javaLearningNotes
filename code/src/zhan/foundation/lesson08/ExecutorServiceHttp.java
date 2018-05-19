package zhan.foundation.lesson08;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
public class ExecutorServiceHttp {

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ServerSocket serverSocket = new ServerSocket(8080);
        executorService.execute(new ServiceHttp(serverSocket));

    }


    public static class ServiceHttp implements Runnable {
        ServerSocket serverSocket;

        public ServiceHttp(ServerSocket serverSocket){
            this.serverSocket = serverSocket;
        }

        @Override
        public void run(){
            try{
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " before serverSocket.accept()");
                    Socket socket = serverSocket.accept();
                    System.out.println(Thread.currentThread().getName() + " Request: " + socket.toString() + " cooonected");
                    LineNumberReader in = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
                    String lineInput;
                    String requestPage = null;
                    while ((lineInput = in.readLine()) != null) {
                        System.out.println(lineInput);
                        if (in.getLineNumber() == 1) {
                            requestPage = lineInput.substring(lineInput.indexOf("/") + 1, lineInput.lastIndexOf(" "));
                            System.out.println("request page :" + requestPage);
                        } else {
                            if (lineInput.isEmpty()) {
                                System.out.println(Thread.currentThread().getName() + " header finished");
                                doResponseGet(requestPage, socket);
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private static void doResponseGet(String requestPage, Socket socket) throws IOException {
        final String WEB_ROOT = "f:";
        File theFile = new File(WEB_ROOT,requestPage);
        OutputStream out = socket.getOutputStream();
        if(theFile.exists()){
            //从服务器根目录下找到用户请求的文件并发送回浏览器
            InputStream fileIn = new FileInputStream(theFile);
            byte[] buf = new byte[fileIn.available()];
            fileIn.read(buf);
            fileIn.close();

            String msg = new String(buf,"gb2312");
            String response = "HTTP/1.1 200 ok\r\n";
            response += "Server: zhanyd Server/0.1 \r\n";
            response += "Content-Length: " + (msg.length()) + "\r\n";
            response += "\r\n";
            response += msg;
            out.write(response.getBytes());
            out.flush();
            //socket.close();
            System.out.println(Thread.currentThread().getName() + " return file " + requestPage);
        }else{
            String msg = "Hello you connected me...";
            String response = "HTTP/1.1 200 ok\r\n";
            response += "Server: zhanyd Server/0.1 \r\n";
            response += "Content-Length: " + (msg.length()) + "\r\n";
            response += "\r\n";
            response += msg;
            out.write(response.getBytes());
            out.flush();
            System.out.println(Thread.currentThread().getName() + " Hello you connected me...");
        }

        //out.close();
    }

}
