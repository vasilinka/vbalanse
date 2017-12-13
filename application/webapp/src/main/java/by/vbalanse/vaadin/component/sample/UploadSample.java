package by.vbalanse.vaadin.component.sample;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;
import org.vaadin.easyuploads.FileFactory;
import org.vaadin.easyuploads.UploadField;

import java.io.File;
import java.io.InputStream;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class UploadSample {

  public static final String TEMP_FILE_DIR = new File(
      System.getProperty("java.io.tmpdir")).getPath();

  private void buildMainView() {
    UploadFinishedHandler handler = new UploadFinishedHandler() {
      public void handleFile(InputStream inputStream, String fileName, String mimeType, long length) {
        Notification.show(fileName + " type " + mimeType);
      }
    };

    UploadStateWindow window = new UploadStateWindow();

    MultiFileUpload singleUpload = new MultiFileUpload(handler, window, false);
    singleUpload.setCaption("Single upload");
    singleUpload.setPanelCaption("Single upload");
    singleUpload.getSmartUpload().setCaption("Single upload");

    MultiFileUpload multiUpload = new MultiFileUpload(handler, window);
    multiUpload.setCaption("Multi upload");
    multiUpload.setPanelCaption("Multi upload");
    multiUpload.getSmartUpload().setCaption("Multi upload");

    final UploadField uploadField = new UploadField();
    uploadField.setFieldType(UploadField.FieldType.FILE);
    uploadField.setFileFactory(new FileFactory() {
      public File createFile(String fileName, String mimeType) {
        File f = new File(TEMP_FILE_DIR + fileName);
        return f;
      }
    });

    Button b = new Button("Show value");
    b.addListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        Object value = uploadField.getValue();
        //Window.showNotification("Value:" + value);
        Notification.show("Value:" + value);
      }
    });

    //formLayout.addComponent(singleUpload);
    //formLayout.addComponent(multiUpload);
    //formLayout.addComponent(uploadField);
    //formLayout.addComponent(b);

    MultiFileUpload components1 = new MultiFileUpload(new UploadFinishedHandler() {
      public void handleFile(InputStream inputStream, String s, String s2, long l) {
        Notification.show("Tesst");
        //To change body of implemented methods use File | Settings | File Templates.
      }
    }, new UploadStateWindow());
    MultiFileUpload  upload = components1;
    HorizontalLayout components = new HorizontalLayout();
    //components.add;
    //formLayout.addComponent(upload);

  }
}
