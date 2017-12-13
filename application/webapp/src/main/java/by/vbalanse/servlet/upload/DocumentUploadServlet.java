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

import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class DocumentUploadServlet implements Controller {

  private static final Logger LOGGER = Logger.getLogger(DocumentUploadServlet.class);

  private StorageFileFacade storageFileFacade;

  @Required
  public void setStorageFileFacade(StorageFileFacade storageFileFacade) {
    this.storageFileFacade = storageFileFacade;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/plain;charset=UTF-8");

    PrintWriter out = response.getWriter();

    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (!isMultipart) {
      throw new ServletException("Servlet support only multipart data,");
    }

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    FileOutputStream fos = null;
    try {
      @SuppressWarnings("unchecked")
      List<FileItem> items = upload.parseRequest(request);
      if (items.size() != 1) {
        throw new ServletException("Servlet can upload only single file.");
      }

      FileItem file = items.get(0);

      // todo: implement validation, and other stuff
      if (file == null) {
        throw new ServletException("No data.");
      }

      // saving temp file
      AbstractStorageFileEntity abstractStorageFileEntity = storageFileFacade.reserveStorageFileInTempFolder(file.getName(), 0L);
      String fileWithFullPath = storageFileFacade.getRealFilePath(abstractStorageFileEntity);
      fos = new FileOutputStream(fileWithFullPath);
      IOUtils.copy(file.getInputStream(), fos);


    } catch (Exception e) {
      LOGGER.error("Failed to upload the file.", e);

      out.print("FAILURE");
    } finally {
      IOUtils.closeQuietly(fos);
    }

    return null;
  }

}
