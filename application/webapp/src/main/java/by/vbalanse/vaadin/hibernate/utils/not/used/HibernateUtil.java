package by.vbalanse.vaadin.hibernate.utils.not.used;

import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.server.VaadinServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class HibernateUtil
{
  private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
  private static SessionFactory sessionFactory;

  static
  {
    try
    {
      logger.debug("Initializing HibernateUtil");

      //SpringContextHelper springContextHelper = new SpringContextHelper();


//      final Configuration configuration = new Configuration();
//      configuration.configure();
//
//      final ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
//
//      final ServiceRegistry serviceRegistry = serviceRegistryBuilder
//          .applySettings(configuration.getProperties())
//          .buildServiceRegistry();
//
//      sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    catch (Throwable e)
    {
      logger.error(e.toString());
      throw new ExceptionInInitializerError(e);
    }
  }

  protected String[] getSpringConfigLocations() {
    return new String[]{
        "spring-configuration/common-config-datasource.xml",
        "spring-configuration/common-config-hibernate.xml",
        "spring-configuration/hibernate-model.xml"
    };
  }


  public static SessionFactory getSessionFactory()
  {
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    SessionFactory sessionFactory = (SessionFactory) springContextHelper.getBean("sessionFactory");
    return sessionFactory;
  }

  public static Session getSession() {
    Session currentSession = HibernateUtil.getSessionFactory()
        .getCurrentSession();
    if(!currentSession.getTransaction().isActive()) {
      currentSession.beginTransaction();
    }
    return currentSession;
  }

  private void closeSession() {
    Session sess = HibernateUtil.getSessionFactory()
        .getCurrentSession();
    if(sess.getTransaction().isActive()) {
      sess.getTransaction().commit();
    }
    sess.flush();
    sess.close();
  }

  public static void setSessionFactory(SessionFactory sessionFactory) {
    HibernateUtil.sessionFactory = sessionFactory;

  }
}