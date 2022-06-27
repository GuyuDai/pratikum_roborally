package client;

import com.google.gson.JsonObject;
import protocol.Welcome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author Felicia Saruba
 */

public class Client {
    private static final int SERVER_PORT = 1788;
    private static final String SERVER_IP = "localhost";

    private static ClientReceive clientReceive;

    public static ClientReceive getClientReceive() {
        return clientReceive;
    }

    public void init() {
        try {
            //Build client with the local host
            Socket client = new Socket(SERVER_IP, SERVER_PORT);
            //Start thread for receiving message from server.
            clientReceive = new ClientReceive(client);
            System.out.println(new Welcome(42));
            clientReceive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




        //public void sendJsonMsg (JsonObject jsonObject) {
        //  try {
        //    OutputStream out = clientReceive
        //}
        //}
        //@Override
        //public void run() {

        //}
    }
