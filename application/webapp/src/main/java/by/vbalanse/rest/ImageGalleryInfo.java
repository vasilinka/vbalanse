package by.vbalanse.rest;

import by.vbalanse.facade.article.ImageInfo;

import java.util.List;

/**
 * Created by Vasilina on 15.04.2015.
 */
public class ImageGalleryInfo {
  List<ImageInfo> gallery;

  public List<ImageInfo> getGallery() {
    return gallery;
  }

  public void setGallery(List<ImageInfo> gallery) {
    this.gallery = gallery;
  }
}
