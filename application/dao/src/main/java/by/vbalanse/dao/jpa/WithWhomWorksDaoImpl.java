package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.user.psychologist.WithWhomWorksDao;
import by.vbalanse.model.psychologist.WithWhomWorks;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "withWhomWorksDao")
public class WithWhomWorksDaoImpl extends AbstractManagedEntityDaoSpringImpl<WithWhomWorks> implements WithWhomWorksDao {

  public List<WithWhomWorks> findPersonalTherapyWays() {
    Query query = createQuery("select ae from WithWhomWorks ptw");
    return query.getResultList();
  }
}
