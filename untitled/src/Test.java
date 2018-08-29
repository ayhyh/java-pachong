import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-19
 * @time: 15:07
 */
public class Test {
    public static void main(String[] args) throws IOException {


        for(int i=1;i<=5;i++){
            getImageUrl(i);
        }

    }
    public static void getImageUrl(Integer i){
        try
        {
            //初始化url
            URL url = new URL("http://www.xiaohuar.com/list-1-"+i+".html");
            //返回url连接对象
            URLConnection urlConnection = url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "gb2312"));
//            stringBuffer是可变长度的字符串容器
            StringBuffer urlString=new StringBuffer();
            String current;
            String reg = "<img width=\"210\"(.*?)/>";
            //将规则封装成对象。
            Pattern p = Pattern.compile(reg);
            //让正则对象和要作用的字符串相关联。获取匹配器对象。
            Map<String,String> map=new HashMap<String,String>();
            String school=null;
            String imgUrl=null;
            while((current = in.readLine()) != null)
            {
                Matcher m  = p.matcher(current);
                if(m.find() && !current.isEmpty()){
//                    System.out.println(current);
                    school=current.trim().substring(current.indexOf("alt"),current.indexOf("src")-7);
//                    System.out.println(school);
                    imgUrl=current.trim().substring(current.indexOf("src"),current.indexOf("/>")-7);
                    if(!imgUrl.contains("http")){
                            StringBuffer sb=new StringBuffer(imgUrl);
                            sb.insert(0,"http://www.xiaohuar.com");
                            imgUrl=sb.toString();
                    }
//                    System.out.println(imgUrl);
                    map.put(school,imgUrl);
                }
            }
            Set<Map.Entry<String,String>> entrySet= map.entrySet();

            Iterator<Map.Entry<String,String>> it =   entrySet.iterator();

            while(it.hasNext())
            {
                Map.Entry<String,String> me = it.next();
                String key = me.getKey();
                String value = me.getValue();
                downLoadFromUrl(value,key+".jpg","F:\\bingJpg");
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(10*1000);
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
        System.out.println("info:"+url+" | "+fileName+" download success");
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
