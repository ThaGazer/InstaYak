package instayak.serialization;

/******************************************************************************
 * Created by Justin Ritter on 1/20/2017.
 * Assignment Program0
 * Project is InstaYak
 * Package instayak.serialization
 *****************************************************************************/
public class InstaYakException extends java.lang.Exception {
  /**
   * Constructs InstaYak exception
   *
   * @param message exception message
   * @param cause   exception cause
   */
  public InstaYakException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs InstaYak exception
   *
   * @param message exception message
   */
  public InstaYakException(String message) {
    super(message);
  }
}
