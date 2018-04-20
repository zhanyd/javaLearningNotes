# 习题解答

## 1.1 说说下面的几个方法，分别锁的是什么东西？

 public static synchronized void doIt(){xx};
 pubilc syncronzied void doIt() {xxx)
 pubilc void doIt(){ syncronized(myobj) ....}
 
1. public static synchronized void doIt(){xx}; 
   synchronized作用于静态方法上，作用于整个类，锁住了该类的所有对象
2. pubilc syncronzied void doIt() {xxx)  
   syncronzied作用于方法上，作用于当前对象，锁住了当前对象
3. pubilc void doIt(){ syncronized(myobj) ....}
   syncronized作用于方法内的一个代码块，锁住了当前对象的代码块，必须获得myobj的锁方能执行（可以是类或者对象）


synchronized是Java中的关键字，是一种同步锁。它修饰的对象有以下几种：
1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；
2. 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
3. 修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；
4. 修改一个类，其作用的范围是synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象。


## 2.说说为什么下面的代码是错误的 public void doIt() { syncronized(new ObjA()) {xxxx}
 
syncronized(new ObjA())加锁的对象是一个新new的对象，该对象永远也无法被引用，不是当前对象，所以当前对象的syncronized代码块无效。
