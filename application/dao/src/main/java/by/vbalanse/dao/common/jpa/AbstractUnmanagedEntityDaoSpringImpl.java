package by.vbalanse.dao.common.jpa;


import by.vbalanse.model.common.AbstractUnmanagedEntity;

import java.io.Serializable;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="e.terehov@itision.com">Eugene Terehov</a>
 */
public class AbstractUnmanagedEntityDaoSpringImpl<E extends AbstractUnmanagedEntity<ID>, ID extends Serializable> extends AbstractDaoSpringImpl<E, ID>
    implements AbstractUnmanagedEntityDao<E, ID> {

}
