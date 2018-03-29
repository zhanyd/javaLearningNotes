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

