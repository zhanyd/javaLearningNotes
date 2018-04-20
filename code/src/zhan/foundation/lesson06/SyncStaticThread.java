package zhan.foundation.lesson06;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public class SyncStaticThread implements Runnable {
    private static int count;

    public SyncStaticThread(){
        count = 0;
    }

    public synchronized static void method() {
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        method();
    }


    public static void main(String[] args){
        SyncStaticThread syncThread = new SyncStaticThread();
        Thread thread1 = new Thread(new SyncStaticThread(),"SyncThread1");
        Thread thread2 = new Thread(new SyncStaticThread(),"SyncThread2");
        thread1.start();
        thread2.start();
    }
}
