package by.vbalanse.vaadin.portal;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
//@WebServlet(value = {"/myui/*", "/VAADIN/*"},
//    asyncSupported = true)
@Theme(value = "mytheme")
public class PortalPage extends UI {
  @Override
  protected void init(VaadinRequest request) {
    addExtension(new ModularGridExtension());
    setContent(new PortalView());
    setWidth("1000px");
    setStyleName("main-main");

    //setTh("JPAContainerDemo");
  }
}
