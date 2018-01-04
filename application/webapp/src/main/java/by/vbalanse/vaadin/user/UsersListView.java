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

import by.vbalanse.dao.user.psychologist.PsychologistDao;
import by.vbalanse.facade.utils.StringUtils;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.user.RoleTypeEnum;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.component.AbstractEntityEditor;
import by.vbalanse.vaadin.component.AbstractListView;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.user.psychologist.PsychologistEntityEditor;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManagerFactory;

@org.springframework.stereotype.Component(value = "usersListView")
@Scope("prototype")
public class UsersListView extends AbstractListView<UserEntity, UserEntityEditor> {

  private Button editPsychologyButton;
  SpringContextHelper helper;
  private JPAContainer<PsychologistEntity> psychologistContainer;

  public UsersListView() {
    super();
    psychologistContainer = JPAContainerFactory.make(PsychologistEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
    addAttachListener(new AttachListener() {
      public void attach(AttachEvent event) {
        helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
      }
    });
    initPsychologyButton();
    //addButton(editPsychologyButton);

  }

  @Override
  public JPAContainer<UserEntity> getContainer() {
    helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    return JPAContainerFactory.make(UserEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
  }

  private void initPsychologyButton() {
    itemTable.addListener(new Property.ValueChangeListener() {
      public void valueChange(ValueChangeEvent event) {
        editPsychologyEnabled(event.getProperty().getValue() != null
            && container.getItem(event.getProperty().getValue()).getEntity().getRole().getRoleType().equals(RoleTypeEnum.ROLE_PSYCHOLOGIST));
      }

      private void editPsychologyEnabled(boolean b) {
        editPsychologyButton.setEnabled(b);
      }

    });

    editPsychologyButton = new Button("Редактировать психолога");

    editPsychologyButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        PsychologistDao psychologistDao = (PsychologistDao) helper.getBean("psychologistDao");
        PsychologistEntity psychologist = psychologistDao.findPsychologist((Long) itemTable.getValue());
        final EntityItem<PsychologistEntity> item;
        //todo: this logic should be in user creation logic
        if (psychologist == null) {
          PsychologistEntity entity = new PsychologistEntity();
          entity.setUserEntity(container.getItem(itemTable.getValue()).getEntity());
          item = psychologistContainer.createEntityItem(entity);
        } else {
          item = psychologistContainer.getItem(psychologist.getId());
        }
        PsychologistEntityEditor psychologistEntityEditor = new PsychologistEntityEditor(item);
        adminView.setEditFlow(psychologistEntityEditor);
        /*psychologistEntityEditor.addListener(new AbstractEntityEditor.EditorSavedListener() {
          public void editorSaved(AbstractEntityEditor.EditorSavedEvent event) {
            psychologistContainer.addEntity(item.getEntity());
            //??? do we need that
          }
        });*/
      }
    });
    editPsychologyButton.setEnabled(false);
    addButton(editPsychologyButton);
  }

  @Override
  protected UserEntityEditor getEntityEditor(EntityItem<UserEntity> entity) {
    UserEntityEditor personEditor = new UserEntityEditor(entity);
    personEditor.addListener(new AbstractEntityEditor.EditorSavedListener<JPAContainerItem<UserEntity>>() {
      public void editorSaved(AbstractEntityEditor.EditorSavedEvent<JPAContainerItem<UserEntity>> event) {
        UserEntity bean = event.getSavedItem().getEntity();
        String password = bean.getPasswordMD5hash();
        bean.setPasswordMD5hash(StringUtils.md5(password));
        container.addEntity(bean);
      }
    });
    return personEditor;
  }

  @Override
  protected UserEntity getEntityInstance() {
    return new UserEntity();
  }

  @Override
  protected Object[] getVisibleColumns() {
    return new Object[]{"firstName", "lastName",
        "role"};
  }

  protected Container.Filter updateFilters(String textFilter) {
    if (textFilter != null && !textFilter.equals("")) {
      Or or = new Or(new Like("firstName", textFilter + "%", false),
          new Like("lastName", textFilter + "%", false));
      return or;
    }
    return null;
  }

}
