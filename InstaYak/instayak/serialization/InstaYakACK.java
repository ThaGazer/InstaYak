package instayak.serialization;

import java.io.IOException;

/*******************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization
 ******************************************************************************/
public class InstaYakACK extends InstaYakMessage {
  /**
   * constructs ACK message
   */
  public InstaYakACK() {
  }

  /**
   * Constructs new ACK message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException
   * @throws IOException
   */
  public InstaYakACK(MessageInput in)
      throws InstaYakException, IOException {
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = "ACK" + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public String getOperation() {
    return ack;
  }

  /**
   * Returns a String representation ("ACK") return string representation
   *
   * @return toString in class java.lang.Object
   */
  public String toString() {
    return ack;
  }
}
