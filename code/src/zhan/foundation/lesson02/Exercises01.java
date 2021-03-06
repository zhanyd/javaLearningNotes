package zhan.foundation.lesson02;

import java.io.UnsupportedEncodingException;

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
