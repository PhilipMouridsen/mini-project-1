import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ReliableUDPClient {
    private static int serverPort = 7007; // For the server we're connecting to.

    public static void main(String args[]) {
        DatagramSocket aSocket = null;

        String ip = new String(args[0]);
        int serverPort = Integer.parseInt(args[1]); // 7007
        String input = new String(args[2]);

        try {
            aSocket = new DatagramSocket(8009); // My port for listening to requests.

            byte[] msgBytes = input.getBytes();

            // Send the message
            InetAddress aHost = InetAddress.getByName(ip); // Philip "10.26.10.251"
            // InetAddress aHost = InetAddress.getByName("10.26.15.161");

            try {
                aSocket.connect(new InetSocketAddress(aHost, serverPort));
                System.out.println("is connected to server");
            } catch (SocketException e) {
                e.getStackTrace();
                System.exit(0);
            }

            DatagramPacket request = new DatagramPacket(msgBytes, msgBytes.length, aHost, serverPort);
            aSocket.send(request);

            System.out.println(aHost);

            // Receive reply
            byte[] buffer = new byte[1000]; // Allocate a buffer into which the reply message is written
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);

            // Check Echo
            String respond = new String(reply.getData());
            if(respond.equals(input)){
                System.out.println("Correct transmission");
                System.out.println("Input: " + input + ", respond: " + respond);
            } else {
                System.out.println("Wrong Transmission");
                System.out.println("Should be: " + input + " is: " + respond);
            }

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
