package by.vbalanse.rest;

import by.vbalanse.convert.Transferer;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.facade.content.CommentFacade;
import by.vbalanse.facade.content.CommentInfo;
import by.vbalanse.facade.security.UserDetailsImpl;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.content.CommentEntity;
import by.vbalanse.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Vasilina on 28.02.2015.
 */
@Controller
@RequestMapping("/psy/comment")
public class CommentService {

  @Autowired
  private CommentFacade commentFacade;

  @Autowired
  private UserDao userDao;

  @Autowired
  private ArticleService articleService;

  @Autowired
  private Transferer transferer;

  @RequestMapping(value = "saveComment", method = RequestMethod.POST)
  public
  @ResponseBody
  CommentJsonSaveResult saveComment(@RequestParam(value = "text") String text, @RequestParam(value = "parent_id", defaultValue = ValueConstants.DEFAULT_NONE, required = false) Long parentId,
                                    @RequestParam(value = "comment_id", defaultValue = ValueConstants.DEFAULT_NONE, required = false) Long commentId,
                                    @RequestParam(value = "articleId") Long articleId) {
    CommentInfo commentInfo = new CommentInfo();
    commentInfo.setText(text);
    commentInfo.setArticleId(articleId);
    commentInfo.setReplyTo(parentId);
    commentInfo.setId(commentId);
    CommentEntity commentEntity = commentFacade.saveComment(commentInfo);
    return transferer.transfer(CommentEntity.class, CommentJsonSaveResult.class, commentEntity);
  }

  @RequestMapping(value = "removeComment", method = RequestMethod.POST)
  public
  @ResponseBody
  JsonResult deleteComment(@RequestParam(value = "comment_id", defaultValue = ValueConstants.DEFAULT_NONE, required = false) Long commentId) {
    commentFacade.removeComment(commentId);
    return new JsonResult(true);
  }

  @RequestMapping(value = "listComment", method = RequestMethod.GET)
  public
  @ResponseBody
  CommentListInfo findComments(@RequestParam(value = "articleId") Long articleId) {
    List<CommentEntity> comments = commentFacade.findComments(articleId);
    List<CommentJsonSaveResult> commentJsonSaveResults = transferer.transferList(CommentEntity.class, CommentJsonSaveResult.class, comments);
    CommentListInfo commentListInfo = new CommentListInfo();
    long size = 0;
    if (commentJsonSaveResults != null) {
      size = (long) commentJsonSaveResults.size();
    }
    commentListInfo.setTotal_comment(size);
    commentListInfo.setComments(commentJsonSaveResults);
    UserDetailsImpl user = UserFacadeImpl.getUserDetails();
    if (user == null) {
      //anonymous
      UserCommentInfo user1 = new UserCommentInfo();
      user1.setIs_add_allowed(true);
      user1.setIs_edit_allowed(false);
      user1.setIs_logged_in(false);
      user1.setFullname("Анонимный пользователь");
      commentListInfo.setUser(user1);
    } else  {
      UserEntity userEntity = userDao.find(user.getId());
      UserCommentInfo transfer = transferer.transfer(UserEntity.class, UserCommentInfo.class, userEntity);
      commentListInfo.setUser(transfer);
    }
    return commentListInfo;
  }


}
