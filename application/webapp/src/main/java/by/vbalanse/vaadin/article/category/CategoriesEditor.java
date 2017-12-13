package by.vbalanse.vaadin.article.category;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Component(value = "categoriesEditor")
@Scope(value = "prototype")
public class CategoriesEditor extends VerticalLayout implements ComponentContainer {

  @Autowired
  private CategoryEditorComponent categoryEditorComponent;

  public CategoriesEditor() {
    super();
  }

  @PostConstruct
  private void buildMainArea() {
    addComponent(categoryEditorComponent);
  }
}
