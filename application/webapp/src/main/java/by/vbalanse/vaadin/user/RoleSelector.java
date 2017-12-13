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

import by.vbalanse.model.user.RoleEntity;
import by.vbalanse.vaadin.AdminUI;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

/**
 * A custom field that allows selection of a role.
 */
public class RoleSelector extends CustomField<RoleEntity> {
  private ComboBox geographicalRole = new ComboBox();
  //private ComboBox role = new ComboBox();

  private JPAContainer<RoleEntity> container;

  public RoleSelector() {
    container = JPAContainerFactory.make(RoleEntity.class,
        AdminUI.PERSISTENCE_UNIT);
    setCaption("Role");
    // Only list "roots" which are in our example geographical super
    // roles
//        geoContainer.addContainerFilter(new IsNull("parent"));
//      LazyJpaEntityManagerProvider entityManagerProvider = new LazyJpaEntityManagerProvider();
////      container = JPAContainerFactory.make(UserEntity.class,
////          "rantzPU");
//      container.getEntityProvider().setEntityManager(null);
//      container.getEntityProvider().setEntityManagerProvider(
//          entityManagerProvider);
//      container.getEntityProvider().setLazyLoadingDelegate(
//          new HibernateLazyLoadingDelegate());
    geographicalRole.setContainerDataSource(container);

    geographicalRole.setItemCaptionPropertyId("description");
    geographicalRole.setImmediate(true);

    //container.setApplyFiltersImmediately(false);
    //filterRoles(null);
    //geographicalRole.setContainerDataSource(container);
//        role.setItemCaptionPropertyId("name");

    geographicalRole.addListener(new ValueChangeListener() {
      public void valueChange(
          Property.ValueChangeEvent event) {
                /*
                 * Modify the actual value of the custom field.
                 */
        RoleEntity entity = container
            .getItem(geographicalRole.getValue()).getEntity();
        setValue(entity, false);
      }
    });
  }

  @Override
  protected Component initContent() {
    CssLayout cssLayout = new CssLayout();
    cssLayout.addComponent(geographicalRole);
    return cssLayout;

  }

//    @Override
//    protected Component initContent() {
//        CssLayout cssLayout = new CssLayout();
//        cssLayout.addComponent(geographicalRole);
//        cssLayout.addComponent(role);
//        return cssLayout;
//    }

  /**
   * Modify available options based on the "geo role" select.
   */
//    private void filterRoles(Role currentGeoRole) {
//        if (currentGeoRole == null) {
//            role.setValue(null);
//            role.setEnabled(false);
//        } else {
//            container.removeAllContainerFilters();
//            container.addContainerFilter(new Equal("parent",
//                    currentGeoRole));
//            container.applyFilters();
//            role.setValue(null);
//            role.setEnabled(true);
//        }
//    }

//    @Override
//    public void setPropertyDataSource(Property newDataSource) {
//        super.setPropertyDataSource(newDataSource);
//        setRole((Role) newDataSource.getValue());
//    }

//    @Override
//    public void setValue(Role newValue) throws ReadOnlyException,
//            Converter.ConversionException {
//        super.setValue(newValue);
//        setRole(newValue);
//    }

//    private void setRole(Role role) {
//        geographicalRole.setValue(role != null ? role
//                .getParent().getId() : null);
//        this.role
//                .setValue(role != null ? role.getId() : null);
//    }
//
  @Override
  public Class<? extends RoleEntity> getType() {
    return RoleEntity.class;
  }

  public void setValue(RoleEntity newValue) throws ReadOnlyException,
      Converter.ConversionException {
    super.setValue(newValue);
    //markAsDirty();
    setRole(newValue);
  }

  private void setRole(RoleEntity role) {
    if (role != null) {
      geographicalRole.setValue(role.getId());
    }
  }

  @Override
  public void setPropertyDataSource(Property newDataSource) {
    super.setPropertyDataSource(newDataSource);
//    EntityItemProperty property;
//    Object masterEntity;
//    property = (EntityItemProperty) newDataSource;
//    masterEntity = property.getItem().getEntity();
//    if (newDataSource.getValue() == null) {
//      try {
//        createdInstance = newDataSource.getType().newInstance();
//        if (isBuffered()) {
//          tryToSetBackReference();
//        }
//        editedEntityItem = createItemForInstance(createdInstance);
//        setItemDataSource(editedEntityItem, getVisiblePropertyIds());
//        if (isBuffered()) {
//          newDataSource.setValue(editedEntityItem.getEntity());
//          createdInstance = null;
//        }
//      } catch (InstantiationException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      } catch (IllegalAccessException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    } else {
//      editedEntityItem = getItemForInstance(newDataSource.getValue());
//      setItemDataSource(editedEntityItem, getVisiblePropertyIds());
//    }
//    newDataSource.getItem.getPropertyValue(entity, propertyId);
//    setRole((JPAContainerItem.ItemPr)  newDataSource).getRealValue();
    RoleEntity value = (RoleEntity) newDataSource.getValue();
    //super.setValue(value);
    setRole(value);
  }

}
