package by.vbalanse.facade.content;

import by.vbalanse.dao.article.ArticleDao;
import by.vbalanse.dao.content.CommentDao;
import by.vbalanse.model.content.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Vasilina on 28.02.2015.
 */
public interface CommentFacade {

  CommentEntity saveComment(CommentInfo commentEntity);

  List<CommentEntity> findComments(Long articleId);

  void removeComment(Long commentId);

  long countComments(Long articleId);
}
