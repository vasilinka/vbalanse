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

import by.vbalanse.facade.article.ImageInfo;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface AttachmentFacade {

  void saveOrUpdate(long attachmentGroupId, List<AttachmentParams> attachments, String destinationFolderCode);

  List<AbstractAttachmentEntity> findAttachments(long attachmentGroupId);

  void deleteAttachments(Set<Long> attachmentsIds);

  AbstractAttachmentEntity updateAttachment(AttachmentParams params);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  AbstractAttachmentEntity saveAttachment(AttachmentParams params, String destinationFolderCode);

  AttachmentImageEntity saveImageAttachment(ImageInfo imageInfo, AttachmentImageEntity oldAttachment);

  AttachmentImageEntity saveImageAttachment(long newTempFileId, AttachmentImageEntity oldAttachment);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  AbstractAttachmentEntity saveAttachment(AttachmentParams params, String destinationFolderCode, int maxThumbnailWidth);

  AttachmentClonedWrapper saveAttachmentClone(Long attachmentId, Long attachmentGroupId);

  AttachmentClonedWrapper saveAttachmentClone(Long attachmentId);

  void resizeThumbnailToWidth(AttachmentImageEntity attachment, int maxThumbnailWidth);

  AttachmentGroupClonedWrapper saveAttachmentGroupClone(Long attachmentGroupId);

  List<AbstractAttachmentEntity> findNotConvertedVideoAttachmentList(int maxCountOfAttachment);

  List<AbstractAttachmentEntity> findConvertedVideoWithoutPreviewImageList(int maxCountOfAttachment);
}
