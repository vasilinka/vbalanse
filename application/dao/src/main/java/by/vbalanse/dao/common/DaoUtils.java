package by.vbalanse.dao.common;

import by.vbalanse.model.common.AbstractEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */
public class DaoUtils {

  public static void addOrder(Criteria criteria, String orderbyProperty, boolean ascending) {
    criteria.addOrder(ascending ? Order.asc(orderbyProperty) : Order.desc(orderbyProperty));
  }

  public static long count(Criteria criteria) {
    criteria.setProjection(Projections.rowCount());

    return (Long) criteria.uniqueResult();
  }

  /**
   * Use ${@link #count(org.hibernate.Criteria)} instead of this one
   */
  @Deprecated
  public static int getCount(Criteria criteria) {
    criteria.setProjection(Projections.rowCount());

    return ((Long) criteria.uniqueResult()).intValue();
  }

  public static List sublist(Criteria criteria, @Nullable Integer firstResult, @Nullable Integer maxResult) {
    if (firstResult != null && firstResult >= 0) {
      criteria.setFirstResult(firstResult);
    }

    if (maxResult != null && maxResult > 0) {
      criteria.setMaxResults(maxResult);
    }

    return criteria.list();
  }

  public static List sublist(Query query, @Nullable Integer firstResult, @Nullable Integer maxResult) {
    if (firstResult != null && firstResult >= 0) {
      query.setFirstResult(firstResult);
    }

    if (maxResult != null && maxResult > 0) {
      query.setMaxResults(maxResult);
    }

    return query.list();
  }

  /**
   * Use ${@link #sublist(org.hibernate.Criteria, Integer, Integer)} instead of this one
   */
  @Deprecated
  public static List getSublist(Criteria criteria, int firstResult, int maxResult) {
    if (firstResult >= 0) {
      criteria.setFirstResult(firstResult);
    }

    if (maxResult > 0) {
      criteria.setMaxResults(maxResult);
    }

    return criteria.list();
  }

  /**
   * Use ${@link #sublist(org.hibernate.Query, Integer, Integer)}  instead of this one
   */
  @Deprecated
  public static List getSublist(Query query, int firstResult, int maxResult) {
    if (firstResult >= 0) {
      query.setFirstResult(firstResult);
    }

    if (maxResult > 0) {
      query.setMaxResults(maxResult);
    }

    return query.list();
  }

  public static List page(Criteria criteria, int pageNumber, int objectsPerPage) {
    if (pageNumber < 0) {
      throw new RuntimeException("pageNumber must be greater than 0");
    }
    if (objectsPerPage < 1) {
      throw new RuntimeException("objectsPerPage must be greater than 1");
    }

    return sublist(criteria, objectsPerPage * pageNumber, objectsPerPage);
  }

  public static List page(Query query, int pageNumber, int objectsPerPage) {
    if (pageNumber < 0) {
      throw new RuntimeException("pageNumber must be greater than 0");
    }
    if (objectsPerPage < 1) {
      throw new RuntimeException("objectsPerPage must be greater than 1");
    }

    return sublist(query, objectsPerPage * pageNumber, objectsPerPage);
  }

  /**
   * Use ${@link #page(org.hibernate.Criteria, int, int)}  instead of this one
   */
  @Deprecated
  public static List getPage(Criteria criteria, int objectsPerPage, int pageNumber) {
    if (objectsPerPage > 0) {
      criteria.setFirstResult(objectsPerPage * pageNumber);
      criteria.setMaxResults(objectsPerPage);
    }

    return criteria.list();
  }

  /**
   * Use ${@link #page(org.hibernate.Query, int, int)}   instead of this one
   */
  @Deprecated
  public static List getPage(Query query, int objectsPerPage, int pageNumber) {
    if (objectsPerPage > 0) {
      query.setFirstResult(objectsPerPage * pageNumber);
      query.setMaxResults(objectsPerPage);
    }

    return query.list();
  }

  public static Criteria addLikeRestriction(Criteria criteria, String propertyName, String value) {
    if (value != null && !value.equals("")) {
      criteria.add(Restrictions.like(propertyName, "%" + value + "%"));
    }

    return criteria;
  }

  public static <E extends AbstractEntity<PK>, PK extends Serializable> Set<PK> extractIds(Set<E> entities) {
    Set<PK> ids = null;
    if (entities != null) {
      ids = new HashSet<PK>(entities.size());
      for (AbstractEntity<PK> abstractEntity : entities) {
        ids.add(abstractEntity.getId());
      }
    }

    return ids;
  }

}
