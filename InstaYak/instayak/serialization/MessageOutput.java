package instayak.serialization;
/*******************************************************************************
 * Created by Justin Ritter on 1/23/2017.
 * Project is InstaYak in package messageIO
 ******************************************************************************/

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MessageOutput {
  private static final String ISO = "ISO8859-1";
  private OutputStreamWriter messageOut;

  /**
   * Constructs a new output sink from an OutputStream
   *
   * @param out byte output sink
   */
  public MessageOutput(OutputStream out) throws NullPointerException {
    try {
      //encapsulates outputstream into outputstreamwriter
      messageOut = new OutputStreamWriter(out, ISO);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes the string out to the OutputStreamWriter
   *
   * @param strOut string to write out
   * @throws IOException if I/O problem
   */
  public void writeStr(String strOut) throws IOException {
    messageOut.write(strOut, 0, strOut.length());
    messageOut.flush();
  }

  /**
   * Returns the out going message
   *
   * @return out going message
   */
  public OutputStreamWriter getWriter() {
    return messageOut;
  }
}
