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

import by.vbalanse.model.common.AbstractEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = StorageFileAudioEntity.TABLE_NAME)
@ForeignKey(name = StorageFileAudioEntity.TABLE_NAME + AbstractEntity.DELIMITER_INDEX + AbstractEntity.COLUMN_ID)
public class StorageFileAudioEntity extends AbstractStorageFileEntity {

  public static final String TABLE_NAME = AbstractStorageFileEntity.TABLE_NAME + "$audio";

  public StorageFileAudioEntity() {
  }

}
