package zhan.foundation.lesson02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Exercises03 {

	
	public static void main(String[] args) throws Exception {
		long stratWriteTime = System.currentTimeMillis();
		writeToBufferedWriter("f:\\salaryWriterChannel.txt");
		long endWriteTime = System.currentTimeMillis();
        System.out.println("write cost : " + (endWriteTime - stratWriteTime));
        readFromBufferedReader("f:\\salaryWriterChannel.txt");
        System.out.println("read cost : " + (System.currentTimeMillis() - endWriteTime));
        System.out.println("all cost : " + (System.currentTimeMillis() - stratWriteTime));

	}
	
	 /**
	  * 按字符写入文件
	  * @param filename
	  * @throws Exception
	  */
	 public static void writeToBufferedWriter(String filename) throws Exception{
		 try(FileWriter writer = new FileWriter(filename);
	        BufferedWriter bufferedWrite = new BufferedWriter(writer)
		 ){
	        	Random random = new Random();
	 	        int count = 10_000_000;
	 	        char[] name = new char[5];

	 	        String writeStr;
	 	        for(int i = 0; i < count; i++){
	 	            for(int j = 0; j < 5;j++){
	 	                name[j] = (char)(random.nextInt(26) + 97);
	 	            }

	 	            writeStr =  String.valueOf(name) + "," + random.nextInt(1000000) + "," + random.nextInt(1000000) + '\n';
	 	            bufferedWrite.write(writeStr);
	 	        }

	 	        bufferedWrite.flush();
	 	        System.out.println("writeToBufferedWriter write " + count + " done");
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
