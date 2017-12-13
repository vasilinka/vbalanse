package by.vbalanse.rest;

/**
 * Created by Vasilina on 06.04.2015.
 */
public class NameIdJsonInfo extends IdJsonResult {
  private String value;

  public NameIdJsonInfo() {
  }

  public NameIdJsonInfo(Long id, String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
