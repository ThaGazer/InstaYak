package instayak.serialization.test;

import instayak.serialization.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
public class InstaYakChallengeTest {
    protected String xpectOp = "CLNG";
    protected String xpectData;
    protected String xpectMsg;

    public InstaYakChallengeTest(String a, String b) {
        xpectMsg = a;
        xpectData = b;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> list() {
        ArrayList a = new ArrayList();
        a.add(new Object[]{"CLNG 12345\r\n", "12345"});
        return a;
    }

    @Test
    public void testGetOperation() throws InstaYakException {
        InstaYakChallenge msg = new InstaYakChallenge("123");
        Assert.assertEquals(xpectOp, msg.getOperation());
    }

    @Test
    public void testToString() throws InstaYakException {
        String xpectToString = "Challenge: Nonce=" + xpectData;
        InstaYakChallenge msg = new InstaYakChallenge(xpectData);
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
    public void testSetNonce() throws InstaYakException, IOException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(xpectMsg.getBytes()));
        InstaYakMessage msg = InstaYakMessage.decode(in);
        Assert.assertEquals(xpectData, ((InstaYakChallenge)msg).getNonce());
    }

    @Test (expected = InstaYakException.class)
    public void testBadSetNonce() throws InstaYakException, IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream("CLNG nonNum\r\n".getBytes());
        MessageInput in = new MessageInput(bIn);
        InstaYakMessage.decode(in);
    }
}