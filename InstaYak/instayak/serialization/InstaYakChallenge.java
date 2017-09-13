package instayak.serialization;

import java.io.IOException;
import java.io.InputStreamReader;

/*******************************************************************************
 * Created by Justin Ritter on 1/20/2017.
 * Assignemnt Program0
 * Project is InstaYak
 * Package instayak.serialization
 ******************************************************************************/
public class InstaYakChallenge extends InstaYakMessage {
  private String servnonce;
  //string literal for validation checking
  private static final String nums = "[\\d]+";
  private static final String toStringRet = "Challenge: Nonce=";

  /**
   * Constructs challenge message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakChallenge(MessageInput in)
      throws InstaYakException, IOException {
    setNonce(in.nextTok());
  }

  /**
   * Constructs challenge message using given values
   *
   * @param nonce challenge nonce
   * @throws InstaYakException if validation fails
   */
  public InstaYakChallenge(String nonce) throws InstaYakException {
    setNonce(nonce);
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    String objString = clng + " " + servnonce + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns nonce
   *
   * @return nonce
   */
  public String getNonce() {
    return servnonce;
  }

  /**
   * Sets nonce
   *
   * @param nonce new nonce
   * @throws InstaYakException if null or invalid nonce
   */
  public void setNonce(String nonce) throws InstaYakException {
    if (nonce != null && !nonce.isEmpty()) {
      if (nonce.matches(nums)) {
        servnonce = nonce;
      } else {
        throw new InstaYakException(errNon_Num);
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
    return clng;
  }

  /**
   * Returns a String representation ("Challenge: Nonce=12345")
   *
   * @return string representation
   */
  public String toString() {
    return toStringRet + servnonce;
  }
}
