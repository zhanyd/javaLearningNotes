# 1,题下面为什么会出错？给出解释，并且纠正错误
 ```
 byte ba=127;
 byte bb=ba<<2;
 System.out.println(bb);
```
答：位运算是针对整型的，进行位操作时，除long型外，其他类型会自动转成int型
```
byte ba=127;
int bb=ba<<2;
System.out.println(bb);
```

