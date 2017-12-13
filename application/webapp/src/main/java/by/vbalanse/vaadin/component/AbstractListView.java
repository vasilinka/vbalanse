package by.vbalanse.vaadin.component;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.vaadin.AdminView;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public abstract class AbstractListView<E extends AbstractManagedEntity, Ed extends AbstractEntityEditor<E>> extends VerticalLayout implements
    ComponentContainer {

  public static final String DELETE_TITLE = "Delete";
  public static final String EDIT_TITLE = "Edit";
  protected Table itemTable;
  protected HorizontalLayout toolbar;

  private TextField searchField;

  private Button newButton;
  private Button deleteButton;
  private Button editButton;

  protected JPAContainer<E> container;
  protected AdminView adminView;

  public AbstractListView() {
    container = getContainer();
    buildMainArea();
  }

  public abstract JPAContainer<E> getContainer();

  public void addButton(Button button) {
    toolbar.addComponent(button, toolbar.getComponentIndex(searchField));
  }

  private void buildMainArea() {
    itemTable = new Table(null, container);
    itemTable.setSelectable(true);
    itemTable.setImmediate(true);
    itemTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
      @Override
      public void itemClick(ItemClickEvent event) {
        setModificationsEnabled(event.getItem() != null);
        itemTable.select(event.getItemId());
        adminView.setEditFlow(getEntityEditor(container.getItem(itemTable.getValue())));
      }

      private void setModificationsEnabled(boolean b) {
        deleteButton.setEnabled(b);
        editButton.setEnabled(b);
      }
    });

    itemTable.setSizeFull();

    itemTable.setVisibleColumns(getVisibleColumns());

    toolbar = new HorizontalLayout();
    newButton = new Button("Add");
    newButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(Button.ClickEvent event) {
        final EntityItem<E> newEntityItem = container.createEntityItem(getEntityInstance());
        showEditForm(newEntityItem);
      }
    });

    deleteButton = new Button(DELETE_TITLE);
    deleteButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(Button.ClickEvent event) {
        container.removeItem(itemTable.getValue());
      }
    });
    deleteButton.setEnabled(false);

    editButton = new Button(EDIT_TITLE);
    editButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(Button.ClickEvent event) {
        EntityItem<E> entity = container.getItem(itemTable.getValue());
        adminView.setEditFlow(getEntityEditor(entity));
      }
    });
    editButton.setEnabled(false);

    searchField = new TextField();
    searchField.setInputPrompt("Search by name");
    searchField.addTextChangeListener(new FieldEvents.TextChangeListener() {

      public void textChange(FieldEvents.TextChangeEvent event) {
        filters(event.getText());
      }
    });

    toolbar.addComponent(newButton);
    toolbar.addComponent(deleteButton);
    toolbar.addComponent(editButton);
    toolbar.addComponent(searchField);
    toolbar.setWidth("100%");
    toolbar.setExpandRatio(searchField, 1);
    toolbar.setComponentAlignment(searchField, Alignment.TOP_RIGHT);

    addComponent(toolbar);
    addComponent(itemTable);
    setExpandRatio(itemTable, 1);
    setSizeFull();

  }

  private void showEditForm(final EntityItem<E> newEntityItem) {
    Ed personEditor = getEntityEditor(newEntityItem);
    personEditor.addListener(new AbstractEntityEditor.EditorSavedListener() {
      public void editorSaved(AbstractEntityEditor.EditorSavedEvent event) {
        container.addEntity(newEntityItem.getEntity());
      }
    });
    adminView.setEditFlow(getEntityEditor(newEntityItem));
  }

  protected abstract Ed getEntityEditor(EntityItem<E> entity);

  protected abstract E getEntityInstance();

  protected abstract Object[] getVisibleColumns();

  protected abstract com.vaadin.data.Container.Filter updateFilters(String textFilter);

  private void filters(String textFilter) {
    container.setApplyFiltersImmediately(false);
    container.removeAllContainerFilters();
    container.addContainerFilter(updateFilters(textFilter));
    container.applyFilters();
  }

  public void setAdminView(AdminView adminView) {
    this.adminView = adminView;
  }
}
