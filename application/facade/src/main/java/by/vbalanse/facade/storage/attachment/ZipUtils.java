/*
 * This Learning Management System (“Software”) is the exclusive and sole property of Baja Education. Inc. (“Baja”).
 * Baja has the sole rights to copy the software, create derivatives or modified versions of it, distribute copies
 * to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights which also includes
 * indirect copying  such as unauthorized translation of the code into a different programming language without
 * written explicit permission from Baja is an infringer and subject to liability for damages or statutory fines.
 * Interested parties may contact bpozos@gmail.com.
 *
 * (c) 2012 Baja Education
 */

package by.vbalanse.facade.storage.attachment;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

  private static Logger log = Logger.getLogger(ZipUtils.class);

  public static void unzipArchive(ZipFile zipfile, String outputDir) {
    try {
      File outputFolder = new File(outputDir);

      for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
        ZipEntry entry = (ZipEntry) e.nextElement();
        unzipEntry(zipfile, entry, outputFolder);
      }
    } catch (Exception e) {
      log.error("Error while extracting file " + zipfile.getName(), e);
    }
  }

  public static void main(String[] args) {
    Set<String> strings = new HashSet<String>();
//    strings.add("d:\\Projects\\LMS\\itision.lms.server\\common\\storage\\application\\facade\\src\\main\\java\\com\\itision\\common\\storage\\facade\\FileOperationException.java");
    strings.add("d:\\Projects\\LMS\\itision.lms.server\\common\\storage\\application\\facade\\src\\main\\java\\com\\itision\\common\\storage\\facade\\");
    strings.add("d:\\Projects\\LMS\\itision.lms.server\\common\\storage\\application\\facade\\src\\main\\java\\com\\itision\\common\\storage\\facadddde\\");
    strings.add("d:\\Projects\\LMS\\itision.lms.server\\common\\storage\\application\\facade\\src\\main\\java\\com\\itision\\common\\storage\\facade\\facade");
    String zipfile = "d:\\Projects\\LMS\\itision.lms.server\\common\\storage\\application\\facade\\src\\main\\java\\com\\itision\\common\\storage\\facade\\test.zip";

    try {
      Map<String, String> map = zipArchive(zipfile, strings);
      for (String s : map.keySet()) {
        System.out.println(s + " " + map.get(s));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Map<String, String> createCorrectZipEntryNames(Set<String> paths) {
    Map<String, String> filePathsZipEntryNameMap = new HashMap<String, String>();

    for (String filePath : paths) {
      File f = new File(filePath);
//      if (!f.canRead()) {
//        continue;
//      }
      String zipFileName = f.getName();
      int counter = 0;
      while (filePathsZipEntryNameMap.containsValue(zipFileName + (counter == 0 ? "" : "[" + counter + "]"))) {
        counter++;
      }
      zipFileName = zipFileName + (counter == 0 ? "" : "[" + counter + "]");
      filePathsZipEntryNameMap.put(filePath, zipFileName);
    }
    return filePathsZipEntryNameMap;
  }

  public static Map<String, String> zipArchive(String zipfile, Set<String> paths) throws IOException {
    Map<String, String> filePathsZipEntryNameMap = createCorrectZipEntryNames(paths);
    zipArchive(zipfile, filePathsZipEntryNameMap);
    return filePathsZipEntryNameMap;
  }

  public static void zipArchive(String zipfile, Map<String, String> filePathsZipEntryNameMap) throws IOException {
    zipArchive(zipfile, filePathsZipEntryNameMap, false);
  }

  public static void zipArchive(String zipfile, Map<String, String> filePathsZipEntryNameMap, boolean skipFileIfCanNotBeRead) throws IOException {
    ZipOutputStream zipOutputStream = null;
    try {
      FileOutputStream fout = new FileOutputStream(zipfile);
      zipOutputStream = new ZipOutputStream(fout);
      for (String filePath : filePathsZipEntryNameMap.keySet()) {
        zipEntry(zipOutputStream, filePath, filePathsZipEntryNameMap.get(filePath), skipFileIfCanNotBeRead);

      }
      IOUtils.closeQuietly(zipOutputStream);
    } catch (IOException e) {
      IOUtils.closeQuietly(zipOutputStream);
      File f = new File(zipfile);
      if (f.exists()) {
        f.delete();
      }
      log.error("Error while zipping file " + zipfile, e);
      throw e;
    }
  }

  private static void zipEntry(ZipOutputStream zipOutputStream, String path, String zipFileName, boolean skipFileIfCanNotBeRead) throws IOException {

    List<String> paths = new ArrayList<String>();
    paths.add(path);
    while (!paths.isEmpty()) {
      String nextPath = paths.remove(0);

      File f = new File(nextPath);
      if (!f.canRead()) {
        log.error("File \"" + nextPath + "\" can not be read ");
        if (skipFileIfCanNotBeRead) {
          continue;
        } else {
          throw new IOException("File can not be read");
        }
      }
      if (f.isDirectory()) {
        for (String subFilePath : f.list()) {
          paths.add(nextPath + "/" + subFilePath);
        }
        continue;
      }
      FileInputStream inputStream = new FileInputStream(f);
      String zipEntryName = Pattern.compile(path, Pattern.LITERAL).matcher(nextPath).replaceFirst(zipFileName);
      log.warn("File \"" + nextPath + "\" was added to archive (\"" + zipEntryName + "\")");
      ZipEntry ze = new ZipEntry(zipEntryName);
      zipOutputStream.putNextEntry(ze);

      try {
        IOUtils.copy(inputStream, zipOutputStream);
      } finally {
        IOUtils.closeQuietly(inputStream);
      }
    }
  }

  private static void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir) throws IOException {
    if (entry.isDirectory()) {
      createDir(new File(outputDir, entry.getName()));
      return;
    }

    File outputFile = new File(outputDir, entry.getName());
    if (!outputFile.getParentFile().exists()) {
      createDir(outputFile.getParentFile());
    }

    log.debug("Extracting: " + entry);
    BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

    try {
      IOUtils.copy(inputStream, outputStream);
    } finally {
      outputStream.close();
      inputStream.close();
    }
  }

  private static void createDir(File dir) {
    log.debug("Creating dir " + dir.getName());
    if (!dir.mkdirs()) {
      throw new RuntimeException("Can not create dir " + dir);
    }
  }

}
