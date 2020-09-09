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
    public void send(DatagramPacket p) throws IOException {
        // TODO Auto-generated method stub
        Response[] responses = Response.values();
        int pickResponse = random.nextInt(4);
        
        String s = new String(p.getData());
        String[] words = s.split(" ");
        String response = "";

        switch (responses[pickResponse]) {
            case DISCARD:
                int index = words.length-1;
                response += words[index];
                System.out.println("DISCARD");
                break;
            case DUPLICATE:
                for(int j=0; j<words.length; j++){
                    response += words[j] + " ";
                    if(j==0) response += words[j] + " ";
                };
                System.out.println("DUPLICATE");
                break;
            case REORDER:
                int[] indexes = new int[words.length];
                for(int j=0; j<indexes.length; j++)
                    indexes[j] = random.nextInt(words.length-1);
                for(int j=0; j<words.length; j++)
                    response += words[indexes[j]] + " ";
                System.out.println("REORDER");
                break;
            case SEND:
                for(int j=0; j<words.length; j++)
                    response += words[j] + " ";
                System.out.println("SEND");
                break;
            default:
                break;
        }

        byte[] msg = response.getBytes();
        p.setData(msg);
        super.send(p);
    }
}