// Kyle Orcutt 

package quoteserver1;

//QuoteClient.java
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuoteClient1 {
    
    public static void main(String[] args) throws IOException, InputMismatchException {
        if (args.length != 1) {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }
        
        // Get valid input from keyboard
        Scanner input = new Scanner(System.in);
        int quoteCount = 0;
        while (quoteCount < 1 || quoteCount > 10) {
            try {
                System.out.println("Please select a number of quotes: 1-9");
                quoteCount = input.nextInt();
                if (quoteCount < 1 || quoteCount > 9)
                    throw new InputMismatchException();
            }
            catch (InputMismatchException eN) {                              
                System.out.println("Error, invalid number provided. Please try again...");
                quoteCount = 0;
                input.nextLine();
            }
        }
        
        System.out.println("Requesting " + quoteCount + " quotes from server...\n");
        
        // Convert input for transfer to network
        ByteBuffer b = ByteBuffer.allocate(quoteCount);
        byte[] buf = new byte[256];
        buf = b.array();
        
        // Get a datagram socket
        DatagramSocket dsocket = new DatagramSocket();

        // Send request to server
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        dsocket.send(packet);
        
        for (int i = 0; i < quoteCount; i++) {
            buf = new byte[256];
            // get response
            packet = new DatagramPacket(buf, buf.length);
            dsocket.receive(packet);

            // display response
            String received = new String(packet.getData());
            System.out.println("Quote of the Moment: " + received + "\n");
        }
        
        dsocket.close();            
    }
}
