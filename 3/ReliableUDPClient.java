import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ReliableUDPClient { // For the server we're connecting to

    public static void main(String args[]) {
        DatagramSocket aSocket = null;

        String ip = new String(args[0]);
        int serverPort = Integer.parseInt(args[1]); // 7007
        String input = new String(args[2]);

        // Checks if the input string is maximum 255 characters
        if (input.length() > 255) {
            boolean validInput = false;
            Scanner sc = new Scanner(System.in);
            while (!validInput) {
                System.out.println("String must be less than: 255 characters");
                System.out.print("Enter string: ");
                input = sc.next();
                if (input.length() <= 255)
                    validInput = true;
            }
        }

        try {
            aSocket = new DatagramSocket(8009); // My port for listening to requests

            byte[] msgBytes = new byte[255];

            for (int i = 0; i < input.length(); i++) {
                msgBytes[i] = input.getBytes()[i];
            }

            // Send the message
            InetAddress aHost = InetAddress.getByName(ip);

            try {
                aSocket.connect(new InetSocketAddress(aHost, serverPort));
                System.out.println("Is connected to server");
            } catch (SocketException e) {
                e.getStackTrace();
                System.exit(0);
            }

            DatagramPacket request = new DatagramPacket(msgBytes, msgBytes.length, aHost, serverPort);
            aSocket.send(request);

            System.out.println(aHost);

            // Receive reply
            byte[] buffer = new byte[255]; // Allocate a buffer into which the reply message is written
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);

            // Check Echo
            String respond = new String(reply.getData());
            if (respond.equals(new String(msgBytes))) {
                System.out.println("Correct Transmission");
                System.out.println("Input: " + input);
                System.out.println("Response: " + respond);
            } else {
                System.out.println("Wrong Transmission");
                System.out.println("Should be: " + input);
                System.out.println("Is: " + respond);
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
