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
package by.vbalanse.vaadin.template;

import by.vbalanse.model.template.EmailTemplateEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.vaadin.component.VerticalLayoutCaption;
import by.vbalanse.vaadin.component.attachment.AttachmentComponent;
import by.vbalanse.vaadin.component.embed.EmbedComponent;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.vaadin.addons.maskedtextfield.MaskedTextField;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import java.io.Serializable;
import java.lang.reflect.Method;

@SuppressWarnings("serial")
public class EmailEntityEditor extends VerticalLayoutCaption implements Button.ClickListener {

  private final EntityItem<EmailTemplateEntity> emailTemplateItem;

  private Button saveButton;
  private Button cancelButton;

  private FieldGroup editorFieldGroup;
  FormLayout formLayout = new FormLayout();
  boolean showConfirm = true;

  public EmailEntityEditor(final EntityItem<EmailTemplateEntity> emailTemplateItem) {
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());

    this.emailTemplateItem = emailTemplateItem;
    editorFieldGroup = new FieldGroup();
    editorFieldGroup.setItemDataSource(emailTemplateItem);
    editorFieldGroup.setFieldFactory(new FieldGroupFieldFactory() {
      public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
        T field1 = new DefaultFieldGroupFieldFactory().createField(dataType, fieldType);
        if (field1 instanceof TextField) {
          ((TextField) field1).setNullRepresentation("");
        }
        return field1;
      }
    });
    editorFieldGroup.setBuffered(false);


    Field<?> themeEmail = editorFieldGroup.buildAndBind("Тема письма", "theme");
    formLayout.addComponent(themeEmail);

    CKEditorConfig config = new CKEditorConfig();
    config.useCompactTags();
    config.disableElementsPath();
    config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
    config.disableSpellChecker();
    config.setToolbarCanCollapse(false);
    //config.addOpenESignFormsCustomToolbar();
    config.setWidth("100%");

    final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
    editorFieldGroup.bind(ckEditorTextField, "content");
    formLayout.addComponent(ckEditorTextField);

    addComponent(formLayout);

    saveButton = new Button("Save", this);
    cancelButton = new Button("Cancel", this);

    formLayout.addComponent(saveButton);
    formLayout.addComponent(cancelButton);


    setSizeUndefined();
    setCaption(buildCaption());
    final EmailEntityEditor current = this;
    cancelButton.addClickListener(new Button.ClickListener() {
      public void buttonClick(ClickEvent e) {
        if (emailTemplateItem.getEntity().getId() == null/* && editorFieldGroup.isModified()*/) {
          //todo: when save button clicked no confirm should be shown perhaps saving logic should be here
          if (showConfirm) {
            ConfirmDialog.show(UI.getCurrent(), "Данные будет потеряны", "Вы уверены что хотите выйти?",
                "Да", "Нет", new ConfirmDialog.Listener() {

                  public void onClose(ConfirmDialog dialog) {
                    if (dialog.isConfirmed()) {
                      //close();

                      // Confirmed to continue
                      //feedback(dialog.isConfirmed());
                    } else {
                      //UI.getCurrent().addWindow(current);
                      // User did not confirm
                      //feedback(dialog.isConfirmed());
                    }
                  }
                });
          }
        }
      }
    });
  }

  /**
   * @return the caption of the editor window
   */
  private String buildCaption() {
    return String.format("%s", emailTemplateItem.getEntity().getTitle());
  }

  /*
   * (non-Javadoc)
   *
   * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.
   * ClickEvent)
   */
  public void buttonClick(ClickEvent event) {
    if (event.getButton() == saveButton) {
      //editorFieldGroup.commit();
      showConfirm = false;
      fireEvent(new EditorSavedEvent(this, emailTemplateItem));
    } else if (event.getButton() == cancelButton) {
      editorFieldGroup.discard();
    }
    //close();
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
