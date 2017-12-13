package by.vbalanse.model.<%- path %>;

import by.vbalanse.model.common.AbstractManagedEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = <%- entityName %>Entity.TABLE_NAME)
public class <%- entityName %>Entity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "<%= entityName.toLowerCase() %>";
  public static final String COLUMN_<%= entityName.toUpperCase() %>_NAME = "title_";
  public static final String COLUMN_<%= entityName.toUpperCase() %>_CODE = "code_";

  @Column(name = COLUMN_<%= entityName.toUpperCase() %>_NAME)
  @NotNull
  private String title;

  @Column(name = COLUMN_<%= entityName.toUpperCase() %>_CODE)
  @NotNull
  private String code;

  public <%- entityName %>Entity() {
  }

  public <%- entityName %>Entity(String title, String code) {
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
