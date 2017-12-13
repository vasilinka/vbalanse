package by.vbalanse.model;

import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.training.TrainingEntity;
import by.vbalanse.model.training.TrainingTimeOrganizationTypeEnum;
import by.vbalanse.model.training.TrainingTypeEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class TrainingImport {

  public static InputStream getUrl(String url) throws ParserConfigurationException, IOException, SAXException {
    //URL url = new URL("http://belbooner.site40.net/testXmls/details.xml");
    return getHTTPXml(new URL(url));
  }

  static InputStream getHTTPXml(URL url) throws ParserConfigurationException, IOException, SAXException {

    //URL url = new URL("http://belbooner.site40.net/testXmls/details.xml");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("ACCEPT", "application/xml");
    return conn.getInputStream();
  }

  public void importTrainings(EntityManager entityManager) throws Exception {
    //Get the DOM Builder Factory
    DocumentBuilderFactory factory =
        DocumentBuilderFactory.newInstance();

    //Get the DOM Builder
    DocumentBuilder builder = factory.newDocumentBuilder();

    //Load and Parse the XML document
    //document contains the complete XML as a Tree.
//    InputStream url = getUrl();
//    BufferedReader in = new BufferedReader(new InputStreamReader(url));
//    String inputLine;
//    while ((inputLine = in.readLine()) != null)
//      System.out.println(inputLine);
    //in.close();
    Document document =
        builder.parse(
            /*ClassLoader.getSystemResourceAsStream("xml/employee.xml")*/getUrl("http://www.b17.ru/rss.php?mod=training&city=191"));

    List<Training> empList = new ArrayList<>();

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
          Training emp = new Training();
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
                    break;
                  case "link":
                    emp.link = content;
                    break;
                  case "description":
                    emp.description = content;
                    break;
                  case "pubDate":
                    emp.pubdate = content;
                    final String OLD_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
                    final String NEW_FORMAT = "yyyy/MM/dd";

// August 12, 2010
                    //String oldDateString = "12/08/2010";
                    String newDateString;

                    System.out.println(emp.pubdate);
                    SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, Locale.US);
                    Date d = sdf.parse(emp.pubdate);
                    emp.date = d;
                    //System.out.println(d);
                    break;
                }
              }
            }
          }
          empList.add(emp);
        }

      }
    }

    //Printing the Employee list populated.
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery criteria = criteriaBuilder.createQuery(PsychologistEntity.class);
    Root from = criteria.from(PsychologistEntity.class);
    TypedQuery query = entityManager.createQuery(criteria);
    List list = query.getResultList();
    int index = new Double(Math.floor(Math.random() * (list.size() - 1))).intValue();
    PsychologistEntity psychologistEntity = (PsychologistEntity) list.get(index);

    CriteriaQuery criteriaCity = criteriaBuilder.createQuery(CityEntity.class);
    Root cityFrom = criteriaCity.from(CityEntity.class);
    TypedQuery queryCity = entityManager.createQuery(criteriaCity);
    List listCity = queryCity.getResultList();
    int indexCity = new Double(Math.floor(Math.random() * (listCity.size() - 1))).intValue();
    CityEntity cityEntity = (CityEntity) listCity.get(indexCity);

    for (Training emp : empList) {
      HashSet<PsychologistEntity> authors = new HashSet<PsychologistEntity>();
      authors.add(psychologistEntity);
      TrainingEntity trainingEntity = new TrainingEntity(emp.title, emp.description, emp.date, TrainingTimeOrganizationTypeEnum.PO_DATE, TrainingTypeEnum.TRAINING, 10,
          "г. Минск ул. Победителей 3 Тел (+375295511321)", "20USD", "6", "Еженедельно в течение в полугода", authors, null, cityEntity);
      entityManager.persist(trainingEntity);
      System.out.println(emp);
    }

  }


  static class Training {
    String title;
    String link;
    String description;
    String pubdate;
    Date date;

    @Override
    public String toString() {
      return title + " " + link + "(" + description + ")" + pubdate;
    }
  }
}
