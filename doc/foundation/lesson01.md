# 习题解答

## 1.下面为什么会出错？给出解释，并且纠正错误
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

## 2.int a=-1024;给出 a>>1与a>>>1的的结果，并且用位移方式图示解释

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

## 3.定义一个10240*10240的byte数组，分别采用行优先与列优先的循环方式来计算 这些单元格的总和，看看性能的差距，并解释原因。
行优先的做法，每次遍历一行，然后到下一行。

代码如下：
```
public static void main(String[] args) {
			
		byte[][] cloumnByte = new byte[1024][1024];
		Random random = new Random();
		for(int i = 0; i < cloumnByte.length; i++){
			for(int j = 0; j < cloumnByte[i].length; j++){
				cloumnByte[i][j] = (byte) random.nextInt(100);
			}
		}
		
		countByRow(cloumnByte);
		countByColumn(cloumnByte);

	}
	
	/**
	 * 行优先
	 * @param cloumnByte
	 */
	public static void countByRow(byte[][] cloumnByte){
		long startTime = System.currentTimeMillis();
		int sum = 0;
		for(int i = 0; i < cloumnByte.length; i++){
			for(int j = 0; j < cloumnByte[i].length; j++){
				sum += cloumnByte[i][j];
			}
		}
		
		System.out.println("用时:" + (System.currentTimeMillis() - startTime));
		System.out.println("sum = " + sum);
	}
	
	/**
	 * 列优先
	 * @param cloumnByte
	 */
	public static void countByColumn(byte[][] cloumnByte){
		long startTime = System.currentTimeMillis();
		int sum = 0;
		for(int i = 0; i < cloumnByte.length; i++){
			for(int j = 0; j < cloumnByte[i].length; j++){
				sum += cloumnByte[j][i];
			}
		}
		
		System.out.println("用时:" + (System.currentTimeMillis() - startTime));
		System.out.println("sum = " + sum);
	}
```

输出：
```
用时:20
sum = 51917472
用时:27
sum = 51917472
```

原因分析：

以我们常见的X86芯片为例，Cache的结构下图所示：整个Cache被分为S个组，每个组是又由E行个最小的存储单元——Cache Line所组成，而一个Cache Line中有B（B=64）个字节用来存储数据，即每个Cache Line能存储64个字节的数据，每个Cache Line又额外包含一个有效位(valid bit)、t个标记位(tag bit)，其中valid bit用来表示该缓存行是否有效；tag bit用来协助寻址，唯一标识存储在CacheLine中的块；而Cache Line里的64个字节其实是对应内存地址中的数据拷贝。根据Cache的结构题，我们可以推算出每一级Cache的大小为B×E×S。

![](img/lesson01-002.png)

局部性包括时间局部性、空间局部性。时间局部性：对于同一数据可能被多次使用，自第一次加载到Cache Line后，后面的访问就可以多次从Cache Line中命中，从而提高读取速度（而不是从下层缓存读取）。空间局部性：一个Cache Line有64字节块，我们可以充分利用一次加载64字节的空间，把程序后续会访问的数据，一次性全部加载进来，从而提高Cache Line命中率（而不是重新去寻址读取）。
行优先的做法使得数据连续一个Cache Line中命中率提高，提升性能。