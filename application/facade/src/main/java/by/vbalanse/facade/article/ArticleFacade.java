package by.vbalanse.facade.article;

import by.vbalanse.facade.ValidationException;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.AuditoryTypeEnum;
import by.vbalanse.model.user.RoleTypeEnum;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface ArticleFacade {

  List<ArticleEntity> findArticleByDepartment(String departmentCode);

  List<ArticleEntity> findArticles();

  List<ArticleEntity> findMyArticles();

  List<ArticleEntity> findArticles(Long authorId);

  //@Secured({RoleTypeEnum.Constants.ROLE_PSYCHOLOGIST_CODE})
  ArticleEntity saveArticle(ArticleInfo articleEntity) throws ValidationException;

  ArticleEntity getArticle(Long id);

  void removeArticle(Long articleId);
}
