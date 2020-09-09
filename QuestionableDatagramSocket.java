import java.util.*;
import java.io.IOException;
import java.net.*;

public class QuestionableDatagramSocket extends DatagramSocket {

    private Random random = new Random();

    enum Response {
        DISCARD, DUPLICATE, REORDER, SEND
    }

    QuestionableDatagramSocket() throws SocketException {
        super();
    }

    QuestionableDatagramSocket(int i) throws SocketException {
        super(i);
    }

    @Override
    public void send(DatagramPacket p) throws IOException {
        // TODO Auto-generated method stub
        Response[] responses = Response.values();
        int pickResponse = random.nextInt(4);

        String s = new String(p.getData());
        String[] words = s.split(" ");
        String respond = new String();

        switch (responses[pickResponse]) {
            case DISCARD:
                System.out.println("DUPLICATE");
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

        byte[] msg = s.getBytes();
        p.setData(msg);
        super.send(p);
    }

}