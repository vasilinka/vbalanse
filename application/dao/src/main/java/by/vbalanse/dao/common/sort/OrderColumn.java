package by.vbalanse.dao.common.sort;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:v.terehova@itision.com">Vasilina Terehova</a>
 */
public class OrderColumn {

  public OrderColumn(String column, boolean asc) {
    this.column = column;
    this.asc = asc;
  }

  private String column;

  private boolean asc;

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public boolean isAsc() {
    return asc;
  }

  public void setAsc(boolean asc) {
    this.asc = asc;
  }
}
