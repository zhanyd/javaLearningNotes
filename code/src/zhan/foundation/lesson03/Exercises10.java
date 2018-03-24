package zhan.foundation.lesson03;

import java.util.IdentityHashMap;

public class Exercises10 {

	public static void main(String[] args) {
		IdentityHashMap<Integer,String> map = new IdentityHashMap<>();
		Integer a=5;
        Integer b=5;
        map.put(a,"100");
        map.put(b,"100");
        System.out.println(map.size());
        map.clear();
        a=Integer.MAX_VALUE-1;
        b=Integer.MAX_VALUE-1;
        map.put(a,"100");
        map.put(b,"100");
        System.out.println(map.size());

	}

}
