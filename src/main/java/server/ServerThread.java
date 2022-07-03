package server;


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
import protocol.ProtocolFormat.MessageBody;
import protocol.ProtocolFormat.MessageType;
import protocol.SetStartingPoint.SetStartingPointBody;
import protocol.SetStatus.SetStatusBody;
import server.Player.Player;


public class ServerThread implements Runnable {
    private static final String PROTOCOL = "Version 1.0";
    private Socket clientSocket;
    private BufferedReader readInput;
    private  PrintWriter writeOutput;
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
            writeOutput = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Message helloClient = new HelloClient(PROTOCOL);
            String HelloClient = helloClient.toString();
            writeOutput.write(HelloClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                String clientMessage = readInput.readLine();
                Message message = new Gson().fromJson(clientMessage, Message.class);
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void identifyMessage(Message message) {
        String messageType = message.getMessageType();
        MessageBody messageBody = message.getMessageBody();
        switch (messageType){
            case MessageType.activePhase:
                ActivePhaseBody activePhaseBody = (ActivePhaseBody) messageBody;
                this.phase = activePhaseBody.getPhase();
                break;

            case MessageType.animation:
                AnimationBody animationBody = (AnimationBody) messageBody;
                type = animationBody.getType();
                break;

            case MessageType.cardPlayed:
                CardPlayedBody cardPlayedBody = (CardPlayedBody) messageBody;
                clientID = cardPlayedBody.getClientID();
                card = cardPlayedBody.getCard();
                break;

            case MessageType.cardSelected:
                CardSelectedBody cardSelectedBody = (CardSelectedBody) messageBody;
                clientID = cardSelectedBody.getClientID();
                register = cardSelectedBody.getRegister();
                break;

            case MessageType.helloServer:
                HelloServerBody helloServerBody = (HelloServerBody) messageBody;
                Id = helloServerBody.getClientID();
                group = helloServerBody.getGroup();
                isAI = helloServerBody.isAI();
                player.setAI(isAI);
                break;

            case MessageType.playerValues:
                PlayerValuesBody playerValuesBody = (PlayerValuesBody) messageBody;
                String name = playerValuesBody.getName();
                int figure = playerValuesBody.getFigure();
                player=new Player(name);
                player.setOwnRobot(figure);
                break;

            case MessageType.setStatus:
                SetStatusBody setStatusBody = (SetStatusBody) messageBody;
                ready = setStatusBody.isReady();
                break;

            case MessageType.sendChat:
                break;

            case MessageType.mapSelected:
                break;

            case MessageType.playCard:
                PlayCardBody playCardBody = (PlayCardBody) messageBody;
                card = playCardBody.getCard();
                break;

            case MessageType.selectCard:

            case MessageType.selectedDamage:

            case MessageType.setStartingPoint:
                SetStartingPointBody setStartingPointBody = (SetStartingPointBody) messageBody;
                x = setStartingPointBody.getX();
                y = setStartingPointBody.getY();
                break;



        }
    }

    public BufferedReader getReadInput(){
        return readInput;
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