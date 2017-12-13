package by.vbalanse.dao.user;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.AuditoryTypeEnum;
import by.vbalanse.model.user.UserEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface UserDao extends AbstractManagedEntityDao<UserEntity> {
  public UserEntity getByEmail(String email);

  UserEntity getByConfirmLink(String activateParameter);

  UserEntity getByEmailResetLink(String activateParameter);
}
