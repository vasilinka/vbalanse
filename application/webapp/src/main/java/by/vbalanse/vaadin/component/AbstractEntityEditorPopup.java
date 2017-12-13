package by.vbalanse.vaadin.component;

import by.vbalanse.model.common.AbstractManagedEntity;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public abstract class AbstractEntityEditorPopup<E extends AbstractManagedEntity> extends Window implements Button.ClickListener,
    FormFieldFactory, Action.Handler {

  protected Item entityItem;
  private Form editorForm;
  private Button saveButton;
  private Button cancelButton;
  private FieldFactory fieldFactory;
  JPAContainer<E> container;
  public static final Action ACTION_ADD = new Action("Add child item");
  public static final Action ACTION_DELETE = new Action("Delete");
  private static final Action[] ACTIONS = new Action[]{ACTION_ADD,
      ACTION_DELETE};

  public AbstractEntityEditorPopup(EntityItem<E> entityItem) {

    initElements();
    editUserOld(entityItem);
    // editUserNew(entityItem);

  }

  protected abstract void initElements();


  private void editUserOld(Item entityItem) {
    fieldFactory = new FieldFactory();
    container = createEntityContainer();

    this.entityItem = entityItem;
    editorForm = new Form();
    editorForm.setFormFieldFactory(this);
    editorForm.setBuffered(true);
    editorForm.setImmediate(true);

    editorForm.setItemDataSource(entityItem, getFieldNames());
    saveButton = new Button("Save", this);
    cancelButton = new Button("Cancel", this);

    editorForm.getFooter().addComponent(saveButton);
    editorForm.getFooter().addComponent(cancelButton);
    setSizeUndefined();
    setContent(editorForm);
    setCaption(buildCaption());
  }

  public abstract List<Serializable> getFieldNames();

  protected abstract JPAContainer<E> createEntityContainer();

  /**
   * @return the caption of the editor window
   */
  abstract protected String buildCaption();

  /*
   * (non-Javadoc)
   *
   * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.
   * ClickEvent)
   */
  public void buttonClick(Button.ClickEvent event) {
    if (event.getButton() == saveButton) {
      editorForm.commit();
      fireEvent(new EditorSavedEvent(this, entityItem));
    } else if (event.getButton() == cancelButton) {
      editorForm.discard();
    }
    close();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item,
   * java.lang.Object, com.vaadin.ui.Component)
   */
  public Field createField(Item item, Object propertyId, Component uiContext) {
    Field field;
    field = fieldFactory.createField(item, propertyId,
        uiContext);
    //fixme: think how to add validator
//    field.addValidator(new BeanValidator(E.class, propertyId
//        .toString()));

    return field;

  }

  /*
   * Returns the set of available actions
   */
  public Action[] getActions(Object target, Object sender) {
    return ACTIONS;
  }

  /*
   * Handle actions
   */
  public void handleAction(Action action, Object sender, final Object target) {
  }

  public void addListener(EditorSavedListener listener) {
    try {
      Method method = EditorSavedListener.class.getDeclaredMethod(
          "editorSaved", new Class[]{EditorSavedEvent.class});
      addListener(EditorSavedEvent.class, listener, method);
    } catch (final NoSuchMethodException e) {
      // This should never happen
      throw new RuntimeException(
          "Internal error, editor saved method not found");
    }
  }

  public void removeListener(EditorSavedListener listener) {
    removeListener(EditorSavedEvent.class, listener);
  }

  public static class EditorSavedEvent extends Event {

    private Item savedItem;

    public EditorSavedEvent(Component source, Item savedItem) {
      super(source);
      this.savedItem = savedItem;
    }

    public Item getSavedItem() {
      return savedItem;
    }
  }

  public interface EditorSavedListener extends Serializable {
    public void editorSaved(EditorSavedEvent event);
  }

}