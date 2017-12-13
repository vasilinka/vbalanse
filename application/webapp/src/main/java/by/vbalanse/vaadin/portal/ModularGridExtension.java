package by.vbalanse.vaadin.portal;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.server.ClientConnector;
import com.vaadin.ui.UI;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@JavaScript({"modulargrid_debug.js"/*, "analytics_connector.js"*/})
public class ModularGridExtension extends AbstractJavaScriptExtension {
  public ModularGridExtension() {
    pushCommand("_setAccount");
  }

  public void trackPageview(String name) {
    //pushCommand("_trackPageview", name);
  }

  private void pushCommand(Object... commandAndArguments) {
    // Cast to Object to use Object[] commandAndArguments as the first
    // varargs argument instead of as the full varargs argument array.
    callFunction("pushCommand", (Object) commandAndArguments);
  }

  @Override
  protected Class<? extends ClientConnector> getSupportedParentType() {
    return UI.class;
  }
}