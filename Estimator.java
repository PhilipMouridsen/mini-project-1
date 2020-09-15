import java.net.*;
import java.io.*;

public class Estimator {
    private static int serverPort = 7007; // For the server we're connecting to

    public static void main(String args[]) {
        DatagramSocket aSocket = null;

        final int size = Integer.parseInt(args[0]);
        final int totalNumber = Integer.parseInt(args[1]);
        final int timeout = Integer.parseInt(args[2]);

        int received = 0;
        int duplicate = 0;
        int reordered = 0;

        try {
            aSocket = new DatagramSocket(8010); // My port for listening to requests
            aSocket.setSoTimeout(timeout);

            //InetAddress aHost = InetAddress.getByName("localhost");
            InetAddress aHost = InetAddress.getByName("10.26.31.224");

            byte[] msgBytes = new byte[size];

            // Send the message
            String msg = "Hello World";
            msgBytes = msg.getBytes();
            
            int counter = 0;
            while (counter < totalNumber) {
                counter++;

                DatagramPacket request = new DatagramPacket(msgBytes, msgBytes.length, aHost, serverPort);
                aSocket.send(request);

                System.out.println(aHost);

                // Receive reply
                byte[] buffer = new byte[1000]; // Allocate a buffer into which the reply message is written
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                
                try {
                    aSocket.receive(reply);
                    String response = new String(reply.getData());
                    System.out.println(response);
                    //compare the send and receieved string
                    String[] sendMsg = msg.split(" ");
                    String[] responseMsg = response.split(" ");

                    //Check for duplicated replies
                    if(sendMsg.length < responseMsg.length){
                        duplicate++;
                        System.out.println("Duplicate:");
                        for(int i=0; i<responseMsg.length; i++) System.out.print(responseMsg[0] + " ");
                        System.out.println();
                    }
                        
                    //Check for reordered replies
                    else if((sendMsg.length == responseMsg.length) && (!sendMsg[0].equals(responseMsg[0]))){
                        reordered++;
                        System.out.println("Reordered:");
                        for(int i=0; i<responseMsg.length; i++) System.out.print(responseMsg[0] + " ");
                        System.out.println();
                    }
                    
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
            printResults(size, totalNumber, received, timeout, reordered, duplicate);
        }
    }

    private static void printResults(int size, int totalNumber, int received, int timeout, int reordered, int duplicate){
        System.out.println("Size per packet: " + size + ", timeout: " + timeout + "ms");
        System.out.println("Amount sent: " + totalNumber);
        double percentage = (double) (received * 100) / totalNumber;
        System.out.println("Amount received: " + received + " (" + percentage + " %)" );
        percentage = (double) (duplicate * 100) / totalNumber;
        System.out.println("Amount duplicates: " + duplicate + " (" + percentage + " %)" );
        percentage = (double) (reordered * 100) / totalNumber;
        System.out.println("Amount reordered: " + reordered + " (" + percentage + " %)");
    }
}