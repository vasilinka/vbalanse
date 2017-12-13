package by.vbalanse.rest;

import by.vbalanse.convert.Convert;
import by.vbalanse.convert.NoConvert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vasilina on 03.03.2015.
 */
public class CommentJsonSaveResult extends JsonResult {

  public CommentJsonSaveResult() {
    super(true);
  }

  @Convert(name = "dateOfComment")
  private Date posted_date;

  private String text;

  @Convert(name = "id")
  private Long comment_id;

  @Convert(name = "id", object = "parentComment")
  private Long parent_id;

  @Convert(name = "id", object = "userEntity$author")
  private Long created_by;

  @NoConvert
  private String in_reply_to;

  @Convert(names = {"firstName", " ", "lastName"}, object = "userEntity$author")
  private String fullname;

  @Convert(name = "children")
  private List<CommentJsonSaveResult> childrens = new ArrayList<>(0);

  public Date getPosted_date() {
    return posted_date;
  }

  public void setPosted_date(Date posted_date) {
    this.posted_date = posted_date;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getComment_id() {
    return comment_id;
  }

  public void setComment_id(Long comment_id) {
    this.comment_id = comment_id;
  }

  public Long getParent_id() {
    return parent_id;
  }

  public void setParent_id(Long parent_id) {
    this.parent_id = parent_id;
  }

  public String getIn_reply_to() {
    return in_reply_to;
  }

  public void setIn_reply_to(String in_reply_to) {
    this.in_reply_to = in_reply_to;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public List<CommentJsonSaveResult> getChildrens() {
    return childrens;
  }

  public void setChildrens(List<CommentJsonSaveResult> childrens) {
    this.childrens = childrens;
  }

  public Long getCreated_by() {
    return created_by;
  }

  public void setCreated_by(Long created_by) {
    this.created_by = created_by;
  }
}
