package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.template.TemplateDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.model.template.EmailTemplateEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserEntity_;
import org.springframework.stereotype.Repository;

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
@Repository(value = "templateDao")
public class TemplateDaoImpl extends AbstractManagedEntityDaoSpringImpl<EmailTemplateEntity> implements TemplateDao {
  public EmailTemplateEntity getByCode(String code) {
    Query query = createQuery("select ete from EmailTemplateEntity ete where ete.title = :code");
    query.setParameter("code", code);
    return (EmailTemplateEntity) query.getSingleResult();
  }

}
