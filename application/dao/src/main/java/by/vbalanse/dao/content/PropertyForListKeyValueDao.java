package by.vbalanse.dao.content;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.content.PropertyForListKeyValueEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface PropertyForListKeyValueDao extends AbstractManagedEntityDao<PropertyForListKeyValueEntity> {

  List<PropertyForListKeyValueEntity> getProperties(long userId, String key);

  int maxOrder(long userId, String key);

}