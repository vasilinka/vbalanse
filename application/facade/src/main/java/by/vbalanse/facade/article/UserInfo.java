package by.vbalanse.facade.article;

/**
 * Created by Vasilina on 04.02.2015.
 */
public class UserInfo extends HasIdInfo {
  private String firstName;
  private String lastName;

  public UserInfo() {
  }

  public UserInfo(Long id, String firstName, String lastName) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
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
}
