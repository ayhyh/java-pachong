
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-28
 * @time: 19:32
 */
public class Tess4j {
    public static void main(String[] args) throws IOException, TesseractException {
        String str=getStuInfo2.Tess4j.getImgWord("C:\\Users\\Administrator\\Desktop\\test.png");
        System.out.println(str);
    }
}
