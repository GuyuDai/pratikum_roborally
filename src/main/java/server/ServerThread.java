package server;


import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.Socket;
import protocol.*;
import com.google.gson.Gson;
import protocol.ActivePhase.ActivePhaseBody;
import protocol.Animation.AnimationBody;
import protocol.CardPlayed.CardPlayedBody;
import protocol.CardSelected.CardSelectedBody;
import protocol.HelloServer.HelloServerBody;
import protocol.PlayCard.PlayCardBody;
import protocol.PlayerValues.PlayerValuesBody;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageAdapter;
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.MessageType;
import protocol.SendChat.SendChatBody;
import protocol.SetStartingPoint.SetStartingPointBody;
import protocol.SetStatus.SetStatusBody;
import server.Control.Timer;
import server.Player.Player;


public class ServerThread implements Runnable {
    private static final String PROTOCOL = "Version 1.0";
    private static final String GROUP = "Origionelle Oktopusse";
    private Socket clientSocket;
    private BufferedReader readInput;
    private  BufferedWriter writeOutput;
    public static boolean gameActive = false;

    private boolean alive;

    /**
     * ActivePhase
     */
    int phase;

    //TODO Alive?

    /**
     * Animation
     */
    String type;


    /**
     * CardPlayed
     */
    int clientID;
    String card;


    /**
     * CardSelected
     */
    int register;


    /**
     * HelloServer
     */
    String group;
    int Id;
    boolean isAI;


    /**
     * PlayerValues
     */


    /**
     * SetStatus
     */
    boolean ready;


    /**
     * SendChat
     */
    String message;
    int to;


    /**
     * MapSelection
     */
    String map;





    /**
     * SetStartingPoint
     */
    int x;
    int y;



    private Player player;
    // private static Game game = null;

    public ServerThread(Socket clientSocket) throws IOException {
        this. clientSocket = clientSocket;
        try {
            readInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Message helloClient = new HelloClient(PROTOCOL);  //this message is missing
            String HelloClient = helloClient.toString();
            sendMessage(HelloClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                String clientMessage = readInput.readLine();
                System.out.println(clientMessage + "----------original message");  //test
                Message message = wrapMessage(clientMessage);
                System.out.println("-----------------------------------------------------");  //test
                System.out.println(message.toString());  //test
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message wrapMessage(String input){
        if(input.contains("\"messageType\":\"ActivePhase\",\"messageBody\"")){
            return new Gson().fromJson(input, ActivePhase.class);
        }
        if(input.contains("\"messageType\":\"Alive\",\"messageBody\"")){
            return new Gson().fromJson(input, Alive.class);
        }
        if(input.contains("\"messageType\":\"Animation\",\"messageBody\"")){
            return new Gson().fromJson(input, Animation.class);
        }
        if(input.contains("\"messageType\":\"CardPlayed\",\"messageBody\"")){
            return new Gson().fromJson(input, CardPlayed.class);
        }
        if(input.contains("\"messageType\":\"CardSelected\",\"messageBody\"")){
            return new Gson().fromJson(input, CardSelected.class);
        }
        if(input.contains("\"messageType\":\"CardsYouGotNow\",\"messageBody\"")){
            return new Gson().fromJson(input, CardsYouGotNow.class);
        }
        if(input.contains("\"messageType\":\"CheckPointReached\",\"messageBody\"")){
            return new Gson().fromJson(input, CheckPointReached.class);
        }
        if(input.contains("\"messageType\":\"ClientMessage\",\"messageBody\"")){
            return new Gson().fromJson(input, ClientMessage.class);
        }
        if(input.contains("\"messageType\":\"ConnectionUpdate\",\"messageBody\"")){
            return new Gson().fromJson(input, ConnectionUpdate.class);
        }
        if(input.contains("\"messageType\":\"CurrentCards\",\"messageBody\"")){
            return new Gson().fromJson(input, CurrentCards.class);
        }
        if(input.contains("\"messageType\":\"CurrentPlayer\",\"messageBody\"")){
            return new Gson().fromJson(input, CurrentPlayer.class);
        }
        if(input.contains("\"messageType\":\"DrawDamage\",\"messageBody\"")){
            return new Gson().fromJson(input, DrawDamage.class);
        }
        if(input.contains("\"messageType\":\"Energy\",\"messageBody\"")){
            return new Gson().fromJson(input, Energy.class);
        }
        if(input.contains("\"messageType\":\"Error\",\"messageBody\"")){
            return new Gson().fromJson(input, ErrorMessage.class);
        }
        if(input.contains("\"messageType\":\"GameFinished\",\"messageBody\"")){
            return new Gson().fromJson(input, GameFinished.class);
        }
        if(input.contains("\"messageType\":\"GameStarted\",\"messageBody\"")){
            return new Gson().fromJson(input, GameStarted.class);
        }
        if(input.contains("\"messageType\":\"HelloClient\",\"messageBody\"")){
            return new Gson().fromJson(input, HelloClient.class);
        }
        if(input.contains("\"messageType\":\"HelloServer\",\"messageBody\"")){
            return new Gson().fromJson(input, HelloServer.class);
        }
        if(input.contains("\"messageType\":\"MapSelected\",\"messageBody\"")){
            return new Gson().fromJson(input, MapSelected.class);
        }
        if(input.contains("\"messageType\":\"Movement\",\"messageBody\"")){
            return new Gson().fromJson(input, Movement.class);
        }
        if(input.contains("\"messageType\":\"NotYourCards\",\"messageBody\"")){
            return new Gson().fromJson(input, NotYourCards.class);
        }
        if(input.contains("\"messageType\":\"PickDamage\",\"messageBody\"")){
            return new Gson().fromJson(input, PickDamage.class);
        }
        if(input.contains("\"messageType\":\"PlayCard\",\"messageBody\"")){
            return new Gson().fromJson(input, PlayCard.class);
        }
        if(input.contains("\"messageType\":\"PlayerAdded\",\"messageBody\"")){
            return new Gson().fromJson(input, PlayerAdded.class);
        }
        if(input.contains("\"messageType\":\"PlayerStatus\",\"messageBody\"")){
            return new Gson().fromJson(input, PlayerStatus.class);
        }
        if(input.contains("\"messageType\":\"PlayerTurning\",\"messageBody\"")){
            return new Gson().fromJson(input, PlayerTurning.class);
        }
        if(input.contains("\"messageType\":\"PlayerValues\",\"messageBody\"")){
            return new Gson().fromJson(input, PlayerValues.class);
        }
        if(input.contains("\"messageType\":\"Reboot\",\"messageBody\"")){
            return new Gson().fromJson(input, Reboot.class);
        }
        if(input.contains("\"messageType\":\"RebootDirection\",\"messageBody\"")){
            return new Gson().fromJson(input, RebootDirection.class);
        }
        if(input.contains("\"messageType\":\"ReceivedChat\",\"messageBody\"")){
            return new Gson().fromJson(input, ReceivedChat.class);
        }
        if(input.contains("\"messageType\":\"ReplaceCard\",\"messageBody\"")){
            return new Gson().fromJson(input, ReplaceCard.class);
        }
        if(input.contains("\"messageType\":\"SelectedCard\",\"messageBody\"")){
            return new Gson().fromJson(input, SelectedCard.class);
        }
        if(input.contains("\"messageType\":\"SelectedDamage\",\"messageBody\"")){
            return new Gson().fromJson(input, SelectedDamage.class);
        }
        if(input.contains("\"messageType\":\"SelectionFinished\",\"messageBody\"")){
            return new Gson().fromJson(input, SelectionFinished.class);
        }
        if(input.contains("\"messageType\":\"SelectMap\",\"messageBody\"")){
            return new Gson().fromJson(input, SelectMap.class);
        }
        if(input.contains("\"messageType\":\"SendChat\",\"messageBody\"")){
            return new Gson().fromJson(input, SendChat.class);
        }
        if(input.contains("\"messageType\":\"SetStartingPoint\",\"messageBody\"")){
            return new Gson().fromJson(input, SetStartingPoint.class);
        }
        if(input.contains("\"messageType\":\"SetStatus\",\"messageBody\"")){
            return new Gson().fromJson(input, SetStatus.class);
        }
        if(input.contains("\"messageType\":\"ShuffleCoding\",\"messageBody\"")){
            return new Gson().fromJson(input, ShuffleCoding.class);
        }
        if(input.contains("\"messageType\":\"StartingPointTaken\",\"messageBody\"")){
            return new Gson().fromJson(input, StartingPointTaken.class);
        }
        if(input.contains("\"messageType\":\"TimerEnded\",\"messageBody\"")){
            return new Gson().fromJson(input, TimerEnded.class);
        }
        if(input.contains("\"messageType\":\"TimerStarted\",\"messageBody\"")){
            return new Gson().fromJson(input, TimerStarted.class);
        }
        if(input.contains("\"messageType\":\"Welcome\",\"messageBody\"")){
            return new Gson().fromJson(input, Welcome.class);
        }
        if(input.contains("\"messageType\":\"YourCards\",\"messageBody\"")){
            return new Gson().fromJson(input, YourCards.class);
        }

        return new ErrorMessage("Error when parsing String to Message");
    }

    private void identifyMessage(Message message) {
        /*
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        Gson gson = gsonBuilder.create();

         */

        String messageType = message.getMessageType();
        String body = message.getMessageBody();
        //MessageBody messageBody = gson.fromJson(body,MessageBody.class);

        switch (messageType){
            case MessageType.activePhase:
                ActivePhaseBody activePhaseBody = new Gson().fromJson(body,ActivePhaseBody.class);
                this.phase = activePhaseBody.getPhase();
                break;

            case MessageType.animation:
                AnimationBody animationBody = new Gson().fromJson(body,AnimationBody.class);
                type = animationBody.getType();
                break;

            case MessageType.cardPlayed:
                CardPlayedBody cardPlayedBody = new Gson().fromJson(body,CardPlayedBody.class);
                clientID = cardPlayedBody.getClientID();
                card = cardPlayedBody.getCard();
                break;

            case MessageType.cardSelected:
                CardSelectedBody cardSelectedBody = new Gson().fromJson(body,CardSelectedBody.class);
                clientID = cardSelectedBody.getClientID();
                register = cardSelectedBody.getRegister();
                break;

            case MessageType.helloServer:
                HelloServerBody helloServerBody = new Gson().fromJson(body,HelloServerBody.class);
                Id = helloServerBody.getClientID();
                group = helloServerBody.getGroup();
                isAI = helloServerBody.isAI();
                setAI(isAI);
                if(isAI){
                    player=new Player("AI");
                    player.setAI(isAI);
                }
                Timer.countDown(5);
                sendMessage(new Alive().toString());
                break;

            case MessageType.playerValues:
                PlayerValuesBody playerValuesBody = new Gson().fromJson(body,PlayerValuesBody.class);
                String name = playerValuesBody.getName();
                int figure = playerValuesBody.getFigure();
                player = new Player(name);
                player.setOwnRobot(figure);
                break;

            case MessageType.setStatus:
                SetStatusBody setStatusBody = new Gson().fromJson(body,SetStatusBody.class);
                ready = setStatusBody.isReady();
                break;

            case MessageType.sendChat:
                SendChatBody sendChatBody = new Gson().fromJson(body,SendChatBody.class);
                //some behaviour
                break;

            case MessageType.mapSelected:
                break;

            case MessageType.playCard:
                PlayCardBody playCardBody = new Gson().fromJson(body,PlayCardBody.class);
                card = playCardBody.getCard();
                break;

            case MessageType.selectCard:
                break;

            case MessageType.selectedDamage:
                break;

            case MessageType.setStartingPoint:
                SetStartingPointBody setStartingPointBody = new Gson().fromJson(body,SetStartingPointBody.class);
                x = setStartingPointBody.getX();
                y = setStartingPointBody.getY();
                break;

        }
    }

    public void sendMessage(String msg){
        try {
            writeOutput.write(msg);
            writeOutput.newLine();
            writeOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedReader getReadInput(){
        return readInput;
    }

    public BufferedWriter getWriteOutput() {
        return writeOutput;
    }

    public Socket getClientSocket() {return clientSocket;}

    public static boolean isGameActive() {
        return gameActive;
    }

    public int getID() {
        return clientID;
    }

    public void setID(int clientID) {
        this.clientID = clientID;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}