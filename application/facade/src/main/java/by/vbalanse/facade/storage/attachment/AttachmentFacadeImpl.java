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

import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.dao.storage.StorageSubfolderDao;
import by.vbalanse.dao.storage.attachment.AttachmentDao;
import by.vbalanse.dao.storage.attachment.AttachmentGroupDao;
import by.vbalanse.facade.article.ImageInfo;
import by.vbalanse.facade.storage.FileOperationException;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.storage.StorageFileFacadeImpl;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.common.utils.HibernateUtils;
import by.vbalanse.model.storage.*;
import by.vbalanse.model.storage.attachment.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipFile;


@Service("attachmentFacade")
public class AttachmentFacadeImpl implements AttachmentFacade {

  @Resource(name = "attachmentFacade")
  private AttachmentFacade attachmentFacade;
  @Autowired
  private StorageFileFacade storageFileFacade;

  @Autowired
  private AttachmentDao attachmentDao;
  @Autowired
  private AttachmentGroupDao attachmentGroupDao;
  @Autowired
  private StorageFileDao storageFileDao;
  @Autowired
  private StorageSubfolderDao storageSubfolderDao;

  public void setAttachmentFacade(AttachmentFacade attachmentFacade) {
    this.attachmentFacade = attachmentFacade;
  }

  public void setStorageFileFacade(StorageFileFacade storageFileFacade) {
    this.storageFileFacade = storageFileFacade;
  }

  public void setAttachmentDao(AttachmentDao attachmentDao) {
    this.attachmentDao = attachmentDao;
  }

  public void setAttachmentGroupDao(AttachmentGroupDao attachmentGroupDao) {
    this.attachmentGroupDao = attachmentGroupDao;
  }

  public void setStorageFileDao(StorageFileDao storageFileDao) {
    this.storageFileDao = storageFileDao;
  }

  public void setStorageSubfolderDao(StorageSubfolderDao storageSubfolderDao) {
    this.storageSubfolderDao = storageSubfolderDao;
  }

  public void saveOrUpdate(long attachmentGroupId, List<AttachmentParams> attachmentsParams, String destinationFolderCode) {
    // getting AttachmentGroup to check that it is exists
    AttachmentGroupEntity attachmentGroup = attachmentGroupDao.find(attachmentGroupId);
    // getting Attachments by the AttachmentGroup primary key
    List<AbstractAttachmentEntity> existingAttachments = attachmentDao.findAttachmentsByGroup(attachmentGroup.getId());

    // searching for the attachments that must be deleted
    List<AbstractAttachmentEntity> toDelete = new ArrayList<AbstractAttachmentEntity>();
    for (AbstractAttachmentEntity attachment : existingAttachments) {
      boolean mustBeDeleted = true;

      for (AttachmentParams params : attachmentsParams) {
        if (attachment.getId().equals(params.getId())) {
          mustBeDeleted = false;

          break;
        }
      }

      if (mustBeDeleted) {
        toDelete.add(attachment);
      }
    }

    // creating new Attachments and updating old
    for (AttachmentParams params : attachmentsParams) {
      if (params.getId() == null) {
        AbstractAttachmentEntity newAttachment = attachmentFacade.saveAttachment(params, destinationFolderCode);
        newAttachment.setAttachmentGroup(attachmentGroup);
        attachmentDao.save(newAttachment);
      } else {
        AbstractAttachmentEntity existingAttachment = attachmentDao.find(params.getId());
        if (!existingAttachments.contains(existingAttachment)) {
          throw new RuntimeException("AbstractAttachment(id=" + existingAttachment.getId() + ")" +
              " does not belongs to AttachmentGroup(id=" + attachmentGroup.getId() + ") " +
              "and can not be updated.");
        }

        // there are 2 possible cases for the update. If user had uploaded new attachemt, there is no way to do
        // simple update of the file, if the type was changed from audio to video for example. In this case we
        // must delete old attachment and create a new one, within the same attachment group
        if (params.getNewTempStorageFileId() == null) {
          attachmentFacade.updateAttachment(params);
        } else {
          AbstractAttachmentEntity newAttachment = attachmentFacade.saveAttachment(params, destinationFolderCode);
          newAttachment.setAttachmentGroup(attachmentGroup);
          attachmentDao.update(newAttachment);

          toDelete.add(existingAttachment);
        }
      }
    }

    // deleting attachments
    attachmentDao.deleteAll(toDelete);
  }

  public List<AbstractAttachmentEntity> findAttachments(long attachmentGroupId) {
    return attachmentDao.findAttachmentsByGroup(attachmentGroupId);
  }

  public void deleteAttachments(Set<Long> attachmentsIds) {
    for (Long attachmentId : attachmentsIds) {
      AbstractAttachmentEntity attachment = attachmentDao.find(attachmentId);

      attachmentDao.delete(attachment);
    }
  }

  public AbstractAttachmentEntity updateAttachment(AttachmentParams params) {
    if (params.getNewTempStorageFileId() != null) {
      throw new RuntimeException("Attachment can not be updated, because it contains newly uploaded file. " +
          "Use save atachment instead of update.");
    }

    AbstractAttachmentEntity attachment = attachmentDao.find(params.getId());
    attachment.setName(params.getName());
    attachment.setDescription(params.getDescription());
    attachment.setThumbnailWidth(params.getThumbnailWidth());

    if (attachment instanceof AttachmentImageEntity) {

      String imageFilePath = storageFileFacade.getRealFilePath(((AttachmentImageEntity) attachment).getImageThumbnailFile());
      String thumbnailFilePath = storageFileFacade.getRealFilePath(((AttachmentImageEntity) attachment).getImageThumbnailFile());
      ImageTools imageTools = ImageToolsFactory.getImageTools(imageFilePath);

      ImageFileProperties imageThumbnailProperties = imageTools.resizeToWidth(imageFilePath, thumbnailFilePath, params.getThumbnailWidth());

      ((AttachmentImageEntity) attachment).setImageThumbnailFile(((AttachmentImageEntity) attachment).getImageThumbnailFile());
      ((AttachmentImageEntity) attachment).getImageThumbnailFile().setTemp(false);
      ((AttachmentImageEntity) attachment).getImageThumbnailFile().setWidth(imageThumbnailProperties.getWidth());
      ((AttachmentImageEntity) attachment).getImageThumbnailFile().setHeight(imageThumbnailProperties.getHeight());
    }

    attachmentDao.update(attachment);

    return attachment;
  }

  public AbstractAttachmentEntity saveAttachment(AttachmentParams params, String destinationFolderCode) {
    return saveAttachment(params, destinationFolderCode, params.getThumbnailWidth());
  }

  public AttachmentImageEntity saveImageAttachment(long newTempFileId, AttachmentImageEntity oldAttachment) {
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.setNewImageId(newTempFileId);
    return saveImageAttachment(imageInfo, oldAttachment);
  }

  public AttachmentImageEntity saveImageAttachment(ImageInfo imageInfo, AttachmentImageEntity oldAttachment) {
    if (imageInfo != null && imageInfo.getNewImageId() != null) {
      AbstractStorageFileEntity abstractStorageFileEntity = storageFileDao.find(imageInfo.getNewImageId());
//    AttachmentFileEntity oldAttachmentEntity = null;
//    if (oldAttachmentId != null) {
//
//    }
      Long oldAttachmentId = oldAttachment== null ? null : oldAttachment.getId();
      AttachmentParamsImpl params = AttachmentFacadeImpl.getAttachmentParams(abstractStorageFileEntity, oldAttachmentId);
      AbstractAttachmentEntity abstractAttachmentEntity = null;
      //if (oldAttachmentId == null) {
        abstractAttachmentEntity = attachmentFacade.saveAttachment(params, AbstractStorageFileEntity.IMAGE_FOLDER);
      //} else {
      //update used only for change thumbnail width, name, description
      //  abstractAttachmentEntity = attachmentFacade.updateAttachment(params);
      //}

      return (AttachmentImageEntity) abstractAttachmentEntity;
    } else {
      return null;
    }
  }

  public static AttachmentParamsImpl getAttachmentParams(AbstractStorageFileEntity abstractStorageFileEntity, Long oldAttachmentId) {
    AttachmentParamsImpl params = new AttachmentParamsImpl();
    params.setId(oldAttachmentId);
    params.setName(abstractStorageFileEntity.getFileName());
    params.setThumbnailWidth(UserFacadeImpl.THUMBNAIL_WIDTH);
    params.setNewTempStorageFileId(abstractStorageFileEntity.getId());
    return params;
  }



  public AbstractAttachmentEntity saveAttachment(AttachmentParams params, String destinationFolderCode, int maxThumbnailWidth) {
    AbstractStorageFileEntity storageFile;
    try {
      storageFile = storageFileFacade.cloneTempStorageFile(destinationFolderCode, params.getNewTempStorageFileId());
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }

    AbstractAttachmentEntity attachment;

    // checking file instance, creating copies of temp file. All created StorageFileEntities must be temp
    // the temp value must be removed only during the hibernate session flush in order not to create trash data
    if (storageFile instanceof StorageFileArchiveEntity) {
      StorageFileArchiveEntity storageFileArchive = (StorageFileArchiveEntity) storageFile;

      boolean isContentPage;
      // we need to check, may be it is content page file
      try {
        ZipFile zipFile = new ZipFile(storageFileFacade.getRealFilePath(storageFileArchive));
        isContentPage = zipFile.getEntry("index.html") != null;

        if (isContentPage) {
          StorageSubfolderEntity subfolder = storageFileFacade.reserveStorageSubfolder(
              storageFile.getCreatorId(), storageFile.getFolder().getCode()
          );

          String subfolderRealPath = storageFileFacade.getRealSubfolderPath(subfolder);
          ZipUtils.unzipArchive(zipFile, subfolderRealPath);
          zipFile.close();

          AttachmentContentPageEntity attachmentContentPage = new AttachmentContentPageEntity();

          attachmentContentPage.setArchiveFile(storageFileArchive);
          attachmentContentPage.setPreviewFolder(subfolder);
          subfolder.setTemp(false);
          storageSubfolderDao.update(subfolder);

          attachment = attachmentContentPage;
        } else {
          AttachmentFileEntity attachmentFile = new AttachmentFileEntity();

          attachmentFile.setFile(storageFileArchive);

          attachment = attachmentFile;
        }

        storageFile.setTemp(false);
        storageFileDao.update(storageFile);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else if (storageFile instanceof StorageFileAudioEntity) {
      AttachmentAudioEntity attachmentAudio = new AttachmentAudioEntity();

      attachmentAudio.setAudioFile((StorageFileAudioEntity) storageFile);
      storageFile.setTemp(false);
      storageFileDao.update(storageFile);

      attachment = attachmentAudio;
    } else if (storageFile instanceof StorageFileEntity) {
      if (storageFile.getExtension().equalsIgnoreCase("pdf")) {
        AttachmentDocumentEntity attachmentFile = new AttachmentDocumentEntity();

        attachmentFile.setFile((StorageFileEntity) storageFile);
        storageFile.setTemp(false);
        storageFileDao.update(storageFile);

        attachment = attachmentFile;
      } else if (storageFile.getExtension().equalsIgnoreCase("html")
          || storageFile.getExtension().equalsIgnoreCase("htm")) {
        StorageSubfolderEntity subfolder = storageFileFacade.reserveStorageSubfolder(
            storageFile.getCreatorId(), storageFile.getFolder().getCode()
        );

        String subfolderRealPath = storageFileFacade.getRealSubfolderPath(subfolder);
        try {
          FileUtils.copyFile(
              new File(storageFileFacade.getRealFilePath(storageFile)),
              new File(FilenameUtils.concat(subfolderRealPath, "index.html"))
          );
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        AttachmentContentPageEntity attachmentContentPage = new AttachmentContentPageEntity();

        attachmentContentPage.setPreviewFolder(subfolder);
        subfolder.setTemp(false);
        storageSubfolderDao.update(subfolder);

        attachment = attachmentContentPage;
      } else {
        AttachmentFileEntity attachmentFile = new AttachmentFileEntity();

        attachmentFile.setFile(storageFile);
        storageFile.setTemp(false);
        storageFileDao.update(storageFile);

        attachment = attachmentFile;
      }
    } else if (storageFile instanceof StorageFileImageEntity) {
      AttachmentImageEntity attachmentImage = new AttachmentImageEntity();

      // creating thumbnail for it
      StorageFileImageEntity thumbnailFile =
          (StorageFileImageEntity) storageFileFacade.reserveStorageFileClone(storageFile.getFolder().getCode(), storageFile.getId());
      // trying to create thumbnail for it
      String imageFilePath = storageFileFacade.getRealFilePath(storageFile);
      String thumbnailFilePath = storageFileFacade.getRealFilePath(thumbnailFile);
      ImageTools imageTools = ImageToolsFactory.getImageTools(imageFilePath);

      ImageFileProperties imageProperties = imageTools.getImageFileProperties(imageFilePath);
      ImageFileProperties imageThumbnailProperties = imageTools.resizeToWidth(imageFilePath, thumbnailFilePath, maxThumbnailWidth);

      // updating image file
      attachmentImage.setImageFile((StorageFileImageEntity) storageFile);
      storageFile.setTemp(false);
      ((StorageFileImageEntity) storageFile).setWidth(imageProperties.getWidth());
      ((StorageFileImageEntity) storageFile).setHeight(imageProperties.getHeight());
      storageFileDao.update(storageFile);

      // updating thumbnail file
      attachmentImage.setImageThumbnailFile(thumbnailFile);
      thumbnailFile.setTemp(false);
      thumbnailFile.setWidth(imageThumbnailProperties.getWidth());
      thumbnailFile.setHeight(imageThumbnailProperties.getHeight());
      storageFileDao.update(thumbnailFile);

      attachment = attachmentImage;
    } else if (storageFile instanceof StorageFileVideoEntity) {
      AttachmentVideoEntity attachmentVideo = new AttachmentVideoEntity();
      String extension = storageFile.getExtension();
      if (extension.equalsIgnoreCase("mp4")) {
        attachmentVideo.setMp4VideoFile((StorageFileVideoEntity) storageFile);
      } else if (extension.equalsIgnoreCase("flv")) {
        attachmentVideo.setFlvVideoFile((StorageFileVideoEntity) storageFile);
      } else {
        // others try to convert
        attachmentVideo.setMp4VideoFile((StorageFileVideoEntity) storageFile);
      }

      //attachmentVideo.setConverterTaskId(
      //    videoConvertationProtoServiceClient.submitFileForConversion(storageFileFacade.getRealFilePath(storageFile)));

      attachmentVideo.setConverterTaskId(null);
      attachmentVideo.setConvertingFinished(false);
      storageFile.setTemp(false);
      storageFileDao.update(storageFile);

      attachment = attachmentVideo;
    } else {
      throw new RuntimeException("Unexpected StorageFile class");
    }

    attachment.setName(params.getName());
    attachment.setDescription(params.getDescription());
    attachment.setThumbnailWidth(params.getThumbnailWidth());
    attachmentDao.save(attachment);

    return attachment;
  }

  public void resizeThumbnailToWidth(AttachmentImageEntity attachment, int maxThumbnailWidth) {
    String imageFilePath = storageFileFacade.getRealFilePath(attachment.getImageFile());
    String thumbnailFilePath = storageFileFacade.getRealFilePath(attachment.getImageThumbnailFile());
    ImageTools imageTools = ImageToolsFactory.getImageTools(imageFilePath);
    ImageFileProperties imageThumbnailProperties = imageTools.resizeToWidth(imageFilePath, thumbnailFilePath, maxThumbnailWidth);
    attachment.getImageThumbnailFile().setWidth(imageThumbnailProperties.getWidth());
    attachment.getImageThumbnailFile().setHeight(imageThumbnailProperties.getHeight());
    storageFileDao.update(attachment.getImageThumbnailFile());
  }

  public AttachmentClonedWrapper saveAttachmentClone(Long attachmentId) {
    AbstractAttachmentEntity attachment = attachmentDao.find(attachmentId);
    return saveAttachmentClone(attachment, null);
  }

  public AttachmentClonedWrapper saveAttachmentClone(Long attachmentId, Long assignToAttachmentGroupId) {
    AbstractAttachmentEntity attachment = attachmentDao.find(attachmentId);
    AttachmentGroupEntity attachmentGroup = attachmentGroupDao.find(assignToAttachmentGroupId);
    return saveAttachmentClone(attachment, attachmentGroup);
  }

  public AttachmentGroupClonedWrapper saveAttachmentGroupClone(Long attachmentGroupId) {
    AttachmentGroupEntity attachmentGroup = attachmentGroupDao.find(attachmentGroupId);
    return saveAttachmentGroupClone(attachmentGroup);
  }

  public List<AbstractAttachmentEntity> findNotConvertedVideoAttachmentList(int maxCountOfAttachment) {
    return attachmentDao.findNotConvertedVideoAttachmentList(maxCountOfAttachment);
  }

  public List<AbstractAttachmentEntity> findConvertedVideoWithoutPreviewImageList(int maxCountOfAttachment) {
    return attachmentDao.findConvertedVideoWithoutPreviewImageList(maxCountOfAttachment);
  }

  private AttachmentGroupClonedWrapper saveAttachmentGroupClone(AttachmentGroupEntity attachmentGroup) {
    AttachmentGroupEntity newAttachmentGroup = new AttachmentGroupEntity();
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    attachmentGroupDao.save(newAttachmentGroup);
    for (AbstractAttachmentEntity abstractAttachment : attachmentGroup.getAttachments()) {
      storageEntitiesWrapper.addAll(saveAttachmentClone(abstractAttachment, newAttachmentGroup).getStorageEntitiesWrapper());
    }
    AttachmentGroupClonedWrapper attachmentGroupClonedWrapper = new AttachmentGroupClonedWrapper();
    attachmentGroupClonedWrapper.setAttachmentGroup(newAttachmentGroup);
    attachmentGroupClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentGroupClonedWrapper;
  }

  private AttachmentClonedWrapper saveAttachmentClone(AbstractAttachmentEntity attachment, AttachmentGroupEntity attachmentGroup) {
    attachment = HibernateUtils.deproxy(attachment);
    if (attachment instanceof AttachmentAudioEntity) {
      return saveAttachmentClone((AttachmentAudioEntity) attachment, attachmentGroup);
    } else if (attachment instanceof AttachmentContentPageEntity) {
      return saveAttachmentClone((AttachmentContentPageEntity) attachment, attachmentGroup);
    } else if (attachment instanceof AttachmentDocumentEntity) {
      return saveAttachmentClone((AttachmentDocumentEntity) attachment, attachmentGroup);
    } else if (attachment instanceof AttachmentFileEntity) {
      return saveAttachmentClone((AttachmentFileEntity) attachment, attachmentGroup);
    } else if (attachment instanceof AttachmentVideoEntity) {
      return saveAttachmentClone((AttachmentVideoEntity) attachment, attachmentGroup);
    } else if (attachment instanceof AttachmentImageEntity) {
      return saveAttachmentClone((AttachmentImageEntity) attachment, attachmentGroup);
    } else {
      throw new RuntimeException("Unexpected StorageFile class");
    }

  }

  private AttachmentClonedWrapper saveAttachmentClone(AttachmentAudioEntity attachment, AttachmentGroupEntity attachmentGroup) {
    AttachmentAudioEntity newAttachment = new AttachmentAudioEntity();
    cloneAbstractAttachment(attachment, newAttachment, attachmentGroup);
    StorageFileAudioEntity tempStorageFile;
    try {
      tempStorageFile = storageFileFacade.cloneTempStorageFile(attachment.getAudioFile().getFolder().getCode(), attachment.getAudioFile().getId(), true);
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }
    newAttachment.setAudioFile(tempStorageFile);
    attachmentDao.save(newAttachment);
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    storageEntitiesWrapper.addStorageFile(tempStorageFile);
    AttachmentClonedWrapper attachmentClonedWrapper = new AttachmentClonedWrapper();
    attachmentClonedWrapper.setAttachment(newAttachment);
    attachmentClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentClonedWrapper;
  }


  private AttachmentClonedWrapper saveAttachmentClone(AttachmentContentPageEntity attachment, AttachmentGroupEntity attachmentGroup) {
    AttachmentContentPageEntity newAttachment = new AttachmentContentPageEntity();
    cloneAbstractAttachment(attachment, newAttachment, attachmentGroup);
    StorageFileArchiveEntity tempStorageFile;
    StorageSubfolderEntity subfolder;
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    if (attachment.getArchiveFile() != null) {
      try {
        tempStorageFile = storageFileFacade.cloneTempStorageFile(attachment.getArchiveFile().getFolder().getCode(), attachment.getArchiveFile().getId(), true);
        subfolder = storageFileFacade.reserveStorageSubfolder(tempStorageFile.getCreatorId(), tempStorageFile.getFolder().getCode());
      } catch (FileOperationException e) {
        throw new RuntimeException(e);
      }
      try {
        ZipFile zipFile = new ZipFile(storageFileFacade.getRealFilePath(tempStorageFile));
        String subfolderRealPath = storageFileFacade.getRealSubfolderPath(subfolder);
        ZipUtils.unzipArchive(zipFile, subfolderRealPath);
        zipFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      newAttachment.setArchiveFile(tempStorageFile);
      storageEntitiesWrapper.addStorageFile(tempStorageFile);
    } else {
      subfolder = storageFileFacade.reserveStorageSubfolder(attachment.getPreviewFolder().getCreatorId(), attachment.getPreviewFolder().getFolder().getCode());
      String srcSubfolderRealPath = storageFileFacade.getRealSubfolderPath(attachment.getPreviewFolder());
      String destSubfolderRealPath = storageFileFacade.getRealSubfolderPath(subfolder);
      File srcDir = new File(srcSubfolderRealPath);
      File destDir = new File(destSubfolderRealPath);
      if (srcDir.exists()) {
        try {
          FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
    newAttachment.setPreviewFolder(subfolder);
    attachmentDao.save(newAttachment);
    storageEntitiesWrapper.addStorageSubfolder(subfolder);
    AttachmentClonedWrapper attachmentClonedWrapper = new AttachmentClonedWrapper();
    attachmentClonedWrapper.setAttachment(newAttachment);
    attachmentClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentClonedWrapper;
  }

  private AttachmentClonedWrapper saveAttachmentClone(AttachmentDocumentEntity attachment, AttachmentGroupEntity attachmentGroup) {
    AttachmentDocumentEntity newAttachment = new AttachmentDocumentEntity();
    cloneAbstractAttachment(attachment, newAttachment, attachmentGroup);
    StorageFileEntity tempStorageFile;
    try {
      tempStorageFile = storageFileFacade.cloneTempStorageFile(attachment.getFile().getFolder().getCode(), attachment.getFile().getId(), true);
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }
    newAttachment.setFile(tempStorageFile);
    attachmentDao.save(newAttachment);
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    storageEntitiesWrapper.addStorageFile(tempStorageFile);
    AttachmentClonedWrapper attachmentClonedWrapper = new AttachmentClonedWrapper();
    attachmentClonedWrapper.setAttachment(newAttachment);
    attachmentClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentClonedWrapper;
  }

  private AttachmentClonedWrapper saveAttachmentClone(AttachmentFileEntity attachment, AttachmentGroupEntity attachmentGroup) {
    AttachmentFileEntity newAttachment = new AttachmentFileEntity();
    cloneAbstractAttachment(attachment, newAttachment, attachmentGroup);
    AbstractStorageFileEntity tempStorageFile;
    try {
      tempStorageFile = storageFileFacade.cloneTempStorageFile(attachment.getFile().getFolder().getCode(), attachment.getFile().getId(), true);
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }
    newAttachment.setFile(tempStorageFile);
    attachmentDao.save(newAttachment);
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    storageEntitiesWrapper.addStorageFile(tempStorageFile);
    AttachmentClonedWrapper attachmentClonedWrapper = new AttachmentClonedWrapper();
    attachmentClonedWrapper.setAttachment(newAttachment);
    attachmentClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentClonedWrapper;
  }

  private AttachmentClonedWrapper saveAttachmentClone(AttachmentVideoEntity attachment, AttachmentGroupEntity attachmentGroup) {
    AttachmentVideoEntity newAttachment = new AttachmentVideoEntity();
    cloneAbstractAttachment(attachment, newAttachment, attachmentGroup);
    StorageFileVideoEntity tempStorageFlvFile = null;
    StorageFileVideoEntity tempStorageMp4File = null;
    //flv video attachment
    try {
      if (attachment.getFlvVideoFile() != null) {
        tempStorageFlvFile = storageFileFacade.cloneTempStorageFile(attachment.getFlvVideoFile().getFolder().getCode(), attachment.getFlvVideoFile().getId(), true);
        newAttachment.setFlvVideoFile(tempStorageFlvFile);
      }
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }
    try {
      if (attachment.getMp4VideoFile() != null) {
        tempStorageMp4File = storageFileFacade.cloneTempStorageFile(attachment.getMp4VideoFile().getFolder().getCode(), attachment.getMp4VideoFile().getId(), true);
        newAttachment.setMp4VideoFile(tempStorageMp4File);
      }
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }

    newAttachment.setConvertingFinished(attachment.isConvertingFinished());
    newAttachment.setConverterTaskId(attachment.getConverterTaskId());

//    newAttachment.setConverterTaskId(videoConvertationProtoServiceClient.submitFileForConversion(storageFileFacade.getRealFilePath(newAttachment.get)));

    attachmentDao.save(newAttachment);
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    if (attachment.getFlvVideoFile() != null) {
      storageEntitiesWrapper.addStorageFile(tempStorageFlvFile);
    }
    if (attachment.getMp4VideoFile() != null) {
      storageEntitiesWrapper.addStorageFile(tempStorageMp4File);
    }
    AttachmentClonedWrapper attachmentClonedWrapper = new AttachmentClonedWrapper();
    attachmentClonedWrapper.setAttachment(newAttachment);
    attachmentClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentClonedWrapper;
  }

  private AttachmentClonedWrapper saveAttachmentClone(AttachmentImageEntity attachment, AttachmentGroupEntity attachmentGroup) {
    AttachmentImageEntity newAttachment = new AttachmentImageEntity();
    cloneAbstractAttachment(attachment, newAttachment, attachmentGroup);
    StorageFileImageEntity imageFile;
    StorageFileImageEntity imageThumbnailFile;
    try {
      imageFile = storageFileFacade.cloneTempStorageFile(attachment.getImageFile().getFolder().getCode(), attachment.getImageFile().getId(), true);
      imageThumbnailFile = storageFileFacade.cloneTempStorageFile(attachment.getImageThumbnailFile().getFolder().getCode(), attachment.getImageThumbnailFile().getId(), true);
    } catch (FileOperationException e) {
      throw new RuntimeException(e);
    }
    newAttachment.setImageFile(imageFile);
    newAttachment.setImageThumbnailFile(imageThumbnailFile);
    attachmentDao.save(newAttachment);
    StorageEntitiesWrapper storageEntitiesWrapper = new StorageEntitiesWrapper();
    storageEntitiesWrapper.addStorageFile(imageFile);
    storageEntitiesWrapper.addStorageFile(imageThumbnailFile);
    AttachmentClonedWrapper attachmentClonedWrapper = new AttachmentClonedWrapper();
    attachmentClonedWrapper.setAttachment(newAttachment);
    attachmentClonedWrapper.setStorageEntitiesWrapper(storageEntitiesWrapper);
    return attachmentClonedWrapper;
  }

  private <A extends AbstractAttachmentEntity> A cloneAbstractAttachment(A attachment, A newAttachment, AttachmentGroupEntity attachmentGroup) {
    newAttachment.setAttachmentGroup(attachmentGroup);
    newAttachment.setName(attachment.getName());
    newAttachment.setDescription(attachment.getDescription());
    newAttachment.setThumbnailWidth(attachment.getThumbnailWidth());
    return newAttachment;
  }


}
