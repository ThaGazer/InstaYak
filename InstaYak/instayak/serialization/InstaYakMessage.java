package instayak.serialization;

import java.io.IOException;
import java.io.InputStreamReader;

/*******************************************************************************
 * Created by Justin Ritter on 1/20/2017.
 * Assignment Program0
 * Project is InstaYak
 * Package is instayak.serialization
 ******************************************************************************/
public abstract class InstaYakMessage {
  //const literals for the switch
  protected static final String version = "INSTAYAK";
  protected static final String uon = "UOn";
  protected static final String slmd = "SLMD";
  protected static final String id = "ID";
  protected static final String error = "ERROR";
  protected static final String cred = "CRED";
  protected static final String clng = "CLNG";
  protected static final String ack = "ACK";
  protected static final String iso8859_1 = "ISO8859-1";
  //error messages used by sub classes
  protected static final String errNon_Num = "Error: non-numeric";
  protected static final String errNull = "Error: null object";
  protected static final String errvalid = "Error: validation";
  protected static final String errNon_alphnum = "Error: non-alphanumeric";

  /**
   * Creates a instaYak message
   */
  public InstaYakMessage() {
  }

  /**
   * Deserializes message from input source
   *
   * @param in deserialization input source
   * @return a specific InstaYak message resulting from deserialization
   * @throws InstaYakException if parse or validation problem
   * @throws IOException       if I/O problem
   */
  public static InstaYakMessage decode(MessageInput in)
      throws InstaYakException, IOException {

    String operation = in.nextTok();
    switch (operation) {
      case version:
        return new InstaYakVersion(in);
      //break;
      case uon:
        return new InstaYakUOn(in);
      //break;
      case slmd:
        return new InstaYakSLMD(in);
      //break;
      case id:
        return new InstaYakID(in);
      //break;
      case error:
        return new InstaYakError(in);
      //break;
      case cred:
        return new InstaYakCredentials(in);
      //break;
      case clng:
        return new InstaYakChallenge(in);
      //break;
      case ack:
        return new InstaYakACK(in);
      //break;
      default:
        throw new InstaYakException("ERROR: invalid operation");
        //break;
    }
  }

  /**
   * Serializes message to given output sink
   *
   * @param out serialization output sink
   * @throws IOException if I/O problem
   */
  public abstract void encode(MessageOutput out) throws IOException;

  /**
   * Returns message operation
   *
   * @return message operation
   */
  public abstract String getOperation();
}
