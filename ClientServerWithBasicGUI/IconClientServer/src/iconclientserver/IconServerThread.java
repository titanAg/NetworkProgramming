// Kyle Orcutt 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iconclientserver;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.imageio.ImageIO;

/**
 *
 * @author Kyle
 */
public class IconServerThread extends Thread {
    protected DatagramSocket dsocket = null;
    protected BufferedReader in = null;
    protected boolean running = true;
    
    public IconServerThread() throws IOException {
        super("IconServerThread");
        dsocket = new DatagramSocket(4445);
    }
    
    public void run() {
        while (running) {
            try {
                byte[] inputBuf = new byte[256];               
                System.out.println("Ready for client connection...\n");
                
                // receive request
                DatagramPacket packet = new DatagramPacket(inputBuf, inputBuf.length);
                dsocket.receive(packet);
                                
                // Get client address, port, and image selection
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                int selection = packet.getLength();
                
                System.out.println("Sending image " + selection + " to client...\n");
                
                // Get image and format for transfer to client
                File image = new File("bug" + selection + ".gif");
                BufferedImage imageBuf = ImageIO.read(image);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();        
                ImageIO.write(imageBuf, "gif", outputStream);
                outputStream.flush();
                byte[] outputBuf = outputStream.toByteArray();
                
                // Send response to client
                packet = new DatagramPacket(outputBuf, outputBuf.length, address, port);
                dsocket.send(packet);                
            }
            catch (IOException e) {
                System.out.println(e + "\nServer stopped");
                running = false;
            }
        }
        dsocket.close();
    }
}
