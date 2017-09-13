package instayak.serialization.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import instayak.serialization.*;
import org.junit.Assert;
import org.junit.Test;

/**********************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization.test
 *********************************************************************************/
public class InstaYakACKTest {

    private static final byte[] m1Enc;
    private static final InstaYakACK m1;
    private static final InstaYakACK m2;

    static{
        try{
            m1Enc = "ACK\r\n".getBytes("ISO8859-1");
            m1 = new InstaYakACK();
            m2 = new InstaYakACK();
        }catch(UnsupportedEncodingException var1){
            throw new RuntimeException(var1);
        }
    }

    @Test
    public void testNormalACK() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("ACK\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakACK ack = new InstaYakACK(msg);
        assertEquals(ack.getOperation(), "ACK");
    }

    @Test (expected = InstaYakException.class)
    public void testFrontSpace() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream(" ACK\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput in = new MessageInput(f);
        InstaYakMessage.decode(in);
    }

    @Test(expected = InstaYakException.class)
    public void testDoubleBackR() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("ACK\r\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput in = new MessageInput(f);
        InstaYakMessage.decode(in);
    }

    @Test
    public void testParallelACKEncode() throws IOException {
        ByteArrayOutputStream bout1 = new ByteArrayOutputStream();
        MessageOutput out1 = new MessageOutput(bout1);
        ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
        MessageOutput out2 = new MessageOutput(bout2);
        m2.encode(out2);
        m1.encode(out1);
        Assert.assertArrayEquals(m1Enc, bout1.toByteArray());
        Assert.assertArrayEquals(m1Enc, bout2.toByteArray());
    }

    @Test (expected = InstaYakException.class)
    public void testBadOperation() throws InstaYakException, IOException {
        InputStream f =  new ByteArrayInputStream("AcK\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput in = new MessageInput(f);
        InstaYakMessage.decode(in);
    }
}

