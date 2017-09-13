package instayak.app;

import instayak.serialization.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static java.lang.System.exit;

/*******************************************************************************
 * Created by Justin Ritter on 2/14/2017.
 * Project is InstaYak in package instayak.app
 ******************************************************************************/
public class InstaYakClient {
  //string literals
  //instaYak messages
  private static final String instaYakversion = "INSTAYAK";
  private static final String instaYakclng = "CLNG";
  private static final String instaYakerror = "ERROR";
  private static final String instaYakack = "ACK";
  //problem messages
  private static final String parameters =
      "Parameters: <sever> <port> <userid> <password>";
  private static final String validation = "Validation failed: ";
  private static final String communicationProb = "Unable to communicate: ";
  private static final String invalidMsg = "Invalid message: ";
  private static final String unxpectMsg = "Unexpected message: ";
  private static final String MD5Prob = "Unable to get MD5";
  private static final String error = "Error: ";
  //values for something or another
  private static final String MD5 = "MD5";
  private static final String iso8859_1 = "ISO8859_1";
  private static final String option1uon = "^\\s*[uU][oO][nN]\\s*$|";
  private static final String option1slmd = "^\\s*[sS][lL][mM][dD]\\s*$";
  private static final String option2yn = "^\\s*[yY]\\s*$|^\\s*[nN]\\s*$";
  private static final String option2n = "^\\s*[nN]\\s*$";
  //Menu messages
  private static final String menuOptions = "[UOn, SLMD]> ";
  private static final String menuCat = "Category> ";
  private static final String menuFile = "Image Filename> ";
  private static final String menuCont = "Continue (Y/N)> ";
  private static final String menuError = "Error: unknown option";

  /**
   * Main program
   * @param args command line input
   */
  public static void main(String args[]) {
    //check for correct parameters
    if (args.length < 3 || args.length > 4) {
      throw new IllegalArgumentException(parameters);
    }

    //grabs the server and port from command line
    String server = args[0];
    int servPort = (args.length == 4 ? Integer.parseInt(args[1]) : 7);
    String password = args[3];

    //try block creates a instayakID with the id given on the command line
    InstaYakID userID = null;
    try {
      userID = new InstaYakID(args[2]);
    }
    catch (InstaYakException e) {
      System.out.println(validation + e.getMessage());
      exit(0);
    }

    //creates the socket connection to the server
    try (Socket socket = new Socket(server, servPort)) {
      MessageInput msgIn = new MessageInput(socket.getInputStream());
      MessageOutput msgOut = new MessageOutput(socket.getOutputStream());

      //reads first initial message from server
      InstaYakMessage newMsg;

      boolean contConnection = true;
      int loop = 0;
      while (contConnection) {
        //reads in and then prints every message
        // from server to the screen
        newMsg = InstaYakMessage.decode(msgIn);
        System.out.println(newMsg.toString());

        //check for error response
        if (newMsg.getOperation().equals(instaYakerror)) {
          System.out.println(error + ((InstaYakError) newMsg).getMessage());
          exit(0);
        }

        switch (loop) {
          //version case
          case 0:
            if (!newMsg.getOperation().equals(instaYakversion)) {
              unexpectedMsg(newMsg);
            } else {
              //sends id from commandline to server
              userID.encode(msgOut);
              loop++;
            }
            break;
          //challenge case
          case 1:
            if (!newMsg.getOperation().equals(instaYakclng)) {
              unexpectedMsg(newMsg);
            } else {
              //computes hash
              String hashedStr = computeHash
                  (((InstaYakChallenge)newMsg).getNonce() + password);
              //sends hash to server
              new InstaYakCredentials(hashedStr).encode(msgOut);
              loop++;
            }
            break;
          //menu case
          case 2:
            if(!newMsg.getOperation().equals(instaYakack)) {
              unexpectedMsg(newMsg);
            }
            else {
              contConnection = menu(msgOut);
            }
            break;
        }
      }
      //closes the socket
      socket.close();
    }
    catch (IOException e) {
      System.out.println(communicationProb + e.getMessage());
      exit(0);
    }
    catch (InstaYakException e) {
      System.out.println(invalidMsg + e.getMessage());
      exit(0);
    }
  }

  /**
   * Handles all user prompts and requests
   * @param msgOut the output socket to send encoded InstaYakMessages
   * @return a boolean if the user wishes to continue
   * @throws InstaYakException if validation fails
   * @throws IOException I/O problem
   */
  private static boolean menu(MessageOutput msgOut)
      throws InstaYakException, IOException {
    Scanner scn = new Scanner(System.in);
    String userInput = scn.next();
    boolean correctInput = false;
    //prompts for uon/slmd
    System.out.println(menuOptions);
    do {
      if(userInput.matches(option1uon)) {
        String userCat, userFile;
        //prompts for category
        System.out.println(menuCat);
        userCat = scn.next();
        //prompts for iamge filename
        System.out.println(menuFile);
        userFile = scn.next();
        //sends UOn to server
        new InstaYakUOn(userCat, userFile.getBytes()).encode(msgOut);
        correctInput = true;
      }
      else if(userInput.matches(option1slmd)) {
        //sends SLMD to server
        new InstaYakSLMD().encode(msgOut);
        correctInput = true;
      }
      else {
        System.out.println(menuError);
      }
    } while(!correctInput);

    System.out.println(menuCont);
    while(!(userInput = scn.next()).matches(option2yn)) {
      System.out.println(menuError);
    }

    if(userInput.matches(option2n)) {
      return false;
    }
    return true;
  }

  /**
   * Prints to the screen a instaYakMessage
   * @param msg the instaYakMeassge
   */
  private static void unexpectedMsg(InstaYakMessage msg) {
    System.out.println(unxpectMsg + msg.getOperation());
  }

  /**
   * Computes the hash string of the inputed string
   * @param msg string to hash
   * @return the hashed string
   * @throws UnsupportedEncodingException unknown encoding
   */
  private static String computeHash(String msg)
      throws UnsupportedEncodingException {
    try {
      MessageDigest md5 = MessageDigest.getInstance(MD5);
      byte[] buf = md5.digest(msg.getBytes(iso8859_1));
      return hashToString(buf);
    }
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(MD5Prob, e);
    }
  }

  /**
   * Converts the hashed message into a string
   * @param bytes bytes to convert to string
   * @return the hashed string
   */
  private static String hashToString(byte[] bytes) {
    String hexHash = "";
    for (byte b : bytes) {
      String v = Integer.toHexString(Integer.valueOf(b & 0xff));
      if (v.length() == 1)
        v = "0" + v;
      hexHash += v.toUpperCase();
    }
    return hexHash;
  }
}
