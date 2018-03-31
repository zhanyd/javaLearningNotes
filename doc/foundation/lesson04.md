# 习题解答

## 1.说明Stream 与Collection的区别 以及关系
* Stream 不是存储数据的数据结构，Stream是通过操作管道处理数据的
* Collections 会改变数据集状态，Streams不会改变原来的数据源，而是返回一个新的结果
* Streams是懒查询的，得到预期的结果马上返回，不用遍历所有的元素
* Collection 是有限的数据集，而Stream 可以是有限的或无限的
* Stream是一次遍历的，一次操作只遍历一次数据

## 2.下面代码为什么输出流中的每个元素2遍
```
Stream.of("d2", "a2", "b1", "b3", "c")
    .filter(s -> {
        System.out.println("filter: " + s);
        return true;
    })
    .forEach(s -> System.out.println("forEach: " + s));
```
输出
```
filter: d2
forEach: d2
filter: a2
forEach: a2
filter: b1
forEach: b1
filter: b3
forEach: b3
filter: c
forEach: c
```
filter属于中间操作（Intermediate），forEach属于终结操作（Termediate），Intermediate操作是lazy(惰性求值)的，只有在Terminal操作执行时，才会一次性执行。所以filter和forEach会交替执行。

## 3. 用Stream的API实现第四题的结果，其中增加一个过滤条件，即年薪大于10万的才被累加，分别用ParellStream与普通Stream来运算，看看效果的差距

```
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
			 	.filter(f->Long.parseLong(f[1]) > 100000)
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
```
输出
```
writeToBufferedWriter write 10000000 done
write cost : 7704
hw,15223308606,15152
ju,15184041632,15119
on,15180175348,15140
rt,15156636303,15150
vs,15119285951,15048
ny,15115150669,15036
yd,15112401817,15014
ov,15111934834,15106
rp,15094280297,15053
hu,15084670810,15058
read cost : 10958
all cost : 18662
```

加上parallel后效果如下：
```
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
			 	.parallel()
			 	.map(m->m.split(","))
			 	.filter(f->Long.parseLong(f[1]) > 100000)
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
```
输出
```
writeToBufferedWriter write 10000000 done
write cost : 7662
lk,14352012679,13636
pk,14351962554,13592
kh,14325006485,13574
fx,14307290003,13590
sn,14293117685,13512
pj,14283431885,13554
rm,14283161837,13622
hk,14281904594,13627
wm,14279997325,13492
ok,14274950446,13569
read cost : 3189
all cost : 10851
```

## 4. 自己动手编写不少于5个Stream的例子，并解释代码

