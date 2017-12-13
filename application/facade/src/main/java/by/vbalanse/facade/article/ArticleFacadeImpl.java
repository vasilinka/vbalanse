package by.vbalanse.facade.article;

import by.vbalanse.dao.article.ArticleDao;
import by.vbalanse.dao.article.DepartmentDao;
import by.vbalanse.dao.article.TagDao;
import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.security.UserDetailsImpl;
import by.vbalanse.facade.storage.attachment.AttachmentFacade;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.AuditoryTypeEnum;
import by.vbalanse.model.article.TagEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Service("articleFacade")
public class ArticleFacadeImpl implements ArticleFacade {

  @Autowired
  private ArticleDao articleDao;

  @Autowired
  private DepartmentDao departmentDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private TagDao tagDao;

  @Autowired
  private StorageFileDao storageFileDao;

  @Autowired
  private AttachmentFacade attachmentFacade;

  public List<ArticleEntity> findArticles(AuditoryTypeEnum auditoryType) {
    return articleDao.findArticles(auditoryType);
  }

  @Override
  public List<ArticleEntity> findArticleByDepartment(String departmentCode) {
    return articleDao.findArticleByDepartments(departmentCode);
  }

  @Override
  @Deprecated
  public List<ArticleEntity> findArticles() {
    return articleDao.findArticles();
  }

  @Override
  public List<ArticleEntity> findMyArticles() {
    return articleDao.findArticleByAuthorId(UserFacadeImpl.getUserId());
  }

  @Override
  public List<ArticleEntity> findArticles(Long authorId) {
    return articleDao.findArticleByAuthorId(authorId);
  }

  @Override
  public ArticleEntity saveArticle(ArticleInfo article) throws ValidationException {
    ArticleEntity articleEntity;

    if (article.getId() == null) {
      articleEntity = new ArticleEntity();
      articleEntity.setDateOfPublish(new Date());
      UserDetailsImpl user = UserFacadeImpl.getUserDetails();
      articleEntity.setAuthor(userDao.find(user.getId()));
    } else {
      articleEntity = articleDao.find(article.getId());
      articleEntity.setDateOfPublish(article.getDateOfPublish());
    }
    articleEntity.setTitle(article.getTitle());

    if (article.getImage().getNewImageId() != null) {
      AttachmentImageEntity imageEntity = attachmentFacade.saveImageAttachment(article.getImage(), articleEntity.getImage());
      articleEntity.setImage(imageEntity);
    }
    //here should receive all images saved, add property to style max-width: 100%, also save image as ImageEntity for future auto deletion from base and disk
    String text = article.getText();
    Document doc = Jsoup.parse(text);
    Elements images = doc.select("img");

    for (Element img : images) {
      img.attr("href");
      String style = img.attr("style");
      String[] split = style.split(";");
      String newStyle = "";
      if (style.contains("width") || style.contains("height")) {
        for (String part : split) {
          String[] split1 = part.split(":");
          String attrName = split1[0];
          //String attrValue = split1[1];
          if (!attrName.trim().equalsIgnoreCase("width") && !attrName.trim().equalsIgnoreCase("height")) {
            newStyle += part + ";";
          }
        }
      } else {
        newStyle = style;
        if (!newStyle.isEmpty() && newStyle.endsWith(";")) {
          newStyle += ";";
        }
      }
      String styleValue = newStyle + "max-width: 100%;";
      img.attr("style", styleValue);
    }

    String html = doc.body().html();
    html = html.replace("&nbsp;", " ");

    articleEntity.setText(html);
    if (article.getDepartment().getId() == null) {
      throw new ValidationException("department", "Выберите пожалуйста раздел для статьи");
    }
    articleEntity.setDepartment(departmentDao.find(article.getDepartment().getId()));
    List<BaseCodeTitleInfo> tags = article.getTags();
    if (tags != null) {
      List<TagEntity> tagEntities = new ArrayList<>(tags.size());
      if (!tags.isEmpty()) {
        for (BaseCodeTitleInfo tag : tags) {
          TagEntity tagEntity;
          if (tag.getId() == null) {
            tagEntity = new TagEntity(tag.getTitle(), tag.getCode(), true);
            tagDao.save(tagEntity);
          } else {
            tagEntity = tagDao.find(tag.getId());
          }
          tagEntities.add(tagEntity);
        }
        articleEntity.setTags(new HashSet<>(tagEntities));
      }
    } else {
      articleEntity.setTags(Collections.<TagEntity>emptySet());
    }

    articleDao.saveOrUpdate(articleEntity);
    return articleEntity;
  }

  @Override
  public ArticleEntity getArticle(Long id) {
    return articleDao.find(id);
  }

  @Override
  public void removeArticle(Long articleId) {
    articleDao.delete(articleDao.find(articleId));
  }
}


