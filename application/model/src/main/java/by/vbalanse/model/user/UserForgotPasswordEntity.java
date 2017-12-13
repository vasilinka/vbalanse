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

import by.vbalanse.model.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(
    name = UserForgotPasswordEntity.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = UserForgotPasswordEntity.COLUMN_CAS_USER_ID),
        @UniqueConstraint(columnNames = UserForgotPasswordEntity.COLUMN_KEY)
    }
)
public class UserForgotPasswordEntity extends AbstractEntity<Long> {

  final public static String TABLE_NAME = "scrt_forgot_password";
  final public static String COLUMN_CAS_USER_ID = "cas_user_id";
  final public static String COLUMN_KEY = "key_";
  final public static String COLUMN_DATE_OF_CREATE = "date_of_create_";

  @Id
  @Column(name = COLUMN_CAS_USER_ID)
  private Long casUserId;

  @Size(max = 255)
  @Column(name = COLUMN_KEY)
  private String key;

  @NotNull
  @Column(name = COLUMN_DATE_OF_CREATE)
  private Date dateOfCreate;

  public UserForgotPasswordEntity() {
  }

  public Long getId() {
    return getCasUserId();
  }

  public void setCasUserId(Long casUserId) {
    this.casUserId = casUserId;
  }

  public Long getCasUserId() {
    return casUserId;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Date getDateOfCreate() {
    return dateOfCreate;
  }

  public void setDateOfCreate(Date dateOfCreate) {
    this.dateOfCreate = dateOfCreate;
  }
}