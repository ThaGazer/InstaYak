package instayak.serialization.test;

import instayak.serialization.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

import static org.junit.Assert.*;

/**********************************************************************************
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization.test
 **********************************************************************************/
@RunWith(Parameterized.class)
public class InstaYakUOnTest {
    protected String xpectOp = "UOn";
    protected String xpectMsg;
    protected String xpectCat;
    protected byte[] xpectPic;

    public InstaYakUOnTest(String a, String b, byte[] c) {
        xpectMsg = a;
        xpectCat = b;
        xpectPic = c;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> list() {
        ArrayList a = new ArrayList();
        a.add(new Object[]{"UOn 12345 123456787654321234\r\n", "12345", "123456787654321234".getBytes()});
        a.add(new Object[]{"UOn abcd 1234a1\r\n", "abcd", "1234a1".getBytes()});
        return a;
    }

    @Test
    public void testGetOperation() throws InstaYakException, IOException {
        InstaYakUOn msg = new InstaYakUOn("123", "12345".getBytes());
        Assert.assertEquals(xpectOp, msg.getOperation());
    }

    @Test
    public void testToString() throws InstaYakException, UnsupportedEncodingException {
        String xpectToString = "UOn: Category=" + xpectCat + " Image=" + xpectPic;
        InstaYakUOn msg = new InstaYakUOn(xpectCat, xpectPic);
        Assert.assertEquals(xpectToString, msg.toString());
    }

    @Test
    public void testEncode() throws InstaYakException, IOException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(xpectMsg.getBytes()));
        InstaYakMessage msg = InstaYakMessage.decode(in);
        ByteArrayOutputStream byteOut= new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(byteOut);
        msg.encode(out);
        Assert.assertEquals(xpectMsg, byteOut.toString());
    }

    @Test
    public void testSetCategory() throws InstaYakException, IOException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(xpectMsg.getBytes()));
        InstaYakMessage msg = InstaYakMessage.decode(in);
        Assert.assertEquals(xpectCat, ((InstaYakUOn)msg).getCategory());
    }

    @Test (expected = InstaYakException.class)
    public void testBadSetCategory() throws InstaYakException, IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream("UOn something\r\n".getBytes());
        MessageInput in = new MessageInput(bIn);
        InstaYakMessage.decode(in);
    }
}