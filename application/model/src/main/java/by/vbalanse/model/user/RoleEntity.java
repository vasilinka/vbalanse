/*
 * This Learning Management System (“Software”) is the exclusive and sole property of Baja Education. Inc. (“Baja”).
 * Baja has the sole rights to copy the software, create derivatives or modified versions of it, distribute copies
 * to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights which also includes
 * indirect copying  such as unauthorized translation of the code into a different programming language without
 * written explicit permission from Baja is an infringer and subject to liability for damages or statutory fines.
 * Interested parties may contact bpozos@gmail.com.
 *
 * (c) 2012 Baja Education
 */

package by.vbalanse.model.user;

import by.vbalanse.model.common.AbstractManagedEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(
    name = RoleEntity.TABLE_NAME
)
public class RoleEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "scrt_role";

  public static final String COLUMN_CODE = "code_";
  public static final String COLUMN_DESCRIPTION = "description_";

  @NotNull
  @Size(max = 128)
  @Column(name = COLUMN_CODE)
  @Index(name = RoleEntity.TABLE_NAME + RoleEntity.DELIMITER_INDEX + RoleEntity.COLUMN_CODE,
      columnNames = {RoleEntity.COLUMN_CODE})
  private String code;

  @NotNull
  @Lob
  @Column(name = COLUMN_DESCRIPTION)
  private String description;

  public RoleEntity() {
  }

  public RoleEntity(String code, String description) {
//        this.application = application;
    this.code = code;
    this.description = description;
  }

//    public ApplicationEntity getApplication() {
//        return application;
//    }
//
//    public void setApplication(ApplicationEntity application) {
//        this.application = application;
//    }

  public void setRoleType(RoleTypeEnum roleType) {
    this.code = roleType.getCode();
  }

  public RoleTypeEnum getRoleType() {
    return RoleTypeEnum.parse(this.code);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return this.description;
  }

}
