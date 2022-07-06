package client;

import com.google.gson.*;
import protocol.*;
import protocol.ProtocolFormat.*;
import server.CardTypes.*;
import server.Player.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**This class handles the AI on the client side.
 * The AIClient just builds up a Socket and connects to the server
 * Here you will find the simple AI logic for playing the game
 *
 */
public class AIClientReceive extends Thread{

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

    public Card getRegisterNumb(int i) {
        return register.get(i);
    }

    public void setRegister(CopyOnWriteArrayList<Card> register) {
        this.register = register;
    }

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

    ArrayList<Card> nineCardsPile;


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
            Message helloServer = new HelloServer(GROUP,false,PROTOCOL,clientID);
            String helloServerString= helloServer.toString();  //this message is missing
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
        String type = message.getMessageType();
        switch (type){
            case MessageType.helloClient:
                sendMessage(new HelloServer(GROUP,false,PROTOCOL,clientID).toString());
                break;

            case MessageType.alive:
                sendMessage(new HelloServer(GROUP,false,PROTOCOL,clientID).toString());
                break;

            case MessageType.welcome:
            case MessageType.playerAdded:
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
    public BufferedReader getReadInput(){return readInput;}

    public BufferedWriter getWriteOutput(){return writeOutput;}

    public Socket getSocket(){return sockAI;}

    public int getClientID() {
        return this.clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void programmingPhaseAI(){
        // draw from pile 9 cards automatically

        //Choose the first five cards and put in your register
        register.add(nineCardsPile.get(0));
        register.add(nineCardsPile.get(1));
        register.add(nineCardsPile.get(2));
        register.add(nineCardsPile.get(3));
        register.add(nineCardsPile.get(4));
    }

    /** Check incoming messages and call corresponding method
     *
     */

    public void getMessageFromServer(Message message){
        String type = message.getMessageType();
        switch(type){
            case MessageType.currentPlayer:
                if (activePhase.equals("GameInitializing")) {
                    if (map.equals("Death Trap")) {
                        //Position.setX(11);
                        x=11;
                        //Position.setY(8);
                        y=8;
                        sendMessage(new HelloServer(GROUP,false,PROTOCOL,clientID).toString());
                    } else {
                        //Position.setX(1);
                        x=1;
                        //Position.setX(8);
                        x=8;
                    }

                } else if (activePhase.equals("ActivationPhase")) {
                    Card reg1= getRegisterNumb(0);
                    reg1.action();
                    Card reg2= getRegisterNumb(1);
                    reg2.action();
                    Card reg3= getRegisterNumb(2);
                    reg3.action();
                    Card reg4= getRegisterNumb(3);
                    reg4.action();
                    Card reg5= getRegisterNumb(4);
                    reg5.action();
                }
                else if (activePhase.equals("UpgradePhase")) {
                    upgradePhaseAI();

                } else if(activePhase.equals("ProgrammingPhase")){
                    programmingPhaseAI();
                }
        }
    }

    public void upgradePhaseAI(){

    }
}