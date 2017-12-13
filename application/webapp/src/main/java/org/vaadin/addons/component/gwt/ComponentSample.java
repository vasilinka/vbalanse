package org.vaadin.addons.component.gwt;

import by.vbalanse.vaadin.component.widgetset.client.ComponentSampleClientRpc;
import by.vbalanse.vaadin.component.widgetset.client.ComponentSampleServerRpc;
import by.vbalanse.vaadin.component.widgetset.client.ComponentSampleState;
import com.vaadin.shared.AbstractComponentState;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Notification;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class ComponentSample extends AbstractComponent {
  public ComponentSample() {
    registerRpc(new ComponentSampleServerRpc() {

      private int clickCount = 0;

      private ComponentSampleClientRpc getClientRpc() {
        return getRpcProxy(ComponentSampleClientRpc.class);
      }

      public void clicked(String buttonName) {
        Notification.show("Clicked " + buttonName);
      }
    });
    getState().text = "This is MyComponent";
  }

  @Override
  protected ComponentSampleState getState() {
    return (ComponentSampleState) super.getState();
  }
}
