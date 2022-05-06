import java.net.*;


public class SimpleServer
{
	public static void main(String[] args) throws java.io.IOException {
		ServerSocket server = new ServerSocket(2500);

		while (true) {
			// we block here until there is a client connection
			Socket client = server.accept();

			/**
			 * we have a connection!
			 * Let's get some information about it.
                         * An InetAddress is an IP address
			 */

			// get the server-side info
			System.out.print(InetAddress.getLocalHost() + " : ");
			System.out.println(server.getLocalPort());

			// get the client-side info
			InetAddress ipAddr = client.getInetAddress();
			System.out.print(ipAddr.getHostAddress() + "/" + ipAddr.getHostName() + " : ");
			System.out.println(client.getPort());

			// close the socket
			client.close();
		}
	}
}



