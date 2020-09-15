import java.net.*;
import java.io.*;

public class Estimator {
    private static int serverPort = 7007; // For the server we're connecting to

    public static void main(String args[]) {
        DatagramSocket aSocket = null;

        final int size = Integer.parseInt(args[0]);
        final int number = Integer.parseInt(args[1]);
        final int timeout = Integer.parseInt(args[2]);

        int received = 0;

        try {

            aSocket = new DatagramSocket(8009); // My port for listening to requests
            aSocket.setSoTimeout(timeout);

            // InetAddress aHost = InetAddress.getByName("localhost");
            InetAddress aHost = InetAddress.getByName("10.26.31.224");

            byte[] msgBytes = new byte[size];

            // Send the message
            int counter = 0;
            while (counter < number) {
                counter++;

                DatagramPacket request = new DatagramPacket(msgBytes, msgBytes.length, aHost, serverPort);
                aSocket.send(request);

                System.out.println(aHost);

                // Receive reply
                byte[] buffer = new byte[1000]; // Allocate a buffer into which the reply message is written
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                try {
                    aSocket.receive(reply);
                    received++;
                } catch (SocketTimeoutException e) {
                    System.out.println("Timed out, continue...");
                }
            }

        } catch (SocketException e) { // Handle socket errors
            System.out.println("Socket exception: " + e.getMessage());
        } catch (IOException e) { // Handle IO errors
            System.out.println("IO exception: " + e.getMessage());
        } finally { // Close socket
            if (aSocket != null)
                aSocket.close();
            System.out.println("Sent: " + number);
            double percentage = (double) (received * 100) / number;
            System.out.println("Received: " + received + " (" + percentage + " %)" );
        }
    }
}