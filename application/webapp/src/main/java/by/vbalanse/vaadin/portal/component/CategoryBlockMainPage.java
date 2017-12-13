package by.vbalanse.vaadin.portal.component;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.vaadin.article.category.CategoryEditorComponent;
import by.vbalanse.vaadin.component.attachment.AttachmentComponent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class CategoryBlockMainPage extends CustomComponent {

  private final ArticleCategoryEntity category;
  Panel firstBlock;

  public CategoryBlockMainPage(ArticleCategoryEntity category) {
    this.category = category;
    buildMainView();
  }

  private void buildMainView() {
    firstBlock = new Panel();
    //firstBlock.setWidth("100%");
    //firstBlock.addStyleName("pink-color");
    GridLayout firstBlockInnerTable = new GridLayout();
    firstBlockInnerTable.setWidth("250px");
    //firstBlockInnerTable.setWidth("100%");
    //firstBlockInnerTable.setHeight("100%");
    //firstBlock.setHeight("230px");

    //firstBlockInner.setId("inner-category");
    VerticalLayout отношенияМЖ = new VerticalLayout();
    Label label1 = new Label("" + category.getTitle() + "", ContentMode.HTML);
    label1.setStyleName("heading");
    label1.addStyleName("td-align");
    отношенияМЖ.addComponent(label1);
    HorizontalSplitPanel categoryItem = new HorizontalSplitPanel();
    categoryItem.setLocked(true);
    categoryItem.setSplitPosition(152, Unit.PIXELS);
    CssLayout subcategories = new CssLayout();
    Image categoryImage;
    if (category.getImage() == null) {
      categoryImage = new Image("", new ThemeResource("img/otnoshenia.jpg"));
    } else {
      categoryImage = new Image("", new ExternalResource(AttachmentComponent.getFileUrl(category.getImage().getImageThumbnailFile())));
    }
    categoryImage.setWidth(150, Unit.PIXELS);
    categoryImage.setStyleName("image-category");
    categoryItem.setFirstComponent(categoryImage);
    subcategories.addStyleName("subcategories");
    subcategories.setHeight("100%");
    //LazyJpaEntityManagerProvider entityManagerProvider = new LazyJpaEntityManagerProvider();
    for (ArticleCategoryEntity subcategory : category.getSubCateories()) {
      subcategories.addComponent(new Link(subcategory.getTitle(), new ExternalResource("/")));

    }
//    subcategories.addComponent(new Link("О мужчинах", new ExternalResource("/")));
//    subcategories.addComponent(new Link("О женщинах", new ExternalResource("/")));
    отношенияМЖ.addComponent(subcategories);
    отношенияМЖ.addComponent(categoryItem);
    categoryItem.setSecondComponent(subcategories);
    firstBlockInnerTable.addComponent(отношенияМЖ, 0, 0);
    firstBlockInnerTable.setComponentAlignment(отношенияМЖ, Alignment.MIDDLE_CENTER);
    отношенияМЖ.addStyleName("td-align");

    firstBlock.setContent(firstBlockInnerTable);

    setCompositionRoot(firstBlock);
  }
}
