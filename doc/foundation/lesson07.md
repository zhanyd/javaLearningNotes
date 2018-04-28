# 习题解答

## 1.1 解释为什么下面放入会失败

 ```
 SynchronousQueue<String> queue=new SynchronousQueue();
    if(queue.offer("S1"))
    {
        System.out.println("scucess");
    }else
    {
        System.out.println("faield");
    }
```

答：A {@linkplain BlockingQueue blocking queue} in which each insert
  operation must wait for a corresponding remove operation by another
  thread, and vice versa.A synchronous queue does not have any
  internal capacity, not even a capacity of one.  You cannot
  {@code peek} at a synchronous queue because an element is only
  present when you try to remove it; you cannot insert an element
  (using any method) unless another thread is trying to remove it;
  you cannot iterate as there is nothing to iterate.


```
public static void main(String[] args){
        SynchronousQueue<Object> queue = new SynchronousQueue<Object>();
        
        List<Thread> threadListCustomer = IntStream.range(0,5)
                .mapToObj(t->{
                    return new Thread(()->{
                        System.out.println(Thread.currentThread().getName() + " take begin");
                        Object item = null;
                        try {
                            while((item = queue.take()) != null){
                                System.out.println(Thread.currentThread().getName() + " take " +  item.toString() + "item");
                                System.out.println(Thread.currentThread().getName() + " take end");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                })
                .collect(Collectors.toList());
        
        threadListCustomer.forEach(t->t.start());
        
        
        List<Thread> threadListProductor = IntStream.range(0,5)
                .mapToObj(t->{
                    return new Thread(()->{
                        System.out.println(Thread.currentThread().getName() + " offer begin");
                        Object item = new Object();
                        while(queue.offer(item)){
                            System.out.println(Thread.currentThread().getName() + " offer " +  item.toString() + "item");
                            System.out.println(Thread.currentThread().getName() + " offer end");
                        }
                    });
                })
                .collect(Collectors.toList());
        
        threadListProductor.forEach(t->t.start());
        
    }
```

输出
```
Thread-1 take begin
Thread-3 take begin
Thread-2 take begin
Thread-4 take begin
Thread-0 take begin
Thread-5 offer begin
Thread-6 offer begin
Thread-7 offer begin
Thread-7 offer java.lang.Object@12b6fa6fitem
Thread-8 offer begin
Thread-6 offer java.lang.Object@758056e9item
Thread-6 offer end
Thread-6 offer java.lang.Object@758056e9item
Thread-6 offer end
Thread-3 take java.lang.Object@758056e9item
Thread-3 take end
Thread-4 take java.lang.Object@12b6fa6fitem
Thread-4 take end
Thread-7 offer end
Thread-7 offer java.lang.Object@12b6fa6fitem
Thread-7 offer end
Thread-7 offer java.lang.Object@12b6fa6fitem
Thread-7 offer end
Thread-9 offer begin
Thread-1 take java.lang.Object@758056e9item
Thread-1 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@291e1298item
Thread-2 take end
Thread-8 offer java.lang.Object@291e1298item
Thread-8 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-8 offer java.lang.Object@291e1298item
Thread-2 take java.lang.Object@291e1298item
Thread-2 take end
Thread-0 take end
Thread-4 take java.lang.Object@12b6fa6fitem
Thread-3 take java.lang.Object@12b6fa6fitem
Thread-4 take end
Thread-8 offer end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-8 offer java.lang.Object@291e1298item
Thread-8 offer end
Thread-8 offer java.lang.Object@291e1298item
Thread-8 offer end
Thread-8 offer java.lang.Object@291e1298item
Thread-8 offer end
Thread-8 offer java.lang.Object@291e1298item
Thread-8 offer end
Thread-4 take java.lang.Object@291e1298item
Thread-4 take end
Thread-3 take end
Thread-2 take java.lang.Object@291e1298item
Thread-2 take end
Thread-0 take java.lang.Object@291e1298item
Thread-0 take end
Thread-1 take java.lang.Object@291e1298item
Thread-1 take end
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-1 take java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer end
Thread-1 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-1 take java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-2 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-1 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-2 take java.lang.Object@2eda1c97item
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-2 take end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-1 take java.lang.Object@2eda1c97item
Thread-1 take end
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-1 take java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-5 offer java.lang.Object@2eda1c97item
Thread-5 offer end
Thread-1 take end
Thread-3 take java.lang.Object@2eda1c97item
Thread-4 take java.lang.Object@2eda1c97item
Thread-2 take java.lang.Object@2eda1c97item
Thread-2 take end
Thread-4 take end
Thread-3 take end
Thread-0 take java.lang.Object@2eda1c97item
Thread-0 take end

```






## 2.用线程池框架或者fork-jion框架实现一个并发的文件内容查找接口：
    public SearchResult searchInFiles(String key);
    查询指定目录下的所有txt或者java文件（建议查找Java工程文件）
    目录递归最多为4层，即从根目录开始，最多3层子目录中的文件搜索
    每个文件中如果出现关键字，则关键字的次数+1，并且将此文件的路径也保持到List中
    文件中出现关键字最多次的文件排名第一，以此类推：
    屏幕最后输出：
    xxx总共出现N次，
    其中 2次出现在yyy文件中
         3次出现在xxx文件中
 




## 3.用fork-jion框架实现第二课第四题的编程计算，把握分割任务的粒度。
 
 