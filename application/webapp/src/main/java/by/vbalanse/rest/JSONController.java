package by.vbalanse.rest;

import by.vbalanse.facade.article.ArticleFacade;
import by.vbalanse.model.article.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping("/psy/article")
public class JSONController {

  @Autowired
  ArticleFacade articleFacade;

  @RequestMapping(value="article2", method = RequestMethod.GET)
  public @ResponseBody PaginatedListWrapper<ArticleEntity> getShopInJSON( @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "sortFields", defaultValue = "")String sortFields,
                                                    @RequestParam(value = "sortDirections", defaultValue = "")String sortDirections) {
    PaginatedListWrapper<ArticleEntity> paginatedListWrapper = new PaginatedListWrapper<>();
    paginatedListWrapper.setCurrentPage(page);
    paginatedListWrapper.setSortFields(sortFields);
    paginatedListWrapper.setSortDirections(sortDirections);
    paginatedListWrapper.setPageSize(5);
    paginatedListWrapper.setTotalResults(1);
    List<ArticleEntity> articles = articleFacade.findArticles();
    paginatedListWrapper.setList(articles);
    return paginatedListWrapper;
  }

}
