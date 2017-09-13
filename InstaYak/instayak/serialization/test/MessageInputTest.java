package instayak.serialization.test;

import instayak.serialization.InstaYakException;
import instayak.serialization.MessageInput;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**********************************************************************************
 * Created by Justin Ritter on 2/2/2017.
 * Project is InstaYak in package instayak.serialization.test
 **********************************************************************************/
public class MessageInputTest {
    @Test
    public void testnextTok() throws Exception {
        InputStream f =  new ByteArrayInputStream("SLMD\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        assertEquals(msg.nextTok(), "SLMD");
    }

}