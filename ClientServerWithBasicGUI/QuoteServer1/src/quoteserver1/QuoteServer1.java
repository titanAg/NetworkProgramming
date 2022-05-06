// Kyle Orcutt 

package quoteserver1;

//QuoteServer.java
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuoteServer1 {
    public static void main(String[] args) throws IOException {
        new QuoteServerThread().start();
    }
}

class QuoteServerThread extends Thread {

    protected DatagramSocket dsocket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public QuoteServerThread() throws IOException {
	this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        dsocket = new DatagramSocket(4445);

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {

        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];
                
                System.out.println("Ready for client connection...\n");
                
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                dsocket.receive(packet);
                                
                // Get client address, port, and quote selection
                InetAddress address = packet.getAddress();
                int port = packet.getPort();               
                int quoteCount = packet.getLength();
                
                System.out.println("Quote request received from ip: " + address + " port: " + port);
                System.out.println("Sending " + quoteCount + " quote(s) to client...\n");
                
                for (int i = 0; i < quoteCount; i++) {
                    // Determine and convert response 
                    String dString = (in == null) ? new Date().toString() : getNextQuote();
                    buf = dString.getBytes();

                    // send the response to the client at "address" and "port"
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    dsocket.send(packet);
                    
                    // Sleep for 5 seconds between quotes
                    if (quoteCount > 1)
                        Thread.sleep(5000);
                } 
                
            } catch (IOException e) {
                e.printStackTrace();
		moreQuotes = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(QuoteServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dsocket.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
		moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}

