package by.vbalanse.vaadin.component;

import by.vbalanse.facade.storage.FileWithPathId;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.storage.attachment.AttachmentFacade;
import by.vbalanse.facade.storage.attachment.AttachmentParamsImpl;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class UploadOldComponent extends CustomComponent {

  public static final int THUMBNAIL_WIDTH = 200;
  SpringContextHelper helper;
  Upload upload;
  private List<UploadFinishedListener> uploadFinishedListenerList = new ArrayList<UploadFinishedListener>();

  public UploadOldComponent() {
    ImageUploader receiver = new ImageUploader();
    addAttachListener(new AttachListener() {
      public void attach(AttachEvent event) {
        ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
        helper = new SpringContextHelper(servletContext);
      }
    });


// Create the upload with a caption and set receiver later
    upload = new Upload("Upload Image Here", receiver);

    upload.setButtonCaption("Start Upload");
    upload.addSucceededListener(receiver);

    setCompositionRoot(upload);
  }

  class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
    public File file;
    AbstractStorageFileEntity fileEntity;

    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
      // Create upload stream
      StorageFileFacade storageFileFacade = (StorageFileFacade) helper.getBean("storageFileFacade");

      fileEntity =
          storageFileFacade.reserveStorageFileInTempFolder(filename, 1l);

      String fileWithFullPath = getFullPath(fileEntity);


      FileOutputStream fos = saveFile(fileWithFullPath);
      if (fos == null) return null;
      return fos; // Return the output stream to write to
    }

    private FileOutputStream saveFile(String fileWithFullPath) {
      FileOutputStream fos = null; // Stream to write to
      try {
        // Open the file for writing.
        file = new File(fileWithFullPath);

        fos = new FileOutputStream(file);
      } catch (final java.io.FileNotFoundException e) {
        new Notification("Could not open file<br/>",
            e.getMessage(),
            Notification.Type.ERROR_MESSAGE)
            .show(Page.getCurrent());
        return null;
      } catch (IOException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      return fos;
    }

    public void uploadSucceeded(Upload.SucceededEvent event) {
      AttachmentFacade attachmentFacade = (AttachmentFacade) helper.getBean("attachmentFacade");

      AbstractAttachmentEntity attachmentEntity = attachmentFacade.saveAttachment(new AttachmentParamsImpl(fileEntity.getFileName(), "", fileEntity.getId(), (short) THUMBNAIL_WIDTH), "IMG");
      for (UploadFinishedListener uploadFinishedListener : uploadFinishedListenerList) {
        uploadFinishedListener.onUpload(attachmentEntity);
      }
    }


  }

  public void addSuccessEventListener(UploadFinishedListener uploadFinishedListener) {
    uploadFinishedListenerList.add(uploadFinishedListener);

  }

  private String getFullPath(AbstractStorageFileEntity fileEntity) {
    ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
    StorageFolderEntity folder = fileEntity.getFolder();
    String realPath;
    if (folder.isFullPath()) {
      realPath = folder.getPath();
    } else {
      realPath = servletContext.getRealPath(folder.getPath());
    }

    FileWithPathId fileWithPath = new FileWithPathId(fileEntity.getId(), realPath, fileEntity.getId().toString() + "." + fileEntity.getExtension());
    return FilenameUtils.concat(fileWithPath.getFilePath(), fileWithPath.getFileName());
  }

  public interface UploadFinishedListener {

    void onUpload(AbstractAttachmentEntity attachmentEntity);

  }


}
