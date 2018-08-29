import org.omg.PortableInterceptor.INACTIVE;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> stringList=getMuchDayImgUrl(8);
        Integer i=1;
        for(String url:stringList){
            downLoadFromUrl(url,"bing"+i+++".jpg","F:\\bingJpg");
        }
    }
    /**
     * 匹配字符
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
    public static String getBingImgUrl(Integer idx){
        try {

            URL url = new URL("http://cn.bing.com/HPImageArchive.aspx?idx="+idx+"&n=1");
            BufferedReader bufr = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = null;
            String imgUrl=null;
            while((str = bufr.readLine()) != null){
                imgUrl=subString(str,"<url>","</url>");
            }
            String bingImgUrl="https://cn.bing.com"+imgUrl;
            System.out.println(bingImgUrl);
            return bingImgUrl;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 得到多少天的壁纸
     * @param day
     * @return
     */
    public static List<String> getMuchDayImgUrl(Integer day){

        List<String> imgUrlList = new ArrayList<String>();
        for(int i=0;i<day;i++){
            imgUrlList.add(getBingImgUrl(i));
        }
        return imgUrlList;
    }
    /**
     * 下载壁纸
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
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
