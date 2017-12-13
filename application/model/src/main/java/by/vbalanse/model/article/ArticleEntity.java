package by.vbalanse.model.article;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.user.UserEntity;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = ArticleEntity.TABLE_NAME)
@JsonIgnoreProperties()
public class ArticleEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "artcl_article";
  public static final String COLUMN_TITLE = "title_";
  public static final String COLUMN_IS_DRAFT = "is_draft_";
  public static final String COLUMN_TEXT = "text_";
  public static final String COLUMN_AUTHOR = UserEntity.TABLE_NAME + "$author";
  public static final String COLUMN_DATE_OF_PUBLISH = "date_of_publish_";
  private static final String COLUMN_CATEGORY = ArticleCategoryEntity.TABLE_NAME;
  private static final String COLUMN_ARTICLE = ArticleEntity.TABLE_NAME;
  private static final String COLUMN_TAG = TagEntity.TABLE_NAME;
  private static final String COLUMN_DEPARTMENT = DepartmentEntity.TABLE_NAME;
  private static final String COLUMN_FK_IMAGE_PHOTO = AttachmentImageEntity.TABLE_NAME + "$" + "image";

  @NotNull
  @Column(name = COLUMN_TITLE)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_IMAGE_PHOTO)
  private AttachmentImageEntity image;

  @NotNull
  @Column(name = COLUMN_DATE_OF_PUBLISH)
  private Date dateOfPublish;

  @NotNull
  @Lob
  @Column(name = COLUMN_TEXT)
  private String text;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_AUTHOR)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JoinColumn(name=COLUMN_AUTHOR)
  @JsonInclude
  private UserEntity author;

  @Column(name = COLUMN_IS_DRAFT)
  private boolean isDraft;

  //@NotNull
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinColumn(table = "categories", name = COLUMN_ARTICLE, referencedColumnName = COLUMN_CATEGORY)
  private Set<ArticleCategoryEntity> categories;

  @JsonInclude
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(table = "tags", name = COLUMN_ARTICLE, referencedColumnName = COLUMN_TAG)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_TAG)
  private Set<TagEntity> tags;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_DEPARTMENT)
  @JoinColumn(name=COLUMN_DEPARTMENT)
  private DepartmentEntity department;

  public ArticleEntity() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getDateOfPublish() {
    return dateOfPublish;
  }

  public void setDateOfPublish(Date dateOfPublish) {
    this.dateOfPublish = dateOfPublish;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public void setAuthor(UserEntity author) {
    this.author = author;
  }

  public Set<ArticleCategoryEntity> getCategories() {
    return categories;
  }

  public void setCategories(Set<ArticleCategoryEntity> categories) {
    this.categories = categories;
  }

  public DepartmentEntity getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentEntity department) {
    this.department = department;
  }

  public Set<TagEntity> getTags() {
    return tags;
  }

  public void setTags(Set<TagEntity> tags) {
    this.tags = tags;
  }

  public AttachmentImageEntity getImage() {
    return image;
  }

  public void setImage(AttachmentImageEntity image) {
    this.image = image;
  }

  public boolean isDraft() {
    return isDraft;
  }

  public void setDraft(boolean isDraft) {
    this.isDraft = isDraft;
  }
}
