import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-25
 * @time: 0:09
 */
public class qqEamailLogin {
    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //启动js
        webClient.getOptions().setJavaScriptEnabled(true);
//关闭css渲染
        webClient.getOptions().setCssEnabled(false);
//启动重定向
        webClient.getOptions().setRedirectEnabled(true);
//启动cookie管理
        webClient.setCookieManager(new CookieManager());
//启动ajax代理  这个必须加  又是坑
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//js运行时错误，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page=webClient.getPage("https://mail.qq.com");
        int i=webClient.waitForBackgroundJavaScript(30000);
        HtmlPage page1=webClient.getPage(page.getElementById("login_frame").getAttribute("src"));
        int j=webClient.waitForBackgroundJavaScript(30000);
        HtmlElement switcher_plogin= (HtmlElement) page1.getElementById("switcher_plogin");
        switcher_plogin.click();
        HtmlInput username= (HtmlInput) page1.getElementById("u");
        username.focus();
        username.type("372799514");
        username.blur();
        HtmlInput password= (HtmlInput) page1.getElementById("p");
        password.focus();
        password.type("ay6785753");
        password.blur();
        HtmlInput loginBtn= (HtmlInput) page1.getElementById("login_button");
        HtmlPage resPage =loginBtn.click();
        webClient.waitForBackgroundJavaScript(30000);
        System.out.println(resPage.asXml());
    }
}
