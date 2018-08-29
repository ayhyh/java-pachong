import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-24
 * @time: 22:46
 */
public class baiduTest {


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
        HtmlPage page=webClient.getPage("https://www.baidu.com/");
        int i=webClient.waitForBackgroundJavaScript(1000);

        HtmlInput kwInput=page.getHtmlElementById("kw");
        kwInput.setValueAttribute("htmlunit");
        HtmlInput kwbutton=page.getHtmlElementById("su");

        HtmlPage rePage=kwbutton.click();
        webClient.waitForBackgroundJavaScript(1000);
        //这是是个坑  一定要设置运行js时间
        Document document=Jsoup.parse(rePage.asXml());
        List<Element> elementslist=document.getElementsByClass("t");
//        System.out.println(elementslist);
//        System.exit(0);
        for(Element e:elementslist){
            System.out.println(e.text());
        }
    }
}
