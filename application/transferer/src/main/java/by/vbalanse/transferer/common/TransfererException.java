package by.vbalanse.transferer.common;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */
public class TransfererException extends RuntimeException {

  public TransfererException() {
  }

  public TransfererException(String message) {
    super(message);
  }

  public TransfererException(String message, Throwable cause) {
    super(message, cause);
  }

}