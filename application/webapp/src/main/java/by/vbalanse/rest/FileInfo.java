package by.vbalanse.rest;

/**
* writeme: Should be the description of the class
*
* @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
*/
public class FileInfo {

  private String name;
  private String fullUrl;
  private String absolutePath;
  private int width;
  private int height;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFullUrl() {
    return fullUrl;
  }

  public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  public String getAbsolutePath() {
    return absolutePath;
  }

  public void setAbsolutePath(String absolutePath) {
    this.absolutePath = absolutePath;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
