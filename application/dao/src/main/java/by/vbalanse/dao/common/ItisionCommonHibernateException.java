package by.vbalanse.dao.common;

/**
 * Base class for the Itision Common Hibernate exceptions
 *
 * @author Vasilina Terehova
 */
public class ItisionCommonHibernateException extends RuntimeException {

  public ItisionCommonHibernateException() {
  }

  public ItisionCommonHibernateException(String message) {
    super(message);
  }

  public ItisionCommonHibernateException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItisionCommonHibernateException(Throwable cause) {
    super(cause);
  }

}
