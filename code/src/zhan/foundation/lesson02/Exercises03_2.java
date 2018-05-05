package zhan.foundation.lesson02;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Exercises03_2 {

	public static void main(String[] args) throws Exception {
		long stratWriteTime = System.currentTimeMillis();
		//writeToFileOutputStream("f:\\salaryWriterChannel.txt");
		//writeToFileOutputStreamBuffer("f:\\salaryWriterChannel.txt");
		long endWriteTime = System.currentTimeMillis();
        System.out.println("write cost : " + (endWriteTime - stratWriteTime));
        readFromBufferedReader("f:\\salaryWriterChannel.txt");
        System.out.println("read cost : " + (System.currentTimeMillis() - endWriteTime));
        System.out.println("all cost : " + (System.currentTimeMillis() - stratWriteTime));

	}
	
	 /**
	  * 按字节写入文件
	  * @param filename
	  * @throws Exception
	  */
	 public static void writeToFileOutputStream(String filename) throws Exception{
		 try(OutputStream outputStream = new FileOutputStream(filename)){
	        	Random random = new Random();
	 	        int count = 10_000_000;
	 	        char[] name = new char[5];

	 	        String writeStr;
	 	        for(int i = 0; i < count; i++){
	 	            for(int j = 0; j < 5;j++){
	 	                name[j] = (char)(random.nextInt(26) + 97);
	 	            }

	 	           writeStr =  String.valueOf(name) + "," + random.nextInt(1000000) + "," + random.nextInt(1000000) + '\n';
	 	           outputStream.write(writeStr.getBytes());
	 	        }
	 	        System.out.println("writeToFileOutputStream write " + count + " done");
	        }
	    }
	 
	 
	 
	 /**
	  * 按字节buffer写入文件
	  * @param filename
	  * @throws Exception
	  */
	 public static void writeToFileOutputStreamBuffer(String filename) throws Exception{
		 try(OutputStream outputStream = new FileOutputStream(filename);
				BufferedOutputStream buffOutputStream = new BufferedOutputStream(outputStream)){
	        	Random random = new Random();
	 	        int count = 10_000_000;
	 	        char[] name = new char[5];

	 	        String writeStr;
	 	        for(int i = 0; i < count; i++){
	 	            for(int j = 0; j < 5;j++){
	 	                name[j] = (char)(random.nextInt(26) + 97);
	 	            }

	 	           writeStr =  String.valueOf(name) + "," + random.nextInt(1000000) + "," + random.nextInt(1000000) + '\n';
	 	          buffOutputStream.write(writeStr.getBytes());
	 	        }
	 	        
	 	        buffOutputStream.flush();
	 	        System.out.println("writeToFileOutputStreamBuffer write " + count + " done");
	        }
	    }
	 
	 /**
	  * 按字符读取
	  * @param filename
	  * @throws FileNotFoundException
	  * @throws IOException
	  */
	 public static void readFromBufferedReader(String filename) throws FileNotFoundException, IOException{
		 try(FileReader reader = new FileReader(filename);
			 BufferedReader bufferedReader = new BufferedReader(reader)){
			 bufferedReader.lines()
			 	.map(m->m.split(","))
			 	.collect(Collectors.groupingBy(s->s[0].substring(0,2),
                        Collector.of(()->new long[2],
                                (a,sa)->{
                                    a[0] += Long.parseLong(sa[1]) + Long.parseLong(sa[2]);
                                    a[1] += 1L;
                                },
                                (a,b)->{
                                    a[0] += b[0];
                                    a[1] += b[1];
                                    return a;
                                }
                            )
                        )
                )
                .entrySet().stream()
                .sorted((a,b)->Long.compare(b.getValue()[0],a.getValue()[0]))
                .limit(10)
                .forEach(s->System.out.printf("%s,%s,%s\n",s.getKey(),s.getValue()[0],s.getValue()[1]));
			
		 }
	 }

}
