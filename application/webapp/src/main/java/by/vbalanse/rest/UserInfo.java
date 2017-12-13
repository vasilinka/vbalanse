package by.vbalanse.rest;

/**
* writeme: Should be the description of the class
*
* @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
*/
public class UserInfo {
  private String email;
  private String password;

  public UserInfo() {
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
