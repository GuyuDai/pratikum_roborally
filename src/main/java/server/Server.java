package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 1788;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(SERVER_PORT);
        System.out.println("Server starts");
        try {
            while(!server.isClosed())
            {
                Socket client = server.accept();
                new Thread(new ServerThread(client)).start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}