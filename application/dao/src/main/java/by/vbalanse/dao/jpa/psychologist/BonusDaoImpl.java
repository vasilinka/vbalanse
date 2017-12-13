package by.vbalanse.dao.jpa.psychologist;

import by.vbalanse.dao.psychologist.BonusDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.psychologist.BonusEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "BonusDao")
public class BonusDaoImpl extends AbstractManagedEntityDaoSpringImpl<BonusEntity> implements BonusDao {

  @Override
  @SuppressWarnings("unchecked")
  public List<BonusEntity> findBy(Long userId) {
    Query query = createQuery("select be from BonusEntity be where be.userEntity.id=:userId");
    query.setParameter("userId", userId);
    return query.getResultList();
  }

}