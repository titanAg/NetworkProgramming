// Kyle Orcutt 

package reverseserver;

import java.io.*;
import java.net.*;

public class ReverseClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: java SimpleClient <host>");
            System.exit(0);
        }
        Client client = new Client(args[0], 2500);
    }
}

class Client {
    private Socket socket = null;
    private BufferedReader userIn = null;
    private DataInputStream serverIn = null;
    private DataOutputStream output = null;
    
    public Client(String ip, int port) throws IOException {
        try {
           // make connection
           System.out.println("Making connection - ip: " + ip + " & port: " + port);
           socket = new Socket(ip, port);
           System.out.println("Connection established");

           // get input from console
           userIn = new BufferedReader(new InputStreamReader(System.in));
           
           // send output to socket
           output = new DataOutputStream(socket.getOutputStream());
           
           String message = "";           
           while (!message.equals(".")) {
               // Read and send input messages to server
               System.out.println("Enter a message to send to the server or \".\" to exit:");
               message = userIn.readLine();
               output.writeUTF(message);
               
               // Read response from server
               serverIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
               String response = new String(serverIn.readUTF().getBytes());
               System.out.println("Response from server: " + response + "\n");
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
            if (userIn != null)
                userIn.close();
            if (output != null)
                output.close();
        }
    }
    
}
