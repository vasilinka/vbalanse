package by.vbalanse.model.psychologist;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = BonusEntity.TABLE_NAME)
public class BonusEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "psy_bonus";
  public static final String COLUMN_BONUS_NAME = "title_";
  public static final String COLUMN_ICON_NAME = "icon_name_";
  public static final String COLUMN_DIRTY = "dirty_";
  public static final String COLUMN_ORDER = "order_";
  public static final String COLUMN_BONUS_DESCRIPTION = "description_";
  public static final String COLUMN_FK_USER = UserEntity.TABLE_NAME;

  @Column(name = COLUMN_BONUS_NAME)
  //@NotNull
  //all values can be null, but bonus will be marked as dirty if some disappear
  private String title;

  //name in fontello font
  @Column(name = COLUMN_ICON_NAME)
  private String iconName;

  @Column(name = COLUMN_BONUS_DESCRIPTION)
  //@NotNull
  private String description;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_USER)
  private UserEntity userEntity;

  @Column(name = COLUMN_DIRTY)
  //marked as dirty when some required property absent, not normalized form of db
  private boolean dirty;

  @Column(name = COLUMN_ORDER)
  //now not used
  private int order;

  public BonusEntity() {
  }

  public BonusEntity(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String code) {
    this.description = code;
  }

  @Override
  public String toString() {
    return title;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity psychologistEntity) {
    this.userEntity = psychologistEntity;
  }

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getIconName() {
    return iconName;
  }

  public void setIconName(String iconName) {
    this.iconName = iconName;
  }
}
