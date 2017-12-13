package by.vbalanse.facade.content;

import by.vbalanse.dao.article.ArticleDao;
import by.vbalanse.dao.content.CommentDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.facade.security.UserDetailsImpl;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.content.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Vasilina on 28.02.2015.
 */
@Service("commentFacade")
public class CommentFacadeImpl implements CommentFacade {

  @Autowired
  private CommentDao commentDao;

  @Autowired
  private ArticleDao articleDao;

  @Autowired
  private UserDao userDao;


  @Override
  public CommentEntity saveComment(CommentInfo commentInfo) {
    CommentEntity commentEntity;
    if (commentInfo.getId() != null) {
      //todo: if needed status comment updated
      commentEntity = commentDao.find(commentInfo.getId());
    } else {
      commentEntity = new CommentEntity();
      ArticleEntity articleEntity = articleDao.find(commentInfo.getArticleId());
      commentEntity.setArticleEntity(articleEntity);
      UserDetailsImpl user = UserFacadeImpl.getUserDetails();
      commentEntity.setUserEntity$author(userDao.find(user.getId()));
      if (commentInfo.getReplyTo() != null) {
        commentEntity.setParentComment(commentDao.find(commentInfo.getReplyTo()));
      }
    }

    commentEntity.setText(commentInfo.getText());
    commentEntity.setDateOfComment(new Date());
    commentDao.saveOrUpdate(commentEntity);
    return commentEntity;
  }

  @Override
  public List<CommentEntity> findComments(Long articleId) {
    return commentDao.findComments(articleId);
  }

  @Override
  public void removeComment(Long commentId) {
    commentDao.delete(commentDao.find(commentId));
  }

  @Override
  public long countComments(Long articleId) {
    return commentDao.countComments(articleId);
  }
}
