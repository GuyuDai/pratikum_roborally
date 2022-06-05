package client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int SERVER_PORT =1788;
    private static final String SERVER_IP = "localhost";

    private static ClientReceive clientReceive;

    public static ClientReceive getClientReceive(){return clientReceive;}
    public void init() {
        try {
            //Build client with the local host
            Socket client = new Socket(SERVER_IP, SERVER_PORT);
            //Start thread for receiving message from server.
            clientReceive = new ClientReceive(client);
            clientReceive.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}