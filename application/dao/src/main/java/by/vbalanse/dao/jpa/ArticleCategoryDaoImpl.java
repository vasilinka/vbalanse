package by.vbalanse.dao.jpa;

import by.vbalanse.dao.article.ArticleCategoryDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.article.ArticleCategoryEntity;
import org.springframework.stereotype.Repository;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "articleCategoryDao")
public class ArticleCategoryDaoImpl extends AbstractManagedEntityDaoSpringImpl<ArticleCategoryEntity> implements ArticleCategoryDao {

}
