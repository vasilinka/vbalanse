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
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.AdminView;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.user.psychologist.PsychologistEntityEditor;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManagerFactory;

@org.springframework.stereotype.Component(value = "emailListView")
@Scope(value = "prototype")
public class EmailListView extends VerticalLayout implements
    ComponentContainer {

  AdminView adminView;

  private Table emailTemplateTable;

  private TextField searchField;

  private Button newButton;
  private Button deleteButton;
  private Button editButton;

  private JPAContainer<EmailTemplateEntity> emailTemplateContainer;

  private String textFilter;

  public EmailListView() {
    SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    emailTemplateContainer = JPAContainerFactory.make(EmailTemplateEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
    buildMainArea();

  }

  private void buildMainArea() {
    emailTemplateTable = new Table(null, emailTemplateContainer);
    emailTemplateTable.setSelectable(true);
    emailTemplateTable.setImmediate(true);
    emailTemplateTable.addItemClickListener(new ItemClickListener() {
      @Override
      public void itemClick(ItemClickEvent event) {
        setModificationsEnabled(event.getItem() != null);
        emailTemplateTable.select(event.getItemId());
        adminView.setEditFlow(new EmailEntityEditor(emailTemplateContainer.getItem(emailTemplateTable
            .getValue())));

      }

      private void setModificationsEnabled(boolean b) {
        deleteButton.setEnabled(b);
        editButton.setEnabled(b);
      }
    });
    emailTemplateTable.setSizeFull();
    emailTemplateTable.setVisibleColumns(new Object[]{"title", "theme"});
    emailTemplateTable.setColumnHeaders(new String[]{"Название шаблона", "Тема емейла"});

    HorizontalLayout toolbar = new HorizontalLayout();
    newButton = new Button("Добавить");
    Button.ClickListener listener = new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        final EntityItem<EmailTemplateEntity> entityItem = emailTemplateContainer.createEntityItem(new EmailTemplateEntity());
        EmailEntityEditor emailEntityEditor = new EmailEntityEditor(entityItem);
        emailEntityEditor.addListener(new EmailEntityEditor.EditorSavedListener() {
          public void editorSaved(EmailEntityEditor.EditorSavedEvent event) {
            emailTemplateContainer.addEntity(entityItem.getEntity());
            //??? do we need that
          }
        });
        //UI.getCurrent().addWindow(psychologistEntityEditor);
        adminView.setEditFlow(emailEntityEditor);
      }
    }; newButton.addClickListener(listener);

    deleteButton = new Button("Delete");
    deleteButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        emailTemplateContainer.removeItem(emailTemplateTable.getValue());
      }
    });
    deleteButton.setEnabled(false);

    editButton = new Button("Edit");
    editButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        adminView.setEditFlow(new EmailEntityEditor(emailTemplateContainer.getItem(emailTemplateTable
            .getValue())));
      }
    });
    editButton.setEnabled(false);

    searchField = new TextField();
    searchField.setInputPrompt("Поиск по названию");
    searchField.addTextChangeListener(new TextChangeListener() {

      public void textChange(TextChangeEvent event) {
        textFilter = event.getText();
        updateFilters();
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
    addComponent(emailTemplateTable);
    setExpandRatio(emailTemplateTable, 1);
    setSizeFull();

  }

  private void updateFilters() {
    emailTemplateContainer.setApplyFiltersImmediately(false);
    emailTemplateContainer.removeAllContainerFilters();
    if (textFilter != null && !textFilter.equals("")) {
      Or or = new Or(new Like("theme", textFilter + "%", false),
          new Like("title", textFilter + "%", false));
      emailTemplateContainer.addContainerFilter(or);
    }
    emailTemplateContainer.applyFilters();
  }

  public void setAdminView(AdminView adminView) {
    this.adminView = adminView;
  }
}
