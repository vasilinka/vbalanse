/*
 * This software is the exclusive and sole property of Itision.
 * Itision has the sole rights to copy the software, create derivatives or modified versions of it,
 * distribute copies to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights
 * which also includes indirect copying  such as unauthorized translation of the code into a different
 * programming language without written explicit permission from Itision is an infringer and subject
 * to liability for damages or statutory fines. Interested parties may contact e.terehov@itision.com.
 *
 * (c) 2012 Itision
 */

package by.vbalanse.facade.storage;

public class FileWithPath {

  private final String filePath;
  private final String fileName;

  public FileWithPath(String filePath, String fileName) {
    this.filePath = filePath;
    this.fileName = fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getFileName() {
    return fileName;
  }

}
