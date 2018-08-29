import java.io.*;

import static java.lang.Thread.sleep;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-20
 * @time: 17:10
 */
public class BufferedReaderDemo {
    public static void main(String[] args) throws IOException, InterruptedException {

//        FileReader
//        FileReader fileReader=new FileReader("F:\\demo.txt");
//        BufferedReader bufferedReader=new BufferedReader(fileReader);
//        String stringBuffer= new String();
//        while((stringBuffer=bufferedReader.readLine())!=null){
//            System.out.println(stringBuffer);
//        }
//        bufferedReader.close();
//        FileWriter
//        FileWriter fileWriter=new FileWriter("F:\\demo.txt");
//        BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
//        for(int i=0;i<5;i++){
//            bufferedWriter.write("test"+i);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//        }
//        bufferedWriter.close();
//        System.out.println(System.currentTimeMillis());
//        sleep(2000);
//        System.out.println(System.currentTimeMillis());
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("F:\\demo.txt"));
        byte[] bytes=new  byte[1024];
        int line;
        while((line=bufferedInputStream.read(bytes))!=-1){
            System.out.println(line);
            System.out.print(new String(bytes,0,bytes.length));
        }
        bufferedInputStream.close();

    }
}
