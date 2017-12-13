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

package by.vbalanse.servlet.upload;

import by.vbalanse.dao.user.UserDao;
import by.vbalanse.facade.storage.FileWithPathId;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.IOException;

public class LMSSingleFileUploadServlet extends SingleFileUploadServlet<FileWithPathId> {

  @Autowired
  private StorageFileFacade storageFileFacade;

  @Autowired
  private UserDao userDao;
  protected AbstractStorageFileEntity fileEntity;

  protected FileWithPathId generateFileNameWithPath(String fileName) throws IOException {
    fileEntity =
        storageFileFacade.reserveStorageFileInTempFolder(fileName, 1l);
    StorageFolderEntity folder = fileEntity.getFolder();

    ServletContext servletContext = getServletContext();

    String realPath;
    if (folder.isFullPath()) {
      realPath = folder.getPath();
    } else {
      realPath = servletContext.getRealPath(folder.getPath());
    }

    return new FileWithPathId(fileEntity.getId(), realPath, fileEntity.getId().toString() + "." + fileEntity.getExtension());
  }

  protected String prepareSuccessResponseMessage(FileWithPathId fileWithPath) {
    return String.valueOf(fileWithPath.getId());
  }

  protected String prepareFailuerResponseMessage(Exception exception) {
    return "Error during file uload";
  }

}