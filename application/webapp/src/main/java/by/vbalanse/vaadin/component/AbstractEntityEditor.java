package by.vbalanse.vaadin.component;

import by.vbalanse.model.common.AbstractManagedEntity;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public abstract class AbstractEntityEditor<E extends AbstractManagedEntity> extends VerticalLayoutCaption implements
    ComponentContainer, FormFieldFactory, Button.ClickListener {

  protected Form editorForm;
  protected Button saveButton;
  protected Button cancelButton;
  protected FieldFactory fieldFactory;
  JPAContainer<E> container;
  protected EntityItem<E> entityItem;

  public AbstractEntityEditor(EntityItem<E> entityItem) {
    initAdditionalElements();
    initForm(entityItem);
  }

  protected void initAdditionalElements() {

  }


  private void initForm(EntityItem<E> entityItem) {
    fieldFactory = new FieldFactory();
    container = createEntityContainer();
    this.entityItem = entityItem;

    editorForm = new Form();
    editorForm.setFormFieldFactory(this);
    editorForm.setBuffered(true);
    editorForm.setImmediate(true);

    List<Serializable> fieldNames = getFieldNames();
    editorForm.setItemDataSource(entityItem, fieldNames);
    saveButton = new Button("Save", this);
    cancelButton = new Button("Cancel", this);

    editorForm.getFooter().addComponent(saveButton);
    editorForm.getFooter().addComponent(cancelButton);
    setSizeUndefined();
    //setContent(editorForm);
    addComponent(editorForm);
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

  public static class EditorSavedEvent<E> extends Event {

    private E savedItem;

    public EditorSavedEvent(Component source, E savedItem) {
      super(source);
      this.savedItem = savedItem;
    }

    public E getSavedItem() {
      return savedItem;
    }
  }

  public interface EditorSavedListener<E> extends Serializable {
    public void editorSaved(EditorSavedEvent<E> event);
  }

}