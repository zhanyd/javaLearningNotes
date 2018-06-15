package zhan.foundation.lesson07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2018/5/1 0001.
 */
public class Exercises0202  extends RecursiveTask<Integer> {

    private static Map<String,String> fileMap = new HashMap<String,String>();
    private static File[] files;
    private static String keyWords = "String";
    int sum = 0;

    private int start;
    private int end;

    public Exercises0202(int start,int end){
        this.start = start;
        this.end = end;
    }
    
    
    public Exercises0202(int start,int end,File[] files,String keyWords){
        this.start = start;
        this.end = end;
        this.files = files;
        this.keyWords = keyWords;
    }


    @Override
    protected Integer compute(){
        int allSum = 0;

        if(end - start <= 2){
            for(int i = start; i <= end; i++){
                allSum = readFile(files[i],1);
            }
        }else{
            int middle = (start + end) / 2;
            Exercises0202 leftTask = new Exercises0202(start,middle);
            Exercises0202 rightTask = new Exercises0202(middle + 1,end);
            invokeAll(leftTask,rightTask);
            try{
                allSum = leftTask.get() + rightTask.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return allSum;
    }


    public int readFile(File fileCurrent,int currentDeepth) {
        int deeepth = currentDeepth;
        if (deeepth > 4) {
            //System.out.println("deeepth is 4 return");
            return 0;
        }
        File[] files = fileCurrent.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readFile(file, deeepth + 1);
                } else {
                    //获取扩展名
                    String extendName = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
                    if ("java".equals(extendName)) {
                        sum += countKeyWords(file.getAbsolutePath());
                        System.out.println(Thread.currentThread().getName() + " file.getAbsolutePath() = " + file.getAbsolutePath() + " sum = " + sum);
                    }
                }
            }
        }
        return sum;
    }


    /**
     * 查找关键字出现次数
     * @param filePath
     * @throws Exception
     */
    public int countKeyWords(String filePath){
        int sum = 0;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(keyWords)) {
                    sum += line.length() - line.replace(keyWords,"").length();
                }
            }
            if (sum > 0) {
                fileMap.put(filePath, String.valueOf(sum));
            }
            return sum;
        }catch (Exception e){
            e.printStackTrace();
            return sum;
        }
    }

    public static void main(String[] args) throws Exception{

        files = new File("E:\\workspace").listFiles();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Exercises0202 task = new Exercises0202(0,files.length - 1);
        Future<Integer> allSum = forkJoinPool.submit(task);

        System.out.println(keyWords + " 总共出现 " + allSum.get() + "次");

        fileMap.entrySet().stream()
                .sorted((m1,m2)->Integer.compare(Integer.parseInt(m2.getValue()),Integer.parseInt(m1.getValue())))
                .forEach(m->System.out.println(m.getValue() + " 次出现在 " + m.getKey()));

    }
}
