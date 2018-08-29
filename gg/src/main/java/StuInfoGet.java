/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-21
 * @time: 21:30
 */
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-21
 * @time: 17:28
 */

public class StuInfoGet {

    public static String LOGIN_URL = "http://wlzf.cuit.edu.cn/login.aspx";
    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    public static void main(String[] args) throws Exception {

        simulateLogin("2016124082", "2016124082"); // 模拟登陆的用户名和密码

    }

    /**
     * @param userName 用户名
     * @param pwd 密码
     * @throws Exception
     */
    public static void simulateLogin(String userName, String pwd) throws Exception {

        /*
         * 第一次请求
         * grab login form page first
         * 获取登陆提交的表单信息，及修改其提交data数据（login，password）
         */
        // get the response, which we will post to the action URL(rs.cookies())
        Connection con = Jsoup.connect(LOGIN_URL);  // 获取connection
        con.header(USER_AGENT, USER_AGENT_VALUE);   // 配置模拟浏览器
        Connection.Response rs = con.execute();                // 获取响应
        Document d1 = Jsoup.parse(rs.body());       // 转换为Dom树
        List<Element> eleList = d1.select("#user_login");  // 获取提交form表单，可以通过查看页面源码代码得知
        System.out.println(rs.cookies());
        System.exit(0);
        // 获取cooking和表单属性
        // lets make data map containing all the parameters and its values found in the form
        Map<String, String> datas = new HashMap<String, String>();
        for (Element e : eleList) {
            // 设置用户名
            if (e.select("#txt_yhm").attr("name").equals("txt_yhm")) {
                e.select("#txt_yhm").attr("value", userName);
                datas.put("txt_yhm", userName);
            }
            // 设置用户密码
            if (e.select("#txt_pwd").attr("name").equals("txt_pwd")) {
                e.select("#txt_pwd").attr("value", pwd);
                datas.put("txt_pwd", userName);
            }
            System.out.println(e.select("#Image1"));
            System.exit(0);
            if(e.select("#Image1").attr("src").contains("validate.aspx"))
            {
                e.select("#txt_yzm").attr("value",
                        ReadImg.getImgWord("http://wlzf.cuit.edu.cn/"+e.select("#Image1").attr("src")));
                datas.put("txt_yzm",e.select("#txt_yzm").attr("value").trim());
            }
        }
//        System.out.println(datas);
//        System.exit(0);

        /*
         * 第二次请求，以post方式提交表单数据以及cookie信息
         */
        Connection con2 = Jsoup.connect(LOGIN_URL);
        con2.header(USER_AGENT, USER_AGENT_VALUE);
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).followRedirects(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
        // parse the document from response
        System.out.println(login.body());

        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        Map<String, String> map = login.cookies();
        for (String s : map.keySet()) {
            System.out.println(s + " : " + map.get(s));
        }
    }
}

