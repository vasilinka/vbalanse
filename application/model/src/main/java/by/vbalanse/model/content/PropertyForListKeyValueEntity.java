package by.vbalanse.model.content;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * for case when we have list of properties
 */
@Entity
@Table(
    name = PropertyForListKeyValueEntity.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {PropertyForListKeyValueEntity.USER_FIELD, PropertyForListKeyValueEntity.COLUMN_KEY, PropertyForListKeyValueEntity.COLUMN_PRIORITY})
    }
)
public class PropertyForListKeyValueEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "scrt_prop_meta";

  public static final String COLUMN_KEY = "key_";
  public static final String COLUMN_CODE = "code_";
  public static final String COLUMN_VALUE = "value_";
  public static final String COLUMN_PRIORITY = "priority_";
  private static final String COLUMN_FK_USER = UserEntity.TABLE_NAME + "$" + "user";
  public static final String USER_FIELD = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_USER;

  @Column(name = COLUMN_CODE)
  //sample - bonus
  private String code;

  @Column(name = COLUMN_KEY)
  //sample - icon or description or title
  private String key;

  @Column(name = COLUMN_PRIORITY)
  //sample - order number - 0,1,2,3 for list
  //todo: make constraint for user key and priority
  private int order;

  @Column(name = COLUMN_VALUE)
  //sample - real value
  private String value;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = USER_FIELD)
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

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }
}
