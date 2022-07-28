package client;

import com.google.gson.JsonObject;
import protocol.HelloClient;
import protocol.Welcome;
import server.Server;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {

    public static final Logger logger = Log.logFile("ClientLog");

    private static final int SERVER_PORT = 1788;
    private static final String SERVER_IP = "localhost";

    private static ClientReceive clientReceive;

    public static ClientReceive getClientReceive() {
        return clientReceive;
    }

    /**
     * @author: Li
     * initialize Client
     */
    public void init() {
        try {
            //Build client with the local host
            Socket client = new Socket(SERVER_IP, SERVER_PORT);
            //Start thread for receiving message from server.
            clientReceive = new ClientReceive(client);
            clientReceive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(){
        return logger;
    }


}
