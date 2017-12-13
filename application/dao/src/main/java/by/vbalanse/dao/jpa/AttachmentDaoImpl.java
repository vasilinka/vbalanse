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

package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.common.jpa.DaoUtils;
import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.dao.storage.StorageSubfolderDao;
import by.vbalanse.dao.storage.attachment.AttachmentDao;
import by.vbalanse.model.common.utils.HibernateUtils;
import by.vbalanse.model.storage.*;
import by.vbalanse.model.storage.attachment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends AbstractManagedEntityDaoSpringImpl<AbstractAttachmentEntity> implements AttachmentDao {

  @Autowired
  private StorageFileDao storageFileDao;
  @Autowired
  private StorageSubfolderDao storageSubfolderDao;

  public void setStorageFileDao(StorageFileDao storageFileDao) {
    this.storageFileDao = storageFileDao;
  }

  public void setStorageSubfolderDao(StorageSubfolderDao storageSubfolderDao) {
    this.storageSubfolderDao = storageSubfolderDao;
  }

  @SuppressWarnings("unchecked")
  public List<AbstractAttachmentEntity> findAttachmentsByGroup(long attachmentGroupId) {
    Query q = getNamedQuery("find.Attachment.by.attachmentGroupId");
    q.setParameter("attachmentGroupId", attachmentGroupId);
    return q.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<AbstractAttachmentEntity> findNotConvertedVideoAttachmentList(int maxCountOfAttachment) {
    CriteriaQuery criteriaQuery = createCriteria(AbstractAttachmentEntity.class);
    Root from = criteriaQuery.from(AbstractAttachmentEntity.class);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    criteriaQuery.where(criteriaBuilder.equal(from.get(AttachmentVideoEntity_.convertingFinished), false));

    DaoUtils.addOrder(criteriaQuery, criteriaBuilder, from.get(AbstractAttachmentEntity_.id), true);

    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    query.setMaxResults(maxCountOfAttachment);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<AbstractAttachmentEntity> findConvertedVideoWithoutPreviewImageList(int maxCountOfAttachment) {
    CriteriaQuery criteriaQuery = createCriteria(AbstractAttachmentEntity.class);
    Root from = criteriaQuery.from(AbstractAttachmentEntity.class);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    criteriaQuery.where(criteriaBuilder.equal(from.get(AttachmentVideoEntity_.convertingFinished), false));
    criteriaQuery.where(criteriaBuilder.isNull(from.get(AttachmentVideoEntity_.videoPreviewImageFile)));

    DaoUtils.addOrder(criteriaQuery, criteriaBuilder, from.get(AbstractAttachmentEntity_.id), true);
    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    query.setMaxResults(maxCountOfAttachment);
    return query.getResultList();
  }

  @Override
  public void delete(AbstractAttachmentEntity entity) {
    // deproxing the attachment to ge castable instance
    AbstractAttachmentEntity attachment = HibernateUtils.deproxy(entity);

    // we must not delete the storage files and subfolders. For the all related items
    // we just neet to set temp = true and cron will do the storage cleanup later
    if (attachment instanceof AttachmentAudioEntity) {
      StorageFileAudioEntity audioFile = ((AttachmentAudioEntity) attachment).getAudioFile();
      audioFile.setTemp(true);
      storageFileDao.update(audioFile);
    } else if (attachment instanceof AttachmentContentPageEntity) {
      StorageFileArchiveEntity archiveFile = ((AttachmentContentPageEntity) attachment).getArchiveFile();
      if (archiveFile != null) {
        archiveFile.setTemp(true);
        storageFileDao.update(archiveFile);
      }

      StorageSubfolderEntity subfolder = ((AttachmentContentPageEntity) attachment).getPreviewFolder();
      if (subfolder != null) {
        subfolder.setTemp(true);
        storageSubfolderDao.update(subfolder);
      }
    } else if (attachment instanceof AttachmentDocumentEntity) {
      StorageFileEntity file = ((AttachmentDocumentEntity) attachment).getFile();
      file.setTemp(true);
      storageFileDao.update(file);
    } else if (attachment instanceof AttachmentFileEntity) {
      AbstractStorageFileEntity file = ((AttachmentFileEntity) attachment).getFile();
      file.setTemp(true);
      storageFileDao.update(file);
    } else if (attachment instanceof AttachmentImageEntity) {
      StorageFileImageEntity imageFile = ((AttachmentImageEntity) attachment).getImageFile();
      imageFile.setTemp(true);
      storageFileDao.update(imageFile);

      StorageFileImageEntity imageThumbnailFile = ((AttachmentImageEntity) attachment).getImageThumbnailFile();
      imageThumbnailFile.setTemp(true);
      storageFileDao.update(imageThumbnailFile);
    } else if (attachment instanceof AttachmentVideoEntity) {
      StorageFileVideoEntity flvVideoFile = ((AttachmentVideoEntity) attachment).getFlvVideoFile();
      if (flvVideoFile != null) {
        flvVideoFile.setTemp(true);
        storageFileDao.update(flvVideoFile);
      }

      StorageFileVideoEntity mp4VideoFile = ((AttachmentVideoEntity) attachment).getMp4VideoFile();
      if (mp4VideoFile != null) {
        mp4VideoFile.setTemp(true);
        storageFileDao.update(mp4VideoFile);
      }
    } else {
      throw new RuntimeException("Unexpected attachment class");
    }

    // deleting the attachment itself
    super.delete(attachment);
  }

}
