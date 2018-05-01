package zhan.foundation.lesson07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/4/30 0030.
 */
public class Exercises02 {

    private static int allSum = 0;
    private static Map<String,Integer> fileMap = new HashMap<String,Integer>();
    private static final String keyWords = "String";

    /**
     * 查找文件
     * @param path  文件路径
     * @param currentDeepth  递归深度
     */
    public static void readFile(String path,int currentDeepth){
        int deeepth = currentDeepth;

        if(deeepth > 4){
            //System.out.println("deeepth is 4 return");
            return;
        }

        try{
            Files.list(new File(path).toPath())
                    .forEach(f-> {
                                if(f.toFile().isDirectory()) {
                                    readFile(f.toFile().getAbsolutePath(),deeepth + 1);
                                }else {
                                    String extendName = f.toFile().getName().substring(f.toFile().getName().lastIndexOf(".") + 1,f.toFile().getName().length());
                                    if("java".equals(extendName)){
                                        System.out.println(Thread.currentThread().getName() + " file.getAbsolutePath() = " + f.toFile().getAbsolutePath());
                                        countKeyWords(f.toFile().getAbsolutePath());
                                    }
                                }
                            }
                    );
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 查找关键字出现次数
     * @param filePath 文件路径
     * @throws Exception
     */
    public static void countKeyWords(String filePath){
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int sum = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(keyWords)) {
                    sum++;

                    line = line.replaceFirst(keyWords, "");
                    while (line.contains(keyWords)) {
                        line = line.replaceFirst(keyWords, "");
                        sum++;
                    }
                }
            }

            if (sum > 0) {
                fileMap.put(filePath, sum);
                allSum += sum;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception{
        ExecutorService excutor = Executors.newFixedThreadPool(5);
        excutor.execute(()-> readFile("F:\\IdeaProjects",1));
        //excutor.awaitTermination(1, TimeUnit.SECONDS);
        excutor.shutdown();
        while(!excutor.isTerminated()){
        }
        System.out.println(keyWords + " 总共出现 " + allSum + "次");
        fileMap.entrySet().stream()
                .sorted((m1,m2)-> Integer.compare(m2.getValue(),m1.getValue())
                ).forEach(f->System.out.println(f.getValue() + " 次出现在 " + f.getKey()));
    }
}
