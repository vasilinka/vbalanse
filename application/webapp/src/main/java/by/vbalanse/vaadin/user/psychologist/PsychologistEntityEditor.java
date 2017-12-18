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
package by.vbalanse.vaadin.user.psychologist;

import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.vaadin.component.AbstractEntityEditor;
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
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.hibernate.SessionFactory;
import org.vaadin.addons.maskedtextfield.MaskedTextField;
import org.vaadin.dialogs.ConfirmDialog;

import java.io.Serializable;
import java.lang.reflect.Method;

@SuppressWarnings("serial")
public class PsychologistEntityEditor extends VerticalLayoutCaption implements Button.ClickListener {

  private final EntityItem<PsychologistEntity> psychologistEntityEntityItem;

  private Button saveButton;
  private Button cancelButton;

  private FieldGroup editorFieldGroup;
  FormLayout formLayout = new FormLayout();
  boolean showConfirm = true;

  public PsychologistEntityEditor(final EntityItem<PsychologistEntity> psychologistItem) {
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());

    this.psychologistEntityEntityItem = psychologistItem;
    editorFieldGroup = new FieldGroup();
    editorFieldGroup.setItemDataSource(psychologistItem);
    editorFieldGroup.setFieldFactory(new FieldGroupFieldFactory() {
      public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
        T field1 = DefaultFieldGroupFieldFactory.get().createField(dataType, fieldType);
        if (field1 instanceof TextField) {
          ((TextField) field1).setNullRepresentation("");
        }
        return field1;
      }
    });
    editorFieldGroup.setBuffered(false);

    final MaskedTextField phoneField = new MaskedTextField("Телефон +375", "(##) ##-##-###");
    Button testValue = new Button("Test", new Button.ClickListener() {
      public void buttonClick(ClickEvent event) {
        phoneField.setValue("375 (29) 55-11-321");
      }
    });
    Button getValue = new Button("get value", new Button.ClickListener() {
      public void buttonClick(ClickEvent event) {
        Notification.show("value " + phoneField.getValue());
      }
    });
    formLayout.addComponent(testValue);
    formLayout.addComponent(getValue);
    formLayout.setImmediate(true);
    editorFieldGroup.bind(phoneField, "phone");
    formLayout.addComponent(phoneField);

    AttachmentComponent photoComponent = new AttachmentComponent(psychologistItem.getEntity().getPhoto());
    editorFieldGroup.bind(photoComponent, "photo");
    formLayout.addComponent(photoComponent);

    EmbedComponent videoComponent = new EmbedComponent();
    editorFieldGroup.bind(videoComponent, "embedVideo");
    formLayout.addComponent(videoComponent);

    Field<?> skypeField = editorFieldGroup.buildAndBind("Скайп", "skype");
    formLayout.addComponent(skypeField);

    Field<?> siteField = editorFieldGroup.buildAndBind("Сайт", "siteUrl");
    formLayout.addComponent(siteField);

    Field<?> hoursPersonalTherapyField = editorFieldGroup.buildAndBind("Количество часов личной терапии", "hoursPersonalTherapy");
    hoursPersonalTherapyField.addValueChangeListener(new Property.ValueChangeListener() {
      public void valueChange(Property.ValueChangeEvent event) {
        Notification.show("Часы личной терапии изменены");
      }
    });
    formLayout.addComponent(hoursPersonalTherapyField);

    //todo: think of diapason values like до 100 больше 1000
    Field<?> hoursOfPracticeField = editorFieldGroup.buildAndBind("Количество часов практики", "hoursOfPractice");
    formLayout.addComponent(hoursOfPracticeField);

    Field<?> hoursOfGroupTherapyField = editorFieldGroup.buildAndBind("Количество часов групповой терапии", "hoursOfGroupTherapy");
    formLayout.addComponent(hoursOfGroupTherapyField);

    TextArea universityField = new TextArea("Университет");
    editorFieldGroup.bind(universityField, "university");
    universityField.setNullRepresentation("");
    formLayout.addComponent(universityField);

    TextArea educationField = new TextArea("Образование");
    editorFieldGroup.bind(educationField, "education");
    educationField.setNullRepresentation("");
    formLayout.addComponent(educationField);

    Field<?> dateOfBirthField = editorFieldGroup.buildAndBind("День рождения", "dateOfBirth");
    formLayout.addComponent(dateOfBirthField);


    addComponent(formLayout);

    saveButton = new Button("Save", this);
    cancelButton = new Button("Cancel", this);

    formLayout.addComponent(saveButton);
    formLayout.addComponent(cancelButton);


    setSizeUndefined();
    setCaption(buildCaption());
    final PsychologistEntityEditor current = this;
    cancelButton.addClickListener(new Button.ClickListener() {
      public void buttonClick(ClickEvent e) {
        if (psychologistItem.getEntity().getId() == null/* && editorFieldGroup.isModified()*/) {
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
    UserEntity userEntity = psychologistEntityEntityItem.getEntity().getUserEntity();
    return String.format("%s", userEntity.getFirstLastName());
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
      fireEvent(new AbstractEntityEditor.EditorSavedEvent<>(this, psychologistEntityEntityItem));
    } else if (event.getButton() == cancelButton) {
      editorFieldGroup.discard();
    }
    //close();
  }

  public void addListener(AbstractEntityEditor.EditorSavedListener listener) {
    try {
      Method method = AbstractEntityEditor.EditorSavedListener.class.getDeclaredMethod(
          "editorSaved", new Class[]{AbstractEntityEditor.EditorSavedEvent.class});
      addListener(AbstractEntityEditor.EditorSavedEvent.class, listener, method);
    } catch (final NoSuchMethodException e) {
      // This should never happen
      throw new RuntimeException(
          "Internal error, editor saved method not found");
    }
  }


}
