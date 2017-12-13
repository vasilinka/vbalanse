package by.vbalanse.vaadin.component.attachment;

import by.vbalanse.facade.storage.FileWithPathId;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class UploadComponent extends CustomComponent {

  private static final String TEMP_FILE_DIR = new File(
      System.getProperty("java.io.tmpdir")).getPath();
  private MultiFileUpload singleUpload;
  SpringContextHelper helper;
  AbstractStorageFileEntity fileEntity;
  private List<UploadFinishedListener> listeners = new ArrayList<UploadFinishedListener>();

  public UploadComponent() {
    saveBuildMainView();
  }

  private void saveBuildMainView() {
    addAttachListener(new AttachListener() {
      public void attach(AttachEvent event) {
        ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
        helper = new SpringContextHelper(servletContext);
      }
    });
    UploadFinishedHandler handler = new UploadFinishedHandler() {
      public void handleFile(InputStream inputStream, String fileName, String mimeType, long length) {
        StorageFileFacade storageFileFacade = (StorageFileFacade) helper.getBean("storageFileFacade");

        fileEntity =
            storageFileFacade.reserveStorageFileInTempFolder(fileName, 1l);
        String fileWithFullPath = getFullPath(fileEntity);
        // Open the file for writing.

        saveFile(inputStream, fileWithFullPath);
        for (UploadFinishedListener listener : listeners) {
          listener.onUpload(fileEntity);
        }

        //String fileWithFullPath = getFullPath(fileEntity);
      }

      private FileOutputStream saveFile(InputStream inputStream, String fileWithFullPath) {
        File file = new File(fileWithFullPath);

        FileOutputStream fos = null; // Stream to write to
        try {
          // Open the file for writing.
          fos = new FileOutputStream(file);
          byte data[] = new byte[1024];
          int count;
          while ((count = inputStream.read(data, 0, 1024)) != -1)
          {
            fos.write(data, 0, count);
          }
          if (inputStream != null)
            inputStream.close();
          if (fos != null)
            fos.close();
        } catch (final java.io.FileNotFoundException e) {
          new Notification("Could not open file<br/>",
              e.getMessage(),
              Notification.Type.ERROR_MESSAGE)
              .show(Page.getCurrent());
          return null;
        } catch (IOException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
        }
        return fos;
      }
    };
    UploadStateWindow window = new UploadStateWindow();
    MultiFileUpload singleUpload = new MultiFileUpload(handler, window, false);
    setCompositionRoot(singleUpload);
    //uploadField.add
  }

  public void addValueChangeHandler(UploadFinishedListener uploadChangeHandler) {
    listeners.add(uploadChangeHandler);
  }

//  public String getFileName() {
//    return (String) uploadField.getValue();
//  }

  public AbstractStorageFileEntity getStorageFileEntity() {
    return fileEntity;
  }

  public static String getFullPath(AbstractStorageFileEntity fileEntity) {
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

    void onUpload(AbstractStorageFileEntity storageFileEntity);

  }
}
