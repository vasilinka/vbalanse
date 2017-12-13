package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.user.UserInfoKeyValueDao;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserInfoKeyValueEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by Vasilina on 06.04.2015.
 */
@Repository(value = "userInfoKeyValueDao")
public class UserInfoKeyValueDaoImpl extends AbstractManagedEntityDaoSpringImpl<UserInfoKeyValueEntity> implements UserInfoKeyValueDao {
  final static String BANNER_TITLE_1 = "bannerTitle1";
  final static String BANNER_TITLE_2 = "bannerTitle2";
  final static String BANNER_TITLE_3 = "bannerTitle3";
  final static String EDUCATION = "education";
  final static String MAIN_DIRECTION_WORK = "direction_work";
  final static String MAIN_SPECIALITIES = "specialities";
  final static String EXPERIENCE = "experience";
  final static String ADDRESS = "address";
  final static String WORKING_HOURS = "working_hours";
  final static String PREVIOUS_WORK = "previous_work";
  final static String ACADEMIC_DEGREE = "academic_degree";
  public static SortedSet<String> keys = new TreeSet<>();

  {
    keys.add(BANNER_TITLE_1);
    keys.add(BANNER_TITLE_2);
    keys.add(BANNER_TITLE_3);
    keys.add(EDUCATION);
    keys.add(MAIN_DIRECTION_WORK);
    keys.add(MAIN_SPECIALITIES);
    keys.add(EXPERIENCE);
    keys.add(ADDRESS);
    keys.add(WORKING_HOURS);
    keys.add(PREVIOUS_WORK);
    keys.add(ACADEMIC_DEGREE);
  }

  public Map<String, UserInfoKeyValueEntity> getUserMeta(Long userId) {
    Query query = createQuery("select ukv from UserInfoKeyValueEntity ukv where ukv.userEntity.id = :userId");
    query.setParameter("userId", userId);
    List<UserInfoKeyValueEntity> values = query.getResultList();
    Map<String, UserInfoKeyValueEntity> meta = new HashMap<>();
    for (UserInfoKeyValueEntity value : values) {
      meta.put(value.getKey(), value);
    }
    return meta;
  }

  public UserInfoKeyValueEntity saveUserMeta(UserEntity user, String key, String value, Long id) {
    UserInfoKeyValueEntity userMeta = null;
    if (id != null) {
      userMeta = find(id);
    } else {
      userMeta = getUserMeta(user.getId(), key);
      if (userMeta == null) {
        userMeta = new UserInfoKeyValueEntity();
        userMeta.setKey(key);
        userMeta.setUserEntity(user);
      }
    }
    userMeta.setValue(value);
    saveOrUpdate(userMeta);
    return userMeta;
  }

  public UserInfoKeyValueEntity getUserMeta(Long userId, String key) {
    Query query = createQuery("select ukv from UserInfoKeyValueEntity ukv where ukv.userEntity.id = :userId and ukv.key=:key");
    query.setParameter("userId", userId);
    query.setParameter("key", key);
    try {
      return (UserInfoKeyValueEntity) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public static SortedSet<String> getUserKeys() {
    return Collections.unmodifiableSortedSet(keys);
  }
}
