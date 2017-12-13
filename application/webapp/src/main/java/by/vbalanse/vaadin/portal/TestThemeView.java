package by.vbalanse.vaadin.portal;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * @author mikolasusla@gmail.com
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class TestThemeView extends CustomComponent /*extends HorizontalLayout*/ {

    public TestThemeView() {
        postConstruct();
    }

    @PostConstruct
    public void postConstruct() {
//        Panel panel = new Panel();
        CssLayout cssLayout = new CssLayout();

        CustomLayout content = new CustomLayout("/testpage/testPage");
        //content.addStyleName("v-verticallayout");
//        panel.setContent(content);
        setCompositionRoot(content);
    }
}
