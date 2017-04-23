package mk.trajkovikj.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by srbo on 23-Apr-17.
 */
public class ImageUtils {

    public static SliceType sliceType = SliceType.VERTICAL;

    public static BufferedImage readBufferedImage(File imageFile) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
        }
        return img;
    }

    public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);
        return dest;
    }

    public void saveImage(BufferedImage bi, String fileName) {
        try {
            // retrieve image
            File outputfile = new File( fileName + ".png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {

        }
    }


    public void processImage(BufferedImage image, int divisions) {
        new File("C:\\LoadingSpriteCreator\\").mkdirs();
        if (sliceType == SliceType.VERTICAL) processVerticalImage(image, divisions);
        else processHorizontalImage(image, divisions);
    }

    public void processVerticalImage(BufferedImage image, int divisions) {
        int ppd = calculatePixelsPerDivision(image.getHeight(), divisions);

        for (int i = 1; i <= divisions; i++) {
            Rectangle rectangle = new Rectangle(image.getWidth(), i * ppd);
            BufferedImage croppedImage = cropImage(image, rectangle);
            saveImage(croppedImage, "C:\\LoadingSpriteCreator\\lsc_" + i);
        }
        double leftover = calculatePixelsPerDivisionRemainder(image.getHeight(), divisions);
        if(leftover > 0) {
            int lastEnum = divisions + 1;
            saveImage(image, "C:\\LoadingSpriteCreator\\lsc_" + lastEnum);
        }
    }

    public void processHorizontalImage(BufferedImage image, int divisions) {
        int ppd = calculatePixelsPerDivision(image.getWidth(), divisions);

        for (int i = 1; i <= divisions; i++) {
            Rectangle rectangle = new Rectangle(i * ppd, image.getHeight());
            BufferedImage croppedImage = cropImage(image, rectangle);
            saveImage(croppedImage, "C:\\LoadingSpriteCreator\\lsc_" + i);
        }
        double leftover = calculatePixelsPerDivisionRemainder(image.getWidth(), divisions);
        if(leftover > 0) {
            int lastEnum = divisions + 1;
            saveImage(image, "C:\\LoadingSpriteCreator\\lsc_" + lastEnum);
        }
    }


    public int calculatePixelsPerDivision(int magnitude, int divisions) {
        return (int)(magnitude / divisions);
    }

    public double calculatePixelsPerDivisionRemainder(int magnitude, int divisions) {
        return magnitude - calculatePixelsPerDivision(magnitude, divisions);
    }

}
