package server;


import client.ClientReceive;
import protocol.HelloClient;
import protocol.HelloServer;
import protocol.Welcome;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final String PROTOCOL = "Version 1.0";
    private static final int SERVER_PORT = 1788;

    public static final Logger logger = Logger.getLogger(Server.class.getName());


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(SERVER_PORT);
        logger.log(Level.INFO, new HelloServer("OO", false, PROTOCOL, 0).toString());
        try {
            while(!server.isClosed()) {
                Socket client = server.accept();
                logger.log(Level.INFO, new HelloClient(PROTOCOL).toString());
                new Thread(new ServerThread(client)).start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}