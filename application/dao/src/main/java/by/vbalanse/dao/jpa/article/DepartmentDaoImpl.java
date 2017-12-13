package by.vbalanse.dao.jpa.article;

import by.vbalanse.dao.article.DepartmentDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.article.DepartmentEntity;
import org.springframework.stereotype.Repository;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "DepartmentDao")
public class DepartmentDaoImpl extends AbstractManagedEntityDaoSpringImpl<DepartmentEntity> implements DepartmentDao {
}