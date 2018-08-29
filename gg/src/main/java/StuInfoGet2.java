import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImageInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-22
 * @time: 11:42
 */
public class StuInfoGet2 {
    public static String LOGIN_URL = "http://wlzf.cuit.edu.cn/login.aspx";
    public static void main(String arg[]) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        Set<Cookie> cookie=null;
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        HtmlPage page = null;
        try {
            page = webClient.getPage(LOGIN_URL);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }

        int i=webClient.waitForBackgroundJavaScript(10000);

        //异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        String userName = "2016124082";
        String pwds = "2016124082";
        HtmlElement yhm = (HtmlElement) page.getElementById("txt_yhm");
        yhm.focus();
        yhm.type(userName.trim());
        HtmlElement pwd = (HtmlElement) page.getElementById("txt_pwd");
        pwd.focus();
        pwd.type(pwds.trim());
        HtmlElement url = (HtmlElement) page.getElementById("Image1");
        HtmlElement yzm = (HtmlElement) page.getElementById("txt_yzm");
        yzm.focus();
        yzm.type("ffff");
        int j=webClient.waitForBackgroundJavaScript(3000);

        HtmlImageInput btn= (HtmlImageInput) page.getElementById("ImageButton1");
        int k=webClient.waitForBackgroundJavaScript(10000);

        HtmlPage page1= (HtmlPage) btn.click();
        int h=webClient.waitForBackgroundJavaScript(10000);

        System.out.println(page1.asText());
        System.out.println(page1.asXml());
    }
    }




