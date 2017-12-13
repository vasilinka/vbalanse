package by.vbalanse.vaadin.component;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ClickLabel extends CssLayout {

  public ClickLabel(String value) {
    setStyleName("click-label");
    Label label = new Label(value, ContentMode.HTML);
    label.setSizeUndefined();
    addComponent(label);
  }
}
