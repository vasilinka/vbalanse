package by.vbalanse.vaadin.component.widgetset.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.MouseEventDetails;
import org.vaadin.addons.component.gwt.ComponentSample;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Connect(ComponentSample.class)
public class ComponentSampleConnector extends AbstractComponentConnector {
  private final ComponentSampleServerRpc serverRpc = RpcProxy.create(ComponentSampleServerRpc.class, this);

  public ComponentSampleConnector() {
    registerRpc(ComponentSampleClientRpc.class, new ComponentSampleClientRpc() {
    });
    getWidget().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        final MouseEventDetails mouseDetails =
            MouseEventDetailsBuilder
                .buildMouseEventDetails(
                    event.getNativeEvent(),
                    getWidget().getElement());
        ComponentSampleServerRpc rpc =
            getRpcProxy(ComponentSampleServerRpc.class);

        // Make the call
        rpc.clicked(mouseDetails.getButtonName());
      }
    });
  }

  @Override
  protected Widget createWidget() {
    return GWT.create(ComponentSampleWidget.class);
  }

  @Override
  public ComponentSampleWidget getWidget() {
    return (ComponentSampleWidget) super.getWidget();
  }

  @Override
  public ComponentSampleState getState() {
    return (ComponentSampleState) super.getState();
  }

  @Override
  public void onStateChanged(StateChangeEvent stateChangeEvent) {
    super.onStateChanged(stateChangeEvent);
    final String text = getState().text;
    getWidget().setText(text);
  }
}
