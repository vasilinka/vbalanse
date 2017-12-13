package by.vbalanse.facade.article;

import by.vbalanse.convert.NoConvert;

import java.util.Date;
import java.util.List;

/**
* writeme: Should be the description of the class
*
* @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
*/
public class ArticleInfo extends HasIdInfo {
  private String title;
  private String text;
  @NoConvert
  private String excerpt;
  private Date dateOfPublish;
  private BaseCodeTitleInfo department;
  private UserInfo author;
  private List<HasIdInfo> categories;
  private List<BaseCodeTitleInfo> tags;
  @NoConvert
  private long countOfComments;
  private ImageInfo image;

  public ArticleInfo() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getDateOfPublish() {
    return dateOfPublish;
  }

  public void setDateOfPublish(Date dateOfPublish) {
    this.dateOfPublish = dateOfPublish;
  }

  public BaseCodeTitleInfo getDepartment() {
    return department;
  }

  public void setDepartment(BaseCodeTitleInfo department) {
    this.department = department;
  }

  public UserInfo getAuthor() {
    return author;
  }

  public void setAuthor(UserInfo author) {
    this.author = author;
  }

  public List<HasIdInfo> getCategories() {
    return categories;
  }

  public void setCategories(List<HasIdInfo> categories) {
    this.categories = categories;
  }

  public List<BaseCodeTitleInfo> getTags() {
    return tags;
  }

  public void setTags(List<BaseCodeTitleInfo> tags) {
    this.tags = tags;
  }

  public long getCountOfComments() {
    return countOfComments;
  }

  public void setCountOfComments(long countOfComments) {
    this.countOfComments = countOfComments;
  }

  public String getExcerpt() {
    return excerpt;
  }

  public void setExcerpt(String excerpt) {
    this.excerpt = excerpt;
  }

  public ImageInfo getImage() {
    return image;
  }

  public void setImage(ImageInfo image) {
    this.image = image;
  }
}
