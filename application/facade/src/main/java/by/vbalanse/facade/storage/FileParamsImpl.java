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

public class FileParamsImpl implements FileParams {

  private boolean useNew;

  private Long storageImageFileId;

  public FileParamsImpl() {
  }

  public FileParamsImpl(boolean useNew, Long storageImageFileId) {
    this.useNew = useNew;
    this.storageImageFileId = storageImageFileId;
  }

  public boolean isUseNew() {
    return useNew;
  }

  public void setUseNew(boolean useNew) {
    this.useNew = useNew;
  }

  public Long getStorageFileId() {
    return storageImageFileId;
  }

  public void setStorageImageFileId(Long storageImageFileId) {
    this.storageImageFileId = storageImageFileId;
  }
}