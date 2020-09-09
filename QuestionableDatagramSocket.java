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

    QuestionableDatagramSocket(int port) throws SocketException {
        super(port);
    }

    @Override
    public void send(DatagramPacket p) throws IOException {

        Response[] responses = Response.values();
        int pickResponse = random.nextInt(4);

        String s = new String(p.getData());
        String[] words = s.split(" ");
        List<String> stringList = new ArrayList<String>(Arrays.asList(words));
        String response = "";

        switch (responses[pickResponse]) {
            case DISCARD:
                String lastWord = stringList.get(stringList.size() - 1);
                response = lastWord;
                System.out.println("DISCARD");
                break;

            case DUPLICATE:
                stringList.add(stringList.get(0));
                System.out.println("DUPLICATE");
                break;

            case REORDER:
                Collections.shuffle(stringList);

                StringBuilder sb = new StringBuilder();
                for (String word : stringList) {
                    sb.append(word + " ");
                }
                response = sb.toString().trim();
                System.out.println("REORDER");
                break;

            case SEND:
                StringBuilder sb2 = new StringBuilder();
                for (String word : stringList) {
                    sb2.append(word + " ");
                }
                response = sb2.toString().trim();
                System.out.println("SEND");
                break;

            default:
                break;
        }

        byte[] msg = response.getBytes();
        byte[] buffer = new byte[1000];

        DatagramPacket outgoing = new DatagramPacket(buffer, buffer.length, p.getAddress(), p.getPort());
        outgoing.setData(msg);
        super.send(outgoing);
    }
}