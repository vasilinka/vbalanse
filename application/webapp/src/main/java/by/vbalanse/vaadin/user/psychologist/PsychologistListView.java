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
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.AdminView;
import by.vbalanse.vaadin.component.AbstractEntityEditor;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
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

@org.springframework.stereotype.Component(value = "psychologistListView")
@Scope(value = "prototype")
public class PsychologistListView extends VerticalLayout implements
    ComponentContainer {

  AdminView adminView;

  private Table psychologistTable;

  private TextField searchField;

  private Button newButton;
  private Button deleteButton;
  private Button editButton;

  private JPAContainer<PsychologistEntity> psychologistContainer;

  private String textFilter;

  public PsychologistListView() {
    SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    psychologistContainer = JPAContainerFactory.make(PsychologistEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
    buildMainArea();

  }

  private void buildMainArea() {
    psychologistTable = new Table(null, psychologistContainer);
    psychologistTable.setSelectable(true);
    psychologistTable.setImmediate(true);
    psychologistTable.addItemClickListener(new ItemClickListener() {
      @Override
      public void itemClick(ItemClickEvent event) {
        setModificationsEnabled(event.getItem() != null);
        psychologistTable.select(event.getItemId());
        adminView.setEditFlow(new PsychologistEntityEditor(psychologistContainer.getItem(psychologistTable
            .getValue())));

      }

      private void setModificationsEnabled(boolean b) {
        deleteButton.setEnabled(b);
        editButton.setEnabled(b);
      }
    });

    psychologistTable.setSizeFull();
    psychologistContainer.addNestedContainerProperty("userEntity.firstName");
    psychologistContainer.addNestedContainerProperty("userEntity.lastName");

    psychologistTable.setVisibleColumns(new Object[]{"userEntity.firstName", "userEntity.lastName",
        "phone"});
    psychologistTable.setColumnHeaders(new String[]{"First Name", "Last Name", "Phone"});

    HorizontalLayout toolbar = new HorizontalLayout();
    newButton = new Button("Add");
    Button.ClickListener listener = new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        final EntityItem<PsychologistEntity> entityItem = psychologistContainer.createEntityItem(new PsychologistEntity());
        PsychologistEntityEditor psychologistEntityEditor = new PsychologistEntityEditor(entityItem);
        psychologistEntityEditor.addListener(new AbstractEntityEditor.EditorSavedListener() {
          public void editorSaved(AbstractEntityEditor.EditorSavedEvent event) {
            psychologistContainer.addEntity(entityItem.getEntity());
            //??? do we need that
          }
        });
        adminView.setEditFlow(psychologistEntityEditor);
      }
    }; newButton.addClickListener(listener);

    deleteButton = new Button("Delete");
    deleteButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        psychologistContainer.removeItem(psychologistTable.getValue());
      }
    });
    deleteButton.setEnabled(false);

    editButton = new Button("Edit");
    editButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        adminView.setEditFlow(new PsychologistEntityEditor(psychologistContainer.getItem(psychologistTable
            .getValue())));
      }
    });
    editButton.setEnabled(false);

    searchField = new TextField();
    searchField.setInputPrompt("Search by name");
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
    addComponent(psychologistTable);
    setExpandRatio(psychologistTable, 1);
    setSizeFull();

  }

  private void updateFilters() {
    psychologistContainer.setApplyFiltersImmediately(false);
    psychologistContainer.removeAllContainerFilters();
    if (textFilter != null && !textFilter.equals("")) {
      Or or = new Or(new Like("firstName", textFilter + "%", false),
          new Like("lastName", textFilter + "%", false));
      psychologistContainer.addContainerFilter(or);
    }
    psychologistContainer.applyFilters();
  }

  public void setAdminView(AdminView adminView) {
    this.adminView = adminView;
  }
}
