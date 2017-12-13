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
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(
    name = RoleRightEntity.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {RoleRightEntity.COLUMN_FK_ROLE, RoleRightEntity.COLUMN_CODE_OF_RIGHT, RoleRightEntity.COLUMN_CODE_OF_ASSET})
    }
)
public class RoleRightEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "scrt_role_right";


  public static final String COLUMN_FK_ROLE = "fk_" + RoleEntity.TABLE_NAME;
  public static final String COLUMN_CODE_OF_RIGHT = "code_of_right_";
  public static final String COLUMN_CODE_OF_ASSET = "code_of_asset_";

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_ROLE)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_ROLE)
  private RoleEntity role;

  @NotNull
  @Size(max = 128)
  @Column(name = COLUMN_CODE_OF_RIGHT)
  private String codeOfRight;

  @NotNull
  @Size(max = 128)
  @Column(name = COLUMN_CODE_OF_ASSET)
  private String codeOfAsset;

  public RoleRightEntity() {
  }

  public RoleRightEntity(RoleEntity role, String codeOfAsset, String codeOfRight) {
    this.role = role;
    this.codeOfRight = codeOfRight;
    this.codeOfAsset = codeOfAsset;
  }

  public RoleRightEntity(Long id, RoleEntity role, String codeOfAsset, String codeOfRight) {
    super(id);
    this.role = role;
    this.codeOfRight = codeOfRight;
    this.codeOfAsset = codeOfAsset;
  }

  public RoleEntity getRole() {
    return role;
  }

  public void setRole(RoleEntity role) {
    this.role = role;
  }

  public String getCodeOfRight() {
    return codeOfRight;
  }

  public void setCodeOfRight(String codeOfRight) {
    this.codeOfRight = codeOfRight;
  }

  public String getCodeOfAsset() {
    return codeOfAsset;
  }

  public void setCodeOfAsset(String codeOfAsset) {
    this.codeOfAsset = codeOfAsset;
  }
}