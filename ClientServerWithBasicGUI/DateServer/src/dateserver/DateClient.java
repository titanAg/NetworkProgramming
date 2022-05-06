// Kyle Orcutt 

package dateserver;

import java.net.*;
import java.io.*;

public class DateClient
{
	// the default port
	public static final int PORT = 6013;
	// this could be replaced with an IP address or IP name
	public static final String host = "localhost";
        
        public static final int BUFFER_SIZE = 256;

	public static void main(String[] args) throws java.io.IOException {
		BufferedInputStream fromServer = null;
                byte[] buffer = new byte[BUFFER_SIZE];
		Socket server = null;

		try {
                        debug("Sending request to server...\n");
			// create socket and connect to default port
			server = new Socket(host, PORT);

			// read the date and close the socket
			fromServer = new BufferedInputStream(server.getInputStream());
                        debug("Message received from server: ");
			String line = "";
                        int numBytes;
			while ((numBytes = fromServer.read(buffer)) > 0) {
                            line += new String(buffer,0,numBytes); 
                        }
                        System.out.print(line);
			// closing the input stream closes the socket as well
			fromServer.close();
		} catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			// let's close streams and sockets
			if (fromServer!= null)
				fromServer.close();
			if (server != null)
				server.close();
		}
	}
        
        private static void debug(String msg) {
            System.out.println(msg);
        }
}
