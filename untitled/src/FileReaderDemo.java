import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-20
 * @time: 12:36
 * 文件读取
 */
public class FileReaderDemo {

    public static void main(String[] args) throws IOException {
        FileReader fileReader=new FileReader("D:\\test.txt");
        int ch=0;
        int count=0;
        char[]  chars=new char[1024];
        //read(chars)将读取的数放入字符数组中
        while((ch= fileReader.read(chars))!=-1){
            System.out.print(new String(chars,0,ch));
            count++;
        }
        System.out.println(chars.length);
        System.out.println(fileReader.getEncoding());
        System.out.println(count);
        fileReader.close();
    }
}
