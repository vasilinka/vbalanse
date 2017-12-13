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

import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import by.vbalanse.model.storage.StorageSubfolderEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface StorageFileFacade {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  //@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
  AbstractStorageFileEntity reserveStorageFile(String fileName, Long creatorId, String codeOfFolder);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  AbstractStorageFileEntity reserveStorageFileInTempFolder(String fileName, Long creatorId);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  AbstractStorageFileEntity reserveStorageFileClone(String destinationFolderCode, Long storageFileId);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  StorageSubfolderEntity reserveStorageSubfolder(Long creatorId, String folderCode);

  @Transactional(propagation = Propagation.REQUIRED)
  <F extends AbstractStorageFileEntity> F cloneTempStorageFile(String destinationFolderCode, Long storageFileId)
      throws FileOperationException;

  <F extends AbstractStorageFileEntity> F cloneTempStorageFile(String destinationFolderCode, Long storageFileId, boolean ignoreFileOperationException)
      throws FileOperationException;

  @Transactional(propagation = Propagation.SUPPORTS)
    // todo: must be moved to a separate class, like StorageFiletools os something like that
  String getRealFilePath(AbstractStorageFileEntity storageFile);

  @Transactional(propagation = Propagation.SUPPORTS)
    // todo: must be moved to a separate class, like StorageFiletools os something like that
  String getRealFileUrl(AbstractStorageFileEntity storageFile);

  @Transactional(propagation = Propagation.SUPPORTS)
    // todo: must be moved to a separate class, like StorageFiletools os something like that
  String getRealSubfolderPath(StorageSubfolderEntity subfolder);

  @Transactional(propagation = Propagation.SUPPORTS)
  String getRealFolderPath(StorageFolderEntity folder);

  String findFailedStorageFileErrorMessage();

  void findFailedStorageFilesByQuartz();

  List<Long> findUsedTempStorageFileIdList();

  List<Long> findNotUsedNotTempStorageFileIdList();

  List<Long> findAbstractStorageFileIdList();

  FileWithPathId generateFileNameWithPath(String fileName) throws IOException;
}
