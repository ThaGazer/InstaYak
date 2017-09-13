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

import instayak.serialization.InstaYakChallenge;
import instayak.serialization.InstaYakException;
import instayak.serialization.InstaYakMessage;
import instayak.serialization.InstaYakSLMD;
import instayak.serialization.MessageInput;
import instayak.serialization.MessageOutput;

/**********************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization.test
 *********************************************************************************/
public class InstaYakSLMDTest {
    //private static final InstaYakSLMD m1;
    private static final byte[] m1Enc;
    private static final InstaYakSLMD m1;
    private static final InstaYakSLMD m2;

    static{
        try{
            m1Enc = "SLMD\r\n".getBytes("ISO8859-1");
            m1 = new InstaYakSLMD();
            m2 = new InstaYakSLMD();
        }catch(UnsupportedEncodingException var1){
            throw new RuntimeException(var1);
        }
    }

    @Test
    public void testNormalSLMD() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("SLMD\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakSLMD slmd = new InstaYakSLMD();
        slmd.decode(msg);
        assertEquals(slmd.getOperation(), "SLMD");
    }

    @Test (expected = InstaYakException.class)
    public void testFrontSpace() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream(" SLMD\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakMessage.decode(msg);
    }

    @Test(expected = InstaYakException.class)
    public void testDoubleBackR() throws IOException, InstaYakException {
        InputStream f =  new ByteArrayInputStream("SLMD\r\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakMessage.decode(msg);
    }

    @Test
    public void testParallelSLMDEncode() throws IOException {
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
    public void testWrongOperation() throws InstaYakException, IOException {
        InputStream f =  new ByteArrayInputStream("SlMd\r\n".getBytes(StandardCharsets.ISO_8859_1));
        MessageInput msg = new MessageInput(f);
        InstaYakMessage.decode(msg);
    }
}
