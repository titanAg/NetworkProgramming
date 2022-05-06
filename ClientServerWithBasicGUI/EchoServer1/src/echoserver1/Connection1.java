// Kyle Orcutt 

package echoserver1;

/**
 * This is the separate thread that services each
 * incoming echo client request.
 */

import java.net.*;
import java.io.*;

public class Connection1 implements Runnable
{
	public Connection1(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
		handler.process(client);
        }
        catch (IOException e) {
        	e.printStackTrace();
    }
	}




	private Socket	client;
	private Handler handler = new Handler();
}

class Handler
{
        public static final int BUFFER_SIZE = 256;

	/**
	 * this method is invoked by a separate thread
	 */
	public void process(Socket client) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		String line;
		InputStream fromClient = null;
		OutputStream toClient = null;

		try {
			/**
			 * get the input and output streams associated with the socket.
			 */
			fromClient = new BufferedInputStream(client.getInputStream());
			toClient = new BufferedOutputStream(client.getOutputStream());
			int numBytes;

            /** continually loop until the client closes the connection */
			while ( (numBytes = fromClient.read(buffer)) > 0) {
                                toClient.write(buffer,0,numBytes);
				toClient.flush();

			}

        }
		catch (IOException ioe) {
			System.err.println(ioe);
		}
        finally {
                    // close streams and socket
                    if (fromClient != null)
                        fromClient.close();
                    if (toClient != null)
                        toClient.close();
                    if (client != null)
                        client.close();
                }
	}
}


