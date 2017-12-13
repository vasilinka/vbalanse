package by.vbalanse.rest;

import by.vbalanse.convert.NoConvert;

/**
* writeme: Should be the description of the class
*
* @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
*/
public class JsonResult {
  @NoConvert
  private boolean success;

  public JsonResult() {
  }

  public JsonResult(Boolean success) {
    this.success = success;
  }

  public boolean getSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
