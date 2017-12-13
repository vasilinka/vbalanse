package by.vbalanse.servlet.upload;

import by.vbalanse.facade.storage.FileWithPathId;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Vasilina on 02.02.2015.
 */
public class CkEditorUploadServlet extends LMSSingleFileUploadServlet {

  private static final Logger log = Logger.getLogger(CkEditorUploadServlet.class);
  private static final String FUNC_NO = "CKEditorFuncNum";
  private String[] allowFileTypeArr;

  @Autowired
  private StorageFileFacade storageFileFacade;


  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //response.setContentType("text/plain;charset=UTF-8");
    String relUrl = null;


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

      FileWithPathId fileWithPath = generateFileNameWithPath(file.getName());
      String fileWithFullPath = FilenameUtils.concat(fileWithPath.getFilePath(), fileWithPath.getFileName());
      fos = new FileOutputStream(fileWithFullPath);
      IOUtils.copy(file.getInputStream(), fos);



      //relUrl = response.
//      if(isAllowFileType(FilenameUtils.getName(uplFile.getName()))) {
//        relUrl = fileSaveManager.saveFile(uplFile, imageBaseDir, imageDomain);
//
//      }else {
//        errorMessage = "Restricted Image Format";
//      }


      StringBuffer sb = new StringBuffer();
      sb.append("<script type=\"text/javascript\">\n");
      relUrl = getUrl(storageFileFacade.getRealFileUrl(fileEntity), request, fileEntity);
      // Compressed version of the document.domain automatic fix script.
      // The original script can be found at [fckeditor_dir]/_dev/domain_fix_template.js
      // sb.append("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
      sb.append("window.parent.CKEDITOR.tools.callFunction(").append(request.getParameter(FUNC_NO)).append(", '");
      sb.append(relUrl);
//      if(errorMessage != null) {
//        sb.append("', '").append(errorMessage);
//      }
      sb.append("');\n </script>");

      response.setContentType("text/html");
      response.setHeader("Cache-Control", "no-cache");

      out.print(sb.toString());
      out.flush();
      out.close();



      //out.print(ResponseCodes.RESPONSE_OK + ResponseCodes.DEIMITER + prepareSuccessResponseMessage(fileWithPath));
    } catch (Exception e) {
      log.error("Failed to upload the file.", e);

      out.print(ResponseCodes.RESPONSE_FAILURE + ResponseCodes.DEIMITER + prepareFailuerResponseMessage(e));
    } finally {
      IOUtils.closeQuietly(fos);
    }
  }

  public static String getUrl(String folderFilePath, HttpServletRequest request, AbstractStorageFileEntity fileEntity) {
    return request.getScheme() + "://" +   // "http" + "://
        request.getServerName() +       // "myhost"
        ":" +                           // ":"
        request.getServerPort() +       // "8080"
        request.getContextPath() +"/" + fileEntity.getFolder().getPath() + "/"+fileEntity.getId() + "." + fileEntity.getExtension();

  }

  protected boolean isAllowFileType(String fileName) {
    boolean isAllow = false;
    if(allowFileTypeArr != null && allowFileTypeArr.length > 0) {
      for(String allowFileType : allowFileTypeArr) {
        if(StringUtils.equalsIgnoreCase(allowFileType, StringUtils.substringAfterLast(fileName, "."))) {
          isAllow = true;
          break;
        }
      }
    }else {
      isAllow = true;
    }

    return isAllow;
  }


}
