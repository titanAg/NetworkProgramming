//QuoteClient.java
import java.io.*;
import java.net.*;

public class RMIQuoteClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }

        // get a datagram socket
        DatagramSocket dsocket = new DatagramSocket();

        // send request
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        dsocket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        dsocket.receive(packet);

        // display response
        String received = new String(packet.getData());
        System.out.println("Quote of the Moment: " + received);

        dsocket.close();
    }
}
