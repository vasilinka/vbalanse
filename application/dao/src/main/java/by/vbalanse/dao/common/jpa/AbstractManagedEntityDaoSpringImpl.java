package by.vbalanse.dao.common.jpa;


import by.vbalanse.dao.common.AbstractDao;
import by.vbalanse.model.common.AbstractManagedEntity;

/**
 * writeme: Should be the description of the class
 *
 * @author Eugene Terehov
 */
public abstract class AbstractManagedEntityDaoSpringImpl<E extends AbstractManagedEntity> extends AbstractDaoSpringImpl<E, Long>
    implements AbstractDao<E,Long> {

}
