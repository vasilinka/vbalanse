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

import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.dao.storage.StorageFileStatusDao;
import by.vbalanse.dao.storage.StorageFolderDao;
import by.vbalanse.dao.storage.StorageSubfolderDao;
import by.vbalanse.facade.storage.attachment.AttachmentParamsImpl;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.storage.*;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.devlib.schmidt.imageinfo.ImageInfo;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "storageFileFacade")
public class StorageFileFacadeImpl implements StorageFileFacade, ServletContextAware {

  @Autowired
  private ServletContext servletContext;

  @Resource(name = "storageFileFacade")
  private StorageFileFacade storageFileFacade;
  @Autowired
  private StorageFileDao storageFileDao;
  @Autowired
  private StorageFolderDao storageFolderDao;
  @Autowired
  private StorageSubfolderDao storageSubfolderDao;
  private Logger log = Logger.getLogger(getClass());
  @Autowired
  private StorageFileStatusDao storageFileStatusDao;

  private Map<String, Class<? extends AbstractStorageFileEntity>> fileExtensionClassMap;

  {
    fileExtensionClassMap = new HashMap<String, Class<? extends AbstractStorageFileEntity>>();
    fileExtensionClassMap.put("doc", StorageFileEntity.class);
    fileExtensionClassMap.put("docx", StorageFileEntity.class);
    fileExtensionClassMap.put("pdf", StorageFileEntity.class);
    fileExtensionClassMap.put("ppt", StorageFileEntity.class);

    fileExtensionClassMap.put("mp3", StorageFileAudioEntity.class);

    fileExtensionClassMap.put("zip", StorageFileArchiveEntity.class);

    fileExtensionClassMap.put("bmp", StorageFileImageEntity.class);
    fileExtensionClassMap.put("gif", StorageFileImageEntity.class);
    fileExtensionClassMap.put("jpg", StorageFileImageEntity.class);
    fileExtensionClassMap.put("jpeg", StorageFileImageEntity.class);
    fileExtensionClassMap.put("png", StorageFileImageEntity.class);

    fileExtensionClassMap.put("3gp", StorageFileVideoEntity.class);
    fileExtensionClassMap.put("f4v", StorageFileVideoEntity.class);
    fileExtensionClassMap.put("flv", StorageFileVideoEntity.class);
    fileExtensionClassMap.put("m4v", StorageFileVideoEntity.class);
    fileExtensionClassMap.put("mov", StorageFileVideoEntity.class);
    fileExtensionClassMap.put("mp4", StorageFileVideoEntity.class);
  }

  @Required
  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public void setStorageFileFacade(StorageFileFacade storageFileFacade) {
    this.storageFileFacade = storageFileFacade;
  }

  public void setStorageFileDao(StorageFileDao storageFileDao) {
    this.storageFileDao = storageFileDao;
  }

  public void setStorageFolderDao(StorageFolderDao storageFolderDao) {
    this.storageFolderDao = storageFolderDao;
  }

  public void setStorageSubfolderDao(StorageSubfolderDao storageSubfolderDao) {
    this.storageSubfolderDao = storageSubfolderDao;
  }

  public void setStorageFileStatusDao(StorageFileStatusDao storageFileStatusDao) {
    this.storageFileStatusDao = storageFileStatusDao;
  }

  public static boolean isImage(final InputStream in) {
    ImageInfo ii = new ImageInfo();
    ii.setInput(in);
    return ii.check();
  }

  public AbstractStorageFileEntity reserveStorageFile(String fileName, Long creatorId, String codeOfFolder) {
    StorageFolderEntity tempFolder = storageFolderDao.getByCode(codeOfFolder);

    String extention = FilenameUtils.getExtension(fileName.toLowerCase());
    Class<? extends AbstractStorageFileEntity> clazz = fileExtensionClassMap.get(extention.toLowerCase());
    if (clazz == null) {
      clazz = StorageFileEntity.class;
    }

    AbstractStorageFileEntity tempFile;
    try {
      tempFile = clazz.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }


    tempFile.setDateOfCreate(new Date());
    tempFile.setTemp(true);
    tempFile.setRealFileName(fileName);
    tempFile.setExtension(extention);
    tempFile.setCreatorId(creatorId);
    tempFile.setFolder(tempFolder);
    storageFileDao.save(tempFile);

    return tempFile;
  }

  public AbstractStorageFileEntity reserveStorageFileInTempFolder(String fileName, Long creatorId) {
    return reserveStorageFile(fileName, creatorId, StorageFolderEntity.CODE_FOLDER_TEMP);
  }

  public AbstractStorageFileEntity reserveStorageFileClone(String destinationFolderCode, Long storageFileId) {
    AbstractStorageFileEntity file = storageFileDao.find(storageFileId);
    AbstractStorageFileEntity newFile;
    StorageFolderEntity destinationFolder = storageFolderDao.getByCode(destinationFolderCode);

    if (file instanceof StorageFileAudioEntity) {
      newFile = new StorageFileAudioEntity();
    } else if (file instanceof StorageFileArchiveEntity) {
      newFile = new StorageFileArchiveEntity();
    } else if (file instanceof StorageFileEntity) {
      newFile = new StorageFileEntity();
    } else if (file instanceof StorageFileImageEntity) {
      StorageFileImageEntity imageFile = (StorageFileImageEntity) file;
      StorageFileImageEntity newImageFile = new StorageFileImageEntity();

      newImageFile.setWidth(imageFile.getWidth());
      newImageFile.setHeight(imageFile.getHeight());

      newFile = newImageFile;
    } else if (file instanceof StorageFileVideoEntity) {
      StorageFileVideoEntity videoFile = (StorageFileVideoEntity) file;
      StorageFileVideoEntity newVideoFile = new StorageFileVideoEntity();

      newVideoFile.setWidth(videoFile.getWidth());
      newVideoFile.setHeight(videoFile.getHeight());

      newFile = newVideoFile;
    } else {
      throw new RuntimeException("Unexpected class of Storage file");
    }
    newFile.setRealFileName(file.getRealFileName());
    newFile.setExtension(file.getExtension());
    newFile.setCreatorId(file.getCreatorId());
    newFile.setDateOfCreate(new Date());
    newFile.setTemp(true);
    newFile.setFolder(destinationFolder);

    storageFileDao.save(newFile);

    return newFile;
  }

  public StorageSubfolderEntity reserveStorageSubfolder(Long creatorId, String folderCode) {
    StorageFolderEntity folder = storageFolderDao.getByCode(folderCode);

    StorageSubfolderEntity subfolder = new StorageSubfolderEntity();
    subfolder.setFolder(folder);
    subfolder.setTemp(true);
    subfolder.setCreatorId(creatorId);
    subfolder.setDateOfCreate(new Date());
    storageSubfolderDao.save(subfolder);

    return subfolder;
  }

  @SuppressWarnings("unchecked")
  public <F extends AbstractStorageFileEntity> F cloneTempStorageFile(String destinationFolderCode, Long storageFileId)
      throws FileOperationException {
    return (F) cloneTempStorageFile(destinationFolderCode, storageFileId, false);
  }

  @SuppressWarnings("unchecked")
  public <F extends AbstractStorageFileEntity> F cloneTempStorageFile(String destinationFolderCode, Long storageFileId, boolean ignoreFileOperationException)
      throws FileOperationException {
    // todo: investigate if there is a way to avoid injection of the bean to itself. calling the method from the class could be done through the proxy
    AbstractStorageFileEntity storageFileClone = storageFileFacade.reserveStorageFileClone(destinationFolderCode, storageFileId);

    // todo: this line was at the top, but for some reason that caused NullPointer exception on second time execution. Investigate that.
    AbstractStorageFileEntity storageFile = storageFileDao.find(storageFileId);
    File sourceFile = new File(storageFileFacade.getRealFilePath(storageFile));
    File destFile = new File(storageFileFacade.getRealFilePath(storageFileClone));

    if (sourceFile.exists()) {
      try {
        FileUtils.copyFile(sourceFile, destFile, true);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      FileOperationException exception = new FileOperationException("Can't copy file.");
      if (!ignoreFileOperationException) {
        throw exception;
      } else {
        log.error("Can't copy file.", exception);
      }
    }

    return (F) storageFileClone;
  }

  // todo: must be moved to a separate class, like StorageFileTools os something like that

  public String getRealFilePath(AbstractStorageFileEntity storageFile) {
    StorageFolderEntity folder = storageFile.getFolder();

    return FilenameUtils.concat(
        getRealFolderPath(folder), String.valueOf(storageFile.getId()) + "." + storageFile.getExtension()
    );
  }

  public String getRealFileUrl(AbstractStorageFileEntity storageFile) {
    StorageFolderEntity folder = storageFile.getFolder();

    String concat = FilenameUtils.concat(folder.getPath(), String.valueOf(storageFile.getId() + "." + storageFile.getExtension()));
    concat = concat.replace('\\','/');
    return
        "/" + concat;
  }
  // todo: must be moved to a separate class, like StorageFileTools os something like that

  public String getRealSubfolderPath(StorageSubfolderEntity subfolder) {
    return FilenameUtils.concat(
        getRealFolderPath(subfolder.getFolder()),
        "" + subfolder.getId()
    );
  }

  // todo: must be moved to a separate class, like StorageFileTools os something like that

  public String getRealFolderPath(StorageFolderEntity folder) {
    return folder.isFullPath()
        ?
        folder.getPath()
        :
        servletContext.getRealPath(folder.getPath());
  }

  public String findFailedStorageFileErrorMessage() {
    StorageFileStatusEntity statusEntity = storageFileStatusDao.find(1l);
    if (statusEntity == null) {
      return null;
    } else {
      if (statusEntity.isAttachmentWithTempStorageFile() || statusEntity.isNotTempStorageFileWithNoParent() || statusEntity.isStorageFileAbstractOnly()) {
        return "error";
      }
    }
    return null;
  }

  public void findFailedStorageFilesByQuartz() {
    log.info("Searching for failed storage file started at " + new Date());
    List<Long> usedTempFileIds = findUsedTempStorageFileIdList();
    List<Long> notUsedNotTempFileIds = findNotUsedNotTempStorageFileIdList();
    List<Long> abstractStorageFileIdList = findAbstractStorageFileIdList();

    StorageFileStatusEntity statusEntity = storageFileStatusDao.find(1l);
    if (statusEntity == null) {
      statusEntity = new StorageFileStatusEntity();
    }
    statusEntity.setAttachmentWithTempStorageFile(usedTempFileIds != null && !usedTempFileIds.isEmpty());
    statusEntity.setNotTempStorageFileWithNoParent(notUsedNotTempFileIds != null && !notUsedNotTempFileIds.isEmpty());
    statusEntity.setStorageFileAbstractOnly(abstractStorageFileIdList != null && !abstractStorageFileIdList.isEmpty());
    storageFileStatusDao.saveOrUpdate(statusEntity);
  }

  public List<Long> findUsedTempStorageFileIdList() {
    return storageFileDao.findUsedTempStorageFileIdList();
  }

  public List<Long> findNotUsedNotTempStorageFileIdList() {
    return storageFileDao.findNotUsedNotTempStorageFileIdList();
  }

  public List<Long> findAbstractStorageFileIdList() {
    return storageFileDao.findAbstractStorageFileIdList();
  }

  public FileWithPathId generateFileNameWithPath(String fileName) throws IOException {
    AbstractStorageFileEntity fileEntity =
        storageFileFacade.reserveStorageFileInTempFolder(fileName, 1l);
    StorageFolderEntity folder = fileEntity.getFolder();

    String realPath;
    if (folder.isFullPath()) {
      realPath = folder.getPath();
    } else {
      realPath = servletContext.getRealPath(folder.getPath());
    }

    return new FileWithPathId(fileEntity.getId(), realPath, fileEntity.getId().toString() + "." + fileEntity.getExtension());
  }


}
