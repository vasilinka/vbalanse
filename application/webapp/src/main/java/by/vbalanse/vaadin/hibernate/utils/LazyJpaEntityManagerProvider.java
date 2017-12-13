package by.vbalanse.vaadin.hibernate.utils;

import com.vaadin.addon.jpacontainer.EntityManagerProvider;

import javax.persistence.EntityManager;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class LazyJpaEntityManagerProvider
    implements EntityManagerProvider {
  private static ThreadLocal<EntityManager>
      entityManagerThreadLocal =
      new ThreadLocal<EntityManager>();

  public EntityManager getEntityManager() {
    return entityManagerThreadLocal.get();
  }

  public static void setCurrentEntityManager(
      EntityManager em) {
    entityManagerThreadLocal.set(em);
  }
}