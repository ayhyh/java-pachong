import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-20
 * @time: 23:20
 */
public class Demo2 {
    public static void main(String[] args) {

        for(Integer i=1;i<=5593;i++){
            try {
                Map<String,String>  map=getUrl(i);
                Set<Map.Entry<String,String>> entrySet= map.entrySet();

                Iterator<Map.Entry<String,String>> it =   entrySet.iterator();

                while(it.hasNext())
                {
                    Map.Entry<String,String> me = it.next();
                    String key = me.getKey();
                    String value = me.getValue();
                    String name=key+value.substring(value.length()-4,value.length());
                    downLoadFromUrl(value,name,"F:\\meizi");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Map<String,String> getUrl(Integer i) throws IOException {
        Document doc = Jsoup.connect("http://www.meizitu.com/a/"+i+".html").get();
          Elements link=doc.getElementById("maincontent").getElementsByTag("img");
          Map<String,String> map=new HashMap<String,String>();
          for(Element e:link){
              String alt=UUID.randomUUID().toString().replace("-", "").substring(0,7);
              String src=e.attr("src");
              map.put(alt.trim().replace("，",""),src);
          }
        System.out.println(map);
        System.out.println(link);
        return map;
    }
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        //conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");


        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);


        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }




        System.out.println("info:"+fileName+'：'+url+" download success");


    }
    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
