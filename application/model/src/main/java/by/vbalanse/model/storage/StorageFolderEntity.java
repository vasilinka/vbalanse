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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = StorageFolderEntity.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = StorageFolderEntity.COLUMN_CODE)})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class StorageFolderEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "stg_folder";

  public static final String COLUMN_CODE = "code_";
  public static final String COLUMN_PATH = "path_";
  public static final String COLUMN_IS_FULL_PATH = "is_full_path_";

  public static final String CODE_FOLDER_TEMP = "TMP";
  public static final String CODE_FOLDER_ORIGINAL = "ORG";
  public static final String CODE_FOLDER_CONVERTED = "CNV";

  @NotNull
  @Column(name = COLUMN_CODE)
  private String code;

  @NotNull
  @Column(name = COLUMN_PATH)
  private String path;

  /**
   * true - system full path to the file, false - relative path starting from servlet context
   */
  @NotNull
  @Column(name = COLUMN_IS_FULL_PATH, columnDefinition = "BIT", length = 1)
  private boolean fullPath;

  public StorageFolderEntity() {
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public boolean isFullPath() {
    return fullPath;
  }

  public void setFullPath(boolean fullPath) {
    this.fullPath = fullPath;
  }

}
