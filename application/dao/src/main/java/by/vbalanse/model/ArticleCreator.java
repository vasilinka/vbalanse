package by.vbalanse.model;

import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.DepartmentEntity;
import by.vbalanse.model.article.TagEntity;
import by.vbalanse.model.user.UserEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class ArticleCreator {
  static DataLoad dataLoad;
  static EntityManager entityManager;
  public static void main1() {
    dataLoad = new DataLoad();
    entityManager = dataLoad.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
//    createArticle();
  }

  public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ParseException {
    main1();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();

    //Get the DOM Builder Factory
    DepartmentEntity departmentEntity = new DepartmentEntity("Импортировано b17", "b17");
    UserEntity userEntity = dataLoad.getAdminUser();
    entityManager.persist(departmentEntity);
    DocumentBuilderFactory factory =
        DocumentBuilderFactory.newInstance();

    //Get the DOM Builder
    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document =
        builder.parse(
            /*ClassLoader.getSystemResourceAsStream("xml/employee.xml")*/TrainingImport.getUrl("http://www.b17.ru/rss.php?mod=article"));

    List<Article> articles = new ArrayList<>();
    List<ArticleEntity> articleEntities = new ArrayList<>();

    //Iterating through the nodes and extracting the data.
    NodeList channel = document.getDocumentElement().getElementsByTagName("channel");
    Node item = channel.item(0);
    NodeList nodeList = item.getChildNodes();

    for (int i = 0; i < nodeList.getLength(); i++) {

      //We have encountered an <employee> tag.
      Node node = nodeList.item(i);
      String trim = node.getTextContent().trim();
      //System.out.println("item" + trim);
      if (node.getNodeName().equals("item")) {
        if (node instanceof Element) {
          Article emp = new Article();
          ArticleEntity articleEntity = new ArticleEntity();
          articleEntity.setDepartment(departmentEntity);
          articleEntity.setAuthor(userEntity);
//          emp.id = node.getAttributes().
//              getNamedItem("id").getNodeValue();

          NodeList childNodes = node.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);

            //Identifying the child tag of employee encountered.
            if (cNode instanceof Element) {
              Node lastChild = cNode.getLastChild();
              if (lastChild != null) {
                String content = lastChild.
                    getTextContent().trim();
                switch (cNode.getNodeName()) {
                  case "title":
                    emp.title = content;
                    articleEntity.setTitle(content);
                    break;
                  case "link":
                    emp.link = content;
                    String tag = JsoupTest.getTagName(content);
                    HashSet<TagEntity> objects = new HashSet<>();
                    TagEntity existingTag = null;
                    try {
                      existingTag = (TagEntity) entityManager.createQuery("select te from TagEntity te where te.title = :tag").setParameter("tag",  tag).getSingleResult();
                    } catch (NoResultException e) {
                      existingTag = null;
                    }
                    if (existingTag == null) {
                      existingTag = new TagEntity(tag, tag, true);
                      entityManager.persist(existingTag);
                    }
                    objects.add(existingTag);
                    articleEntity.setTags(objects);
                    break;
                  case "description":
                    emp.description = content;
                    articleEntity.setText(content);
                    break;
                  case "pubDate":
                    emp.pubdate = content;
                    final String OLD_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
                    final String NEW_FORMAT = "yyyy/MM/dd";

// August 12, 2010
                    //String oldDateString = "12/08/2010";
                    String newDateString;

                    //System.out.println(emp.pubdate);
                    SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, Locale.US);
                    Date d = sdf.parse(emp.pubdate);
                    emp.date = d;
                    articleEntity.setDateOfPublish(d);
                    //System.out.println(d);
                    break;
                }
              }
            }
          }
          articles.add(emp);
          entityManager.persist(articleEntity);
          articleEntities.add(articleEntity);
        }

      }
    }
    System.out.println(articles);
    transaction.commit();

  }

  static class Article {
    String title;
    String link;
    String description;
    String pubdate;
    Date date;

    @Override
    public String toString() {
      return title + " " + link + "(" + description + ")" + pubdate + "\r\n";
    }
  }


  public static ArticleEntity createArticle() {
    UserEntity userEntity = dataLoad.getAdminUser();
    ArticleEntity articleEntity = dataLoad.createArticle(userEntity);
    DepartmentEntity departmentEntity = dataLoad.getDepartment("relation");
    articleEntity.setDepartment(departmentEntity);
    entityManager.persist(articleEntity);
    return articleEntity;
  }
}
