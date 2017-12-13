package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.user.psychologist.PsychologistDao;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.psychologist.PsychologistEntity_;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserEntity_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "psychologistDao")
public class PsychologistDaoImpl extends AbstractManagedEntityDaoSpringImpl<PsychologistEntity> implements PsychologistDao {

  @SuppressWarnings("unchecked")
  public PsychologistEntity findPsychologist(long userId) {
    CriteriaQuery criteriaQuery = createCriteria(PsychologistEntity.class);
    Root<PsychologistEntity> from = criteriaQuery.from(PsychologistEntity.class);
    Path<UserEntity> userEntityPath = from.get(PsychologistEntity_.userEntity);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    //ParameterExpression<Long> userIdParam = criteriaBuilder.parameter(Long.class);
    Expression<?> expression = userEntityPath.get(UserEntity_.id);
    criteriaQuery.where(criteriaBuilder.equal(expression, userId));
    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    //query.setParameter(userIdParam, userId);
    return (PsychologistEntity) query.getSingleResult();
  }

}
