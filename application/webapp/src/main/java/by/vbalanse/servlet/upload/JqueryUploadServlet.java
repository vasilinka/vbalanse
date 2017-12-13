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
import by.vbalanse.facade.storage.attachment.ImageTools;
import by.vbalanse.facade.storage.attachment.ImageToolsFactory;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.util.ImageUtils;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class JqueryUploadServlet extends SingleFileUploadServlet<FileWithPathId> {

  @Autowired
  private StorageFileFacade storageFileFacade;

  @Autowired
  private UserDao userDao;
  protected AbstractStorageFileEntity fileEntity;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
      if (items.size() < 1) {
        items.get(0);
        //throw new ServletException("Servlet can upload only single file.");
      }

      FileItem file = items.get(0);

      // todo: implement validation, and other stuff
      if (file == null) {
        throw new ServletException("No data.");
      }

      ImageInfo imageInfo = new ImageInfo();
      imageInfo.setInput(file.getInputStream());
      FileWithPathId fileWithPath = storageFileFacade.generateFileNameWithPath(file.getName() + "." + imageInfo.getFormatName());
      String fileWithFullPath = FilenameUtils.concat(fileWithPath.getFilePath(), fileWithPath.getFileName());
      fos = new FileOutputStream(fileWithFullPath);
      IOUtils.copy(file.getInputStream(), fos);
      fos.close();

//      out.print(ResponseCodes.RESPONSE_OK + ResponseCodes.DEIMITER + prepareSuccessResponseMessage(fileWithPath));
      FileInfo fileInfo = new FileInfo();
      fileInfo.setId(fileEntity.getId());
      fileInfo.setUrl(CkEditorUploadServlet.getUrl(storageFileFacade.getRealFileUrl(fileEntity), request, fileEntity));
      fileInfo.setName(fileEntity.getFileName());

      //FileInfo data = new FileInfo();
      //data.setUrl(("/uploads/") + next.getName());
      ArrayList<FileInfo> fileInfos = new ArrayList<>();
      FileInfoResult fileInfoResult = new FileInfoResult(fileInfos);
      ImageTools imageTools = ImageToolsFactory.getImageTools(fileWithFullPath);
      imageTools.getImageFileProperties(fileWithFullPath);
      fileInfo.setSize((int) file.getSize());
      fileInfos.add(fileInfo);
      //return fileInfoResult;

      //fileInfo.setSize( fileEntity.get);

    } catch (Exception e) {
      log.error("Failed to upload the file.", e);

      out.print(ResponseCodes.RESPONSE_FAILURE + ResponseCodes.DEIMITER + prepareFailuerResponseMessage(e));
    } finally {
      IOUtils.closeQuietly(fos);
    }
  }


  @Override
  protected FileWithPathId generateFileNameWithPath(String fileExtension) throws IOException {
    return storageFileFacade.generateFileNameWithPath(fileExtension);
  }

  protected String prepareSuccessResponseMessage(FileWithPathId fileWithPath) {
    return String.valueOf(fileWithPath.getId());
  }

  protected String prepareFailuerResponseMessage(Exception exception) {
    return "Error during file uload";
  }

}