package by.vbalanse.dao.jpa.content;

import by.vbalanse.dao.content.PropertyForListKeyValueDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.content.PropertyForListKeyValueEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "propertyForListKeyValueDao")
public class PropertyForListKeyValueDaoImpl extends AbstractManagedEntityDaoSpringImpl<PropertyForListKeyValueEntity> implements PropertyForListKeyValueDao {
    @Override
    public List<PropertyForListKeyValueEntity> getProperties(long userId, String key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int maxOrder(long userId, String key) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}