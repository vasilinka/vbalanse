/**
 * Copyright 2009-2013 Oy Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.vbalanse.vaadin.article;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.TargetAuditoryEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.component.AbstractEntityEditor;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Item;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.event.Action;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class ArticleEntityEditor extends AbstractEntityEditor<ArticleEntity> implements Action.Handler {

  public static final Action ACTION_ADD = new Action("Add child item");
  public static final Action ACTION_DELETE = new Action("Delete");
  private static final Action[] ACTIONS = new Action[]{ACTION_ADD,
      ACTION_DELETE};
  SpringContextHelper helper;


  Tree tree;

  public ArticleEntityEditor(EntityItem<ArticleEntity> articleItem) {
    super(articleItem);
  }

  @Override
  public List<Serializable> getFieldNames() {
    return Arrays.asList("title",
        "dateOfPublish", "author", ArticleCategoryEntity.class, "auditories", "categories");
  }

  @Override
  protected JPAContainer<ArticleEntity> createEntityContainer() {
    SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    return JPAContainerFactory.make(ArticleEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
  }

  @Override
  protected String buildCaption() {
    return String.format("%s %s", entityItem.getItemProperty("title")
        .getValue(), entityItem.getItemProperty("dateOfPublish").getValue());
  }

  @Override
  public Field createField(Item item, Object propertyId, Component uiContext) {
    Field field;
    fieldFactory = new FieldFactory();
    fieldFactory.setMultiSelectType(ArticleCategoryEntity.class,
        TwinColSelect.class);
    fieldFactory.setMultiSelectType(TargetAuditoryEntity.class,
        TwinColSelect.class);
    field = fieldFactory.createField(item, propertyId,
        uiContext);
    if ("author".equals(propertyId) || "auditories".equals(propertyId)) {
    } else if ("categories".equals(propertyId)) {
      createTree();
      field = tree;
      tree.addActionHandler(this);

    } else if (field instanceof TextField) {
      ((TextField) field).setNullRepresentation("");
    }

    field.addValidator(new BeanValidator(ArticleEntity.class, propertyId
        .toString()));

    return field;

  }

  private void createTree() {
    tree = new Tree();
    JPAContainer<ArticleCategoryEntity> categories = new HierarchicalArticleCategoryContainer(ArticleCategoryEntity.class);
    tree.setContainerDataSource(categories);
    tree.setCaption("Категория");

    tree.setMultiSelect(true);
    tree.setImmediate(true);
    tree.setItemCaptionPropertyId("title");
    tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);

    for (Object itemId : tree.rootItemIds())
      tree.expandItemsRecursively(itemId);
  }

  @Override
  public void handleAction(Action action, Object sender, final Object target) {
    if (action == ACTION_ADD) {
      final CategoryNameComponent categoryNameComponent = new CategoryNameComponent();
      categoryNameComponent.setModal(true);
      UI.getCurrent().addWindow(categoryNameComponent);
      categoryNameComponent.addCloseListener(new Window.CloseListener() {
        public void windowClose(Window.CloseEvent e) {
          ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
          articleCategoryEntity.setTitle(categoryNameComponent.getValue());
          JPAContainer<ArticleCategoryEntity> articleCategoriesContainer = JPAContainerFactory.make(ArticleCategoryEntity.class, ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
          Item item1 = tree.getItem(target);
          articleCategoryEntity.setCategory(((JPAContainerItem<ArticleCategoryEntity>) item1).getEntity());
          articleCategoriesContainer.addEntity(articleCategoryEntity);
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


  /*
   * Returns the set of available actions
   */
  public Action[] getActions(Object target, Object sender) {
    return ACTIONS;
  }

}
