package by.vbalanse.facade.article;

import by.vbalanse.convert.NoConvert;
import by.vbalanse.facade.article.HasIdInfo;

/**
 * Created by Vasilina on 28.03.2015.
 */
public class ImageInfo extends HasIdInfo {
  @NoConvert
  private String name;
  @NoConvert
  private String url;
  @NoConvert
  private Long newImageId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getNewImageId() {
    return newImageId;
  }

  public void setNewImageId(Long newImageId) {
    this.newImageId = newImageId;
  }
}
