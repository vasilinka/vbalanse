package by.vbalanse.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements.;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class JsoupTest {

  public static void main2(String[] args) throws IOException {
    File input = new File("E:\\projects\\vbalanse\\application\\gestalt_persons.html");
    Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

    Elements links = doc.select("a[href]"); // a with href
    Elements pngs = doc.select("img[src$=.png]");
    // img with src ending .png

    Elements select = doc.select("table.contentpaneopen tr td a.contentpagetitle");
    Iterator<Element> iterator = select.iterator();
    while (iterator.hasNext()) {
      Element next = iterator.next();
      //System.out.println(next.attr("href") + " " +  next.text());
    }

    Elements select2 = doc.select("table.contentpaneopen tr td table td");
    Iterator<Element> iterator2 = select2.iterator();
    while (iterator2.hasNext()) {
      Element next = iterator2.next();
      System.out.println(next.html());
      System.out.println("--------------------------------------------------------------");
    }

    //System.out.println(select);
    // > tr > td > a

    // div with class=masthead

    //Elements resultLinks = doc.select("h3.r > a"); // direct a after h3
  }

  public static String getTagName(String url) throws IOException {



    Document doc = Jsoup.connect(url).get();

//    Elements links = doc.select("a[href]"); // a with href
//    Elements pngs = doc.select("img[src$=.png]");
    // img with src ending .png

    Elements a = doc.select("div.h1_razdel a:eq(1)");
    Iterator<Element> iterator = a.iterator();
    Element next = iterator.next();
    return next.html();
//    System.out.println("/////////////////////////////////");
//
//    while (iterator.hasNext()) {
//      Element next = iterator.next();
//      System.out.println(next.text());
//    }
//    return null;

//    Elements select2 = doc.select("table.contentpaneopen tr td table td");
//    Iterator<Element> iterator2 = select2.iterator();
//    while (iterator2.hasNext()) {
//      Element next = iterator2.next();
//      System.out.println(next.html());
//      System.out.println("--------------------------------------------------------------");
//    }
  }
}
