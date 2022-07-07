package client;

import protocol.ProtocolFormat.*;
import server.CardTypes.*;
import server.Player.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

;

public class AIClient extends Client {

    private volatile Socket sockAI;
    //sending messages
    private PrintWriter sender;
    //reading messages
    private BufferedReader reader;
    private int clientID;
    public boolean isGameRunning=false;
    private boolean activationPhase= false;
    private List<String> aiCards= new ArrayList<>();
    private String[] aiRegister= new String[5];
    private int aiEnergyCubes=5;

    int phase;

    private String activePhase = null;
    String type;
    //Position in Client
    private int x;  //colum
    private int y;  //row

    String card;
    String [] cards;

    private CopyOnWriteArrayList<Card> register;

    String group;
    int Id;
    boolean isAI;

    String name;
    int figure;

    boolean ready;
    String newCard;

    String message;
    int to;

    String map;

    String action;

    ArrayList<ActiveCard> activeCards;


    int count;
    String[] availableMaps;
    boolean isPrivate;
    int cardsInHand;
    int number;
    String source;
    String protocol;
    boolean isConnected;
    private Player player;
    String rotation;
    String error;
    String direction;
    int from;
    int[] clientIDs;
    String messageBody;
    private static ClientReceive clientAIReceive;


    //Starting the ActiviationPhase
    public void setActivationPhase(){this.activationPhase=true;}

    //constructor for AI
    public AIClient() throws IOException{

        sockAI= new Socket("AIHost", 1788);
        sender= new PrintWriter(sockAI.getOutputStream(),true);
        reader= new BufferedReader(new InputStreamReader(sockAI.getInputStream()));
        clientAIReceive = new ClientReceive(sockAI);
    }


   /* public void run() {
        try {
            String message;

            while (!sockAI.isClosed()) {

                message = reader.readLine();

                if (message != null) {

                    if(message.equals("GameOn")){

                        isGameRunning = true;
                        sockAI.close();
                    }else{
                        identifyMessage(ClientReceive.wrapMessage(message));

                    }
                }
            }
            RR.leaveGame(this);
        } catch (IOException e) {
            try {
                sockAI.close();
                RR.leaveGame(this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
                //isConnected= message.getMessageBody().getConnected();
                action= message.getMessageBody().getAction();
                break;
            case MessageType.currentCards:
                //activeCards=message.getMessageBody().getActiveCards();
                break;
            case MessageType.currentPlayer:
                clientID=message.getMessageBody().getClientID();
                if (activePhase.equals("GameInitializing")) {
                    if (map.equals("Death Trap")) {
                        Position.setX(11);
                        Position.setY(8);
                    } else {
                        Position.setX(1);
                        Position.setX(8);
                    }

                } else if (activePhase.equals("ActivationPhase")) {
                    Card reg1= Player.getRegister(0);
                    reg1.action();
                    Card reg2= Player.getRegister(1);
                    reg2.action();
                    Card reg3= Player.getRegister(2);
                    reg3.action();
                    Card reg4= Player.getRegister(3);
                    reg4.action();
                    Card reg5= Player.getRegister(4);
                    reg5.action();
                }
                else if (activePhase.equals("UpgradePhase")) {
                    Player.upgradePhaseAI();

                } else if(activePhase.equals("ProgrammingPhase")){
                    Player.programmingPhaseAI();
                }


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
                gameMap= (ArrayList) message.getMessageBody().getMap();
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
                drawSpamCard(count);
                //availablePiles=message.getMessageBody().getAvailablePiles();
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
                //reboot();
                break;
            case MessageType.rebootDirection:
                direction=message.getMessageBody().getDirection();
                break;
            case MessageType.receivedChat:
                //message = message.getMessageBody().getMessage();
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
                if (availableMaps.equals("DizzyHighway")){
                    //MapBuilder.printMap();
                }
                break;
            case MessageType.sendChat:
                //message = message.getMessageBody().getMessage();
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

            case MessageType.welcome:
                clientID=message.getMessageBody().getClientID();
                System.out.println("server roboter list " + RR.getActivePlayers().size());
                System.out.println("check for free robot " + RR.getActivePlayers().contains("SquashBot"));
                // set a available robot to AI
                setPlayerValues("AI", new Robot("SquashBot"));
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

            case MessageType.mapSelected:
                String mapSelect = String.valueOf(message.getMessageBody().getMap());
                if (mapSelect == "DizzyHighway"){
                    //MapBuilder.printMap();
                }
                break;



        }


    }


    public void setPlayerValues(String name, Robot robotFigure) {
        //ClientReceive.sendMessage(robotFigure);

    }

    */




}

