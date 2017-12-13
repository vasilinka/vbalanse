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

import org.apache.commons.io.FilenameUtils;

public class ImageToolsFactory {

  private static ImageTools pngImageTools;
  private static ImageTools jpegImageTools;
  private static ImageTools gifImageTools;
  private static ImageTools bmpImageTools;

  public static ImageTools getImageTools(String fileName) {
    String extension = FilenameUtils.getExtension(fileName);

    if (extension.compareToIgnoreCase("jpg") == 0 || extension.compareToIgnoreCase("jpeg") == 0) {
      return getJPEGImageTools();
    } else if (extension.compareToIgnoreCase("png") == 0) {
      return getPNGImageTools();
    } else if (extension.compareToIgnoreCase("gif") == 0) {
      return getGIFImageTools();
    } else if (extension.compareToIgnoreCase("bmp") == 0) {
      return getBMPImageTools();
    }

    throw new RuntimeException();
  }

  private static ImageTools getPNGImageTools() {
    if (pngImageTools == null) {
      pngImageTools = new PNGImageTools();
    }

    return pngImageTools;
  }

  private static ImageTools getJPEGImageTools() {
    if (jpegImageTools == null) {
      jpegImageTools = new JPEGImageTools();
    }

    return jpegImageTools;
  }

  private static ImageTools getGIFImageTools() {
    if (gifImageTools == null) {
      gifImageTools = new GIFImageTools();
    }

    return gifImageTools;
  }

  public static ImageTools getBMPImageTools() {
    if (bmpImageTools == null) {
      bmpImageTools = new BMPImageTools();
    }

    return bmpImageTools;
  }
}
