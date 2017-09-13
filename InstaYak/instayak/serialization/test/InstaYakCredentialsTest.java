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
 * Created by Justin Ritter on 1/30/2017.
 * Project is InstaYak in package instayak.serialization.test
 **********************************************************************************/
@RunWith(Parameterized.class)
public class InstaYakCredentialsTest {
    protected String xpectOp = "CRED";
    protected String xpectData;
    protected String xpectMsg;

    public InstaYakCredentialsTest(String a, String b) {
        xpectMsg = a;
        xpectData = b;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> list() {
        ArrayList a = new ArrayList();
        a.add(new Object[]{"CRED 12345123451234512345123451234512345\r\n", "12345123451234512345123451234512345"});
        a.add(new Object[]{"CRED ABCDEABCDEABCDEABCDEABCDEABCDEABCDE\r\n", "ABCDEABCDEABCDEABCDEABCDEABCDEABCDE"});
        a.add(new Object[]{"CRED ABCDEABCDEABCDE12345123451234512345\r\n", "ABCDEABCDEABCDE12345123451234512345"});
        return a;
    }

    @Test
    public void testGetOperation() throws InstaYakException {
        InstaYakCredentials msg = new InstaYakCredentials("123456789123456789123456789123456");
        Assert.assertEquals(xpectOp, msg.getOperation());
    }

    @Test
    public void testToString() throws InstaYakException {
        String xpectToString = "Credential: Hash=" + xpectData;
        InstaYakCredentials msg = new InstaYakCredentials(xpectData);
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
    public void testSetCRED() throws InstaYakException, IOException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(xpectMsg.getBytes()));
        InstaYakMessage msg = InstaYakMessage.decode(in);
        Assert.assertEquals(xpectData, ((InstaYakCredentials)msg).getHash());
    }

    @Test (expected = InstaYakException.class)
    public void testBadSetCRED() throws InstaYakException, IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream("CRED 13245asdf\r\n".getBytes());
        MessageInput in = new MessageInput(bIn);
        InstaYakMessage.decode(in);
    }
}