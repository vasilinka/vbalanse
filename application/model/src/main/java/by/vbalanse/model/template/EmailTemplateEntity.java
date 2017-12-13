package by.vbalanse.model.template;

import by.vbalanse.model.common.AbstractManagedEntity;

import javax.persistence.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = EmailTemplateEntity.TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = EmailTemplateEntity.COLUMN_TITLE))
public class EmailTemplateEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "tmpl_email";
  public static final String COLUMN_TITLE = "title_";
  public static final String COLUMN_THEME = "theme_";
  public static final String COLUMN_CONTENT = "content_";

  public static final String SUCCESS_REGISTER_CODE = "Register_success";
  public static final String PASSWORD_CHANGE_CODE = "Password_change";

  @Column(name = COLUMN_TITLE)
  private String title;

  @Column(name = COLUMN_THEME)
  private String theme;

  @Lob
  @Column(name = COLUMN_CONTENT)
  private String content;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
