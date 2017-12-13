package by.vbalanse.model.article;

import by.vbalanse.model.common.AbstractManagedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = TagEntity.TABLE_NAME)
public class TagEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "tag";
  public static final String COLUMN_TAG_NAME = "title_";
  public static final String COLUMN_TAG_CODE = "code_";
  public static final String COLUMN_USER_CREATED = "is_user_created_";

  @Column(name = COLUMN_TAG_NAME)
  @NotNull
  private String title;

  @Column(name = COLUMN_TAG_CODE)
  @NotNull
  private String code;

  @Column(name = COLUMN_USER_CREATED)
  private boolean userCreated = false;

  public TagEntity() {
  }

  public TagEntity(String title, String code) {
    this.title = title;
    this.code = code;
  }

  public TagEntity(String title, String code, boolean userCreated) {
    this.title = title;
    this.code = code;
    this.userCreated = userCreated;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return title;
  }

  public boolean isUserCreated() {
    return userCreated;
  }

  public void setUserCreated(boolean userCreated) {
    this.userCreated = userCreated;
  }
}
