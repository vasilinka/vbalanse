package by.vbalanse.dao.user;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserInfoKeyValueEntity;

import java.util.Map;

/**
 * Created by Vasilina on 06.04.2015.
 */
public interface UserInfoKeyValueDao extends AbstractManagedEntityDao<UserInfoKeyValueEntity> {

  UserInfoKeyValueEntity saveUserMeta(UserEntity user, String key, String value, Long id);

  Map<String, UserInfoKeyValueEntity> getUserMeta(Long userId);

  UserInfoKeyValueEntity getUserMeta(Long userId, String key);
}
