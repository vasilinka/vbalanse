package by.vbalanse.vaadin.portal.component;

import by.vbalanse.vaadin.component.ClickLabel;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class AlsoTabSection extends CustomComponent {

  private CssLayout container;

  public AlsoTabSection(String title, List<String> contentTitleList) {
    CssLayout container = new CssLayout();
    Label tabArticle = new Label(title);
    tabArticle.setWidth("100px");
    tabArticle.setStyleName("article-tab");
    container.addComponent(tabArticle);
    CssLayout статьиList = new CssLayout();
    статьиList.setStyleName("article-list-right");
    for (String contentTitle : contentTitleList) {
      статьиList.addComponent(new ClickLabel("<font color='blue'>" + contentTitle + "</font>"));
    }
    container.addComponent(статьиList);

    setCompositionRoot(container);
  }
}
