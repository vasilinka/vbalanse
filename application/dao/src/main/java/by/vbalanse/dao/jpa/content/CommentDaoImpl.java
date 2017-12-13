package by.vbalanse.dao.jpa.content;

import by.vbalanse.dao.content.CommentDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.content.CommentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "commentDao")
public class CommentDaoImpl extends AbstractManagedEntityDaoSpringImpl<CommentEntity> implements CommentDao {

  @Override
  @SuppressWarnings("unchecked")
  public List<CommentEntity> findComments(Long articleId) {
    Query query = createQuery("select ce from CommentEntity ce where ce.articleEntity.id=:articleId and ce.parentComment is null order by ce.dateOfComment desc");
    query.setParameter("articleId", articleId);
    return query.getResultList();
  }

  @Override
  public Long countComments(Long articleId) {
    Query query = createQuery("select count(ce) from CommentEntity ce where ce.articleEntity.id=:articleId");
    query.setParameter("articleId", articleId);
    return (long) query.getSingleResult();
  }

}