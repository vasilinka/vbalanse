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
import by.vbalanse.model.storage.StorageFileVideoEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = AttachmentVideoEntity.TABLE_NAME)
@ForeignKey(name = AttachmentVideoEntity.TABLE_NAME + AbstractEntity.DELIMITER_INDEX + AbstractEntity.COLUMN_ID)
public class AttachmentVideoEntity extends AbstractAttachmentEntity {

  public static final String TABLE_NAME = AbstractAttachmentEntity.TABLE_NAME + "$video";

  public static final String COLUMN_FK_STORAGE_FILE$VIDEO = "fk_" + StorageFileVideoEntity.TABLE_NAME + "_mp4";
  public static final String COLUMN_FK_STORAGE_FILE$FLV_VIDEO = "fk_" + StorageFileVideoEntity.TABLE_NAME + "_flv";
  public static final String COLUMN_FK_STORAGE_FILE$PREVIEW_IMAGE = "fk_" + StorageFileImageEntity.TABLE_NAME;
  public static final String COLUMN_CONVERTER_TASK_ID = "converter_task_id_";
  public static final String COLUMN_CONVERTER_FINISHED = "converter_finished_";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_STORAGE_FILE$VIDEO)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_STORAGE_FILE$VIDEO)
  private StorageFileVideoEntity mp4VideoFile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_STORAGE_FILE$FLV_VIDEO)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_STORAGE_FILE$FLV_VIDEO)
  private StorageFileVideoEntity flvVideoFile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = COLUMN_FK_STORAGE_FILE$PREVIEW_IMAGE)
  @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_STORAGE_FILE$PREVIEW_IMAGE)
  private StorageFileImageEntity videoPreviewImageFile;

  @Column(name = COLUMN_CONVERTER_TASK_ID)
  private Long converterTaskId;

  @NotNull
  @Column(name = COLUMN_CONVERTER_FINISHED, columnDefinition = "BIT", length = 1)
  private boolean convertingFinished;

  public Long getConverterTaskId() {
    return converterTaskId;
  }

  public void setConverterTaskId(Long converterTaskId) {
    this.converterTaskId = converterTaskId;
  }

  public boolean isConvertingFinished() {
    return convertingFinished;
  }

  public void setConvertingFinished(boolean convertingFinished) {
    this.convertingFinished = convertingFinished;
  }

  public StorageFileVideoEntity getMp4VideoFile() {
    return mp4VideoFile;
  }

  public void setMp4VideoFile(StorageFileVideoEntity mp4VideoFile) {
    this.mp4VideoFile = mp4VideoFile;
  }

  public StorageFileVideoEntity getFlvVideoFile() {
    return flvVideoFile;
  }

  public void setFlvVideoFile(StorageFileVideoEntity flvVideoFile) {
    this.flvVideoFile = flvVideoFile;
  }

  public StorageFileImageEntity getVideoPreviewImageFile() {
    return videoPreviewImageFile;
  }

  public void setVideoPreviewImageFile(StorageFileImageEntity videoPreviewImageFile) {
    this.videoPreviewImageFile = videoPreviewImageFile;
  }
}