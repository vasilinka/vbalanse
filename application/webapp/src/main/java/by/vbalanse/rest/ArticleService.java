package by.vbalanse.rest;

import by.vbalanse.convert.Transferer;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.article.ArticleFacade;
import by.vbalanse.facade.article.ArticleInfo;
import by.vbalanse.facade.article.ImageInfo;
import by.vbalanse.facade.content.CommentFacade;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.storage.StorageFileFacadeImpl;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.storage.StorageFileImageEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping("/psy/article")
public class ArticleService {

  @Autowired
  private ArticleFacade articleFacade;

  @Autowired
  private Transferer transferer;

  @Autowired
  private CommentFacade commentFacade;

  @Autowired
  private StorageFileFacade storageFileFacade;

  @RequestMapping(value = "saveArticle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  ArticleInfo saveArticle(@RequestBody ArticleInfo article) throws ValidationException {
    ArticleEntity articleEntity = articleFacade.saveArticle(article);
    ArticleInfo articleInfo = new ArticleInfo();
    //transfer only needed properties
    articleInfo.setDateOfPublish(articleEntity.getDateOfPublish());
    articleInfo.setId(articleEntity.getId());
    if (articleEntity.getImage() != null) {
      articleInfo.setImage(transferer.<StorageFileImageEntity, ImageInfo>transfer(StorageFileImageEntity.class, ImageInfo.class, articleEntity.getImage().getImageFile()));
    }
    return articleInfo;
  }

  @RequestMapping(value = "getArticle", method = RequestMethod.GET)
  public
  @ResponseBody
  ArticleInfo getArticle(@RequestParam(value = "articleId") Integer id) throws ValidationException {
    ArticleEntity article = articleFacade.getArticle(Long.valueOf(id));
    ArticleInfo transfer = transferer.transfer(ArticleEntity.class, ArticleInfo.class, article);
    if (article.getImage()!=null) {
      transfer.getImage().setUrl(storageFileFacade.getRealFileUrl(article.getImage().getImageFile()));
    }
    transfer.setCountOfComments(commentFacade.countComments(article.getId()));
    return transfer;
  }


  @RequestMapping(value = "removeArticle", method = RequestMethod.GET)
  public
  @ResponseBody
  void removeArticle(@RequestParam Long articleId) {
    articleFacade.removeArticle(articleId);
  }

  //todo: make pagination according to smart table
  @RequestMapping(value = "articleList", method = RequestMethod.GET)
  public
  @ResponseBody
  PaginatedListWrapper<ArticleEntity> findArticles(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "sortFields", defaultValue = "") String sortFields,
                                                   @RequestParam(value = "sortDirections", defaultValue = "") String sortDirections) {
    PaginatedListWrapper<ArticleEntity> paginatedListWrapper = new PaginatedListWrapper<>();
    paginatedListWrapper.setCurrentPage(page);
    paginatedListWrapper.setSortFields(sortFields);
    paginatedListWrapper.setSortDirections(sortDirections);
    paginatedListWrapper.setPageSize(5);
    paginatedListWrapper.setTotalResults(1);
    List<ArticleEntity> articles = articleFacade.findMyArticles();
    paginatedListWrapper.setList(articles);
    return paginatedListWrapper;
  }

  @RequestMapping(value = "articlesPublic", method = RequestMethod.GET)
  public
  @ResponseBody
  List<ArticleInfo> findArticlesPublic() {
    List<ArticleEntity> articles = articleFacade.findArticles();

    List<ArticleInfo> articleInfos = transferer.transferList(ArticleEntity.class, ArticleInfo.class, articles);
    for (ArticleInfo articleInfo : articleInfos) {
      articleInfo.setCountOfComments(commentFacade.countComments(articleInfo.getId()));
    }
    //throw new NullPointerException();
    return articleInfos;

  }


}
