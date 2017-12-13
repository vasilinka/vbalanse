package by.vbalanse.model.content;

import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.content.book.BookEntity;
import by.vbalanse.model.user.UserEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(name = CommentEntity.TABLE_NAME)
public class CommentEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "artcl_comment";
  public static final String COLUMN_TITLE = "title_";
  public static final String COLUMN_TEXT = "text_";
  public static final String COLUMN_DATE_OF_ANSWER = "date_of_answer_";
  public static final String COLUMN_AUTHOR = UserEntity.TABLE_NAME + "$author";
  public static final String COLUMN_PARENT_COMMENT = CommentEntity.TABLE_NAME + "$parent";
  public static final String COLUMN_ARTICLE = ArticleEntity.TABLE_NAME;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_ARTICLE)
  private ArticleEntity articleEntity;

  //comment can be to article or to book or to psychologist
  //private BookEntity book;
  //psychologist to whom comment is saved
//  @NotNull
//  @ManyToOne(fetch = FetchType.EAGER)
//  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_AUTHOR)
//  private UserEntity userEntity$psychologist;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_AUTHOR)
  private UserEntity userEntity$author;

  @NotNull
  @Column(name = COLUMN_DATE_OF_ANSWER)
  private Date dateOfComment;

  @NotNull
  @Column(name = COLUMN_TITLE)
  private String text;

  private boolean anonymous;

  @ManyToOne(fetch = FetchType.LAZY)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_PARENT_COMMENT)
  private CommentEntity parentComment;

  @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
  private List<CommentEntity> children;

  public ArticleEntity getArticleEntity() {
    return articleEntity;
  }

  public void setArticleEntity(ArticleEntity articleEntity) {
    this.articleEntity = articleEntity;
  }

//  public BookEntity getBook() {
//    return book;
//  }
//
//  public void setBook(BookEntity book) {
//    this.book = book;
//  }

//  public UserEntity getUserEntity$psychologist() {
//    return userEntity$psychologist;
//  }
//
//  public void setUserEntity$psychologist(UserEntity userEntity$psychologist) {
//    this.userEntity$psychologist = userEntity$psychologist;
//  }

  public UserEntity getUserEntity$author() {
    return userEntity$author;
  }

  public void setUserEntity$author(UserEntity userEntity$author) {
    this.userEntity$author = userEntity$author;
  }

  public Date getDateOfComment() {
    return dateOfComment;
  }

  public void setDateOfComment(Date dateOfComment) {
    this.dateOfComment = dateOfComment;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public CommentEntity getParentComment() {
    return parentComment;
  }

  public void setParentComment(CommentEntity parentComment) {
    this.parentComment = parentComment;
  }

  public List<CommentEntity> getChildren() {
    return children;
  }

  public void setChildren(List<CommentEntity> children) {
    this.children = children;
  }
}
