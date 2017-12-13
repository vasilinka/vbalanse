package by.vbalanse.vaadin.article.category;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.vaadin.article.CategoryNameComponent;
import by.vbalanse.vaadin.article.HierarchicalArticleCategoryContainer;
import by.vbalanse.vaadin.component.UploadOldComponent;
import by.vbalanse.vaadin.component.attachment.AttachmentComponent;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.hibernate.utils.not.used.HibernateUtil;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@org.springframework.stereotype.Component(value = "categoryEditorComponent2")
@Scope("prototype")
@Deprecated
public class CategoryEditorHbnComponent extends CustomComponent implements Action.Handler {

  Tree tree;
  VerticalLayout formContainer;
  HorizontalLayout categoryNameBar;
  private TextField categoryNameField;
  HbnContainer<ArticleCategoryEntity> articleCategoryEntityHbnContainer;
  private Button change;
  HorizontalLayout panel;
  SpringContextHelper helper;
  JPAContainer<ArticleCategoryEntity> categories;
  Embedded image;
  private UploadOldComponent upload;

  private static final Action ACTION_ADD = new Action("Add child item");
  private static final Action ACTION_DELETE = new Action("Delete");
  private static final Action[] ACTIONS = new Action[]{ACTION_ADD,
      ACTION_DELETE};

  public CategoryEditorHbnComponent() {
  }

  private void initEditor() {
    formContainer = new VerticalLayout();
    categoryNameBar = new HorizontalLayout();
    formContainer.addComponent(categoryNameBar);
    //categoryNameBar.setMargin(false, false, false, true);
    categoryNameBar.setEnabled(false);
    panel.addComponent(formContainer);
    // textfield
    categoryNameField = new TextField("Item name");
    categoryNameField.setImmediate(true);
    categoryNameBar.addComponent(categoryNameField);
    upload = new UploadOldComponent();
    image = new Embedded("Uploaded Image");
    image.setVisible(false);
    upload.addSuccessEventListener(new UploadOldComponent.UploadFinishedListener() {
      public void onUpload(final AbstractAttachmentEntity attachmentEntity) {
        //EntityItem<ArticleCategoryEntity> item = categories.getItem(tree.getValue());
        HbnContainer<ArticleCategoryEntity>.EntityItem<ArticleCategoryEntity> item = articleCategoryEntityHbnContainer.getItem(tree.getValue());
        ArticleCategoryEntity pojo = item.getPojo();
        pojo.setImage((AttachmentImageEntity) attachmentEntity);
        articleCategoryEntityHbnContainer.updateEntity(pojo);
//        ArticleCategoryEntity entity = item.getEntity();
//        entity.setImage((AttachmentImageEntity) attachmentEntity);
        //articleCategoryEntityHbnContainer.();
        //articleCategoryDao.save(entity);
        image.setVisible(true);
        image.setSource(new ExternalResource(AttachmentComponent.getFileUrl(((AttachmentImageEntity) attachmentEntity).getImageFile())));
      }
    });


    formContainer.addComponent(upload);
    formContainer.addComponent(image);

    // apply-button
    change = new Button("Apply", new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        if (!categoryNameField.getValue().equals("")) {
          Item item = tree.getItem(tree.getValue());
          Property name = item.getItemProperty("title");
          name.setValue(categoryNameField.getValue());
        }
      }
    });
    categoryNameBar.addComponent(change);
    categoryNameBar.setComponentAlignment(change, Alignment.BOTTOM_LEFT);

  }

  @PostConstruct
  public void buildMainView() {
    /*addAttachListener(new AttachListener() {
      public void attach(AttachEvent event) {
        ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
        helper = new SpringContextHelper(servletContext);
      }
    });*/

    panel = new HorizontalLayout();
    categories = new HierarchicalArticleCategoryContainer(ArticleCategoryEntity.class);
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    SessionFactory sessionFactory = (SessionFactory) springContextHelper.getBean("sessionFactory");
    articleCategoryEntityHbnContainer = new HbnContainer<>(ArticleCategoryEntity.class, sessionFactory);

    tree = new Tree("Categories", /*categories*/articleCategoryEntityHbnContainer);
    tree.setMultiSelect(false);
    tree.setImmediate(true);
    tree.setItemCaptionPropertyId("title");
    tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
    for (Object itemId : tree.rootItemIds())
      tree.expandItemsRecursively(itemId);
    tree.addActionHandler(this);
    //tree.addActionHandler(new );
    tree.addListener(new ItemClickEvent.ItemClickListener() {
      public void itemClick(ItemClickEvent event) {
        if (event.getItemId() != null) {
          // If something is selected from the tree, get its 'name' and
          // insert it into the textfield
          Item item = tree.getItem(event.getItemId());
          ArticleCategoryEntity pojo = articleCategoryEntityHbnContainer.getItem(event.getItemId()).getPojo();
          categoryNameField.setValue((String) item
              .getItemProperty("title").getValue());
          if (pojo.getCategory() != null) {
            //tree.requestRepaintAll();
          }

          categoryNameField.requestRepaint();
          Session session = HibernateUtil.getSession();
          //session.refresh();
          categoryNameBar.setEnabled(true);
          upload.setEnabled(true);
          //AttachmentImageEntity image1 = categories.getItem(event.getItemId()).getEntity().getImage();
          AttachmentImageEntity image1 = articleCategoryEntityHbnContainer.getItem(event.getItemId()).getPojo().getImage();
          if (image1 != null) {
            image.setSource(new ExternalResource(AttachmentComponent.getFileUrl(articleCategoryEntityHbnContainer.getItem(event.getItemId()).getPojo().getImage().getImageFile())));
            image.setVisible(true);
          } else {
            image.setVisible(false);
          }
        } else {
          categoryNameField.setValue("");
          categoryNameBar.setEnabled(false);
          upload.setEnabled(false);

        }
        //event.
      }
    });
    panel.addComponent(tree);
    setCompositionRoot(panel);


    initEditor();
  }


  public Action[] getActions(Object target, Object sender) {
    return ACTIONS;
  }

  public void handleAction(Action action, Object sender, final Object target) {
    if (action == ACTION_ADD) {
      final CategoryNameComponent categoryNameComponent = new CategoryNameComponent();
      categoryNameComponent.setModal(true);
      UI.getCurrent().addWindow(categoryNameComponent);
      categoryNameComponent.addCloseListener(new Window.CloseListener() {
        public void windowClose(Window.CloseEvent e) {
          ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
          articleCategoryEntity.setTitle(categoryNameComponent.getValue());
          //JPAContainer<ArticleCategoryEntity> articleCategoriesContainer = JPAContainerFactory.make(ArticleCategoryEntity.class, AdminUI.PERSISTENCE_UNIT);tree.expandItemsRecursively(itemId);
          Item item1 = tree.getItem(target);
          articleCategoryEntity.setCategory((ArticleCategoryEntity) ((HbnContainer.EntityItem) item1).getPojo());
          articleCategoryEntityHbnContainer.saveEntity(articleCategoryEntity);
          for (Object itemId : tree.rootItemIds())
            tree.expandItemsRecursively(itemId);
          tree.requestRepaintAll();
        }
      });
    } else if (action == ACTION_DELETE) {
      Object parent = tree.getParent(target);
      tree.removeItem(target);
      // If the deleted object's parent has no more children, set its
      // childrenallowed property to false
      if (parent != null && tree.getChildren(parent).size() == 0) {
        //tree.setChildrenAllowed(parent, false);
      }
    }
  }

}
