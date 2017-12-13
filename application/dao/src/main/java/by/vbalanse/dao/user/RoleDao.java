package by.vbalanse.dao.user;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.user.RoleEntity;
import by.vbalanse.model.user.RoleTypeEnum;
import by.vbalanse.model.user.UserEntity;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface RoleDao  extends AbstractManagedEntityDao<RoleEntity> {
  RoleEntity getByCode(String code);
}
