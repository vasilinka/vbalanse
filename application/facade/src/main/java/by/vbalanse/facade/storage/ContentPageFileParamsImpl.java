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

package by.vbalanse.facade.storage;

public class ContentPageFileParamsImpl extends FileParamsImpl implements ContentPageFileParams {

  public ContentPageFileParamsImpl() {
  }

  public ContentPageFileParamsImpl(boolean useNew, Long storageImageFileId) {
    super(useNew, storageImageFileId);
  }
}
