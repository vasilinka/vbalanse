package by.vbalanse.model.content;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.user.UserEntity;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:4vasilinka@gmail.com">Vasiina Terehova</a>
 */
@Entity
@Table(name = FeedbackEntity.TABLE_NAME)
public class FeedbackEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "feedback";
  public static final String COLUMN_FIRST_NAME = "first_name_";
  public static final String COLUMN_EMAIL = "email_";
  public static final String COLUMN_MESSAGE = "message_";
  public static final String COLUMN_PSYCHOLOGIST_EVALUATED = UserEntity.TABLE_NAME + "$evaluated";

  @NotNull
  @Column(name = COLUMN_FIRST_NAME)
  public String firstName;
  
  @Email
  @Column(name = COLUMN_EMAIL, unique = true)
  public String email;

  @NotNull
  @Column(name = COLUMN_MESSAGE, nullable = false)
  public String message;

 /* @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_PSYCHOLOGIST_EVALUATED)*/
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_PSYCHOLOGIST_EVALUATED)
  private UserEntity userEntity$evaluated;

  public UserEntity getUserEntity$evaluated() {
    return userEntity$evaluated;
  }

  public void setUserEntity$evaluated(UserEntity userEntity$evaluated) {
    this.userEntity$evaluated = userEntity$evaluated;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
