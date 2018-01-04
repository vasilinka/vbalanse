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
package by.vbalanse.vaadin.user;

import by.vbalanse.facade.utils.StringUtils;
import by.vbalanse.model.user.RoleEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.component.AbstractEntityEditor;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class UserEntityEditor extends AbstractEntityEditor<UserEntity> {

  private static final String PASSWORD_MD5_HASH = "passwordMD5hash";

  public UserEntityEditor(EntityItem<UserEntity> entityItem) {
    super(entityItem);
  }

  @Override
  public List<Serializable> getFieldNames() {
    Serializable[] strings = {"firstName",
        "lastName", PASSWORD_MD5_HASH, "role"};
    return Arrays.asList(strings);
  }

  @Override
  protected JPAContainer<UserEntity> createEntityContainer() {
    SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    return JPAContainerFactory.make(UserEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
  }

  /**
   * @return the caption of the editor window
   */
  protected String buildCaption() {
    return String.format("%s %s", entityItem.getItemProperty("firstName")
        .getValue(), entityItem.getItemProperty("lastName").getValue());
  }

  /*
   * (non-Javadoc)
   *
   * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.
   * ClickEvent)
   */
  public void buttonClick(ClickEvent event) {
    if (event.getButton() == saveButton) {
      Property property = editorForm.getItemProperty(PASSWORD_MD5_HASH);
      if (property.getValue() == null || !property.getValue().toString().equals(editorForm.getField(PASSWORD_MD5_HASH).getValue().toString())) {
        String value = editorForm.getField(PASSWORD_MD5_HASH).getValue().toString();
        editorForm.getField(PASSWORD_MD5_HASH).setValue(StringUtils.md5(value));
      }
      editorForm.commit();
      fireEvent(new AbstractEntityEditor.EditorSavedEvent<>(this, entityItem));
    } else if (event.getButton() == cancelButton) {
      editorForm.discard();
    }
    //close();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item,
   * java.lang.Object, com.vaadin.ui.Component)
   */
  public Field createField(Item item, Object propertyId, Component uiContext) {
    Field field = DefaultFieldFactory.get().createField(item, propertyId,
        uiContext);
    if ("role".equals(propertyId)) {
      field = new RoleSelector();
    } else if (PASSWORD_MD5_HASH.equals(propertyId)) {
      field = new PasswordField("Password");
    } else if (field instanceof TextField) {
      ((TextField) field).setNullRepresentation("");
    }

    field.addValidator(new BeanValidator(UserEntity.class, propertyId
        .toString()));

    return field;
  }

}
