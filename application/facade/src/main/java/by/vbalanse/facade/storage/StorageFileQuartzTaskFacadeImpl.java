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
import by.vbalanse.dao.storage.StorageSubfolderDao;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageSubfolderEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service(value = "storageFileQuartzTaskFacade")
public class StorageFileQuartzTaskFacadeImpl implements StorageFileQuartzTaskFacade {

  private StorageFileDao storageFileDao;
  private StorageFileFacade storageFileFacade;
  private StorageSubfolderDao storageSubfolderDao;
  private static Logger log = Logger.getLogger(StorageFileQuartzTaskFacadeImpl.class);
  public static final int MAX_TEMP_FILE = 20;
  private boolean removeTempFileEnable;
  private boolean checkStorageFileEnable;
  private StorageFileQuartzTaskFacade storageFileQuartzTaskFacade;

  public void setRemoveTempFileEnable(boolean removeTempFileEnable) {
    this.removeTempFileEnable = removeTempFileEnable;
  }

  public void setStorageFileDao(StorageFileDao storageFileDao) {
    this.storageFileDao = storageFileDao;
  }

  public void setStorageSubfolderDao(StorageSubfolderDao storageSubfolderDao) {
    this.storageSubfolderDao = storageSubfolderDao;
  }

  public void setStorageFileFacade(StorageFileFacade storageFileFacade) {
    this.storageFileFacade = storageFileFacade;
  }

  public void setCheckStorageFileEnable(boolean checkStorageFileEnable) {
    this.checkStorageFileEnable = checkStorageFileEnable;
  }

  public void setStorageFileQuartzTaskFacade(StorageFileQuartzTaskFacade storageFileQuartzTaskFacade) {
    this.storageFileQuartzTaskFacade = storageFileQuartzTaskFacade;
  }

  public void deleteTempFiles() {
    if (removeTempFileEnable) {
      List<AbstractStorageFileEntity> tempStorageFileList = storageFileDao.findTempStorageFileList(MAX_TEMP_FILE);
      for (AbstractStorageFileEntity storageFileEntity : tempStorageFileList) {
        try {
          storageFileQuartzTaskFacade.deleteFile(storageFileEntity);
        } catch (Exception e) {
          log.error("Exception on " + storageFileEntity.getId());
          storageFileEntity.setTemp(false);
          storageFileDao.update(storageFileEntity);
        }
      }
      List<StorageSubfolderEntity> tempStorageSubFileList = storageSubfolderDao.findTempStorageFileList(MAX_TEMP_FILE);
      for (StorageSubfolderEntity storageSubfolderEntity : tempStorageSubFileList) {
        try {
          storageFileQuartzTaskFacade.deleteSubfolder(storageSubfolderEntity);
        } catch (Exception e) {
          log.error("Exception on " + storageSubfolderEntity.getId());
          storageSubfolderEntity.setTemp(false);
          storageSubfolderDao.update(storageSubfolderEntity);
        }
      }
    }
  }

  public void deleteFile(AbstractStorageFileEntity storageFileEntity) throws Exception {
    String storageFileName = storageFileEntity.getFileName();
    String realFilePath = storageFileFacade.getRealFilePath(storageFileEntity);
    File file = new File(realFilePath);
    if (file.canRead()) {
      boolean wasDeleted = file.delete();
      logResults(storageFileName, wasDeleted, "file", "");
    } else {
      logResults(storageFileName, false, "file", ". File does not exist");
    }
    storageFileDao.delete(storageFileEntity);
    logResults(storageFileName, true, "StorageFileEntity", "");
  }

  public void deleteSubfolder(StorageSubfolderEntity storageSubfolderEntity) throws Exception {
    Long storageFolderId = storageSubfolderEntity.getId();
    String realSubfolderPath = storageFileFacade.getRealSubfolderPath(storageSubfolderEntity);
    File folder = new File(realSubfolderPath);
    if (folder.canRead()) {
      deleteFolder(folder);
    } else {
      logResults(String.valueOf(storageFolderId), false, "subfolder", ". Subfolder does not exist");
    }
    storageSubfolderDao.delete(storageSubfolderEntity);
    logResults(String.valueOf(storageFolderId), true, "storageSubfolderEntity", "");
  }

  public void checkStorageFileConsistencyByQuartz() {
    if (checkStorageFileEnable) {
      storageFileFacade.findFailedStorageFilesByQuartz();
    }
  }

  private void deleteFolder(File folder) {
    File[] listOfFiles = folder.listFiles();
    for (File listOfFile : listOfFiles) {
      if (listOfFile.isFile()) {
        if (listOfFile.canRead()) {
          boolean wasDeleted = listOfFile.delete();
          logResults(listOfFile.getName(), wasDeleted, "file", "");
        } else {
          logResults(listOfFile.getName(), false, "file", ". File does not exist");
        }
      } else {
        if (listOfFile.canRead()) {
          deleteFolder(listOfFile);
          if (listOfFile.canRead()) {
            boolean wasDeleted = listOfFile.delete();
            logResults(listOfFile.getName(), wasDeleted, "subfolder", "");
          } else {
            logResults(listOfFile.getName(), false, "subfolder", ". Subfolder does not exist");
          }
        }
      }
    }
    if (folder.canRead()) {
      boolean wasDeleted = folder.delete();
      logResults(folder.getName(), wasDeleted, "subfolder", "");
    } else {
      logResults(folder.getName(), false, "subfolder", ". Subfolder does not exist");
    }
  }

  private void logResults(String fileName, boolean wasDeleted, String fileType, String reason) {
    if (!wasDeleted) {
      log.error("Can not delete temp " + fileType + ": " + fileName + " " + reason);
    } else {
      log.info("Temp " + fileType + " was successfully deleted: " + fileName);
    }
  }
}
