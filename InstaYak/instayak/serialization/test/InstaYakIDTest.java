package instayak.serialization.test;

import instayak.serialization.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**********************************************************************************
 * Created by Justin Ritter on 2/1/2017.
 * Project is InstaYak in package instayak.serialization.test
 **********************************************************************************/
@RunWith(Parameterized.class)
public class InstaYakIDTest {
    protected String xpectOp = "ID";
    protected String xpectData;
    protected String xpectMsg;

    public InstaYakIDTest(String a, String b) {
        xpectMsg = a;
        xpectData = b;
    }

    @Parameters
    public static Collection<Object[]> list() {
        ArrayList a = new ArrayList();
        a.add(new Object[]{"ID 12345\r\n", "12345"});
        a.add(new Object[]{"ID abcd\r\n", "abcd"});
        a.add(new Object[]{"ID ab12\r\n", "ab12"});
        a.add(new Object[]{"ID aA1\r\n", "aA1"});
        return a;
    }

    @Test
    public void testGetOperation() throws InstaYakException {
        InstaYakID msg = new InstaYakID("123");
        Assert.assertEquals(xpectOp, msg.getOperation());
    }

    @Test
    public void testToString() throws InstaYakException {
        String xpectToString = "ID: ID=" + xpectData;
        InstaYakID msg = new InstaYakID(xpectData);
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
    public void testSetID() throws InstaYakException, IOException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(xpectMsg.getBytes()));
        InstaYakMessage msg = InstaYakMessage.decode(in);
        Assert.assertEquals(xpectData, ((InstaYakID)msg).getID());
    }

    @Test (expected = InstaYakException.class)
    public void testBadSetID() throws InstaYakException, IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream("INSTAYAK nonNum\r\n".getBytes());
        MessageInput in = new MessageInput(bIn);
        InstaYakMessage.decode(in);
    }
}