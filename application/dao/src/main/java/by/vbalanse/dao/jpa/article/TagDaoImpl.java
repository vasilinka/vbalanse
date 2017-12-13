package by.vbalanse.dao.jpa.article;

import by.vbalanse.dao.article.TagDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.article.TagEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "TagDao")
public class TagDaoImpl extends AbstractManagedEntityDaoSpringImpl<TagEntity> implements TagDao {

  @Override
  @SuppressWarnings("unchecked")
  public List<TagEntity> findTags(String filter, List<Long> selectedIds) {
    if (filter.isEmpty() && selectedIds.isEmpty()) {
      return findAll();
    }
    String selectedCond = "";
    if (!selectedIds.isEmpty()) {
      selectedCond = " te.id not in (:ids) ";
    }
    String filterCond = "";
    if (!filter.isEmpty()) {
      filterCond = " te.title like :filter ";
    }
    Query query = createQuery("select te from TagEntity te where " + selectedCond + ((!selectedCond.isEmpty() && !filterCond.isEmpty()) ? " and " : "")  + filterCond);
    if (!selectedCond.isEmpty()) {
      query.setParameter("ids", selectedIds);
    }
    if (!filterCond.isEmpty()) {
      query.setParameter("filter", "%" + filter.toLowerCase() + "%");
    }
    return query.getResultList();
  }
}