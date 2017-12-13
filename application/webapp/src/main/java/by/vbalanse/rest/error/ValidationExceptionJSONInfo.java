package by.vbalanse.rest.error;

import by.vbalanse.rest.error.ExceptionJSONInfo;

import java.util.Map;

/**
* writeme: Should be the description of the class
*
* @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
*/
public class ValidationExceptionJSONInfo extends ExceptionJSONInfo {

  public ValidationExceptionJSONInfo() {
    super(ExceptionJSONInfo.VALIDATION_EXCEPTION);
  }

  public ValidationExceptionJSONInfo(Map<String, String> fieldErrors) {
    this();
    this.fieldErrors = fieldErrors;
  }

  private Map<String, String> fieldErrors;

  public Map<String, String> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(Map<String, String> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }
}
