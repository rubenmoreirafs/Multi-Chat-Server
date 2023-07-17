

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class WebChatServer implements Runnable{

    private ArrayList<Socket> socketList = new ArrayList<>();

    private Socket socket;


    private final int port = 5561;


    public static void main(String[] args) {
        WebChatServer webChatServer = new WebChatServer();
        Thread webChatServerThread = new Thread(webChatServer);
        webChatServerThread.start();
    }

    public void createConnection() {

            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while(true) {
                    System.out.println("Waiting for connection");
                    socket = serverSocket.accept();
                    System.out.println("Connection accepted");
                    socketList.add(socket);
                    System.out.println("Connection sent to the list");
                    new ServerWorker(socket, this);
                    System.out.println("New ServerWorker Created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    public void sendAll(String message) {
        for (Socket socket: socketList) {
            try {
                System.out.println("Message from client received: " + message);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
                out.flush();
                System.out.println("Message sent to everyone");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void run() {
        createConnection();
    }

}


