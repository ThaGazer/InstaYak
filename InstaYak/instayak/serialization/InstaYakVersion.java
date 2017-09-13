package instayak.serialization;

import java.io.IOException;
import java.io.InputStreamReader;

/*******************************************************************************
 * Created by Justin Ritter on 1/20/2017.
 * Assignment Program0
 * Project is InstaYak
 * Package instayak.serialization
 ******************************************************************************/
public class InstaYakVersion extends InstaYakMessage {
  //hold the current version of the build
  private String variant;
  //string literal for validation
  private static final String nums = "[\\d.]+";
  private static final String toStringRet = "InstaYak";

  /**
   * Constructs version message
   */
  public InstaYakVersion() {
    variant = "1.0";
  }

  /**
   * Constructs version message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakVersion(MessageInput in)
      throws InstaYakException, IOException {
    setVersion(in.nextTok());
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = version + " " + variant + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public String getOperation() {
    return version;
  }

  /**
   * Returns the version
   *
   * @return version
   */
  public String getVersion() {
    return variant;
  }

  /**
   * Sets the version
   *
   * @param strIn string representation
   * @throws InstaYakException
   */
  public void setVersion(String strIn) throws InstaYakException {
    if (!strIn.isEmpty()) {
      if (strIn.matches(nums)) {
        variant = strIn;
      } else {
        throw new InstaYakException(errNon_Num);
      }
    }
  }

  /**
   * Returns a String representation ("InstaYak")
   *
   * @return String representation
   */
  public String toString() {
    return toStringRet;
  }
}
