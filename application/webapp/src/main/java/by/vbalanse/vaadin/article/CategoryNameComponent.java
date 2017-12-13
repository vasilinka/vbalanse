package by.vbalanse.vaadin.article;


import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@SuppressWarnings("serial")
public class CategoryNameComponent extends Window {

  TextField  categoryName;
  Button add;

  public CategoryNameComponent() {
    VerticalLayout components = new VerticalLayout();
    categoryName = new TextField("Категория");
    components.addComponent(categoryName);
    add = new Button("Добавить", new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        close();
      }
    });
    components.addComponent(add);
    setSizeUndefined();
    setContent(components);
  }

  public String getValue() {
    return categoryName.getValue();
  }
}
