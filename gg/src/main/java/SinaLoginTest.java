/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-22
 * @time: 17:04
 */
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;


public class SinaLoginTest {
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        client.getOptions().setCssEnabled(false);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage page = client.getPage("http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.3.16)");
        //System.out.println(page.asText());
        //登录

        HtmlInput ln = page.getHtmlElementById("username");
        HtmlInput pwd = page.getHtmlElementById("password");
        HtmlInput btn = page.getFirstByXPath(".//*[@id='vForm']/div[3]/ul/li[6]/div[2]/input");

        ln.setAttribute("value", "18581548456");
        pwd.setAttribute("value", "ay6785753");

        HtmlPage page2 = btn.click();
        //登录完成，现在可以爬取任意你想要的页面了。
        System.out.println("\n\n\n");
        System.out.println(page2.asText());

        HtmlPage page3 = client.getPage("http://weibo.com/friends?leftnav=1&wvr=5&isfriends=1&step=2");
        System.out.println(" : " + page3.asXml());

    }
}
