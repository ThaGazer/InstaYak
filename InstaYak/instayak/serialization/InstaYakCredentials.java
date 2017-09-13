package instayak.serialization;

import java.io.IOException;
import java.io.InputStreamReader;

/*******************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project InstaYak
 * Package instayak.serialization
 ******************************************************************************/
public class InstaYakCredentials extends InstaYakMessage {
  private String hashingThroughTheSnow;
  private static final String upperAlphanums = "[\\dA-Z]+";
  private static final String toStringRet = "Credential: Hash=";

  /**
   * Constructs credentials message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakCredentials(MessageInput in)
      throws InstaYakException, IOException {
    setHash(in.nextTok());
  }

  /**
   * Constructs credentials message using given hash
   *
   * @param hash hash for credentials
   * @throws InstaYakException if validation of hash fails
   */
  public InstaYakCredentials(String hash) throws InstaYakException {
    setHash(hash);
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = cred + " " + hashingThroughTheSnow + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns hash
   *
   * @return hash
   */
  public final String getHash() {
    return hashingThroughTheSnow;
  }

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public String getOperation() {
    return cred;
  }

  /**
   * Sets hash
   *
   * @param hash new hash
   * @throws InstaYakException if null or invalid hash
   */
  private void setHash(String hash) throws InstaYakException {
    if (hash != null && !hash.isEmpty()) {
      if (hash.length() > 32 && hash.matches(upperAlphanums)) {
        hashingThroughTheSnow = hash;
      }
      else {
        throw new InstaYakException(errvalid);
      }
    }
    else {
      throw new InstaYakException(errNull);
    }
  }

  /**
   * Returns a String representation ("Credentials: Hash=12345")
   *
   * @return string representation
   */
  public String toString() {
    return toStringRet + hashingThroughTheSnow;
  }
}
