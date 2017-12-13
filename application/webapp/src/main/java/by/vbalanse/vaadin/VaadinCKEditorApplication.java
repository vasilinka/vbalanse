package by.vbalanse.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Theme(value = "ckexample")
public class VaadinCKEditorApplication extends UI {

  @Override
  public void init(VaadinRequest vaadinRequest) {

    VerticalLayout content = new VerticalLayout();
    //Window mainWindow = new Window("Vaadin CKEditor Application", content);
    //mainWindow.setSizeFull();
    setContent(content);

    content.addComponent(new Button("Hit server"));


    CKEditorConfig config = new CKEditorConfig();
    config.useCompactTags();
    config.disableElementsPath();
    config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
    config.disableSpellChecker();
    config.setToolbarCanCollapse(false);
    //config.addOpenESignFormsCustomToolbar();
    config.setWidth("100%");

    final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
    content.addComponent(ckEditorTextField);

    final String editorInitialValue =
        "<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";

    ckEditorTextField.setValue(editorInitialValue);
    //ckEditorTextField.setReadOnly(true);
    ckEditorTextField.addListener(new Label.ValueChangeListener() {

      public void valueChange(Property.ValueChangeEvent event) {
        Notification.show("CKEditor contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
      }
    });

  }
}