package by.vbalanse.vaadin.component.widgetset.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class ComponentSampleWidget extends Label {
  public static final String CLASSNAME = "mywidget";

  public ComponentSampleWidget() {
    setStyleName(CLASSNAME);
    setText("This is MyWidget");
  }
}
