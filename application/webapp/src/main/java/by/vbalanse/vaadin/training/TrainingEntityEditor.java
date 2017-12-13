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
package by.vbalanse.vaadin.training;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.training.TrainingEntity;
import by.vbalanse.model.training.TrainingTimeOrganizationTypeEnum;
import by.vbalanse.model.training.TrainingTypeEnum;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.component.VerticalLayoutCaption;
import by.vbalanse.vaadin.component.attachment.AttachmentComponent;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.portal.PortalPage;
import by.vbalanse.vaadin.portal.PortalView;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("serial")
public class TrainingEntityEditor extends VerticalLayoutCaption implements
    ComponentContainer, Button.ClickListener {

  private final EntityItem<TrainingEntity> trainingEntityEntityItem;

  private Button saveButton;
  private Button cancelButton;

  private FieldGroup editorFieldGroup;
  JPAContainer<CityEntity> cityContainer;
  JPAContainer<PsychologistEntity> psychologistContainer;
  FormLayout formLayout = new FormLayout();

  public TrainingEntityEditor(EntityItem<TrainingEntity> trainingItem) {
    cityContainer = JPAContainerFactory.make(CityEntity.class, AdminUI.PERSISTENCE_UNIT);
    psychologistContainer = JPAContainerFactory.make(PsychologistEntity.class, AdminUI.PERSISTENCE_UNIT);

    this.trainingEntityEntityItem = trainingItem;
    editorFieldGroup = new FieldGroup();
    editorFieldGroup.setItemDataSource(trainingItem);
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

    formLayout.setImmediate(true);

    AttachmentComponent imageComponent = new AttachmentComponent(trainingItem.getEntity().getImage());
    editorFieldGroup.bind(imageComponent, "image");
    formLayout.addComponent(imageComponent);

    Field<?> titleField = editorFieldGroup.buildAndBind("Название", "title");
    titleField.addValidator(new BeanValidator(TrainingEntity.class, "title"));
    formLayout.addComponent(titleField);

    Field<?> descriptionField = editorFieldGroup.buildAndBind("Описание", "description");
    formLayout.addComponent(descriptionField);

    FieldFactory fieldFactory = new FieldFactory();
    fieldFactory.setSingleSelectType(CityEntity.class,
        ComboBox.class);
    Field city = fieldFactory.createField(trainingItem, "city", this);
    editorFieldGroup.bind(city, "city");
    city.addValidator(new BeanValidator(TrainingEntity.class, "city"));
    formLayout.addComponent(city);



    Form customerForm = new Form();
    customerForm
        .setVisibleItemProperties(new Object[]{"authors"});
    customerForm.setCaption("Edit article categories");
    FieldFactory jpaContainerFieldFactory = new FieldFactory();
    jpaContainerFieldFactory.setVisibleProperties(TrainingEntity.class, "authors");

    jpaContainerFieldFactory.setMultiSelectType(PsychologistEntity.class,
        TwinColSelect.class);

    customerForm.setFormFieldFactory(jpaContainerFieldFactory);
    customerForm.setItemDataSource(trainingItem, Arrays.asList("authors"));
    Field field = customerForm.getField("authors");
    customerForm.setItemDataSource(trainingItem,
        Arrays.asList("categories", "title"));
    formLayout.addComponent(field);


    TrainingTimeOrganizationTypeEnumSelector trainingTimeOrganizationType = new TrainingTimeOrganizationTypeEnumSelector();
    trainingTimeOrganizationType.setImmediate(true);
    trainingTimeOrganizationType.setCaption("Способ запуска мероприятия");
    editorFieldGroup.bind(trainingTimeOrganizationType, "timeOrganizationType");
    trainingTimeOrganizationType.addValidator(new BeanValidator(TrainingEntity.class, "timeOrganizationType"));
    formLayout.addComponent(trainingTimeOrganizationType);

    BeanItemContainer<TrainingTypeEnum> newDataSource = new BeanItemContainer<TrainingTypeEnum>(TrainingTypeEnum.class);
    newDataSource.addAll(Arrays.asList(TrainingTypeEnum.values()));
    ListSelect trainingType = new ListSelect("Тип мероприятия", newDataSource);
    trainingType.setImmediate(true);
    VaadinSession.getCurrent().setConverterFactory(new MyConverterFactory());
    trainingType.setItemCaptionPropertyId("title");
    trainingType.addValidator(new BeanValidator(TrainingEntity.class, "trainingType"));
    editorFieldGroup.bind(trainingType, "trainingType");
    formLayout.addComponent(trainingType);

    Field<?> contactsField = editorFieldGroup.buildAndBind("Контакты", "contacts");
    contactsField.addValidator(new BeanValidator(TrainingEntity.class, "contacts"));
    formLayout.addComponent(contactsField);

    Field<?> priceField = editorFieldGroup.buildAndBind("Цена за встречу", "price");
    formLayout.addComponent(priceField);

    Field<?> hoursOfTraining = editorFieldGroup.buildAndBind("Часов за занятие", "hoursOfTraining");
    formLayout.addComponent(hoursOfTraining);

    Field<?> periodOfMeet = editorFieldGroup.buildAndBind("Периодичность встреч", "periodOfMeet");
    formLayout.addComponent(periodOfMeet);

    Field<?> minCountOfMembers = editorFieldGroup.buildAndBind("Минимальное количество участников", "minCountOfMembers");
    formLayout.addComponent(minCountOfMembers);

    Field<?> dateOfStart = editorFieldGroup.buildAndBind("Дата начала", "dateOfStart");
    formLayout.addComponent(dateOfStart);


    addComponent(formLayout);

    saveButton = new Button("Save", this);
    cancelButton = new Button("Cancel", this);

    formLayout.addComponent(saveButton);
    formLayout.addComponent(cancelButton);
    setSizeUndefined();
    setCaption(buildCaption());
  }

  /**
   * @return the caption of the editor window
   */
  private String buildCaption() {
    if (trainingEntityEntityItem.getEntity().getId() == null) {
      return "Новый тренинг/группа";
    }
    return String.format("%s", trainingEntityEntityItem.getItemProperty("title")
        .getValue());
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
      fireEvent(new EditorSavedEvent(this, trainingEntityEntityItem));
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
