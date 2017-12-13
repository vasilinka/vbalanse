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

package by.vbalanse.model.storage;

import by.vbalanse.model.common.AbstractManagedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = StorageFileStatusEntity.TABLE_NAME)
public class StorageFileStatusEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = AbstractStorageFileEntity.TABLE_NAME + "_status";

  public static final String COLUMN_ATTACHMENT_WITH_TEMP_STORAGE_FILE = "att_with_temp_stg_file_";
  public static final String COLUMN_NOT_TEMP_STORAGE_FILE_WITH_NO_PARENT = "not_temp_stg_no_parent_";
  public static final String COLUMN_STORAGE_FILE_ABSTRACT_ONLY = "stg_file_abstract_";

  @NotNull
  @Column(name = COLUMN_ATTACHMENT_WITH_TEMP_STORAGE_FILE, columnDefinition = "BIT", length = 1)
  private boolean attachmentWithTempStorageFile;

  @NotNull
  @Column(name = COLUMN_NOT_TEMP_STORAGE_FILE_WITH_NO_PARENT, columnDefinition = "BIT", length = 1)
  private boolean notTempStorageFileWithNoParent;

  @NotNull
  @Column(name = COLUMN_STORAGE_FILE_ABSTRACT_ONLY, columnDefinition = "BIT", length = 1)
  private boolean storageFileAbstractOnly;

  public boolean isAttachmentWithTempStorageFile() {
    return attachmentWithTempStorageFile;
  }

  public void setAttachmentWithTempStorageFile(boolean attachmentWithTempStorageFile) {
    this.attachmentWithTempStorageFile = attachmentWithTempStorageFile;
  }

  public boolean isNotTempStorageFileWithNoParent() {
    return notTempStorageFileWithNoParent;
  }

  public void setNotTempStorageFileWithNoParent(boolean notTempStorageFileWithNoParent) {
    this.notTempStorageFileWithNoParent = notTempStorageFileWithNoParent;
  }

  public boolean isStorageFileAbstractOnly() {
    return storageFileAbstractOnly;
  }

  public void setStorageFileAbstractOnly(boolean storageFileAbstractOnly) {
    this.storageFileAbstractOnly = storageFileAbstractOnly;
  }
}
