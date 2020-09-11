
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {
    private static int serverPort = 7007; // For the server we're connecting to.

    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        Scanner msgScan = new Scanner(System.in);

        while (true) { // Keep asking user for messages.
            try {

                aSocket = new DatagramSocket(8009); // My port for listening to requests.
                
                // Read a message from standard input and convert to byte array
                String msg = msgScan.nextLine();
                byte[] msgBytes = msg.getBytes();

                // Send the message
                InetAddress aHost = InetAddress.getByName("localhost");
                // InetAddress aHost = InetAddress.getByName("10.26.15.161");

                DatagramPacket request = new DatagramPacket(msgBytes, msgBytes.length, aHost, serverPort);
                aSocket.send(request);

                System.out.println(aHost);

                // Receive reply
                byte[] buffer = new byte[1000]; // Allocate a buffer into which the reply message is written
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);

                // Print reply message
                System.out.println("Echoed from server: " + new String(reply.getData()));

            } catch (SocketException e) { // Handle socket errors
                System.out.println("Socket exception: " + e.getMessage());
            } catch (IOException e) { // Handle IO errors
                System.out.println("IO exception: " + e.getMessage());
            } finally { // Close socket
                if (aSocket != null)
                    aSocket.close();
            }
        }
    }
}
