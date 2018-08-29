import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
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
public class kaoshilLogin {
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
        HtmlPage page=webClient.getPage("http://47.106.121.212/Exam_System_Loop/Code_Admin/web/index.php?r=front%2Fsite%2Findex");
        int i=webClient.waitForBackgroundJavaScript(5000);

        HtmlInput username= (HtmlInput) page.getElementById("loginname");
        username.setValueAttribute("2016124082");
        HtmlInput password= (HtmlInput) page.getElementById("password");
        password.setValueAttribute("2016124082");
        HtmlButton loginBtn= (HtmlButton) page.getElementById("imgLogin");
        HtmlPage resPage =loginBtn.click();
        webClient.waitForBackgroundJavaScript(5000);
        HtmlPage page1=webClient.getPage("http://47.106.121.212/Exam_System_Loop/Code_Admin/web/index.php?r=front%2Fsite%2Fperson-info");
        webClient.waitForBackgroundJavaScript(5000);

        System.out.println(page1.asText());
    }
}
