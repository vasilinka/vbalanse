package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.user.RoleDao;
import by.vbalanse.model.user.RoleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository("roleDao")
public class RoleDaoImpl extends AbstractManagedEntityDaoSpringImpl<RoleEntity> implements RoleDao {

  public RoleEntity getByCode(String code) {
    Query query = createQuery("select re from RoleEntity re where re.code=:code");
    query.setParameter("code", code);
    return (RoleEntity) query.getSingleResult();
  }
}
