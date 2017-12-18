package by.vbalanse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vasilina on 25.04.2015.
 */
public class FontelloFinder {
  public static void main(String[] args) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream("D:\\projects\\vbalanse\\application\\webapp\\src\\main\\frontend\\js\\fontello.json");
    FileInputStream inputFile = new FileInputStream("D:\\projects\\vbalanse\\application\\webapp\\src\\main\\frontend\\css\\fontello\\fontello.css");
    String theString2 = IOUtils.toString(inputFile);
    ArrayList<String> strings = new ArrayList<>();
    Matcher matcher = Pattern.compile(".fontelloicon-([a-zA-Z0-9]*):before").matcher(theString2);
    while (matcher.find()) {
      System.out.println("group 1: " + matcher.group(1));
      strings.add(matcher.group(1));
    }
    //JsonWriter jsonWriter = new JsonWriter(fileOutputStream);
    fileOutputStream.write(new Gson().toJson(strings).getBytes());
    fileOutputStream.close();
    inputFile.close();

  }
}
