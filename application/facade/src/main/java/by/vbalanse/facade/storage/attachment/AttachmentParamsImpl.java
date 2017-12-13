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

public class AttachmentParamsImpl implements AttachmentParams {

  public static final short DEFAULT_THUMBNAIL_WIDTH = 150;

  private Long id;
  private String name;
  private String description;
  private Short thumbnailWidth;
  private Long newTempStorageFileId;

  /**
   * Default contructor
   */
  public AttachmentParamsImpl() {
  }

  /**
   * Create params for new Attachment
   *
   * @param name                 name of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param description          description of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param newTempStorageFileId primary key of the uploaded {@link com.itision.lms.server.common.storage.model.AbstractStorageFileEntity StorageFile}
   * @param thumbnailWidth       thumbnailWidth for attachment {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   */
  public AttachmentParamsImpl(String name, String description, long newTempStorageFileId, Short thumbnailWidth) {
    this.name = name;
    this.description = description;
    this.newTempStorageFileId = newTempStorageFileId;
    this.thumbnailWidth = thumbnailWidth;
  }

  /**
   * Create params to update name and description of the existing attachment
   *
   * @param id             primary key of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param name           name of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param description    description of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param thumbnailWidth thumbnailWidth for attachment {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   */
  public AttachmentParamsImpl(Long id, String name, String description, Short thumbnailWidth) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnailWidth = thumbnailWidth;
  }

  /**
   * @param id                   primary key of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param name                 name of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param description          description of the {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   * @param newTempStorageFileId primary key of the uploaded {@link com.itision.lms.server.common.storage.model.AbstractStorageFileEntity StorageFile}
   * @param thumbnailWidth       thumbnailWidth for attachment {@link com.itision.lms.server.common.attachment.model.AbstractAttachmentEntity Attachment}
   */
  public AttachmentParamsImpl(Long id, String name, String description, Long newTempStorageFileId, Short thumbnailWidth) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.newTempStorageFileId = newTempStorageFileId;
    this.thumbnailWidth = thumbnailWidth;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getNewTempStorageFileId() {
    return newTempStorageFileId;
  }

  public void setNewTempStorageFileId(Long newTempStorageFileId) {
    this.newTempStorageFileId = newTempStorageFileId;
  }

  public Short getThumbnailWidth() {
    return thumbnailWidth;
  }

  public void setThumbnailWidth(Short thumbnailWidth) {
    this.thumbnailWidth = thumbnailWidth;
  }
}
