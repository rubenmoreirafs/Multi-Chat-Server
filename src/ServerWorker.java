
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerWorker implements Runnable{

    Socket socket;
    WebChatServer webChatServer;


    public ServerWorker(Socket socket, WebChatServer webChatServer) {
        this.socket = socket;
        this.webChatServer = webChatServer;
        Thread serverWorkerThread = new Thread(this);
        serverWorkerThread.start();

    }

    public void receiveAndSendMessage() {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String answer = in.readLine();
                System.out.println("Message sent to WebChatServer");
                webChatServer.sendAll(answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void run() {
        receiveAndSendMessage();
    }
}
