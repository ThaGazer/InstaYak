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
public class InstaYakVersionTest {
    protected String xpectMsg;
    protected String xpectOp = "INSTAYAK";
    protected String xpectData;

    public InstaYakVersionTest(String opIn, String dataIn) {
        xpectMsg = opIn;
        xpectData = dataIn;
    }

    @Parameters
    public static Collection<Object[]> list() {
        ArrayList a = new ArrayList();
        a.add(new Object[]{"INSTAYAK 1.0\r\n", "1.0"});
        a.add(new Object[]{"INSTAYAK 1\r\n", "1"});
        a.add(new Object[]{"INSTAYAK .1\r\n", ".1"});
        return a;
    }

    @Test
    public void testGetOperation() {
        InstaYakVersion msg = new InstaYakVersion();
        Assert.assertEquals(xpectOp, msg.getOperation());
    }

    @Test
    public void testToString() {
        String xpectToString = "InstaYak";
        InstaYakVersion msg = new InstaYakVersion();
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
    public void testSetVersion() throws InstaYakException, IOException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(xpectMsg.getBytes()));
        InstaYakMessage msg = InstaYakMessage.decode(in);
        Assert.assertEquals(xpectData, ((InstaYakVersion)msg).getVersion());
    }

    @Test (expected = InstaYakException.class)
    public void testBadSetVersion() throws InstaYakException, IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream("INSTAYAK nonNum\r\n".getBytes());
        MessageInput in = new MessageInput(bIn);
        InstaYakMessage msg = InstaYakMessage.decode(in);
    }
}