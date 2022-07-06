package client;

import com.google.gson.*;
import protocol.*;
import protocol.PickDamage.*;
import protocol.ProtocolFormat.*;
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
 */
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


    private Card[] register = new Card[5];

    String group;
    int Id;
    boolean isAI;

    String name;
    int figure;

    private HashMap<Integer, Direction> currentDirectionsofAllPlayers = new HashMap<>();

    boolean ready;
    String newCard;

    String message;
    int to;

    String map;

    String action;

    ArrayList<Card> nineCardsPile;

    int pointerForRegister = 0;
    int countofDamage;
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
    private final HashMap<Integer, int[]> activePositionsOfAllPlayers = new HashMap<>();


    //Starting the ActiviationPhase
    public void setActivationPhase() {
        this.activationPhase = true;
    }


    private BufferedReader readInput;
    private BufferedWriter writeOutput;

    private static final String PROTOCOL = "Version 1.0";
    private static final String GROUP = "Origionelle Oktopusse";

    public AIClientReceive(Socket socket) {
        this.sockAI = socket;
        //sendMessageToServer("test");
        try {
            readInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Message helloServer = new HelloServer(GROUP, false, PROTOCOL, clientID);
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
                System.out.println(serverMessage + "-----------original message");  //test
                Message message = wrapMessage(serverMessage);
                System.out.println("--------------------------------------------------------------");  //test
                System.out.println(message);  //test
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void identifyMessage(Message message) {
        String type = message.getMessageType();
        switch (type) {
            case MessageType.helloClient:
                sendMessage(new HelloServer(GROUP, false, PROTOCOL, clientID).toString());
                break;

            case MessageType.alive:
                sendMessage(new HelloServer(GROUP, false, PROTOCOL, clientID).toString());
                break;

            case MessageType.welcome:
            case MessageType.playerAdded:
        }
    }

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
        register[0] = nineCardsPile.get(0);
        register[0] = nineCardsPile.get(1);
        register[0] = nineCardsPile.get(2);
        register[0] = nineCardsPile.get(3);
        register[0] = nineCardsPile.get(4);
    }

    /**
     * Check incoming messages and call corresponding method
     */

    public void getMessageFromServer(Message message) {
        String type = message.getMessageType();
        switch (type) {

            case MessageType.welcome:
                //gets the ClientID from Server
                Welcome.WelcomeBody welcomeBody = new Gson().fromJson(messageBody, Welcome.WelcomeBody.class);
                clientID = welcomeBody.getClientID();
                break;

            case MessageType.playerStatus:
                //Sobald ein Spieler seine Auswahl erfolgreich getroffen hat,
                // kann er dem Server signalisieren bereit zu sein.
                // Diese Meinung kann er via Boolean aber auch zurückziehen!
                break;
            case MessageType.selectMap:
                break;
            case MessageType.mapSelected:
                MapSelected.MapSelectedBody mapSelectedBody = new Gson().fromJson(messageBody, MapSelected.MapSelectedBody.class);
                map = mapSelectedBody.getMap();
                break;
            case MessageType.currentPlayer:
                if (activePhase.equals("GameInitializing")) {
                    if (map.equals("Death Trap")) {
                        //Position.setX(11);
                        x = 11;
                        //Position.setY(8);
                        y = 1;
                        sendMessage(new SetStartingPoint(x, y).toString());
                    } else {
                        //There is the same Startpoint on every MAP exept DeathTrap
                        x = 1;
                        y = 1;
                        sendMessage(new SetStartingPoint(x, y).toString());
                    }

                } else if (activePhase.equals("ActivationPhase")) {
                    //If its your turn it will always play the first register and deletes the card from the register after action.
                    Card reg1 = register[pointerForRegister];
                    reg1.action();
                    pointerForRegister++;
                    //benachrichtigt den Server welche Karte gespielt wird
                    sendMessage(new PlayCard(reg1.name).toString());
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
                //or is this too complicated???
                StartingPointTaken.StartingPointTakenBody startingPointTakenBody = new Gson().fromJson(messageBody, StartingPointTaken.StartingPointTakenBody.class);
                int startingPositionSetbyOtherPlayer = startingPointTakenBody.getClientID();
                int otherPlayerX = startingPointTakenBody.getX();
                int otherPlayerY = startingPointTakenBody.getY();

                removeStartPointsInHashSet(otherPlayerX, otherPlayerY);

                // store the start position
                startingPointsOfPlayers.get(startingPositionSetbyOtherPlayer)[0] = otherPlayerX;
                startingPointsOfPlayers.get(startingPositionSetbyOtherPlayer)[1] = otherPlayerX;
                // set current position
                activePositionsOfAllPlayers.get(startingPositionSetbyOtherPlayer)[0] = otherPlayerX;
                activePositionsOfAllPlayers.get(startingPositionSetbyOtherPlayer)[1] = otherPlayerY;

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
                    register[i] = cards.get(i);
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
                Card replacedCard = replaceCardBody.getNewCard();
                if (clientForReplace == clientID) {
                    register[registerForReplace] = replacedCard;
                    /*if (registerForReplace == 0) {
                        pointerForRegister = 4;
                    } else {
                        pointerForRegister--;
                    }*/
                }
                break;
            case MessageType.reboot:
                Reboot.RebootBody rebootBody = new Gson().fromJson(messageBody, Reboot.RebootBody.class);
                int clientReboot = rebootBody.getClientID();

                // clear all my registers if I reboot
                if (clientReboot == clientID) {
                    myNineCardsOnPile.clear();
                    for (int i = 0; i < register.length; i++) {
                        register[i] = null;
                    }
                    pointerForRegister = 0;
                    System.out.println("client reboot to start point");
                    break;
                }
        }
    }

    //ToDo
    public void upgradePhaseAI() {

    }

    /**
     * if the start position is taken, remove it from hashset
     *
     * @param x the x
     * @param y the y
     */
    public void removeStartPointsInHashSet(int x, int y) {
        HashSet<Position> delete = new HashSet<>();
        if (map.equals("Death Trap")) {
            for (Position position : availableDeathTrapStartingPositions) {
                if (position.getX() == x && position.getY() == y) {
                    delete.add(position);
                }
            }
            availableDeathTrapStartingPositions.removeAll(delete);
        } else {
            for (Position position : availableStartingPositions) {
                if (position.getX() == x && position.getY() == y) {
                    delete.add(position);
                }
            }
            availableStartingPositions.removeAll(delete);
        }
    }
}
