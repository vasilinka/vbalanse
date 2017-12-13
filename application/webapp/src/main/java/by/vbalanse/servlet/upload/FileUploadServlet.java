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

import by.vbalanse.facade.storage.StorageFacade;
import by.vbalanse.facade.storage.UploadCodes;
import by.vbalanse.model.storage.StorageFileEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileUploadServlet extends HttpServlet {

  private static final Logger log = Logger.getLogger(FileUploadServlet.class);

  protected static StorageFacade storageFacade;

  public void setStorageFacade(StorageFacade storageFacade) {
    FileUploadServlet.storageFacade = storageFacade;
  }

  protected String result;

  @SuppressWarnings("unchecked")
  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletOutputStream os = response.getOutputStream();

    try {
      if (!ServletFileUpload.isMultipartContent(request))
        return;

      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload upload = new ServletFileUpload(factory);
      List<FileItem> items = upload.parseRequest(request);

      List<StorageFileEntity> storageFiles = new ArrayList<StorageFileEntity>();

      //now only one file is uploaded at once.
      for (FileItem fileItem : items) {

        StorageFileEntity storageFile = saveFile(os, fileItem);
        if (storageFile == null) {
          return;
        } else {
          storageFiles.add(storageFile);
        }
      }

      //means should be 1
      if (storageFiles.size() > 0) {
        os.print(UploadCodes.SUCCESS_CODE);
        for (int i = 0; i < storageFiles.size(); i++) {
          if (i > 0) {
            os.print(',');
          }
          os.print(storageFiles.get(i).getId().toString());
        }
      } else {
        //no files to save
        returnError(os, UploadCodes.ERROR_CODE +
                "no file");
      }
    } catch (Exception e) {
      log.error("", e);
      returnError(os, UploadCodes.ERROR_CODE +
              "validation failure");
    }

  }

  protected StorageFileEntity saveFile(ServletOutputStream os, FileItem fileItem) throws IOException {
    result = validate(fileItem);
    if (result != null) {
      returnError(os, UploadCodes.ERROR_CODE + result);
      return null;
    } else {
      return storageFacade.saveStorageFile(fileItem);
    }
  }

  protected void returnError(ServletOutputStream printWriter, String error) throws IOException {
    printWriter.print(error);
  }

  /**
   * validates file to some properties : file size, content
   * return error description
   * if null returned no error ocurred
   *
   * @param fileItem file info
   * @return error description
   */
  protected abstract String validate(FileItem fileItem);

}
