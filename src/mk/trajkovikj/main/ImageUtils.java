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

    public static SliceType sliceType = SliceType.VERTICAL_TOP;

    public static BufferedImage readBufferedImage(File imageFile) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
        }
        return img;
    }

    public static BufferedImage cropImage(BufferedImage src, Rectangle rect, int x, int y) {
        BufferedImage dest = src.getSubimage(x, y, rect.width, rect.height);
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


    public void processImage(BufferedImage image, int divisions, String outDirectoryPath) {
        new File(outDirectoryPath).mkdirs();
        switch (sliceType) {
            case VERTICAL_TOP : { processVerticalTopImage(image, divisions, outDirectoryPath); break; }
            case VERTICAL_BOTTOM: { processVerticalBottomImage(image, divisions, outDirectoryPath); break; }
            case HORIZONTAL_LEFT: { processHorizontalLeftImage(image, divisions, outDirectoryPath); break; }
            case HORIZONTAL_RIGHT: { processHorizontalRightImage(image, divisions, outDirectoryPath); break; }
            default:  break;
        }

    }

    public void processVerticalTopImage(BufferedImage image, int divisions, String outDirectoryPath) {
        int ppd = calculatePixelsPerDivision(image.getHeight(), divisions);

        for (int i = 1; i <= divisions; i++) {
            Rectangle rectangle = new Rectangle(image.getWidth(), i * ppd);
            BufferedImage croppedImage = cropImage(image, rectangle, 0, 0);
            saveImage(croppedImage, outDirectoryPath + "\\lsc_" + i);
        }
        double leftover = calculatePixelsPerDivisionRemainder(image.getHeight(), divisions);
        if(leftover > 0) {
            int lastEnum = divisions + 1;
            saveImage(image, outDirectoryPath + "\\lsc_" + lastEnum);
        }
    }

    public void processVerticalBottomImage(BufferedImage image, int divisions, String outDirectoryPath) {
        int ppd = calculatePixelsPerDivision(image.getHeight(), divisions);

        for (int i = 1; i <= divisions; i++) {
            Rectangle rectangle = new Rectangle(image.getWidth(), i * ppd);
            BufferedImage croppedImage = cropImage(image, rectangle, 0, image.getHeight() - i * ppd);
            saveImage(croppedImage, outDirectoryPath + "\\lsc_" + i);
        }
        double leftover = calculatePixelsPerDivisionRemainder(image.getHeight(), divisions);
        if(leftover > 0) {
            int lastEnum = divisions + 1;
            saveImage(image, outDirectoryPath + "\\lsc_" + lastEnum);
        }
    }

    public void processHorizontalLeftImage(BufferedImage image, int divisions, String outDirectoryPath) {
        int ppd = calculatePixelsPerDivision(image.getWidth(), divisions);

        for (int i = 1; i <= divisions; i++) {
            Rectangle rectangle = new Rectangle(i * ppd, image.getHeight());
            BufferedImage croppedImage = cropImage(image, rectangle, 0, 0);
            saveImage(croppedImage, outDirectoryPath + "\\lsc_" + i);
        }
        double leftover = calculatePixelsPerDivisionRemainder(image.getWidth(), divisions);
        if(leftover > 0) {
            int lastEnum = divisions + 1;
            saveImage(image, outDirectoryPath + "\\lsc_" + lastEnum);
        }
    }

    public void processHorizontalRightImage(BufferedImage image, int divisions, String outDirectoryPath) {
        int ppd = calculatePixelsPerDivision(image.getWidth(), divisions);

        for (int i = 1; i <= divisions; i++) {
            Rectangle rectangle = new Rectangle(i * ppd, image.getHeight());
            BufferedImage croppedImage = cropImage(image, rectangle, image.getWidth() - i * ppd, 0);
            saveImage(croppedImage, outDirectoryPath + "\\lsc_" + i);
        }
        double leftover = calculatePixelsPerDivisionRemainder(image.getWidth(), divisions);
        if(leftover > 0) {
            int lastEnum = divisions + 1;
            saveImage(image, outDirectoryPath + "\\lsc_" + lastEnum);
        }
    }


    public int calculatePixelsPerDivision(int magnitude, int divisions) {
        return (int)(magnitude / divisions);
    }

    public double calculatePixelsPerDivisionRemainder(int magnitude, int divisions) {
        return magnitude - calculatePixelsPerDivision(magnitude, divisions);
    }

}
