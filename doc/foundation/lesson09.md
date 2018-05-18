# 习题解答

## 1 学习HTTP POST附件的协议以及参考实现代码，了解HTTP文件上传的原理（客户端与服务器端）
答：文件上传 http的Content-Type必须设置为multipart/form-data
The media-type multipart/form-data follows the rules of all multipart
   MIME data streams as outlined in [RFC 2046].  In forms, there are a
   series of fields to be supplied by the user who fills out the form.
   Each field has a name. Within a given form, the names are unique.

   "multipart/form-data" contains a series of parts. Each part is
   expected to contain a content-disposition header [RFC 2183] where the
   disposition type is "form-data", and where the disposition contains
   an (additional) parameter of "name", where the value of that
   parameter is the original field name in the form. For example, a part
   might contain a header:

        Content-Disposition: form-data; name="user"

   with the value corresponding to the entry of the "user" field.

   Field names originally in non-ASCII character sets may be encoded
   within the value of the "name" parameter using the standard method
   described in RFC 2047.

chrome抓包如下
![](img/lesson09-001.png)


Wireshark抓包如下
![](img/lesson09-002.png)


## 2 用JDK线程池框架实现PPT中的HTTP Server的代码，每个TCP客户端连接一个线程来处理。





## 3 加分选项：
模拟JSP效果，比如命名为 xxx.msp文件，里面有动态执行的脚本，当HTTP Server收到请求xxx.msp请求时，
  去加载此文件执行，传递request params给脚本，脚本中完成变量的替换，输出为静态的txt或者json等格式输出给客户端，
   如用户请求 http://localhost/hellow.msp?user=aaaa&password=123456
    程序判断此用户是否合法，然后输出 Welcome my big hero aaaa 或者,Sorry ，不认识你。。。

  具体做法可以参考如下：
   第一，采用某种动态脚步技术，如JSR223支持的脚本语言，包括JDK8的Javascript Nashorn，Groovy等来实现xxx.msp
         Beanshell是轻量级的java 脚本语言，也可以使用，http://www.beanshell.org，
   第二，定义一个 pervlet接口（模拟servlet）
               public interface pervlet
                   public PvReponse handlerRequest(PvRequest req)
           采用JDK Compile API来动态编译xxx.msp到class，并且加载执行。

