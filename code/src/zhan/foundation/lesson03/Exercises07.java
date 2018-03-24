package zhan.foundation.lesson03;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
public class Exercises07 {
    public static void main(String[] args) throws Exception {
        HashMap hashMap = new HashMap();
        User user1 = new User("zhan",15);
        User user2 = new User("zhan",15);
        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
        hashMap.put(user1,"1");

        System.out.println(hashMap.get(user2));



       /* HashSet hashSet = new HashSet();
        User user1 = new User("zhan",15);
        User user2 = new User("zhan",15);
        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
        hashSet.add(user1);

        System.out.println(hashSet.contains(user2));*/
    }
}
