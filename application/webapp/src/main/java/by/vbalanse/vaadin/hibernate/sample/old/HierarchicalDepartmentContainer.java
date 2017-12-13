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
package by.vbalanse.vaadin.hibernate.sample.old;

import by.vbalanse.model.user.UserEntity;
import by.vbalanse.vaadin.AdminUI;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;

import java.util.Collection;
//import com.vaadin.demo.jpaaddressbook.domain.Department;

public class HierarchicalDepartmentContainer extends JPAContainer<UserEntity> {

    public HierarchicalDepartmentContainer() {
        super(UserEntity.class);
        setEntityProvider(new CachingLocalEntityProvider<UserEntity>(
            UserEntity.class,
                JPAContainerFactory
                        .createEntityManagerForPersistenceUnit(AdminUI.PERSISTENCE_UNIT)));
        setParentProperty("parent");
    }

    /*@Override
    public boolean areChildrenAllowed(Object itemId) {
        return super.areChildrenAllowed(itemId)
                && getItem(itemId).getEntity().isSuperDepartment();
    }*/

  public Collection<Filter> getContainerFilters() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
