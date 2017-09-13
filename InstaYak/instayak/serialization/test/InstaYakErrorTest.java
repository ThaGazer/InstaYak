package instayak.serialization.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import instayak.serialization.InstaYakError;
import instayak.serialization.InstaYakException;
import instayak.serialization.InstaYakID;
import instayak.serialization.InstaYakMessage;
import instayak.serialization.MessageInput;
import instayak.serialization.MessageOutput;

/**********************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization
 **********************************************************************************/
public class InstaYakErrorTest {

    private static final InstaYakError m1;
    private static final byte[] m1Enc;
    private static final InstaYakError m2;
    private static final byte[] m2Enc;

    static {
        try {
            m1 = new InstaYakError("no grapes");
            m1Enc = "ERROR no grapes\r\n".getBytes("ISO8859-1");
            m2 = new InstaYakError("no plums");
            m2Enc = "ERROR no plums\r\n".getBytes("ISO8859-1");
        } catch (UnsupportedEncodingException | InstaYakException var1) {
            throw new RuntimeException("Evil", var1);
        }
    }

    //Test InstaYakError()
    @Test
    public void testConstructor() throws IOException, InstaYakException {
        InstaYakError err = new InstaYakError("   a valid   error message ");
    }

    @Test (expected= InstaYakException.class)
    public void testInvalidConstructor() throws IOException, InstaYakException {
        InstaYakError err = new InstaYakError("   a valid  ! error message ");
    }


    //Test InstaYakError(MessageInput)
    @Test
    public void testNormalError() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("ERROR me ssa ge \r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakError err = new InstaYakError(msg);
        assertEquals(err.getOperation(), "ERROR");

    }

    @Test (expected = InstaYakException.class)
    public void testErrorWithSymbols() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("ERROR mes!sage\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakError err = new InstaYakError(msg);
    }

    @Test (expected = InstaYakException.class)
    public void testBadBackRError() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("ERROR message\r\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakError err = new InstaYakError(msg);
    }

    //Test InstaYakError encode
    @Test
    public void testParallelEncode() throws IOException {
        ByteArrayOutputStream bout1 = new ByteArrayOutputStream();
        MessageOutput out1 = new MessageOutput(bout1);
        ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
        MessageOutput out2 = new MessageOutput(bout2);
        m2.encode(out2);
        m1.encode(out1);
        Assert.assertArrayEquals(m1Enc, bout1.toByteArray());
        Assert.assertArrayEquals(m2Enc, bout2.toByteArray());
    }

    @Test
    public void testBadEquals() throws IOException, InstaYakException {
        assertFalse(m2.equals(m1));
    }
}
