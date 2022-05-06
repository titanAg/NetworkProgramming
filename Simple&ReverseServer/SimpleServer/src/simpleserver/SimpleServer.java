// Kyle Orcutt 

package simpleserver;
import java.net.*;

public class SimpleServer
{
	public static void main(String[] args) throws java.io.IOException {
		ServerSocket server = new ServerSocket(2500);

		while (true) {
			// we block here until there is a client connection
			Socket client = server.accept();

			// get the server-side
                        System.out.println("Server side:");
			System.out.print(InetAddress.getLocalHost() + " : ");
			System.out.println(server.getLocalPort() + "\n");

			// get the client-side info
                        System.out.println("Client side:");
			InetAddress ipAddr = client.getInetAddress();
			System.out.print(ipAddr.getHostAddress() + "/" + ipAddr.getHostName() + " : ");
			System.out.println(client.getPort() + "\n");

			// close the socket
			client.close();
		}
	}
}



