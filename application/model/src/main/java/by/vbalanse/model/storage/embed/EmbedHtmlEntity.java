package by.vbalanse.model.storage.embed;

import by.vbalanse.model.common.AbstractManagedEntity;
import org.hibernate.validator.constraints.Length;

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
@Table(name = EmbedHtmlEntity.TABLE_NAME)
public class EmbedHtmlEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "embed_html";
  public static final String COLUMN_EMBED_HTML = "embed_html_";

  @NotNull
  @Length(max = 1000)
  @Column(name = COLUMN_EMBED_HTML)
  private String embedHtml;

  public String getEmbedHtml() {
    return embedHtml;
  }

  public void setEmbedHtml(String embedHtml) {
    this.embedHtml = embedHtml;
  }
}
