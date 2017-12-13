package by.vbalanse.vaadin.component;

import com.vaadin.data.fieldgroup.Caption;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class VerticalLayoutCaption extends VerticalLayout {
  private Label captionLabel;

  public VerticalLayoutCaption() {
    setStyleName("vertical-layout-caption");
    captionLabel = new Label();
    captionLabel.setStyleName("vb-caption");
    captionLabel.setContentMode(ContentMode.HTML);
    //captionLabel.setClass
    addComponent(captionLabel);
  }

  @PostConstruct
  public void setCaption(String captionText) {
    if (captionText != null) {
      captionLabel.setValue(captionText);
    }

  }
}
