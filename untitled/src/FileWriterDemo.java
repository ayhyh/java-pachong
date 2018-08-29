import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-20
 * @time: 15:47
 */
public class FileWriterDemo {
    public static void main(String[] args) throws IOException {

        FileWriter fileWriter=new FileWriter("F:\\demo.txt");
        fileWriter.write("dadadad");
        fileWriter.write("xxxxx");
        fileWriter.close();
    }
}
