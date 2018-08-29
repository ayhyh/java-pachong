/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-27
 * @time: 10:10
 */
public class shuzi {
    public static void main(String[] args) {
        Integer i=2016124083;
        i=i+1000;
        i=Integer.parseInt(i.toString().substring(0,7)+"000");
        System.out.println(i);
    }
}
