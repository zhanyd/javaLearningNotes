package zhan.foundation.lesson02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Exercises03_3 {
	public static void main(String[] args) throws Exception {
		long stratWriteTime = System.currentTimeMillis();
		writeToMappedByteBufferChar("f:\\salaryWriterChannel.txt");
		long endWriteTime = System.currentTimeMillis();
        System.out.println("write cost : " + (endWriteTime - stratWriteTime));
        readFromMappedByteBuffer("f:\\salaryWriterChannel.txt");
        System.out.println("read cost : " + (System.currentTimeMillis() - endWriteTime));
        System.out.println("all cost : " + (System.currentTimeMillis() - stratWriteTime));

	}
	
	 /**
     * MappedByteBuffer写入字符串
     * @throws IOException
     */
    public static void writeToMappedByteBufferChar(String fileName) throws IOException{
        Random random = new Random();
        int count = 10_000_000;
        byte[] name = new byte[5];
        try(RandomAccessFile fout = new RandomAccessFile(fileName,"rw");
        	FileChannel fc = fout.getChannel()){

        	System.out.println("fc.position() = " + fc.position());
        	MappedByteBuffer mbf = fc.map(FileChannel.MapMode.READ_WRITE, fc.position(), count*13);
        	
        	for(int i = 0; i < count; i++){
        		for(int j = 0; j < 5;j++){
        			name[j] = (byte)(random.nextInt(26) + 97);
        		}
        		mbf.put(name);
        		mbf.putInt(random.nextInt(1000000));
                mbf.putInt(random.nextInt(1000000));
        	}
        	
        	System.out.println("writeToMappedByteBufferChar write " + count + " done");
        }

    }

	 
	 /**
	  * 按字符读取
	  * @param filename
	  * @throws FileNotFoundException
	  * @throws IOException
	  */
	 public static void readFromMappedByteBuffer(String filename) throws FileNotFoundException, IOException{
		 byte[] name = new byte[5];
		 try(RandomAccessFile fout = new RandomAccessFile(filename,"rw");
		     FileChannel fc = fout.getChannel()){
			 MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fout.length());
			 
			 while(buffer.position() <= 1000){
				buffer.get(name);
				System.out.print(new String(name) + " ");
				System.out.print(buffer.getInt() + " ");
	            System.out.println(buffer.getInt());
			 }
			 
		 }

	 }
}
