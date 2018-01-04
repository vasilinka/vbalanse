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

import by.vbalanse.model.training.TrainingEntity;
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

@org.springframework.stereotype.Component(value = "trainingListView")
@Scope(value = "prototype")
public class TrainingListView extends VerticalLayout implements
    ComponentContainer {

  private Tree groupTree;

  private Table trainingTable;

  private TextField searchField;

  private Button newButton;
  private Button deleteButton;
  private Button editButton;

  private JPAContainer<TrainingEntity> trainingContainer;

  private String textFilter;
  private AdminView adminView;

  public TrainingListView() {
    SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    trainingContainer = JPAContainerFactory.make(TrainingEntity.class,
            ((EntityManagerFactory) helper.getBean("entityManagerFactory")).createEntityManager());
    buildMainArea();

  }

  private void buildMainArea() {
    trainingTable = new Table(null, trainingContainer);
    trainingTable.setSelectable(true);
    trainingTable.setImmediate(true);
    trainingTable.addItemClickListener(new ItemClickListener() {
      @Override
      public void itemClick(ItemClickEvent event) {
        setModificationsEnabled(event.getItem() != null);
        trainingTable.select(event.getItemId());
        adminView.setEditFlow(new TrainingEntityEditor(trainingContainer.getItem(trainingTable
            .getValue())));

      }

      private void setModificationsEnabled(boolean b) {
        deleteButton.setEnabled(b);
        editButton.setEnabled(b);
      }
    });
    trainingTable.setSizeFull();
    trainingTable.addListener(new ItemClickListener() {
      public void itemClick(ItemClickEvent event) {
        if (event.isDoubleClick()) {
          trainingTable.select(event.getItemId());
        }
      }
    });
    //trainingContainer.addNestedContainerProperty("city.title");

    trainingTable.setVisibleColumns(new Object[]{"title",
        "city"});
    trainingTable.setColumnHeaders(new String[]{"Title", "City"});

    HorizontalLayout toolbar = new HorizontalLayout();
    newButton = new Button("Add");
    Button.ClickListener listener = new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        final EntityItem<TrainingEntity> entityItem = trainingContainer.createEntityItem(new TrainingEntity());
        TrainingEntityEditor trainingEntityEditor = new TrainingEntityEditor(entityItem);
        trainingEntityEditor.addListener(new TrainingEntityEditor.EditorSavedListener() {
          public void editorSaved(TrainingEntityEditor.EditorSavedEvent event) {
            trainingContainer.addEntity(entityItem.getEntity());
            //??? do we need that
          }
        });
        //UI.getCurrent().addWindow(trainingEntityEditor);
        adminView.setEditFlow(trainingEntityEditor);
      }
    }; newButton.addClickListener(listener);

    deleteButton = new Button("Delete");
    deleteButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        trainingContainer.removeItem(trainingTable.getValue());
      }
    });
    deleteButton.setEnabled(false);

    editButton = new Button("Edit");
    editButton.addClickListener(new Button.ClickListener() {

      public void buttonClick(ClickEvent event) {
        adminView.setEditFlow(new TrainingEntityEditor(trainingContainer.getItem(trainingTable
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
    addComponent(trainingTable);
    setExpandRatio(trainingTable, 1);
    setSizeFull();

  }

  private void updateFilters() {
    trainingContainer.setApplyFiltersImmediately(false);
    trainingContainer.removeAllContainerFilters();
    if (textFilter != null && !textFilter.equals("")) {
      Or or = new Or(new Like("title", textFilter + "%", false));
      trainingContainer.addContainerFilter(or);
    }
    trainingContainer.applyFilters();
  }

  public void setAdminView(AdminView adminView) {
    this.adminView = adminView;
  }
}
