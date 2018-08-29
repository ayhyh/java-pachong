import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.application.Platform.exit;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-21
 * @time: 16:01
 */
public class LoginDemo {
    public static void main(String[] args) throws Exception {
        LoginDemo loginDemo = new LoginDemo();
        loginDemo.login("372799514@qq.com","ay6785753");// 输入CSDN的用户名，和密码
    }
    /**
     * 模拟登陆CSDN
     *
     * @param userName
     *            用户名
     * @param pwd
     *            密码
     *
     * **/
    public void login(String userName, String pwd) throws Exception {
        // 第一次请求
        Connection con = Jsoup
                .connect("https://passport.csdn.net/account/login");// 获取连接
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
        Connection.Response rs = con.execute();// 获取响应
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树


        List<Element> et = d1.select("#fm1");// 获取form表单，可以通过查看页面源码代码得知
//        System.out.println(et.get(0).getAllElements());
        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<String, String>();
        for (Element e : et) {

            System.out.println( );
//            System.exit(0);
            if (e.select("#username").attr("name").equals("username")) {
                e.select("#username").attr("value", userName);// 设置用户名
            }
            if (e.select("#password").attr("name").equals("password")) {
                e.select("#password").attr("value", pwd); // 设置用户密码
            }
            datas.put(e.select("#username").attr("value"), e.select("#password").attr("value"));
        }
                System.out.println(et);
        System.out.println(datas);
//        System.exit(0);
        /**
         * 第二次请求，post表单数据，以及cookie信息
         *
         * **/
        Connection con2 = Jsoup
                .connect("https://passport.csdn.net/account/login");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 设置cookie和post上面的map数据
        Connection.Response login = con2.ignoreContentType(true).method(Connection.Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
//        System.out.println(login.body());
        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        Map<String, String> map = login.cookies();
        for (String s : map.keySet()) {
            System.out.println(s + "      " + map.get(s));
        }
    }
}
