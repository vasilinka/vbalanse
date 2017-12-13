package by.vbalanse.servlet.upload;

import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.facade.storage.FileWithPathId;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.storage.attachment.ImageTools;
import by.vbalanse.facade.storage.attachment.ImageToolsFactory;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.UnsupportedAttributeException;
import org.codehaus.jackson.map.ObjectMapper;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping(value = {"/test"})
public class UploadController {

  @Autowired
  UserDao userDao;

  @Autowired
  StorageFileFacade storageFileFacade;

  @Autowired
  StorageFileDao storageFileDao;

  @RequestMapping(value = "/crop", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  FileInfoResult handleCrop(@RequestBody FullResizeInfo fullResizeInfo)
      throws Exception {


    //fileInfo.setUrl();

    ResizeInfo resizeInfo = fullResizeInfo.getCrop();
    FileInfo fileInfoToResize = fullResizeInfo.getFile();
    String name = fileInfoToResize.getName();
    ImageTools imageTools = ImageToolsFactory.getImageTools(name);
    AbstractStorageFileEntity abstractStorageFileEntity = storageFileDao.find(fileInfoToResize.getId());
    //should mark for delete old file
    abstractStorageFileEntity.setTemp(true);
    FileWithPathId fileWithPathId = storageFileFacade.generateFileNameWithPath(name);
    AbstractStorageFileEntity abstractStorageFileEntity1 = storageFileDao.find(fileWithPathId.getId());
    imageTools.cut(storageFileFacade.getRealFilePath(abstractStorageFileEntity), FilenameUtils.concat(fileWithPathId.getFilePath(), fileWithPathId.getFileName()), resizeInfo.getX(), resizeInfo.getY(), resizeInfo.getWidth(), resizeInfo.getHeight());
    fileInfoToResize.setName(fileWithPathId.getFileName());
    //data.setId();
    fileInfoToResize.setUrl(storageFileFacade.getRealFileUrl(abstractStorageFileEntity1));
    fileInfoToResize.setId(fileWithPathId.getId());
    ArrayList<FileInfo> files = new ArrayList<>();
    files.add(fileInfoToResize);

    return new FileInfoResult(files);
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public
  @ResponseBody
  FileInfoResult handleFileUpload(HttpServletRequest request)
      throws Exception {
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (!isMultipart) {
      throw new ServletException("Servlet support only multipart data,");
    }

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    FileOutputStream fos = null;
//    try {
//      @SuppressWarnings("unchecked")
//      List<FileItem> items = upload.parseRequest(request);
//      if (items.size() != 1) {
//        throw new ServletException("Servlet can upload only single file.");
//      }

    //FileItem file = items.get(0);

    List<FileItem> items = upload.parseRequest(request);
    Iterator<FileItem> itrator = items.iterator();

    String path = request.getRealPath("/");
    ArrayList<FileInfo> fileInfos = new ArrayList<>();
    while (itrator.hasNext()) {
      FileItem next = itrator.next();
      try {

        if (next.getName() == null) {
          continue;
        }
        //making directories for our required path.
//      File directory = new File(path + "/uploads");
//      directory.mkdirs();

        //MultipartFile multiFile = request.getFile(itrator.next());
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setInput(next.getInputStream());
        String nameWithExtension = next.getName() + "." + imageInfo.getFormatName();

        FileWithPathId fileWithPath = storageFileFacade.generateFileNameWithPath(nameWithExtension);
        String fileWithFullPath = FilenameUtils.concat(fileWithPath.getFilePath(), fileWithPath.getFileName());
        ImageTools imageTools = ImageToolsFactory.getImageTools(nameWithExtension);

        fos = new FileOutputStream(fileWithFullPath);
        IOUtils.copy(next.getInputStream(), fos);
        fos.flush();
        fos.close();
        // just to show that we have actually received the file
//      System.out.println("File Length:" + next.getBytes().length);
//      System.out.println("File Type:" + multiFile.getContentType());
//      String fileName=multiFile.getOriginalFilename();
        //System.out.println("File Name:" +fileName);
//      String path=request.getRealPath("/");
//
//      //making directories for our required path.
//      byte[] bytes = multiFile.getBytes();
//      File directory=    new File(path+ "/uploads");
//      directory.mkdirs();
        // saving the file
//      BufferedOutputStream stream = new BufferedOutputStream(
//          new FileOutputStream(file));
        //stream.write(bytes);
        //stream.close();
        FileInfo data = new FileInfo();
        data.setName(nameWithExtension);
        //data.setId();
        Long storageFileId = fileWithPath.getId();
        AbstractStorageFileEntity storageFileEntity = storageFileDao.find(storageFileId);
        data.setUrl(storageFileFacade.getRealFileUrl(storageFileEntity));
        data.setId(storageFileId);

        FileInfoResult fileInfoResult = new FileInfoResult(fileInfos);
        imageTools.getImageFileProperties(fileWithFullPath);
        data.setSize((int) next.getSize());
        fileInfos.add(data);
        return fileInfoResult;
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        throw new Exception("Error while loading the file");
      }
    }
    throw new UnsupportedAttributeException("no file sent to upload controller", "file");
  }

  @RequestMapping(value = "/upload2", method = RequestMethod.GET)
  @ResponseBody
  public String handleFileUpload2(HttpServletRequest request) {
    return "Hello";
  }

  public String toJson(Object data) {
    ObjectMapper mapper = new ObjectMapper();
    StringBuilder builder = new StringBuilder();
    try {
      builder.append(mapper.writeValueAsString(data));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return builder.toString();
  }
}