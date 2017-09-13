package instayak.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.NullPointerException;

/*******************************************************************************
 * Created by Justin Ritter on 1/23/2017.
 * Project is InstaYak in package messageIO
 ******************************************************************************/

public class MessageInput {
  private static final String ISO = "ISO8859-1";
  private InputStreamReader messageIn;

  /**
   * Constructs a new Input source from an InputStream
   *
   * @param in byte input source
   */
  public MessageInput(InputStream in) throws NullPointerException {
    try {
      //encapsulates inputstream into inputstreamwriter
      messageIn = new InputStreamReader(in, ISO);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the inputStream object
   *
   * @return the incoming inputStream object
   */
  public InputStreamReader getReader() {
    return messageIn;
  }

  /**
   * Returns the next token in the inputStream if possible
   *
   * @return next word in stream
   * @throws IOException       if I/O problem
   * @throws InstaYakException if parse or validation failure
   */
  public String nextTok() throws IOException, InstaYakException {
    String token = "";
    int a;
    boolean readDone = false;
    while (!readDone && (a = messageIn.read()) != -1) {
      if (a == '\r') {
        if (messageIn.read() != '\n') {
          throw new InstaYakException("Error: incorrect frame");
        }
        readDone = true;
      } else if (a == ' ') {
        readDone = true;
      } else {
        token += (char) a;
      }
    }
    if (token.isEmpty()) {
      throw new InstaYakException("Error: empty string");
    }
    return token;
  }

  /**
   * Returns the entire line in the inputStream
   *
   * @return next line in the stream
   * @throws IOException       if I/O problem
   * @throws InstaYakException if parse or validation failure
   */
  public String getline() throws IOException, InstaYakException {
    String line = "";
    int a;
    boolean readDone = false;
    while (!readDone && (a = messageIn.read()) != -1) {
      if (a == '\r') {
        if (messageIn.read() != '\n') {
          throw new InstaYakException("Error: incorrect frame");
        }
        readDone = true;
      } else {
        line += (char) a;
      }
    }
    if (line.isEmpty()) {
      throw new InstaYakException("Error: empty message");
    }
    return line;
  }
}
