package by.vbalanse.dao.common;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:v.terehova@itision.com">Vasilina Terehova</a>
 */
public enum OrderByEnum {

  ASCENDING("asc"),
  DESCENDING("desc");

  private String sqlValue;

  private OrderByEnum(String sqlValue) {
    this.sqlValue = sqlValue;
  }

  public String getSqlValue() {
    return sqlValue;
  }

  public static String getSqlValue(boolean isAscending) {
    if (isAscending) {
      return ASCENDING.getSqlValue();
    } else {
      return DESCENDING.getSqlValue();
    }
  }
}
