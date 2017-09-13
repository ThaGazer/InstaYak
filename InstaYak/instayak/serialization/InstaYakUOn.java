package instayak.serialization;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/*******************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization
 ******************************************************************************/
public class InstaYakUOn extends InstaYakMessage {
  private String category;
  private byte[] image;
  //string literal for validation checking
  private static final String alphaNum = "[\\w]+";
  private static final String toStringRet1 = "UOn: Category=";
  private static final String toStringRet2 = " Image=";

  /**
   * Constructs new UOn message using deserialization.
   * Only parses material specific to this message (that is not operation)
   *
   * @param in deserialization input source
   * @throws InstaYakException if parse or validation failure
   * @throws IOException       if I/O problem
   */
  public InstaYakUOn(MessageInput in)
      throws InstaYakException, IOException {
    setCategory(in.nextTok());
    setImage(in.nextTok().getBytes());
  }

  /**
   * Constructs UOn message using set values
   *
   * @param subject UOn category
   * @param picture UOn image
   * @throws InstaYakException if validation fails
   */
  public InstaYakUOn(String subject, byte[] picture)
      throws InstaYakException, UnsupportedEncodingException {
    setCategory(subject);
    setImage(picture);
  }

  /**
   * Returns a String representation ("UOn: Category=Movie Image=500 bytes")
   *
   * @param out string representation
   * @throws IOException if I/O problem
   */
  public void encode(MessageOutput out) throws IOException {
    byte[] encImage = Base64.getEncoder().withoutPadding().encode(image);
    String objString = uon + " " + category + " " + encImage + "\r\n";
    out.writeStr(objString);
  }

  /**
   * Returns category
   *
   * @return caetgory
   */
  public String getCategory() {
    return category;
  }

  /**
   * Returns image
   *
   * @return image
   */
  public byte[] getImage() {
    return image;
  }

  /**
   * Returns operation
   *
   * @return opertation
   */
  public String getOperation() {
    return uon;
  }

  /**
   * Sets category
   *
   * @param subject new category
   * @throws InstaYakException if null or invalid category
   */
  public void setCategory(String subject) throws InstaYakException {
    if (subject != null && !subject.isEmpty()) {
      if (subject.matches(alphaNum)) {
        category = subject;
      } else {
        throw new InstaYakException(errvalid);
      }
    } else {
      throw new InstaYakException(errNull);
    }
  }

  /**
   * Sets image
   *
   * @param picture image
   * @throws InstaYakException if null image
   */
  public void setImage(byte[] picture)
      throws InstaYakException, UnsupportedEncodingException {
    if (picture != null) {
      image = Base64.getDecoder().decode(new String(picture, iso8859_1));
    } else {
      throw new InstaYakException(errNull);
    }
  }

  /**
   * Returns a String representation ("UOn: Category=Movie Image=500 bytes")
   *
   * @return string representation
   */
  public String toString() {
    return toStringRet1 + category + toStringRet2 + image;
  }
}
