package by.vbalanse.model.content.book;

import java.util.Date;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class BookEntity {
  private String title;
  private String publishingHouse;
  private Date dateOfPublish;
  private String description;
  //good to have chapters description
  private List<ChapterEntity> chapterDescriptions;
}
