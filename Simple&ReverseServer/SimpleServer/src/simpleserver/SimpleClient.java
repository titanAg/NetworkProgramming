// Kyle Orcutt 

package simpleserver;
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
                
                // output server localhost 
                System.out.println("Server side:");
                System.out.print(InetAddress.getLocalHost() + " : ");
                System.out.println(sock.getPort() + "\n");

                
                System.out.println("Client side:");
                InetAddress ipAddr = sock.getInetAddress();
                System.out.print(ipAddr.getHostAddress() + "/" + ipAddr.getHostName() + " : ");
                System.out.println(sock.getLocalPort() + "\n");

		// close the socket
		sock.close();
	}
}



