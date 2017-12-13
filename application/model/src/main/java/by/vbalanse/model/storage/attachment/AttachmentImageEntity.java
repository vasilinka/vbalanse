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

package by.vbalanse.model.storage.attachment;

import by.vbalanse.model.common.AbstractEntity;
import by.vbalanse.model.storage.StorageFileImageEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = AttachmentImageEntity.TABLE_NAME)
@ForeignKey(name = AttachmentImageEntity.TABLE_NAME + AbstractEntity.DELIMITER_INDEX + AbstractEntity.COLUMN_ID)
public class AttachmentImageEntity extends AbstractAttachmentEntity {

  public static final short DEFAULT_THUMBNAIL_IMAGE_WIDTH = 200;

  public static final String TABLE_NAME = AbstractAttachmentEntity.TABLE_NAME + "$image";

  public static final String COLUMN_FK_IMAGE = "fk_" + StorageFileImageEntity.TABLE_NAME;
  public static final String COLUMN_FK_IMAGE$THUMBNAIL = "fk_" + StorageFileImageEntity.TABLE_NAME + "$thumbnail";

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_IMAGE)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_IMAGE)
  private StorageFileImageEntity imageFile;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_IMAGE$THUMBNAIL)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_IMAGE$THUMBNAIL)
  private StorageFileImageEntity imageThumbnailFile;

  public StorageFileImageEntity getImageFile() {
    return imageFile;
  }

  public void setImageFile(StorageFileImageEntity imageFile) {
    this.imageFile = imageFile;
  }

  public StorageFileImageEntity getImageThumbnailFile() {
    return imageThumbnailFile;
  }

  public void setImageThumbnailFile(StorageFileImageEntity imageThumbnailFile) {
    this.imageThumbnailFile = imageThumbnailFile;
  }

}
