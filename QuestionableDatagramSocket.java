import java.util.*;
import java.io.IOException;
import java.net.*;

public class QuestionableDatagramSocket extends DatagramSocket {

    private Random random = new Random();

    enum Response {
        DISCARD, DUPLICATE, REORDER, SEND
    }

    QuestionableDatagramSocket() throws SocketException{
        super();
    }

    QuestionableDatagramSocket(int i) throws SocketException {
        super(i);

    }

    @Override
    public synchronized void receive(DatagramPacket p) throws IOException {
        super.receive(p);
        String s = new String(p.getData());
        
        // Logic
        System.out.println("QuestionableDatagramSocket: " + s);

    }

    @Override
    public void send(DatagramPacket p) throws IOException {
        // TODO Auto-generated method stub
        super.send(p);

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

    public static void main(String[] args) {
        
        try {
            QuestionableDatagramSocket socket = new QuestionableDatagramSocket();
        } catch (SocketException e) { }

    }

}