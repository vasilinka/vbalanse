package by.vbalanse.dao.content;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.content.CommentEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface CommentDao extends AbstractManagedEntityDao<CommentEntity> {

  List<CommentEntity> findComments(Long articleId);

  Long countComments(Long articleId);
}