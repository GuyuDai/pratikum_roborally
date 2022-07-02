package client;

import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;
import java.util.Set;
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
            System.out.println(helloServerString);
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
                System.out.println(serverMessage);  //test
                Message message = wrapMessage(serverMessage);
                System.out.println("--------------------------------------------------------------");  //test
                System.out.println(message);  //test
                //Message message = new Gson().fromJson(serverMessage, Message.class);
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message wrapMessage(String input){
        if(input.contains("\"messageType\": \"ActivePhase\",\n")){
            return new Gson().fromJson(input, ActivePhase.class);
        }
        if(input.contains("\"messageType\": \"Alive\",\n")){
            return new Gson().fromJson(input, Alive.class);
        }
        if(input.contains("\"messageType\": \"Animation\",\n")){
            return new Gson().fromJson(input, Animation.class);
        }
        if(input.contains("\"messageType\": \"CardPlayed\",\n")){
            return new Gson().fromJson(input, CardPlayed.class);
        }
        if(input.contains("\"messageType\": \"CardSelected\",\n")){
            return new Gson().fromJson(input, CardSelected.class);
        }
        if(input.contains("\"messageType\": \"CardsYouGotNow\",\n")){
            return new Gson().fromJson(input, CardsYouGotNow.class);
        }
        if(input.contains("\"messageType\": \"CheckPointReached\",\n")){
            return new Gson().fromJson(input, CheckPointReached.class);
        }
        if(input.contains("\"messageType\": \"ClientMessage\",\n")){
            return new Gson().fromJson(input, ClientMessage.class);
        }
        if(input.contains("\"messageType\": \"ConnectionUpdate\",\n")){
            return new Gson().fromJson(input, ConnectionUpdate.class);
        }
        if(input.contains("\"messageType\": \"CurrentCards\",\n")){
            return new Gson().fromJson(input, CurrentCards.class);
        }
        if(input.contains("\"messageType\": \"CurrentPlayer\",\n")){
            return new Gson().fromJson(input, CurrentPlayer.class);
        }
        if(input.contains("\"messageType\": \"DrawDamage\",\n")){
            return new Gson().fromJson(input, DrawDamage.class);
        }
        if(input.contains("\"messageType\": \"Energy\",\n")){
            return new Gson().fromJson(input, Energy.class);
        }
        if(input.contains("\"messageType\": \"Error\",\n")){
            return new Gson().fromJson(input, ErrorMessage.class);
        }
        if(input.contains("\"messageType\": \"GameFinished\",\n")){
            return new Gson().fromJson(input, GameFinished.class);
        }
        if(input.contains("\"messageType\": \"GameStarted\",\n")){
            return new Gson().fromJson(input, GameStarted.class);
        }
        if(input.contains("\"messageType\": \"HelloClient\",\n")){
            return new Gson().fromJson(input, HelloClient.class);
        }
        if(input.contains("\"messageType\": \"HelloServer\",\n")){
            return new Gson().fromJson(input, HelloServer.class);
        }
        if(input.contains("\"messageType\": \"MapSelected\",\n")){
            return new Gson().fromJson(input, MapSelected.class);
        }
        if(input.contains("\"messageType\": \"Movement\",\n")){
            return new Gson().fromJson(input, Movement.class);
        }
        if(input.contains("\"messageType\": \"NotYourCards\",\n")){
            return new Gson().fromJson(input, NotYourCards.class);
        }
        if(input.contains("\"messageType\": \"PickDamage\",\n")){
            return new Gson().fromJson(input, PickDamage.class);
        }
        if(input.contains("\"messageType\": \"PlayCard\",\n")){
            return new Gson().fromJson(input, PlayCard.class);
        }
        if(input.contains("\"messageType\": \"PlayerAdded\",\n")){
            return new Gson().fromJson(input, PlayerAdded.class);
        }
        if(input.contains("\"messageType\": \"PlayerStatus\",\n")){
            return new Gson().fromJson(input, PlayerStatus.class);
        }
        if(input.contains("\"messageType\": \"PlayerTurning\",\n")){
            return new Gson().fromJson(input, PlayerTurning.class);
        }
        if(input.contains("\"messageType\": \"PlayerValues\",\n")){
            return new Gson().fromJson(input, PlayerValues.class);
        }
        if(input.contains("\"messageType\": \"Reboot\",\n")){
            return new Gson().fromJson(input, Reboot.class);
        }
        if(input.contains("\"messageType\": \"RebootDirection\",\n")){
            return new Gson().fromJson(input, RebootDirection.class);
        }
        if(input.contains("\"messageType\": \"ReceivedChat\",\n")){
            return new Gson().fromJson(input, ReceivedChat.class);
        }
        if(input.contains("\"messageType\": \"ReplaceCard\",\n")){
            return new Gson().fromJson(input, ReplaceCard.class);
        }
        if(input.contains("\"messageType\": \"SelectedCard\",\n")){
            return new Gson().fromJson(input, SelectedCard.class);
        }
        if(input.contains("\"messageType\": \"SelectedDamage\",\n")){
            return new Gson().fromJson(input, SelectedDamage.class);
        }
        if(input.contains("\"messageType\": \"SelectionFinished\",\n")){
            return new Gson().fromJson(input, SelectionFinished.class);
        }
        if(input.contains("\"messageType\": \"SelectMap\",\n")){
            return new Gson().fromJson(input, SelectMap.class);
        }
        if(input.contains("\"messageType\": \"SendChat\",\n")){
            return new Gson().fromJson(input, SendChat.class);
        }
        if(input.contains("\"messageType\": \"SetStartingPoint\",\n")){
            return new Gson().fromJson(input, SetStartingPoint.class);
        }
        if(input.contains("\"messageType\": \"SetStatus\",\n")){
            return new Gson().fromJson(input, SetStatus.class);
        }
        if(input.contains("\"messageType\": \"ShuffleCoding\",\n")){
            return new Gson().fromJson(input, ShuffleCoding.class);
        }
        if(input.contains("\"messageType\": \"StartingPointTaken\",\n")){
            return new Gson().fromJson(input, StartingPointTaken.class);
        }
        if(input.contains("\"messageType\": \"TimerEnded\",\n")){
            return new Gson().fromJson(input, TimerEnded.class);
        }
        if(input.contains("\"messageType\": \"TimerStarted\",\n")){
            return new Gson().fromJson(input, TimerStarted.class);
        }
        if(input.contains("\"messageType\": \"Welcome\",\n")){
            return new Gson().fromJson(input, Welcome.class);
        }
        if(input.contains("\"messageType\": \"YourCards\",\n")){
            return new Gson().fromJson(input, YourCards.class);
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