package by.vbalanse.transferer.common;

import org.apache.log4j.Logger;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */
public abstract class AbstractTransferer<S> {

  private final Logger LOG = Logger.getLogger(getClass());

  private Map<Class, ObjectTransferer> transferers = new HashMap<Class, ObjectTransferer>();

  protected <T> void registerTransferer(Class<T> target, ObjectTransferer<? extends S, T> transferer) {
    transferers.put(target, transferer);
  }

  public <T> List<T> transferList(Iterable<? extends S> source, Class<? extends T> targetClass) {
    if (source == null) {
      return null;
    }

    ArrayList<T> target;
    if (source instanceof List) {
      target = new ArrayList<T>(((List) source).size());
    } else {
      target = new ArrayList<T>();
    }

    for (S o : source) {
      target.add(transfer(o, targetClass));
    }

    return target;
  }

  @SuppressWarnings("unchecked")
  public <T> T transfer(S source, Class<? extends T> targetClass) {
    if (source == null) {
      return null;
    }

    if (source instanceof HibernateProxy) {
      Object probablyDeproxied = ((HibernateProxy) source).getHibernateLazyInitializer().getImplementation();

      source = (S) probablyDeproxied;
    }

    ObjectTransferer transferer = transferers.get(targetClass);
    if (transferer == null) {
      TransfererException transfererException =
          new TransfererException("Transferring can not be done from " + source.getClass().getCanonicalName() +
              " to " + targetClass.getCanonicalName() + ". No transfer found.");
      LOG.fatal(transfererException);
      throw transfererException;
    }

    try {
      return (T) transferer.transfer(source);
    } catch (Exception e) {
      TransfererException transfererException =
          new TransfererException("Transferring can not be done from " + source.getClass().getCanonicalName() +
              " to " + targetClass.getCanonicalName() + ". " + e + " was thrown during" +
              " transfer attempt.", e);
      LOG.fatal(transfererException);
      throw transfererException;
    }
  }

}