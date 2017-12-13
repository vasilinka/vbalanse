package by.vbalanse.dao.common.jpa;


import by.vbalanse.dao.common.AbstractDao;
import by.vbalanse.model.common.AbstractUnmanagedEntity;

import java.io.Serializable;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="e.terehov@itision.com">Eugene Terehov</a>
 */
public interface AbstractUnmanagedEntityDao<E extends AbstractUnmanagedEntity<ID>, ID extends Serializable> extends AbstractDao<E, ID> {

}
