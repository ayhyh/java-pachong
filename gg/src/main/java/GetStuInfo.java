import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-24
 * @time: 13:25
 */
public class GetStuInfo {

    public static void main(String[] args) {

        try {
           Page  page= getLoginAfter("2016124082","2016124082");
            System.out.println(((HtmlPage) page).asXml());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static HtmlPage getLoginAfter(String userName,String passWord) throws Exception{
        //设置浏览器内核
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);
        //是否启用js
        webClient.getOptions().setJavaScriptEnabled(true);
        //是否启用css
        webClient.getOptions().setCssEnabled(false);
        //设置Ajax控制器
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //设置cookies
        webClient.getCookieManager().setCookiesEnabled(true);
        //获取页面
        HtmlPage page = webClient.getPage("http://wlzf.cuit.edu.cn/login.aspx");//登陆页面
        webClient.waitForBackgroundJavaScript(10000);
        HtmlTextInput  username = (HtmlTextInput) page.getElementById("txt_yhm");//账号输入框的id
        HtmlPasswordInput password = (HtmlPasswordInput) page.getElementById("txt_pwd");//密码输入框的id
        HtmlTextInput yzm = (HtmlTextInput) page.getElementById("txt_yzm");
        HtmlImageInput  login= (HtmlImageInput) page.getElementById("ImageButton1");
        HtmlElement urlText = (HtmlElement) page.getElementById("Image1");
        String imgWord=ReadImg.getImgWord("http://wlzf.cuit.edu.cn/" + urlText.getAttribute("src")).trim();
        username.setAttribute("value",userName);
        webClient.waitForBackgroundJavaScript(10000);
        password.setAttribute("value",passWord);
        webClient.waitForBackgroundJavaScript(10000);
        yzm.setAttribute("value",imgWord);
        webClient.waitForBackgroundJavaScript(10000);
        page= (HtmlPage) login.click();
        webClient.waitForBackgroundJavaScript(10000);
        // page 就是返回结果，如果需要html形式 page.asXML 如果是需要Text 则使用 page.asText
        HtmlPage retpage=webClient.getPage("http://wlzf.cuit.edu.cn/grxx.aspx");
        return retpage;
    }
}
