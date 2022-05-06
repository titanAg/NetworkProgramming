// COSC 318 Lab 3
// Kyle Orcutt

package DateQuoteServer;

import java.io.*;

public class DateQuoteServer extends Thread {
    public static void main(String[] args) throws IOException {
        new DateQuoteServerThread().start();
    }
}
