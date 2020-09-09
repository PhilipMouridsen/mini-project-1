import java.net.*;
import java.io.*;

public class UDPServer {

    public static void main(String args[]) {
        int serverPort = 7007;
        QuestionableDatagramSocket serverSocket = null;

        byte[] buffer = new byte[1000];
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

        try {
            serverSocket = new QuestionableDatagramSocket(serverPort);
            System.out.println("UDP echo server on port " + serverPort);

            while (true) {

                // Listening
                System.out.println("Listening");
                serverSocket.receive(incoming);
                System.out.println("Received from client: " + new String(incoming.getData()));
                serverSocket.send(incoming);
            }
        } catch (SocketException e) { // Handle socket errors
            System.out.println("Socket exception: " + e.getMessage());
        } catch (IOException e) { // Handle IO errors
            System.out.println("IO exception: " + e.getMessage());
        } finally { // Close socket
            if (serverSocket != null)
                serverSocket.close();
        }
    }
}
