package by.vbalanse.servlet.upload;

/**
 * Created by Vasilina on 04.04.2015.
 */
public class FullResizeInfo {
  ResizeInfo crop;
  FileInfo file;

  public ResizeInfo getCrop() {
    return crop;
  }

  public void setCrop(ResizeInfo crop) {
    this.crop = crop;
  }

  public FileInfo getFile() {
    return file;
  }

  public void setFile(FileInfo file) {
    this.file = file;
  }
}
