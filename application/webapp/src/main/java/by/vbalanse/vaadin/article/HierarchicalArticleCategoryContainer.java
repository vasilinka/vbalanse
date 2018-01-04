package by.vbalanse.vaadin.article;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.server.VaadinServlet;
import net.sf.ehcache.CacheManager;

import javax.persistence.EntityManagerFactory;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class HierarchicalArticleCategoryContainer extends JPAContainer<ArticleCategoryEntity> {
  /**
   * Creates a new <code>JPAContainer</code> instance for entities of class
   * <code>entityClass</code>. An entity provider must be provided using the
   * {@link #setEntityProvider(com.vaadin.addon.jpacontainer.EntityProvider) }
   * before the container can be used.
   *
   * @param entityClass the class of the entities that will reside in this container
   *                    (must not be null).
   */
  public HierarchicalArticleCategoryContainer(Class<ArticleCategoryEntity> entityClass) {
    super(entityClass);
    //CacheManager.getInstance().shutdown();
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    EntityManagerFactory entityManagerFactory = (EntityManagerFactory) springContextHelper.getBean("entityManagerFactory");
    setEntityProvider(new CachingMutableLocalEntityProvider<ArticleCategoryEntity>(ArticleCategoryEntity.class, entityManagerFactory.createEntityManager()));
    setParentProperty("category");
  }

  public boolean areChildrenAllowed(Object itemId) {
    return true;
  }
}
