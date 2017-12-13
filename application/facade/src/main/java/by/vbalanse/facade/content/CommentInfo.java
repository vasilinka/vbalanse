package by.vbalanse.facade.content;

import by.vbalanse.facade.article.HasIdInfo;

/**
 * Created by Vasilina on 28.02.2015.
 */
public class CommentInfo extends HasIdInfo {
  private String text;
  private Long replyTo;
  private Long articleId;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getReplyTo() {
    return replyTo;
  }

  public void setReplyTo(Long replyTo) {
    this.replyTo = replyTo;
  }

  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }
}
