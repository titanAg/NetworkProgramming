import java.net.*;


public class SimpleClient
{
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("usage: java SimpleClient <host>");
			System.exit(0);
		}

		// host may be either IP name or IP address
		Socket sock = new Socket(args[0], 2500);

		/**
		 * At this point we could get an stream (input and/or output)
		 * to communicate with the other end of the socket.
		 */

		// close the socket
		sock.close();
	}
}



