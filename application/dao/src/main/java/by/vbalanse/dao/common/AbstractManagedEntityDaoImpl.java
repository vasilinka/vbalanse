package by.vbalanse.dao.common;


import by.vbalanse.model.common.AbstractUnmanagedEntity;

import java.io.Serializable;

/**
 * writeme: Should be the description of the class
 *
 * @author Eugene Terehov
 */
public abstract class AbstractManagedEntityDaoImpl<E extends AbstractUnmanagedEntity<ID>, ID extends Serializable> extends AbstractDaoImpl<E, ID>
    implements AbstractUnmanagedEntityDao<E, ID> {

}
