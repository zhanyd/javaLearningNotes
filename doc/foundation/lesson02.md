# 习题解答

## 1.得到 String s="中国" 这个字符串的utf-8编码，gbk编码，iso-8859-1编码的字符串，看看各自有多少字节，同时解释为什么以utf-8编码得到的byte[]无法用gbk的方式“还原”为原来的字符串
 ```
 public class Exercises01 {

	private final static char[] HEX = "0123456789abcdef".toCharArray();
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "中国";
		byte[] utf8 = s.getBytes("utf-8");
		byte[] utf16 = s.getBytes("utf-16");
        byte[] gbk =  s.getBytes("gbk");
        byte[] iso = s.getBytes("iso-8859-1");
        System.out.println("utf-8 length: " + utf8.length);
        System.out.println("utf-16 length: " + utf16.length);
        System.out.println("gbk length: " + gbk.length);
        System.out.println("iso-8859-1 length: " + iso.length);
        System.out.println("utf-8 : " + bytes2HexString(utf8));
        System.out.println("utf-16 : " + bytes2HexString(utf16));
        System.out.println("gbk : " + bytes2HexString(gbk));
        System.out.println("iso-8859-1 : " + bytes2HexString(iso));


        String eUtf8 = new String(utf8,"gbk");
        System.out.println("error trans : " + eUtf8);

        String rUtf8 = new String(utf8,"utf-8");
        System.out.println("right trans : " + rUtf8);

        String eUtf16 = new String(utf16,"utf-8");
        System.out.println("error egbk : " + eUtf16);

        String rUtf16 = new String(utf16,"utf-16");
        System.out.println("right rgbk : " + rUtf16);


        String eGbk = new String(gbk,"utf-8");
        System.out.println("error egbk : " + eGbk);

        String rGbk = new String(gbk,"gbk");
        System.out.println("right rgbk : " + rGbk);
        
        String eIso = new String(iso,"utf-8");
        System.out.println("error egbk : " + eIso);

        String rIso = new String(iso,"iso-8859-1");
        System.out.println("right rgbk : " + rIso);

       
	}
	
	public static String bytes2HexString(byte[] bys) {
        char[] chs = new char[bys.length * 2 + bys.length - 1];
        for (int i = 0, offset = 0; i < bys.length; i++) {
            if (i > 0) {
                chs[offset++] = ' ';
            }
            chs[offset++] = HEX[bys[i] >> 4 & 0xf];
            chs[offset++] = HEX[bys[i] & 0xf];
        }
        return new String(chs);
    }

}
```
答：utf-8编码得到的byte[]长度是6，gbk编码的长度是4，编码规则不同，所以无法用gbk的方式“还原”为原来的字符串。

输出：
```
utf-8 length: 6
utf-16 length: 6
gbk length: 4
iso-8859-1 length: 2
utf-8 : e4 b8 ad e5 9b bd
utf-16 : fe ff 4e 2d 56 fd
gbk : d6 d0 b9 fa
iso-8859-1 : 3f 3f
error trans : 涓浗
right trans : 中国
error egbk : ��N-V�
right rgbk : 中国
error egbk : �й�
right rgbk : 中国
error egbk : ??
right rgbk : ??
```

## 2.分别用大头和小头模式将整数 a=10240写入到文件中（4个字节），并且再正确读出来，打印到屏幕上，同时截图UltraEdit里的二进制字节序列，做对比说明



