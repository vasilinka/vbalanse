package by.vbalanse.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class ValidationException extends Exception {

  Map<String, String> fieldErrors;

  public ValidationException() {
    super();
  }

  public ValidationException(String fieldName, String errorMessage) {
    putField(fieldName, errorMessage);
  }

  private void putField(String fieldName, String errorMessage) {
    if (fieldErrors == null) {
      fieldErrors = new HashMap<>();
    }
    fieldErrors.put(fieldName, errorMessage);
  }

  public ValidationException(Map<String, String> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  public void addFieldError(String fieldName, String message) {
    putField(fieldName, message);
  }

  public Map<String, String> getFieldErrors() {
    return fieldErrors;
  }
}
