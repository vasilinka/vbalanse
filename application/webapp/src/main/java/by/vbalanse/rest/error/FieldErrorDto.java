package by.vbalanse.rest.error;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class FieldErrorDto {
  private String name;
  private String error;

  public FieldErrorDto() {
  }

  public FieldErrorDto(String name, String error) {
    this.name = name;
    this.error = error;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
