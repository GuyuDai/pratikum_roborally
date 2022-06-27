package server;


import client.ClientReceive;
import protocol.HelloClient;
import protocol.HelloServer;
import protocol.Welcome;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String PROTOCOL = "Version 1.0";
    private static final int SERVER_PORT = 1788;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(SERVER_PORT);
        System.out.println(new HelloServer("OO", false, PROTOCOL, 0));
        try {
            while(!server.isClosed()) {
                Socket client = server.accept();
                System.out.println(new HelloClient(PROTOCOL));
                new Thread(new ServerThread(client)).start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}