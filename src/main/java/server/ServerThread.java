package server;


import java.io.*;
import java.net.Socket;
import java.util.*;

import protocol.*;
import com.google.gson.Gson;
import protocol.ProtocolFormat.*;
import server.Player.Player;


public class ServerThread implements Runnable {
    private static List<Server> clientsConnected = new ArrayList();
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
    String [] cards;


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

    String newCard;
    /**
     * SendChat
     */
    String message;

    int to;


    /**
     * MapSelection
     */
    String map;

    String action;

    ArrayList<ActiveCard> activeCards;

    ArrayList<> gameMap;

    /**
     * SetStartingPoint
     */
    int x;
    int y;
    int count;
    String[] availableMaps;
    boolean isPrivate;


    int cardsInHand;
    int number;
    String source;
    String protocol;
    boolean isConnected;
    private Player player;
    // private static Game game = null;
    String rotation;
    String error;
    String direction;
    int from;

    int[] clientIDs;

    String messageBody;


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
                type=message.getMessageBody().getType();
                break;
            case MessageType.cardPlayed:
                clientID=message.getMessageBody().getClientID();
                card =message.getMessageBody().getCard();
                break;
            case MessageType.cardSelected:
                clientID=message.getMessageBody().getClientID();
                register=message.getMessageBody().getRegister();
                break;
            case MessageType.cardsYouGotNow:
                cards=message.getMessageBody().getCards();
                break;
            case MessageType.checkpointReached:
                clientID=message.getMessageBody().getClientID();
                number= message.getMessageBody().getNumber();
                break;
            case MessageType.connectionUpdate:
                clientID=message.getMessageBody().getClientID();
                isConnected= message.getMessageBody().getIsConnected();
                action= message.getMessageBody().getAction();
                break;
            case MessageType.currentCards:
                //activeCards=message.getMessageBody().getActiveCards();
                break;
            case MessageType.currentPlayer:
                clientID=message.getMessageBody().getClientID();
            case MessageType.drawDamage:
                clientID=message.getMessageBody().getClientID();
                cards=message.getMessageBody().getCards();
            case MessageType.energy:
                clientID=message.getMessageBody().getClientID();
                count=message.getMessageBody().getCount();
                source=message.getMessageBody().getSource();
                break;
            case MessageType.error:
                error=message.getMessageBody().getError();
                break;

            case MessageType.gameFinished:
                clientID=message.getMessageBody().getClientID();
                break;

            case MessageType.gameStarted:
                //gameMap=message.getMessageBody().getMap();
                break;
            case MessageType.helloClient:
                protocol = message.getMessageBody().getProtocol();
                break;
            case MessageType.movement:
                clientID=message.getMessageBody().getClientID();
                x=message.getMessageBody().getX();
                y=message.getMessageBody().getY();
                break;
            case MessageType.notYourCards:
                clientID=message.getMessageBody().getClientID();
                cardsInHand=message.getMessageBody().getCardsInHand();
                break;
            case MessageType.pickDamage:
                count=message.getMessageBody().getCount();
                //availablePiles=message.getMessageBody().getAvailablePiles();
                break;
            case MessageType.playerAdded:
                clientID=message.getMessageBody().getClientID();
                name=message.getMessageBody().getName();
                figure=message.getMessageBody().getFigure();
                break;
            case MessageType.playerStatus:
                clientID=message.getMessageBody().getClientID();
                ready=message.getMessageBody().getReady();
                break;
            case MessageType.playerTurning:
                clientID=message.getMessageBody().getClientID();
                rotation=message.getMessageBody().getRotation();
                break;
            case MessageType.playerValues:
                name=message.getMessageBody().getName();
                figure=message.getMessageBody().getFigure();
                break;
            case MessageType.reboot:
                clientID=message.getMessageBody().getClientID();
                break;
            case MessageType.rebootDirection:
                direction=message.getMessageBody().getDirection();
                break;
            case MessageType.receivedChat:
                message = message.getMessageBody().getMessage();
                from=message.getMessageBody().getFrom();
                isPrivate=message.getMessageBody().getMessageIsPrivate();
                break;
            case MessageType.replaceCard:
                register= message.getMessageBody().getRegister();
                newCard= message.getMessageBody().getNewCard();
                clientID=message.getMessageBody().getClientID();
                break;
            case MessageType.selectedDamage:
                cards=message.getMessageBody().getCards();
                break;
            case MessageType.selectionFinished:
                clientID=message.getMessageBody().getClientID();
                break;
            case MessageType.selectMap:
                availableMaps= message.getMessageBody().getAvailableMaps();
                break;
            case MessageType.sendChat:
                message = message.getMessageBody().getMessage();
                break;
            case MessageType.setStartingPoint:
                x=message.getMessageBody().getX();
                y=message.getMessageBody().getY();
                break;
            case MessageType.setStatus:
                ready=message.getMessageBody().getReady();
                break;
            case MessageType.shuffleCoding:
                clientID=message.getMessageBody().getClientID();
                break;
            case MessageType.startingPointTaken:
                x=message.getMessageBody().getX();
                y=message.getMessageBody().getY();
                clientID=message.getMessageBody().getClientID();
                break;
            case MessageType.timerEnded:
                clientIDs=message.getMessageBody().getClientIDs();
                break;
            case MessageType.timerStarted:
                messageBody=message.getMessageBody().getMessageBody();
                break;
            case MessageType.welcome:
                clientID=message.getMessageBody().getClientID();
                break;
            case MessageType.yourCards:
                cardsInHand=message.getMessageBody().getCardsInHand();
                break;
            case MessageType.helloServer:
                clientID=message.getMessageBody().getId();
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
                card =message.getMessageBody().getCard();
                break;
            case MessageType.selectCard:
            case MessageType.selectedDamage:
            case MessageType.setStartingPoint:
                x=message.getMessageBody().getX();
                y=message.getMessageBody().getY();
                break;

        }
    }

    public BufferedReader getReadInput(){
        return readInput;
    }

    public Socket getClientSocket() {return clientSocket;}

    /*public static List<PlayerOnline> getPlayersOnline() {
        return playersOnline;
    }*/


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