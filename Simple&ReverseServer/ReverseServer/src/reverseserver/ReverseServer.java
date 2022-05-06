// Kyle Orcutt  

package reverseserver;

import java.io.*;
import java.net.*;

public class ReverseServer {
    public static void main(String[] args) throws java.io.IOException {
        Server server = new Server(2500);
    }
}

class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    
    public Server(int port) throws IOException {
        try {
            // initialize server
            server = new ServerSocket(port);
            System.out.println("Server started");
            
            while (true) {
                // wait until client connects
                System.out.println("Ready for client..."); 
                socket = server.accept();
                System.out.println("Client connected\n");
                
                String message = "";
                while (!message.equals(".")) {
                    System.out.println("Ready for client message...\n");
                    
                    // get input from client socket
                    input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    
                    // send output to socket
                    output = new DataOutputStream(socket.getOutputStream());
                    
                    // Parse and output message
                    message = new String(input.readUTF().getBytes());
                    System.out.println("Message received from client: \"" + message + "\"");
                    
                    // Reverse message with StringBuilder and output 
                    String rMessage = new StringBuilder(message).reverse().toString();
                    
                    // send message back to client
                    output.writeUTF(rMessage);
                    System.out.println("Message sent to client: \"" + rMessage + "\"\n");
                }
                System.out.println("Client disconnected\n");
            }      
        }
        catch (IOException e) {
            System.out.println(e);
        }
        finally {
            // close connection 
            System.out.println("Closing connection"); 
            if (socket != null)
                socket.close();
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        }
    }
}
