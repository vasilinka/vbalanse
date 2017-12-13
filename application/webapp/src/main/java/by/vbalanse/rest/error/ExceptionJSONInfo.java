package by.vbalanse.rest.error;

/**
* writeme: Should be the description of the class
*
* @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
*/
public class ExceptionJSONInfo {

  public static final String VALIDATION_EXCEPTION = "V";
  public static final String UNKNOWN_EXCEPTION = "U";

  private String url;
  private String message;
  private int status;
  private String type;

  public ExceptionJSONInfo() {
    this(UNKNOWN_EXCEPTION);
  }

  public ExceptionJSONInfo(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
