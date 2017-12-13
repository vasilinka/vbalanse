package by.vbalanse.dao.article;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.AuditoryTypeEnum;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface ArticleDao extends AbstractManagedEntityDao<ArticleEntity> {
  List<ArticleEntity> findArticles(AuditoryTypeEnum auditoryType);

  List<ArticleEntity> findArticles();

  List<ArticleEntity> findArticleByDepartments(String departmentCode);

  List<ArticleEntity> findArticleByAuthorId(Long authorId);
}
