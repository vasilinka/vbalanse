package by.vbalanse.model.article;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = ArticleCategoryEntity.TABLE_NAME)
public class ArticleCategoryEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "artcl_category";
  public static final String COLUMN_TITLE = "title_";
  public static final String COLUMN_CATEGORY = ArticleCategoryEntity.TABLE_NAME + "$parent";

  @OneToOne
  @JoinColumn(name="image_id")
  private AttachmentImageEntity image;

  @NotNull
  @Column(name = COLUMN_TITLE)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  //@JoinColumn(name="category_id")
  //@ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_CATEGORY)
  //@OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name="category_id")
  private ArticleCategoryEntity category;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private Set<ArticleCategoryEntity> subCateories;

  public ArticleCategoryEntity() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArticleCategoryEntity getCategory() {
    return category;
  }

  public void setCategory(ArticleCategoryEntity category) {
    this.category = category;
  }

  @Override
  public String toString() {
    return title;
  }

  public AttachmentImageEntity getImage() {
    return image;
  }

  public void setImage(AttachmentImageEntity image) {
    this.image = image;
  }

  public Set<ArticleCategoryEntity> getSubCateories() {
    return subCateories;
  }
}
