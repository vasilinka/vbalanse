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
    name = UserRightEntity.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {UserRightEntity.COLUMN_FK_USER, UserRightEntity.COLUMN_CODE_OF_RIGHT, UserRightEntity.COLUMN_CODE_OF_ASSET})
    }
)
public class UserRightEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "scrt_user_right";


  public static final String COLUMN_FK_USER = "fk_" + UserEntity.TABLE_NAME;
  public static final String COLUMN_CODE_OF_RIGHT = "code_of_right_";
  public static final String COLUMN_CODE_OF_ASSET = "code_of_asset_";
  public static final String COLUMN_ALLOW = "allow_";

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_USER)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_USER)
  private UserEntity user;

  @NotNull
  @Size(max = 128)
  @Column(name = COLUMN_CODE_OF_RIGHT)
  private String codeOfRight;

  @NotNull
  @Size(max = 128)
  @Column(name = COLUMN_CODE_OF_ASSET)
  private String codeOfAsset;

  @NotNull
  @Column(name = COLUMN_ALLOW, columnDefinition = "BIT", length = 1)
  private boolean allow;

  public UserRightEntity() {
  }

  public UserRightEntity(UserEntity user, String codeOfAsset, String codeOfRight, boolean allow) {
    this.user = user;
    this.codeOfRight = codeOfRight;
    this.codeOfAsset = codeOfAsset;
    this.allow = allow;
  }

  public UserRightEntity(Long id, UserEntity user, String codeOfAsset, String codeOfRight) {
    super(id);
    this.user = user;
    this.codeOfRight = codeOfRight;
    this.codeOfAsset = codeOfAsset;
  }

  public boolean isAllow() {
    return allow;
  }

  public void setAllow(boolean allow) {
    this.allow = allow;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
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