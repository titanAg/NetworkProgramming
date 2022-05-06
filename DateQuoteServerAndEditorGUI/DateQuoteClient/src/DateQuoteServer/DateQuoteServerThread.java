// COSC 318 Lab 3
// Kyle Orcutt

package DateQuoteServer;

//QuoteServer.java
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class DateQuoteServerThread extends Thread {

    protected DatagramSocket dsocket = null;
    protected BufferedReader in = null;
    protected boolean running = true;

    public DateQuoteServerThread() throws IOException {
	this("DateQuoteServerThread");
    }

    public DateQuoteServerThread(String name) throws IOException {
        super(name);
        dsocket = new DatagramSocket(4445);

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {

        while (running) {
            try {
                
                System.out.println("Ready for client connection...\n");
                
                // receive request
                byte[] inBuf = new byte[256];
                DatagramPacket packet = new DatagramPacket(inBuf, inBuf.length);
                dsocket.receive(packet);
                String request = new String(packet.getData(), packet.getOffset(), packet.getLength());
                                
                // Get client address, port, and quote selection
                InetAddress address = packet.getAddress();
                int port = packet.getPort();               
                
                System.out.println("Request for: " + request + " received from via: " + address + " port: " + port);
                
                // Determine and convert response
                byte[] outBuf = new byte[256];
                String output = new Date().toString();
                if (request.equals("quote") && in != null)
                    output = getNextQuote();
                outBuf = output.getBytes();

                // send the response to the client at "address" and "port"
                packet = new DatagramPacket(outBuf, outBuf.length, address, port);
                dsocket.send(packet);
                
            } catch (IOException e) {
                e.printStackTrace();
		running = false;
            }
        }
        dsocket.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
		running = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}
