package by.vbalanse.rest;

import java.util.List;

/**
 * Created by Vasilina on 04.03.2015.
 */
public class CommentListInfo extends JsonResult {
  private List<CommentJsonSaveResult> comments;
  private Long total_comment;
  private UserCommentInfo user;

  public List<CommentJsonSaveResult> getComments() {
    return comments;
  }

  public void setComments(List<CommentJsonSaveResult> comments) {
    this.comments = comments;
  }

  public Long getTotal_comment() {
    return total_comment;
  }

  public void setTotal_comment(Long total_comment) {
    this.total_comment = total_comment;
  }

  public UserCommentInfo getUser() {
    return user;
  }

  public void setUser(UserCommentInfo user) {
    this.user = user;
  }
}
