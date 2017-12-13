package by.vbalanse.rest;

import by.vbalanse.facade.article.ArticleInfo;
import by.vbalanse.facade.article.BonusInfo;
import by.vbalanse.facade.article.ImageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Vasilina on 24.03.2015.
 */
public class PsychologistInfo {

  private List<ArticleInfo> articles;
  private BaseUserInfo userInfo;
  private List<BonusInfo> bonuses;
  private Map<String, NameIdJsonInfo> userMeta;

  private ImageInfo avatar;
  private List<ImageInfo> gallery;

  public List<ArticleInfo> getArticles() {
    return articles;
  }

  public void setArticles(List<ArticleInfo> articles) {
    this.articles = articles;
  }

  public BaseUserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(BaseUserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public List<BonusInfo> getBonuses() {
    return bonuses;
  }

  public void setBonuses(List<BonusInfo> bonuses) {
    this.bonuses = bonuses;
  }

  public ImageInfo getAvatar() {
    return avatar;
  }

  public void setAvatar(ImageInfo avatar) {
    this.avatar = avatar;
  }

  public Map<String, NameIdJsonInfo> getUserMeta() {
    return userMeta;
  }

  public void setUserMeta(Map<String, NameIdJsonInfo> userMeta) {
    this.userMeta = userMeta;
  }

  public List<ImageInfo> getGallery() {
    return gallery;
  }

  public void setGallery(List<ImageInfo> gallery) {
    this.gallery = gallery;
  }
}
