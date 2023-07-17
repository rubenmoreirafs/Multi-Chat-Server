import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    Socket clientSocket;
    ExecutorService cachedPool = Executors.newCachedThreadPool();


    String name = "Rúben";


    public Client() throws IOException {
        clientSocket = new Socket("192.168.0.133", 6666);
        cachedPool.submit(this::sendMessage);
        cachedPool.submit(this::receiveMessage);
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void receiveMessage()  {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String answer = in.readLine();
                System.out.println(answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage()  {
        while(true) {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String answer = br.readLine();
                out.write(answer + "\n");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
