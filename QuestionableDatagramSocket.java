import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sun.tools.jstat.RawOutputFormatter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class QuestionableDatagramSocket extends DatagramSocket {

    Random random = new Random();

    enum Response {
        DISCARD, DUPLICATE, REORDER, SEND
    }

    QuestionableDatagramSocket() throws SocketException {

        Response[] responses = Response.values();
        int pickResponse = random.nextInt(4);

        switch (responses[pickResponse]) {
            case DISCARD:
                System.out.println("DISCARD");
                break;
            case DUPLICATE:
                System.out.println("DUPLICATE");
                break;
            case REORDER:
                System.out.println("REORDER");
                break;
            case SEND:
                System.out.println("SEND");
                break;
            default:
                break;
        }

    }

    @Override
    public synchronized void receive(DatagramPacket p) throws IOException {
        super.receive(p);
        String s = new String(p.getData());

        // Logic

    }

    @Override
    public void send(DatagramPacket p) throws IOException {
        // TODO Auto-generated method stub
        super.send(p);
    }

    public static void main(String[] args) {
        
        try {
            QuestionableDatagramSocket socket = new QuestionableDatagramSocket();
        } catch (SocketException e) { }

    }

}