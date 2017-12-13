/*
 * This Learning Management System (“Software”) is the exclusive and sole property of Baja Education. Inc. (“Baja”).
 * Baja has the sole rights to copy the software, create derivatives or modified versions of it, distribute copies
 * to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights which also includes
 * indirect copying  such as unauthorized translation of the code into a different programming language without
 * written explicit permission from Baja is an infringer and subject to liability for damages or statutory fines.
 * Interested parties may contact bpozos@gmail.com.
 *
 * (c) 2012 Baja Education
 */

package by.vbalanse.facade.storage.attachment;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ImageTools {

  public ImageFileProperties resize(String original, String resized, int maxWidth, int maxHeight) {
    ImageIcon originalImage = new ImageIcon(original);

    ImageIcon resizedImage =
        new ImageIcon(originalImage.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH));

    BufferedImage bi = new BufferedImage(
        resizedImage.getIconWidth(),
        resizedImage.getIconHeight(),
        BufferedImage.TYPE_INT_RGB
    );

    Graphics g = bi.getGraphics();
    g.drawImage(resizedImage.getImage(), 0, 0, null);

    try {
      ImageIO.write(bi, getFormatName(), new File(resized));
    } catch (IOException ioe) {
      System.out.println("Error occured saving thumbnail");
    }
    ImageFileProperties props = new ImageFileProperties();
    props.setWidth(resizedImage.getImage().getWidth(null));
    props.setHeight(resizedImage.getImage().getHeight(null));
    return props;
  }

  public ImageFileProperties cut(String original, String resized, int x, int y, int width, int height) throws IOException {
    BufferedImage image = ImageIO.read(new File(original));
    BufferedImage out = image.getSubimage(x, y, width, height);

    ImageIO.write(out, "jpg", new File(resized));
    ImageFileProperties props = new ImageFileProperties();
    props.setWidth(out.getWidth(null));
    props.setHeight(out.getHeight(null));
    return props;
  }


  public ImageFileProperties resizeToWidth(String original, String resized, int maxWidth) {
    return resize(original, resized, maxWidth, -1);
  }

  public ImageFileProperties resizeToHeight(String original, String resized, int maxHeight) {
    return resize(original, resized, -1, maxHeight);
  }

  public ImageFileProperties getImageFileProperties(String imagePath) {
    ImageFileProperties props = new ImageFileProperties();
    Image i;
    ImageIcon ii;
    ii = new ImageIcon(imagePath);
    i = ii.getImage();

    props.setWidth(i.getWidth(null));
    props.setHeight(i.getHeight(null));
    return props;
  }

  public abstract String getFormatName();

  private static void test(ImageTools imageTools) {
    String folder = "D:\\1\\" + imageTools.getFormatName() + "\\";

    for (int i = 1; i <= 4; i++) {
      String fileName = "" + i + "." + imageTools.getFormatName();
      System.out.println("File " + fileName);

      ImageFileProperties imageFileProperties = imageTools.getImageFileProperties(folder + fileName);
      System.out.println("Width " + imageFileProperties.getWidth());
      System.out.println("Height " + imageFileProperties.getHeight());

      imageTools.resizeToWidth(folder + fileName, folder + "resized" + fileName, 100);

      System.out.println();
    }
  }

  public static void main(String[] args) {
    test(new JPEGImageTools());
    test(new PNGImageTools());
    test(new GIFImageTools());
    test(new BMPImageTools());
  }

}
