package by.vbalanse.vaadin.portal;

import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.portal.component.LoginPageView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * @author mikolasusla@gmail.com
 */
public class LoginUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
        LoginPageView loginPageView = (LoginPageView) springContextHelper.getBean("loginPageView");
        setContent(loginPageView);
    }
}
