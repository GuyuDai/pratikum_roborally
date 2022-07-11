/*package client;

import com.google.gson.*;
import protocol.*;
import protocol.PickDamage.*;
import protocol.ProtocolFormat.*;
import server.BoardTypes.*;
import server.CardTypes.*;
import server.Control.*;
import server.Player.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**This class handles the AI on the client side.
 * The AIClient just builds up a Socket and connects to the server
 * Here you will find the simple AI logic for playing the game
 *

public class AIClientReceive extends Thread {

    private volatile Socket sockAI;
    //sending messages
    private PrintWriter sender;
    //reading messages
    private BufferedReader reader;
    private int clientID;
    public boolean isGameRunning = false;
    private boolean activationPhase = false;
    private List<String> aiCards = new ArrayList<>();
    private String[] aiRegister = new String[5];
    private int aiEnergyCubes = 5;

    int phase;
    private List<String> myNineCardsOnPile = new ArrayList<>();
    private HashSet<Position> availableStartingPositions = new HashSet<>();
    private HashSet<Position> availableDeathTrapStartingPositions = new HashSet<>();


    private String activePhase = null;
    String type;
    //Position in Client
    private int x;  //colum
    private int y;  //row

    private final HashMap<Integer, int[]> startingPointsOfPlayers = new HashMap<>();
    String card;
    String[] cards;
    String[] availablePiles;
    int checkPointNumber;

    private String[] register = new String[5];

    String group;
    int Id;
    boolean isAI;

    String name;
    int figure;
    private HashMap<Integer, Integer> robotsOfPlayers = new HashMap<>();
    private HashMap<Integer, Direction> currentDirectionsofAllPlayers = new HashMap<>();

    boolean ready;
    String newCard;

    String message;
    int to;
    boolean isRobotValuesSet= false;
    String map;

    String action;

    ArrayList<Card> nineCardsPile;

    int pointerForRegister = 0;
    int countofDamage;
    String[] availableMaps;
    boolean isPrivate;
    int cardsInHand;
    int energyStorage;
    private LinkedHashMap<Integer, Boolean> readyStatusOfClients = new LinkedHashMap<>();
    String source;
    String protocol;
    boolean isConnected;
    private Player player;
    String rotation;
    String error;
    String direction;
    int from;
    int[] clientIDs;

    public int getaIRobot() {
        return aIRobot;
    }

    int aIRobot;

    private static ClientReceive clientAIReceive;
    private final HashMap<Integer, int[]> activePositionsOfAllPlayers = new HashMap<>();
    private HashMap<Integer, String> playerNames = new HashMap<>();

    //Starting the ActiviationPhase
    public void setActivationPhase() {
        this.activationPhase = true;
    }


    private BufferedReader readInput;
    private BufferedWriter writeOutput;

    private static final String PROTOCOL = "Version 1.0";
    private static final String GROUP = "Origionelle Oktopusse";

    //identifyMessage(wrapMessage(String.valueOf(readInput)));
    //            setAIRobot();
    public AIClientReceive(Socket socket) {
        this.sockAI = socket;
        setStartingPositions();
        //sendMessageToServer("test");
        try {
            readInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Message helloServer = new HelloServer(GROUP, true, PROTOCOL, clientID);
            String helloServerString = helloServer.toString();  //this message is missing

            //System.out.println(helloServerString);
            //writeOutput.write(helloServerString);
            sendMessage(helloServerString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (!sockAI.isClosed()) {
                String serverMessage = readInput.readLine();
                Message message = wrapMessage(serverMessage);
                getMessageFromServer(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Sets the available StartingPositions
     * Onl
    Board deathTrap= new DeathTrap();
    Board dizzyHighway = new DizzyHighway();
    public void setStartingPositions() {
    //DeathTrap
        availableDeathTrapStartingPositions.add(new Position(11, 1, deathTrap ));
        availableDeathTrapStartingPositions.add(new Position(12, 3, deathTrap));
        availableDeathTrapStartingPositions.add(new Position(11, 4, deathTrap));
        availableDeathTrapStartingPositions.add(new Position(11, 5, deathTrap));
        availableDeathTrapStartingPositions.add(new Position(12, 6, deathTrap));
        availableDeathTrapStartingPositions.add(new Position(11, 8, deathTrap));
        //DizzyHighway
        availableStartingPositions.add(new Position(1, 1,dizzyHighway ));
        availableStartingPositions.add(new Position(0, 3,dizzyHighway));
        availableStartingPositions.add(new Position(1, 4,dizzyHighway));
        availableStartingPositions.add(new Position(1, 5,dizzyHighway));
        availableStartingPositions.add(new Position(0, 6,dizzyHighway));
        availableStartingPositions.add(new Position(1, 8,dizzyHighway));


    }

    private Message wrapMessage(String input) {
        if (input.contains("\"messageType\":\"ActivePhase\",\"messageBody\"")) {
            return new Gson().fromJson(input, ActivePhase.class);
        }
        if (input.contains("\"messageType\":\"Alive\",\"messageBody\"")) {
            return new Gson().fromJson(input, Alive.class);
        }
        if (input.contains("\"messageType\":\"Animation\",\"messageBody\"")) {
            return new Gson().fromJson(input, Animation.class);
        }
        if (input.contains("\"messageType\":\"CardPlayed\",\"messageBody\"")) {
            return new Gson().fromJson(input, CardPlayed.class);
        }
        if (input.contains("\"messageType\":\"CardSelected\",\"messageBody\"")) {
            return new Gson().fromJson(input, CardSelected.class);
        }
        if (input.contains("\"messageType\":\"CardsYouGotNow\",\"messageBody\"")) {
            return new Gson().fromJson(input, CardsYouGotNow.class);
        }
        if (input.contains("\"messageType\":\"CheckPointReached\",\"messageBody\"")) {
            return new Gson().fromJson(input, CheckPointReached.class);
        }
        if (input.contains("\"messageType\":\"ClientMessage\",\"messageBody\"")) {
            return new Gson().fromJson(input, ClientMessage.class);
        }
        if (input.contains("\"messageType\":\"ConnectionUpdate\",\"messageBody\"")) {
            return new Gson().fromJson(input, ConnectionUpdate.class);
        }
        if (input.contains("\"messageType\":\"CurrentCards\",\"messageBody\"")) {
            return new Gson().fromJson(input, CurrentCards.class);
        }
        if (input.contains("\"messageType\":\"CurrentPlayer\",\"messageBody\"")) {
            return new Gson().fromJson(input, CurrentPlayer.class);
        }
        if (input.contains("\"messageType\":\"DrawDamage\",\"messageBody\"")) {
            return new Gson().fromJson(input, DrawDamage.class);
        }
        if (input.contains("\"messageType\":\"Energy\",\"messageBody\"")) {
            return new Gson().fromJson(input, Energy.class);
        }
        if (input.contains("\"messageType\":\"Error\",\"messageBody\"")) {
            return new Gson().fromJson(input, ErrorMessage.class);
        }
        if (input.contains("\"messageType\":\"GameFinished\",\"messageBody\"")) {
            return new Gson().fromJson(input, GameFinished.class);
        }
        if (input.contains("\"messageType\":\"GameStarted\",\"messageBody\"")) {
            return new Gson().fromJson(input, GameStarted.class);
        }
        if (input.contains("\"messageType\":\"HelloClient\",\"messageBody\"")) {
            return new Gson().fromJson(input, HelloClient.class);
        }
        if (input.contains("\"messageType\":\"HelloServer\",\"messageBody\"")) {
            return new Gson().fromJson(input, HelloServer.class);
        }
        if (input.contains("\"messageType\":\"MapSelected\",\"messageBody\"")) {
            return new Gson().fromJson(input, MapSelected.class);
        }
        if (input.contains("\"messageType\":\"Movement\",\"messageBody\"")) {
            return new Gson().fromJson(input, Movement.class);
        }
        if (input.contains("\"messageType\":\"NotYourCards\",\"messageBody\"")) {
            return new Gson().fromJson(input, NotYourCards.class);
        }
        if (input.contains("\"messageType\":\"PickDamage\",\"messageBody\"")) {
            return new Gson().fromJson(input, PickDamage.class);
        }
        if (input.contains("\"messageType\":\"PlayCard\",\"messageBody\"")) {
            return new Gson().fromJson(input, PlayCard.class);
        }
        if (input.contains("\"messageType\":\"PlayerAdded\",\"messageBody\"")) {
            return new Gson().fromJson(input, PlayerAdded.class);
        }
        if (input.contains("\"messageType\":\"PlayerStatus\",\"messageBody\"")) {
            return new Gson().fromJson(input, PlayerStatus.class);
        }
        if (input.contains("\"messageType\":\"PlayerTurning\",\"messageBody\"")) {
            return new Gson().fromJson(input, PlayerTurning.class);
        }
        if (input.contains("\"messageType\":\"PlayerValues\",\"messageBody\"")) {
            return new Gson().fromJson(input, PlayerValues.class);
        }
        if (input.contains("\"messageType\":\"Reboot\",\"messageBody\"")) {
            return new Gson().fromJson(input, Reboot.class);
        }
        if (input.contains("\"messageType\":\"RebootDirection\",\"messageBody\"")) {
            return new Gson().fromJson(input, RebootDirection.class);
        }
        if (input.contains("\"messageType\":\"ReceivedChat\",\"messageBody\"")) {
            return new Gson().fromJson(input, ReceivedChat.class);
        }
        if (input.contains("\"messageType\":\"ReplaceCard\",\"messageBody\"")) {
            return new Gson().fromJson(input, ReplaceCard.class);
        }
        if (input.contains("\"messageType\":\"SelectedCard\",\"messageBody\"")) {
            return new Gson().fromJson(input, SelectedCard.class);
        }
        if (input.contains("\"messageType\":\"SelectedDamage\",\"messageBody\"")) {
            return new Gson().fromJson(input, SelectedDamage.class);
        }
        if (input.contains("\"messageType\":\"SelectionFinished\",\"messageBody\"")) {
            return new Gson().fromJson(input, SelectionFinished.class);
        }
        if (input.contains("\"messageType\":\"SelectMap\",\"messageBody\"")) {
            return new Gson().fromJson(input, SelectMap.class);
        }
        if (input.contains("\"messageType\":\"SendChat\",\"messageBody\"")) {
            return new Gson().fromJson(input, SendChat.class);
        }
        if (input.contains("\"messageType\":\"SetStartingPoint\",\"messageBody\"")) {
            return new Gson().fromJson(input, SetStartingPoint.class);
        }
        if (input.contains("\"messageType\":\"SetStatus\",\"messageBody\"")) {
            return new Gson().fromJson(input, SetStatus.class);
        }
        if (input.contains("\"messageType\":\"ShuffleCoding\",\"messageBody\"")) {
            return new Gson().fromJson(input, ShuffleCoding.class);
        }
        if (input.contains("\"messageType\":\"StartingPointTaken\",\"messageBody\"")) {
            return new Gson().fromJson(input, StartingPointTaken.class);
        }
        if (input.contains("\"messageType\":\"TimerEnded\",\"messageBody\"")) {
            return new Gson().fromJson(input, TimerEnded.class);
        }
        if (input.contains("\"messageType\":\"TimerStarted\",\"messageBody\"")) {
            return new Gson().fromJson(input, TimerStarted.class);
        }
        if (input.contains("\"messageType\":\"Welcome\",\"messageBody\"")) {
            return new Gson().fromJson(input, Welcome.class);
        }
        if (input.contains("\"messageType\":\"YourCards\",\"messageBody\"")) {
            return new Gson().fromJson(input, YourCards.class);
        }

        return new ErrorMessage("Error when parsing String to Message");
    }

    //Todo: Start the AI with methods IdentifyMessage and setStartingPositions



    public void sendMessage(String msg) {
        try {
            writeOutput.write(msg);
            writeOutput.newLine();
            writeOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getReadInput() {
        return readInput;
    }

    public BufferedWriter getWriteOutput() {
        return writeOutput;
    }

    public Socket getSocket() {
        return sockAI;
    }

    public int getClientID() {
        return this.clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void programmingPhaseAI() {
        // draw from pile 9 cards automatically

        //Choose the first five cards and put in your register
        register[0] = String.valueOf(nineCardsPile.get(0));
        register[1] = String.valueOf(nineCardsPile.get(1));
        register[2] = String.valueOf(nineCardsPile.get(2));
        register[3] = String.valueOf(nineCardsPile.get(3));
        register[4] = String.valueOf(nineCardsPile.get(4));

        sendMessage(new SelectedCard(register[0],0,getClientID()).toString());
        sendMessage(new SelectedCard(register[1],1,getClientID()).toString());
        sendMessage(new SelectedCard(register[2],2,getClientID()).toString());
        sendMessage(new SelectedCard(register[3],3,getClientID()).toString());
        sendMessage(new SelectedCard(register[4],4,getClientID()).toString());
    }



    /**

    public void setAIRobot(){
        if(!robotsOfPlayers.containsValue(1)){
            aIRobot=1;
            sendMessage(new PlayerValues(name,1).toString());
        } else if (!robotsOfPlayers.containsValue(2)){
            aIRobot=2;
            sendMessage(new PlayerValues(name,2).toString());
        } else if (!robotsOfPlayers.containsValue(3)){
            aIRobot=3;
            sendMessage(new PlayerValues(name,3).toString());
        } else if (!robotsOfPlayers.containsValue(4)){
            aIRobot=4;
            sendMessage(new PlayerValues(name,4).toString());
        } else if (!robotsOfPlayers.containsValue(5)){
            aIRobot=5;
            sendMessage(new PlayerValues(name,5).toString());
        } else if (!robotsOfPlayers.containsValue(6)){
            aIRobot=6;
            sendMessage(new PlayerValues(name,6).toString());
        }
    }

    public void getMessageFromServer(Message message) {
        String type = message.getMessageType();
        String messageBody= message.getMessageBody();
        switch (type) {

            case MessageType.welcome:
                //gets the ClientID from Server
                Welcome.WelcomeBody welcomeBody = new Gson().fromJson(messageBody, Welcome.WelcomeBody.class);
                clientID = welcomeBody.getClientID();
                break;

            //case MessageType.playerValues:

            case MessageType.playerStatus:
                //Von Seiten des Servers wird der Spielerstatus an alle verbundenen Clients verteilt.
                PlayerStatus.PlayerStatusBody playerStatusBody = new Gson().fromJson(messageBody, PlayerStatus.PlayerStatusBody.class);
                int clientWhoIsReady = playerStatusBody.getClientID();
                boolean ready = playerStatusBody.isReady();
                readyStatusOfClients.put(clientWhoIsReady, ready);
                break;
            case MessageType.selectMap:
                //Saves Map
                MapSelected.MapSelectedBody mapSelectedBody = new Gson().fromJson(messageBody, MapSelected.MapSelectedBody.class);
                String mapName = mapSelectedBody.getMap();
                map = mapName;
                //or always choose DizzyHighway
                //map = "DizzyHighway";
                sendMessage(new MapSelected(map).toString());

                break;

            case MessageType.mapSelected:
                MapSelected.MapSelectedBody mapSelectedBody1 = new Gson().fromJson(messageBody, MapSelected.MapSelectedBody.class);
                map = mapSelectedBody1.getMap();

                   /* if (map.equals("Death Trap")) {
                        //Position.setX(11);

                        if (availableDeathTrapStartingPositions.contains(new Position(11,1, deathTrap))){
                            x = 11;
                            y = 1;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(12,3, deathTrap))){
                            x = 12;
                            y = 3;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(11,4, deathTrap))) {
                            x = 11;
                            y = 4;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(11,5, deathTrap))) {
                            x = 11;
                            y = 5;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(12,6, deathTrap))) {
                            x = 12;
                            y = 6;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(11,8,deathTrap))) {
                            x = 11;
                            y = 8;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        }

                    } else {
                        if(availableStartingPositions.contains(new Position(1,1,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 1;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(0,3,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 0;
                            y = 3;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(1,4,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 4;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(1,5,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 5;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(0,6,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 0;
                            y = 6;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(1,8,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 8;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        }
                    }*/


/*                break;
            case MessageType.currentPlayer:
                /*if (activePhase.equals("GameInitializing")) {
                    if (map.equals("Death Trap")) {
                        //Position.setX(11);

                        if (availableDeathTrapStartingPositions.contains(new Position(11,1, deathTrap))){
                        x = 11;
                        y = 1;
                        //benachrichtigt den Server
                        sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(12,3, deathTrap))){
                            x = 12;
                            y = 3;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(11,4, deathTrap))) {
                            x = 11;
                            y = 4;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(11,5, deathTrap))) {
                            x = 11;
                            y = 5;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(12,6, deathTrap))) {
                            x = 12;
                            y = 6;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if (availableDeathTrapStartingPositions.contains(new Position(11,8,deathTrap))) {
                            x = 11;
                            y = 8;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        }

                    } else {
                        if(availableStartingPositions.contains(new Position(1,1,dizzyHighway))){
                        //There is the same Startpoint on every MAP exept DeathTrap
                        x = 1;
                        y = 1;
                        //benachrichtigt den Server
                        sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(0,3,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 0;
                            y = 3;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(1,4,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 4;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(1,5,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 5;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(0,6,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 0;
                            y = 6;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        } else if(availableStartingPositions.contains(new Position(1,8,dizzyHighway))){
                            //There is the same Startpoint on every MAP exept DeathTrap
                            x = 1;
                            y = 8;
                            //benachrichtigt den Server
                            sendMessage(new SetStartingPoint(x, y).toString());
                        }
                    }


                    }

                  if (activePhase.equals("ActivationPhase")) {
                    //If its your turn it will always play the first register and deletes the card from the register after action.
                    String reg1 = register[pointerForRegister];
                    //reg1.action();
                    pointerForRegister++;
                    //benachrichtigt den Server welche Karte gespielt wird
                    //auf dem Server wird die KartenAction aufgerufen
                    sendMessage(new PlayCard(reg1).toString());
                    //Nachdem Karte gespielt, wird diese aus dem Register entfernt
                    //Or we use a rigister pointer that increses for each register

                    //register.remove(0);

                } else if (activePhase.equals("UpgradePhase")) {
                    upgradePhaseAI();

                } else if (activePhase.equals("ProgrammingPhase")) {
                    programmingPhaseAI();
                }
                break;

            case MessageType.startingPointTaken:
                //Wenn die gewünschte Position valide ist, werden alle Spieler darüber benachrichtigt.
                //or is this too complicated???
                StartingPointTaken.StartingPointTakenBody startingPointTakenBody = new Gson().fromJson(messageBody, StartingPointTaken.StartingPointTakenBody.class);
                int startingPositionSetbyOtherPlayer = startingPointTakenBody.getClientID();
                int otherPlayerXPosition = startingPointTakenBody.getX();
                int otherPlayerYPosition = startingPointTakenBody.getY();

                removeStartPointsInHashSet(otherPlayerXPosition, otherPlayerYPosition);

                // store the start position
                startingPointsOfPlayers.get(startingPositionSetbyOtherPlayer)[0] = otherPlayerXPosition;
                startingPointsOfPlayers.get(startingPositionSetbyOtherPlayer)[1] = otherPlayerYPosition;
                // set current position
                activePositionsOfAllPlayers.get(startingPositionSetbyOtherPlayer)[0] = otherPlayerXPosition;
                activePositionsOfAllPlayers.get(startingPositionSetbyOtherPlayer)[1] = otherPlayerYPosition;*/
                //Todo sendmessage
                /*if(x!=1 && y!=8){
                x=1;
                y=8;
                sendMessage(new SetStartingPoint(x,y).toString());}
                break;
            case MessageType.drawDamage:
                //All damage cards will be transmitted at once
                DrawDamage.DrawDamageBody drawDamageBody = new Gson().fromJson(messageBody, DrawDamage.DrawDamageBody.class);
                //Damage cards put on the cards[]
                cards = drawDamageBody.getCards();
                break;
            case MessageType.pickDamage:

                PickDamageBody pickDamageBody = new Gson().fromJson(messageBody, PickDamageBody.class);
                countofDamage = pickDamageBody.getCount();
                //Saves the damagecards on the available piles.
                availablePiles = pickDamageBody.getAvailablePiles();

                break;
            case MessageType.yourCards:
                //Draws nine cards from the pile on server side and sends them to client
                YourCards.YourCardsBody yourCardsBody = new Gson().fromJson(messageBody, YourCards.YourCardsBody.class);
                List<String> cardsInHand = List.of(yourCardsBody.getCardsInHand());
                // Save all the Cards on your own CardsPile
                for (String card : cardsInHand) {
                    myNineCardsOnPile.add(card);
                }
                break;
            case MessageType.notYourCards:

                NotYourCards.NotYourCardsBody notYourCardsBody = new Gson().fromJson(messageBody, NotYourCards.NotYourCardsBody.class);
                int client = notYourCardsBody.getClientID();
                int cardCount = notYourCardsBody.getCardsInHand();
                System.out.println("Client" + client + "got new Cards: Number" + cardCount);
                break;
            case MessageType.shuffleCoding:
                ShuffleCoding.ShuffleCodingBody shuffleCodingBody = new Gson().fromJson(messageBody, ShuffleCoding.ShuffleCodingBody.class);
                int shuffler = shuffleCodingBody.getClientID();
                System.out.println("Client" + shuffler + "is shuffling");
                break;
            case MessageType.cardSelected:
                //Der Server gibt das belegte Register, natürlich ohne Karteninformation, an alle weiter.
                // "Filled" steht hierbei für die Information, ob eine Karte in ein Register gelegt oder entfernt wurde.
                CardSelected.CardSelectedBody cardSelectedBody = new Gson().fromJson(messageBody, CardSelected.CardSelectedBody.class);
                int registerSelectedCardsbyClient = cardSelectedBody.getClientID();
                int numberOfFilledRegisters = cardSelectedBody.getRegister();
                boolean filled = cardSelectedBody.isFilled();
                System.out.println(registerSelectedCardsbyClient + " has for register " + numberOfFilledRegisters + filled);
                break;
            case MessageType.timerStarted:
                System.out.println("Your Timer started");
                break;
            case MessageType.timerEnded:
                TimerEnded.TimerEndedBody timerEndedBody = new Gson().fromJson(messageBody, TimerEnded.TimerEndedBody.class);
                int[] clientIDs = timerEndedBody.getClientIDs();
                System.out.println("Those Clients didnt finish in time:" + clientIDs);
                break;
            case MessageType.cardsYouGotNow:
                //If gamer was too slow he discards all current cards and gets new ones he needs to put in register
                CardsYouGotNow.CardYouGotNowBody cardsYouGotNowBody = new Gson().fromJson(messageBody, CardsYouGotNow.CardYouGotNowBody.class);
                String[] cards = cardsYouGotNowBody.getCards();
                for (int i = 0; i <= 4; i++) {
                    register[i] = cards[i];
                }
                break;

            case MessageType.currentCards:

                CurrentCards.CurrentCardsBody currentCardsBody = new Gson().fromJson(messageBody, CurrentCards.CurrentCardsBody.class);
                //todo
                break;
            case MessageType.movement:
                //saves the current position of each player
                Movement.MovementBody movementBody = new Gson().fromJson(messageBody, Movement.MovementBody.class);
                int movedClient = movementBody.getClientID();
                int xachse = movementBody.getX();
                int yAchse = movementBody.getY();
                activePositionsOfAllPlayers.get(movedClient)[0] = xachse;
                activePositionsOfAllPlayers.get(movedClient)[1] = yAchse;
                break;
            case MessageType.playerTurning:
                //Saves the direction of each player who faces
                PlayerTurning.PlayerTurningBody playerTurningBody = new Gson().fromJson(messageBody, PlayerTurning.PlayerTurningBody.class);
                int turningClientID = playerTurningBody.getClientID();
                String turnDirection = playerTurningBody.getRotation();

                // Changes the directions of the players in the game

                if (turnDirection.equals("clockwise")) {
                    currentDirectionsofAllPlayers.get(turningClientID).turnRight();
                } else if (turnDirection.equals("counterclockwise")) {
                    currentDirectionsofAllPlayers.get(turningClientID).turnLeft();
                }
                break;
            case MessageType.gameFinished:
                GameFinished.GameFinishedBody gameFinishedBody = new Gson().fromJson(messageBody, GameFinished.GameFinishedBody.class);
                int winnerOfTheGame = gameFinishedBody.getClientID();
                System.out.println("Game is finished, winner is: " + winnerOfTheGame + ".");
                break;
            case MessageType.replaceCard:
                // Beachten Sie außerdem, dass bestimmte Karten auch während der Aktivierung des Registers ersetzt werden können, wie z.B. Spam Karten.
                ReplaceCard.ReplaceCardBody replaceCardBody = new Gson().fromJson(messageBody, ReplaceCard.ReplaceCardBody.class);
                int clientForReplace = replaceCardBody.getClientID();
                int registerForReplace = replaceCardBody.getRegister();
                //String replacedCard = replaceCardBody.getNewCard().getCardName();
                if (clientForReplace == clientID) {
                    //register[registerForReplace] = replacedCard;
                    /*if (registerForReplace == 0) {
                        pointerForRegister = 4;
                    } else {
                        pointerForRegister--;
                    }*/
                /*}
                break;
            case MessageType.reboot:
                Reboot.RebootBody rebootBody = new Gson().fromJson(messageBody, Reboot.RebootBody.class);
                int clientReboot = rebootBody.getClientID();

                // delete cardpile and register
                if (clientReboot == clientID) {
                    myNineCardsOnPile.clear();
                    for (int i = 0; i < register.length; i++) {
                        register[i] = null;
                    }
                    pointerForRegister = 0;
                    System.out.println("client reboot to start point");

                }
                //Depending on which map the Robot will accordingly set the facing direction
                if (map.equals("DizzyHighway")|map.equals("ExtraCrispy")|map.equals("LostBearings")){
                    direction = "right";
                    //gibt dem Server Bescheid über neue direction
                    sendMessage(new RebootDirection(direction).toString());
                } else{
                    direction = "left";
                    //gibt dem Server Bescheid über neue direction
                    sendMessage(new RebootDirection(direction).toString());
                }
                break;
            case MessageType.energy:
                Energy.EnergyBody energyBody = new Gson().fromJson(messageBody, Energy.EnergyBody.class);
                int supposedClient = energyBody.getClientID();
                int amount = energyBody.getCount();
                //If its you the energy will be added to your storage
                if (supposedClient == clientID) {
                    energyStorage = energyStorage + amount;
                }
                break;
            case MessageType.checkpointReached:
                CheckPointReached.CheckPointReachedBody checkPointReachedBody = new Gson().fromJson(messageBody, CheckPointReached.CheckPointReachedBody.class);
                int clientIDCheckReached= checkPointReachedBody.getClientID();
                int numberOfCheckpointsReached= checkPointReachedBody.getNumber();
                //Sets the number of checkpoints reached
                if(clientIDCheckReached==clientID){
                    checkPointNumber=numberOfCheckpointsReached;
                }
                break;
            case MessageType.playerAdded:

                PlayerAdded.PlayerAddedBody playerAddedBody = new Gson().fromJson(messageBody, PlayerAdded.PlayerAddedBody.class);
                int playerAdded = playerAddedBody.getClientID();
                int robotAdded = playerAddedBody.getFigure();
                String nameAdded = playerAddedBody.getName();

                // not add infos twice
                if (!playerNames.containsKey(playerAdded)) {

                    System.out.println(playerNames.get(playerAdded) + ": " + robotsOfPlayers.get(playerAdded));
                    playerNames.put(playerAdded, nameAdded);
                    robotsOfPlayers.put(playerAdded, robotAdded);

                    // if the added player is self, then launch the chatAndGame window
                    if (playerAdded == clientID) {
                        name = nameAdded;
                        //Todo: Start game
                    }

                    if (isRobotValuesSet==false){
                        setAIRobot();
                        isRobotValuesSet=true;
                    }
                }
                break;
            case MessageType.selectedDamage:

        }
    }

    //ToDo
    public void upgradePhaseAI() {

    }

    /**
     * Save all taken starting positions
     *
     *
     */
/*
    public void removeStartPointsInHashSet(int x, int y) {
        HashSet<Position> delete = new HashSet<>();
        if (map.equals("Death Trap")) {
            for (Position position : availableDeathTrapStartingPositions) {
                if (position.getX() == x && position.getY() == y) {
                    delete.add(position);
                }
            }

        } else {
            for (Position position : availableStartingPositions) {
                if (position.getX() == x && position.getY() == y) {
                    delete.add(position);
                }
            }

        }
    }
}

 */



