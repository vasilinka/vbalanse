package by.vbalanse.dao.common.jpa;

import by.vbalanse.model.common.AbstractEntity;
import by.vbalanse.model.user.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.Serializable;

@Service
public abstract class AbstractDaoSpringImpl<E extends AbstractEntity<ID>, ID extends Serializable> extends AbstractDaoImpl<E, ID> {

  //  @Resource
//  private SessionFactory sessionFactory;


  //@Autowired
  @PersistenceContext
  private EntityManager entityManager;

//  protected final Session getSession() {
//    return sessionFactory.getCurrentSession();
//  }

  public Object getSingleResult(Query query) {
    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  protected final EntityManager getEntityManager() {
//    EntityManager entityManager = entityManagerFactory.createEntityManager();
//    entityManager.getTransaction().begin();
    return entityManager;

    //LazyJpaEntityManagerProvider.
  }
} 