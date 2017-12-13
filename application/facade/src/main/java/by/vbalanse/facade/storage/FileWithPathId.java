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

import by.vbalanse.facade.storage.FileWithPath;

public class FileWithPathId extends FileWithPath {

  private final Long id;

  public FileWithPathId(Long id, String filePath, String fileName) {
    super(filePath, fileName);
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
