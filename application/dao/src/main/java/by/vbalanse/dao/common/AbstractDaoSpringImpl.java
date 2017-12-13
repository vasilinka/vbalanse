package by.vbalanse.dao.common;

import by.vbalanse.model.common.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

@Service
public abstract class AbstractDaoSpringImpl<E extends AbstractEntity<ID>, ID extends Serializable> extends AbstractDaoImpl<E, ID> {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  protected final Session getSession() {
    return sessionFactory.getCurrentSession();
  }

}
