package client;

import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;
import javafx.application.Platform;
import transfer.request.RequestWrapper;


public class ClientReceive extends Thread{
    private Socket socket;
    private Gson gson = new Gson();
    private BufferedReader readInput;
    private BufferedWriter writeOutput;

    public ClientReceive(Socket socket) {
        this.socket = socket;
        try {
            readInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String wrappedAnswer = readInput.readLine();
                RequestWrapper wrappedRequest = new Gson().fromJson(wrappedAnswer, RequestWrapper.class);
                identifyRequest(wrappedRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void identifyRequest(RequestWrapper wrappedRequest) {
        switch (wrappedRequest.getCurrentRequest()){
            case PLAYER_INITIALISATION:
                wrappedRequest.getPlayerInitialisation().handleRequest(socket);
                break;
            case MESSAGE:
                wrappedRequest.getMessage().handleRequest(socket);
                break;
            case ACCEPT_PLAYER:
                wrappedRequest.getAcceptPlayer().handleRequest(socket);
                break;
            case CLOSE:
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.exit();
                break;
        }
    }

    public BufferedReader getReadInput(){return readInput;}

    public BufferedWriter getWriteOutput(){return writeOutput;}

    public Socket getSocket(){return socket;}
}