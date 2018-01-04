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
package by.vbalanse.vaadin.article;

import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.AdminView;
import by.vbalanse.vaadin.component.AbstractListView;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.server.VaadinServlet;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManagerFactory;

@org.springframework.stereotype.Component(value = "articleListView")
@Scope(value = "prototype")
public class ArticleListView extends AbstractListView<ArticleEntity, ArticleEntityEditor> {

  @Autowired
  EntityManagerFactory entityManagerFactory;

  @Override
  public JPAContainer<ArticleEntity> getContainer() {
    //CacheManager.getInstance().shutdown();
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    EntityManagerFactory entityManagerFactory = (EntityManagerFactory) springContextHelper.getBean("entityManagerFactory");
    return JPAContainerFactory.make(ArticleEntity.class, entityManagerFactory.createEntityManager());
  }

  @Override
  protected ArticleEntityEditor getEntityEditor(EntityItem<ArticleEntity> entity) {
    return new ArticleEntityEditor(entity);
  }

  @Override
  protected ArticleEntity getEntityInstance() {
    return new ArticleEntity();
  }

  @Override
  protected Object[] getVisibleColumns() {
    return new Object[]{"title", "dateOfPublish",
        "author"};
  }

  @Override
  protected Container.Filter updateFilters(String textFilter) {
    if (textFilter != null && !textFilter.equals("")) {
      return new Like("title", textFilter + "%", false);
    }
    return null;
  }
}
