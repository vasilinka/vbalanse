package by.vbalanse.model.article;

import by.vbalanse.model.common.AbstractManagedEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(
    name = TargetAuditoryEntity.TABLE_NAME
  )
public class TargetAuditoryEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "artcl_auditory";

  public static final String COLUMN_CODE = "code_";
  public static final String COLUMN_DESCRIPTION = "description_";

  @NotNull
  @Size(max = 128)
  @Column(name = COLUMN_CODE)
//  @Index(name = TargetAuditoryEntity.TABLE_NAME + TargetAuditoryEntity.DELIMITER_INDEX + TargetAuditoryEntity.COLUMN_CODE,
//      columnNames = {TargetAuditoryEntity.COLUMN_CODE})
  private String code;

  @NotNull
  @Lob
  @Column(name = COLUMN_DESCRIPTION)
  private String description;

  public TargetAuditoryEntity() {
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return description;
  }
}
