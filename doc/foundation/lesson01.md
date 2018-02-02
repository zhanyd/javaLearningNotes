## 1.题下面为什么会出错？给出解释，并且纠正错误
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
输出：
```
508
```

## 2.int a=-1024;
给出 a>>1与a>>>1的的结果，并且用位移方式图示解释

![](img/lesson01-001.png)

```
int a=-1024;

System.out.println(Integer.toBinaryString(a));
System.out.println(Integer.toBinaryString(a>>1));
System.out.println(Integer.toBinaryString(a>>>1));

System.out.println("a>>1: " + (a>>1));
System.out.println("a>>>1: " + (a>>>1));
```
输出：
```
11111111111111111111110000000000
11111111111111111111111000000000
1111111111111111111111000000000
a>>1: -512
a>>>1: 2147483136
```