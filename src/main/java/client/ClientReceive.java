package client;

import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;
import javafx.application.Platform;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;
import protocol.SendChat;
import protocol.*;


public class ClientReceive extends Thread{

    private int clientID;
    private Socket socket;
    private Gson gson = new Gson();
    private BufferedReader readInput;
    private BufferedWriter writeOutput;

    private static final String PROTOCOL = "Version 1.0";

    public ClientReceive(Socket socket) {
        this.socket = socket;
        try {
            readInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Message helloServer = new HelloServer("SEP OO",false,PROTOCOL,9);
            String helloServerString= helloServer.toString();
            writeOutput.write(helloServerString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String serverMessage = readInput.readLine();
                Message message = wrapMessage(serverMessage);
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message wrapMessage(String input){
        if(input.contains("HelloClient")){
            return new Gson().fromJson(input, HelloClient.class);
        }
        return new ErrorMessage("Error when parsing String to Message");
    }
    private void identifyMessage(Message message) {
        String messageType=message.getMessageType();
        switch (messageType){
            case MessageType.helloClient:
            case MessageType.welcome:
            case MessageType.playerAdded:
        }
    }

    public BufferedReader getReadInput(){return readInput;}

    public BufferedWriter getWriteOutput(){return writeOutput;}

    public Socket getSocket(){return socket;}

    public int getClientID() {
        return this.clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}