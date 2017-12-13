package by.vbalanse.dao.jpa;

import by.vbalanse.dao.article.ArticleDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.AuditoryTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "articleDao")
public class ArticleDaoImpl extends AbstractManagedEntityDaoSpringImpl<ArticleEntity> implements ArticleDao {

  @SuppressWarnings("unchecked")
  public List<ArticleEntity> findArticles(AuditoryTypeEnum auditoryType) {
    Query query = createQuery("select ae from ArticleEntity ae where exists " +
        "(select au from ae.auditories au where au.id =" +
        "(select id from TargetAuditoryEntity where code = :menu)))");
    query.setParameter("menu", auditoryType.getCode());
    return query.getResultList();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ArticleEntity> findArticles() {
    Query query = createQuery("select ae from ArticleEntity ae order by ae.dateOfPublish desc");
    return query.getResultList();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ArticleEntity> findArticleByDepartments(String departmentCode) {
    Query query = createQuery("select ae from ArticleEntity ae where ae.department.code=:code");
    query.setParameter("code", departmentCode);
    return query.getResultList();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ArticleEntity> findArticleByAuthorId(Long authorId) {
    Query query = createQuery("select ae from ArticleEntity ae where ae.author.id=:authorId");
    query.setParameter("authorId", authorId);
    return query.getResultList();
  }

}
