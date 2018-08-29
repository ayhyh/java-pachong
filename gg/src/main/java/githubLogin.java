import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-25
 * @time: 0:09
 */
public class githubLogin {
    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
        //启动js
        webClient.getOptions().setJavaScriptEnabled(true);
//关闭css渲染
        webClient.getOptions().setCssEnabled(false);
//启动重定向
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//启动cookie管理
        webClient.setCookieManager(new CookieManager());
//启动ajax代理  这个必须加  又是坑
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//js运行时错误，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page=webClient.getPage("https://github.com/login");
        int i=webClient.waitForBackgroundJavaScript(5000);

        HtmlInput username= (HtmlInput) page.getElementById("login_field");
        username.setValueAttribute("372799514@qq.com");
        HtmlInput password= (HtmlInput) page.getElementById("password");
        password.setValueAttribute("ay6785753");
        HtmlInput loginBtn= (HtmlInput) page.getElementByName("commit");
        HtmlPage resPage =loginBtn.click();
        webClient.waitForBackgroundJavaScript(5000);
        Document document=Jsoup.parse(resPage.asXml());
        System.out.println(document.getElementsByClass("css-truncate"));
    }
}
