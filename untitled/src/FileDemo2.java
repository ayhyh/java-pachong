import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-20
 * @time: 22:24
 */
public class FileDemo2 {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\毕向东_Java基础源代码\\day04\\fsf\\aaa\\sdf");
        if(!file.exists()){
            file.mkdirs();
            System.out.println("文件不存在，现在创建");
        }else{
            System.out.println("文件存在");
        }
    }
}