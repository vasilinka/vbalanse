package by.vbalanse.transferer.common;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */

public interface ObjectTransferer<S, T> {

  T transfer(S source);

}