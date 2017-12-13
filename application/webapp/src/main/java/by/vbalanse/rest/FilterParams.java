package by.vbalanse.rest;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class FilterParams {

  private String filter;
  private List<Long> ids;
  public FilterParams() {
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public List<Long> getIds() {
    return ids;
  }

  public void setIds(List<Long> ids) {
    this.ids = ids;
  }
}
