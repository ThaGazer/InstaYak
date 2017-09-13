package instayak.serialization;

import java.io.IOException;
import java.io.InputStreamReader;

/*******************************************************************************
 * Created by Justin Ritter on 1/20/2017.
 * Assignment Program0
 * Project is InstaYak
 * Package instayak.serialization
 ******************************************************************************/
public class InstaYakID extends InstaYakMessage {
  private String Identification;
  //string literals for validation checking
  private static final String alphaNum = "[\\w]+";
  private static final String toStringRet = "ID: ID=";

  /**
   * Constructs ID message using deserialization. Only parses material
   * specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakID(MessageInput in) throws InstaYakException, IOException {
    setID(in.nextTok());
  }

  /**
   * Constructs ID message using set values
   *
   * @param id ID for user
   * @throws InstaYakException if validation fails
   */
  public InstaYakID(String id) throws InstaYakException {
    setID(id);
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = id + " " + Identification + '\r' + '\n';
    out.writeStr(objString);
  }

  /**
   * Returns ID
   *
   * @return ID
   */
  public String getID() {
    return Identification;
  }

  /**
   * Sets ID
   *
   * @param id new ID
   * @throws InstaYakException if null or invalid ID
   */
  public void setID(String id) throws InstaYakException {
    if (id != null && !id.isEmpty()) {
      if (id.matches(alphaNum)) {
        Identification = id;
      } else {
        throw new InstaYakException(errNon_alphnum);
      }
    } else {
      throw new InstaYakException(errNull);
    }
  }

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public String getOperation() {
    return id;
  }

  /**
   * Returns a String representation ("ID: ID=bob")
   *
   * @return string representation
   */
  public String toString() {
    return toStringRet + Identification;
  }
}
