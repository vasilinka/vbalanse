package by.vbalanse.dao.common;


import by.vbalanse.model.common.AbstractManagedEntity;

/**
 * writeme: Should be the description of the class
 *
 * @author Eugene Terehov
 */
public abstract class AbstractManagedEntityDaoSpringImpl<E extends AbstractManagedEntity> extends AbstractDaoSpringImpl<E, Long>
    implements AbstractManagedEntityDao<E> {

}
