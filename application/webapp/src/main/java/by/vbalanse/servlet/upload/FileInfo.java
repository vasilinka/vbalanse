package by.vbalanse.servlet.upload;

/**
 * Created by Vasilina on 25.03.2015.
 */
public class FileInfo {
  String name;
  int size = 2000;
  private Long id;
  String url;
  String thumbnailUrl;
  String deleteUrl = "/";
  String deleteType = "DELETE";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public String getDeleteUrl() {
    return deleteUrl;
  }

  public void setDeleteUrl(String deleteUrl) {
    this.deleteUrl = deleteUrl;
  }

  public String getDeleteType() {
    return deleteType;
  }

  public void setDeleteType(String deleteType) {
    this.deleteType = deleteType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
