package by.vbalanse.model.user;

import by.vbalanse.model.common.AbstractManagedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Vasilina on 06.04.2015.
 */
@Entity
@Table(
    name = UserInfoKeyValueEntity.TABLE_NAME
)
public class UserInfoKeyValueEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "scrt_user_meta";

  public static final String COLUMN_KEY = "key_";
  public static final String COLUMN_VALUE = "value_";
  private static final String COLUMN_FK_USER = UserEntity.TABLE_NAME + "$" + "user";

  @Column(name = COLUMN_KEY)
  private String key;

  @Column(name = COLUMN_VALUE)
  private String value;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_USER)
  private UserEntity userEntity;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }
}
