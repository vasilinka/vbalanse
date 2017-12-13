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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * now first name and last name can be null when fast register -
 * only email and password required
 */
@Entity
@Table(
    name = UserEntity.TABLE_NAME
)
public class UserEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "scrt_user";

  public static final String COLUMN_FK_ROLE = "fk_" + RoleEntity.TABLE_NAME;
  public static final String COLUMN_FIRST_NAME = "first_name_";
  public static final String COLUMN_LAST_NAME = "last_name_";
  public static final String COLUMN_PASSWORD_MD5_HASH = "password_md5_hash_";
  public static final String COLUMN_LAST_ACTIVITY_DATE = "last_activity_date_";
  public static final String COLUMN_EMAIL = "email_";
  public static final String COLUMN_CONFIRMED = "confirmed_";
  public static final String COLUMN_CONFIRM_LINK = "confirm_link_";
  public static final String COLUMN_CHANGE_PASSWORD_LINK = "change_password_parameter_";

  @Column(name = COLUMN_FIRST_NAME)
  private String firstName;

  @Column(name = COLUMN_LAST_NAME)
  private String lastName;

  @Column(name = COLUMN_CONFIRMED)
  private boolean confirmed;

  @Column(name = COLUMN_CONFIRM_LINK)
  private String confirmLink;

  @Column(name = COLUMN_CHANGE_PASSWORD_LINK)
  private String changeEmailLink;

  @NotNull
  @Size(max = 255)
  @Column(name = COLUMN_PASSWORD_MD5_HASH)
  private String passwordMD5hash;

  @NotNull
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_ROLE)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_ROLE)
  private RoleEntity role;


  @Column(name = COLUMN_LAST_ACTIVITY_DATE)
  private Date lastActivityDate;

  @NotNull
  @Email
  @Column(name = COLUMN_EMAIL, unique = true)
  private String email;

  public UserEntity() {
  }

  public String getPasswordMD5hash() {
    return passwordMD5hash;
  }

  public void setPasswordMD5hash(String passwordMD5hash) {
    this.passwordMD5hash = passwordMD5hash;
  }

  public Date getLastActivityDate() {
    return lastActivityDate;
  }

  public void setLastActivityDate(Date lastActivityDate) {
    this.lastActivityDate = lastActivityDate;
  }

  public RoleEntity getRole() {
    return role;
  }

  public void setRole(RoleEntity role) {
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
  public String toString() {
    return firstName + " " + lastName;
  }

  public String getFirstLastName() {
    return firstName + " " + lastName;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  public String getConfirmLink() {
    return confirmLink;
  }

  public void setConfirmLink(String confirmLink) {
    this.confirmLink = confirmLink;
  }

  public String getChangeEmailLink() {
    return changeEmailLink;
  }

  public void setChangeEmailLink(String changeEmailLink) {
    this.changeEmailLink = changeEmailLink;
  }
}
