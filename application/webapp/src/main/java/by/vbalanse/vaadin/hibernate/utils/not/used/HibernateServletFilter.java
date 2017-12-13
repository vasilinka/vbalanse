package by.vbalanse.vaadin.hibernate.utils.not.used;

import by.vbalanse.model.common.utils.HibernateUtils;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import net.sf.ehcache.transaction.local.TransactionListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Deprecated
public class HibernateServletFilter implements Filter {

  ServletContext servletContext;
  private static final Logger logger = LoggerFactory.getLogger(HibernateServletFilter.class);

  public void init(FilterConfig filterConfig) throws ServletException {
    logger.debug("Initializing HibernateServletFilter");
  }

  public void destroy() {
    logger.debug("Destroying HibernateServletFilter");
    //final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    if (servletContext != null) {
      SpringContextHelper springContextHelper = new SpringContextHelper(servletContext);
      SessionFactory sessionFactory = (SessionFactory) springContextHelper.getBean("sessionFactory");
      HibernateUtil.setSessionFactory(sessionFactory);
      final Session session = sessionFactory.getCurrentSession();
      //final Session session = new SpringContextHelper()

      if (session.getTransaction().isActive()) {
        logger.debug("Committing the final active transaction");
        session.getTransaction().commit();
      }

      if (session.isOpen()) {
        logger.debug("Closing the final open session");
        session.close();
      }
    }
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    servletContext = httpServletRequest.getSession().getServletContext();
    SpringContextHelper springContextHelper = new SpringContextHelper(servletContext);
    SessionFactory sessionFactory = (SessionFactory) springContextHelper.getBean("sessionFactory");
    final Session session = sessionFactory.getCurrentSession();


    try {
      logger.debug("Starting a database transaction");
      session.beginTransaction();

      chain.doFilter(request, response);

      logger.debug("Committing the active database transaction");
      session.getTransaction().commit();
    } catch (StaleObjectStateException e) {
      logger.error(e.toString());

      if (session.getTransaction().isActive()) {
        logger.debug("Rolling back the active transaction");
        session.getTransaction().rollback();
      }

      throw e;
    } catch (Throwable e) {
      logger.error(e.toString());

      if (session.getTransaction().isActive()) {
        logger.debug("Rolling back the active transaction");
        session.getTransaction().rollback();
      }

      throw new ServletException(e);
    }
  }
}