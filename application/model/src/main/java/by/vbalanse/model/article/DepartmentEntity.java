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
@Table(name = DepartmentEntity.TABLE_NAME)
public class DepartmentEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "department";
  public static final String COLUMN_DEPARTMENT_NAME = "title_";
  public static final String COLUMN_DEPARTMENT_CODE = "code_";

  @Column(name = COLUMN_DEPARTMENT_NAME)
  @NotNull
  private String title;

  @Column(name = COLUMN_DEPARTMENT_CODE)
  @NotNull
  private String code;

  public DepartmentEntity() {
  }

  public DepartmentEntity(String title, String code) {
    this.title = title;
    this.code = code;
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
}
