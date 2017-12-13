package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.model.template.EmailTemplateEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserEntity_;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "userDao")
public class UserDaoImpl extends AbstractManagedEntityDaoSpringImpl<UserEntity> implements UserDao {
  @Override
  public UserEntity getByEmail(String email) {
    CriteriaQuery<UserEntity> criteriaQuery = createCriteria(UserEntity.class);
    Root<UserEntity> from = criteriaQuery.from(UserEntity.class);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    Predicate equal = criteriaBuilder.equal(from.get(UserEntity_.email), email);
    criteriaQuery.where(equal);
    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    return (UserEntity) getSingleResult(query);
  }

  @Override
  public UserEntity getByConfirmLink(String activateParameter) {
    Query query = createQuery("select ue from UserEntity ue where ue.confirmed = false and ue.confirmLink=:confirmLink");
    query.setParameter("confirmLink", activateParameter);
    return (UserEntity) query.getSingleResult();
  }

  @Override
  public UserEntity getByEmailResetLink(String emailResetParameter) {
    Query query = createQuery("select ue from UserEntity ue where ue.changeEmailLink=:emailResetParameter");
    query.setParameter("emailResetParameter", emailResetParameter);
    return (UserEntity) query.getSingleResult();
  }

}
