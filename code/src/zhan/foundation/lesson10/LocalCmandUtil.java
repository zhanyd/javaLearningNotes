package zhan.foundation.lesson10;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LocalCmandUtil {

	public static String callCmdAndgetResult(String cmd){
		StringBuilder result = new StringBuilder();
		try{
			//创建进程管理实例
			ProcessBuilder pb = new ProcessBuilder(cmd.split("\\s"));
			//启动进程
			Process process = pb.start();
			//获得输入流
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "GBK");
			BufferedReader br = new BufferedReader(isr);
			String line;
			//循环读取数据
			while((line = br.readLine()) != null){
				result.append(line);
			}
			is.close();
			isr.close();
			br.close();
			process.waitFor();
			
		}catch(Exception e){
			result.append(e.toString());
		}
		
		return result.toString();
	}
}
