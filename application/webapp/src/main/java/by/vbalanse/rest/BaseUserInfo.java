package by.vbalanse.rest;

import by.vbalanse.convert.Convert;
import by.vbalanse.facade.article.HasIdInfo;

/**
 * Created by Vasilina on 28.03.2015.
 */
public class BaseUserInfo extends HasIdInfo {

  @Convert(names = {"firstName", " ", "lastName"})
  private String fullname;

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }
}
