package instayak.serialization;

import java.io.IOException;

/*******************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization
 ******************************************************************************/
public class InstaYakSLMD extends InstaYakMessage {
  /**
   * constructs SLMD message
   */
  public InstaYakSLMD() {
  }

  /**
   * Constructs new ACK message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakSLMD(MessageInput in) throws InstaYakException, IOException {
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = slmd + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public String getOperation() {
    return slmd;
  }

  /**
   * Returns a String representation
   *
   * @return string representation
   */
  public String toString() {
    return slmd;
  }
}
