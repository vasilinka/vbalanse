package by.vbalanse.dao.jpa.<%- path %>;

import by.vbalanse.dao.<%- path %>.<%- entityName %>Dao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.<%- path %>.<%- modelClass %>;
import org.springframework.stereotype.Repository;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "<%- entityName %>Dao")
public class <%- entityName %>DaoImpl extends AbstractManagedEntityDaoSpringImpl<<%- modelClass %>> implements <%- entityName %>Dao {
}