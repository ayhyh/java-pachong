package getStuInfo1; /**
 * Created by Intellij IDEA.
 *
 * @author: 霍运浩
 * @date: 2018-08-21
 * @time: 19:17
 */

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Tess4j {
    public static String getImgWord(String imgName) throws IOException, TesseractException {
//        BufferedImage originImage = ImageIO.read(new File("F:\\imgExtend\\img-5bd8e35.jpeg"));
//        BufferedImage processedImage = getStuInfo.Tess4j.binaryImage(getStuInfo.Tess4j.grayImage(getStuInfo.Tess4j.clipImage(originImage)));
//        ImageIO.write(processedImage, "jpeg", new File("F:\\imgFormat\\img-5bd8e35.jpeg"));
        String imgWord=null;

            File imageFile = new File(imgName);
            if (imageFile.exists()) {
                System.out.println("我进来了");
                System.out.println(getFormatInFile(imageFile));
            }
            System.out.println(imgName);
            Tesseract instance = new Tesseract();
//            instance.setTessVariable("tessedit_whitelist","0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUWIXVZ");
            instance.setDatapath("C:\\Users\\Administrator\\Desktop\\Tess4J\\tessdata");
            instance.setLanguage("eng");
            instance.setLanguage("");
            //将验证码图片的内容识别为字符串
            String result = instance.doOCR(imageFile);
            System.out.println(result.trim().replace(" ",""));
            imgWord=result.trim().replace(" ","");

        return imgWord;
    }
    public static String getFormatInFile(File f) {
        return getFormatName(f);
    }
    private static String getFormatName(Object o) {
        try {
            // Create an image input stream on the image
            ImageInputStream iis = ImageIO.createImageInputStream(o);

            // Find all image readers that recognize the image format
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                // No readers found
                return null;
            }

            // Use the first reader
            ImageReader reader = iter.next();

            // Close stream
            iis.close();

            // Return the format name
            return reader.getFormatName();
        } catch (IOException e) {
            //
        }

        // The image could not be read
        return null;
    }
    /**
     * 裁剪图片：去掉黑边
     */
    public static BufferedImage clipImage(BufferedImage srcImage) {
        return srcImage.getSubimage(1, 1, srcImage.getWidth() - 2, srcImage.getHeight() - 2);
    }

    /**
     * 灰度化
     */
    public static BufferedImage grayImage(BufferedImage srcImage) {
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY));
    }

    /**
     * 二值化
     */
    public static BufferedImage binaryImage(BufferedImage srcImage) {
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY));
    }

    public static BufferedImage copyImage(BufferedImage srcImage, BufferedImage destImage) {
        for (int y = 0; y < srcImage.getHeight(); y++) {
            for (int x = 0; x < srcImage.getWidth(); x++) {
                destImage.setRGB(x, y, srcImage.getRGB(x, y));
            }
        }
        return destImage;
    }
}
