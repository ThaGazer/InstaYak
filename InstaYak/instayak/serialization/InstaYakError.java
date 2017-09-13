package instayak.serialization;

import java.io.IOException;

/*******************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization
 ******************************************************************************/
public class InstaYakError extends InstaYakMessage {
  private String errMessage;
  //string literals for validation
  private static final String alphaNum_space = "[\\w\\s]+";
  private static final String toStringRet = "Error: Message=";

  /**
   * Constructs error message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakError(MessageInput in)
      throws InstaYakException, IOException {
    setMessage(in.getline());
  }

  /**
   * Constructs error message using set value
   *
   * @param message error message
   * @throws InstaYakException if validation fails
   */
  public InstaYakError(String message) throws InstaYakException {
    setMessage(message);
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = error + " " + errMessage + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns message
   *
   * @return message
   */
  public String getMessage() {
    return errMessage;
  }

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public String getOperation() {
    return error;
  }

  /**
   * Sets message
   *
   * @param message new message
   * @throws InstaYakException if null or invalid message
   */
  public void setMessage(String message) throws InstaYakException {
    if (message != null && !message.isEmpty()) {
      if (message.matches(alphaNum_space)) {
        errMessage = message;
      } else {
        throw new InstaYakException(errvalid);
      }
    } else {
      throw new InstaYakException(errNull);
    }
  }

  /**
   * Returns a String representation ("Error: Message=Bad stuff")
   *
   * @return string representation
   */
  public String toString() {
    return toStringRet + errMessage + "\r\n";
  }
}
