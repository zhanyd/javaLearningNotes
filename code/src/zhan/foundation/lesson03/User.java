package zhan.foundation.lesson03;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
public class User {

    private String name;
    private int age;

   public  User(String name,int age){
       this.name = name;
       this.age = age;
   }

    @Override
    public int hashCode(){
       return this.name.hashCode() + this.age;
    }

    @Override
    public boolean equals(Object obj){
        return this.name.equals(((User)obj).name) && this.age == (((User)obj).age);
    }
}
