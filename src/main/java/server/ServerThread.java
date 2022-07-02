package server;


import java.io.*;
import java.net.Socket;
import java.util.*;
import protocol.*;
import com.google.gson.Gson;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageType;
import server.Player.Player;

import protocol.PlayerValues;


public class ServerThread implements Runnable {
    //private static List<PlayerOnline> playersOnline = new ArrayList();
    private static final String PROTOCOL = "Version 1.0";
    private Socket clientSocket;
    private BufferedReader readInput;
    private  PrintWriter writeOutput;
    public static boolean gameActive = false;

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
    String name;
    int figure;


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
            writeOutput =new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Message helloClient = new HelloClient(PROTOCOL);
            String HelloClient = helloClient.toString();
            writeOutput.println(HelloClient);
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
        String messageType=message.getMessageType();
        switch (messageType){
            case MessageType.activePhase:
                phase=message.getMessageBody().getPhase();
                break;
            case MessageType.animation:
                //type=message.getMessageBody().get();
                break;
            case MessageType.cardPlayed:
                clientID=message.getMessageBody().getId();
                card=message.getMessageBody().getCard();
                break;
            case MessageType.cardSelected:
                clientID=message.getMessageBody().getId();
                register=message.getMessageBody().getRegister();
                break;






            case MessageType.helloServer:
                Id=message.getMessageBody().getId();
                group=message.getMessageBody().getGroup();
                isAI=message.getMessageBody().isAI();
                player.setAI(isAI);
                break;
            case MessageType.playerValues:
                name=message.getMessageBody().getGroup();
                figure=message.getMessageBody().getFigure();
                break;
            case MessageType.setStatus:
                ready=message.getMessageBody().isReady();
                break;
            case MessageType.sendChat:
                break;
            case MessageType.mapSelected:
                break;
            case MessageType.playCard:
                card=message.getMessageBody().getCard();
                break;
            case MessageType.selectCard:
            case MessageType.selectedDamage:
            case MessageType.setStartingPoint:
                //x=message.getMessageBody().getX();
                //y=message.getMessageBody().getY();
                break;



        }
    }

    public BufferedReader getReadInput(){
        return readInput;
    }

    public Socket getClientSocket() {return clientSocket;}




     public static boolean createGame(){
     if(gameActive == false) {
     //game = new Game();
     gameActive = true;
     return true;
     }
     else{
     return false;
     }
     }

     public static boolean isGameActive() {
     return gameActive;
     }


}