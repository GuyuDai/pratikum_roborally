package client;

import client.gameWindow.GameViewModel;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;

import java.util.*;

import javafx.application.Platform;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.MessageAdapter;
import protocol.ProtocolFormat.MessageType;
import protocol.SendChat;
import protocol.*;
import protocol.PlayerAdded.PlayerAddedBody;
import protocol.ReceivedChat.ReceivedChatBody;
import protocol.Welcome.WelcomeBody;
import protocol.HelloClient.HelloClientBody;
import server.Player.Player;
import server.Server;


public class ClientReceive extends Thread{

     int clientID;
    private Socket socket;
    private BufferedReader readInput;
    private BufferedWriter writeOutput;

    private static final String PROTOCOL = "Version 1.0";
    private static final String GROUP = "Origionelle Oktopusse";

    int playerId;
    String playerName;
    int figure;

    String chatMsg;

    int fromId;

    int register;

    int damageCount;
    boolean isPrivate;

    boolean isReady;

    boolean isFilled;

    boolean timerStarted=false;

    String cardPlayed;

    List<Integer> IdList=new ArrayList<>();
    Map<Integer,Integer>IdStartPoint= new HashMap<>();

    Map<Integer,String> IdCardPlayed=new HashMap<>();

    Map<Integer,Boolean> IdReady=new HashMap<>();

    List<Integer> robotNumbers=new ArrayList<>();

    List<Integer> startNumbers=new ArrayList<>();

    Map<Integer,Integer> IdRobot=new HashMap<>();

    Map<String,Integer> IdName = new HashMap<>();

    List<Boolean> readyList=new ArrayList<>();

    String[] maps;

    String[] cards;

    String[]damageDecks;

    String board;

    String[] filledRegister;

    Integer[] positions;

    String turnDirection;

    String animationType;

    int x;
    int y;

    int activePhaseNumber;

    int rebootClientId;

    Map<Integer,Integer[]> IdPosition=new HashMap<>();

    public ClientReceive(Socket socket) {
        this.socket = socket;
        //sendMessageToServer("test");
        try {
            readInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        IdName.put("Server",-1);
        try {
            while (!socket.isClosed()) {
                String serverMessage = readInput.readLine();
                //System.out.println(serverMessage + "-----------original message");  //test
                Message message = wrapMessage(serverMessage);
                //System.out.println("--------------------------------------------------------------");  //test
                System.out.println(message + "wrapped message");  //test
                identifyMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public Message wrapMessage(String input){  //doesn't work
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        Gson gson = gsonBuilder.create();

        return gson.fromJson(input, Message.class);
    }

     */


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
        String body = message.getMessageBody();
        switch (type){
            case MessageType.helloClient:
                break;

            case MessageType.alive:
                sendMessage(new Alive().toString());
                break;

            case MessageType.welcome:
                WelcomeBody welcomeBody=new Gson().fromJson(body,WelcomeBody.class);
                clientID=welcomeBody.getClientID();
                sendMessage(new HelloServer(GROUP,false,PROTOCOL,clientID).toString());
                break;
            case MessageType.playerAdded:
                PlayerAddedBody playerAddedBody=new Gson().fromJson(body,PlayerAddedBody.class);
                playerId=playerAddedBody.getClientID();
                playerName=playerAddedBody.getName();
                figure=playerAddedBody.getFigure();
                IdList.add(playerId);
                robotNumbers.add(figure);
                IdRobot.put(playerId,figure);
                IdName.put(playerName,playerId);
                break;
            case MessageType.receivedChat:
                ReceivedChatBody receivedChatBody=new Gson().fromJson(body,ReceivedChatBody.class);
                 chatMsg=receivedChatBody.getMessage();
                 fromId=receivedChatBody.getFrom();
                 isPrivate=receivedChatBody.isPrivate();
                 receiveChat(chatMsg);
                 break;
            case MessageType.selectMap:
                SelectMap.SelectMapBody selectMapBody=new Gson().fromJson(body,SelectMap.SelectMapBody.class);
                maps=selectMapBody.getAvailableMaps();
                break;
            case MessageType.playerStatus:
                PlayerStatus.PlayerStatusBody playerStatusBody=new Gson().fromJson(body, PlayerStatus.PlayerStatusBody.class);
                isReady=playerStatusBody.isReady();
                playerId=playerStatusBody.getClientID();
                readyList.add(isReady);
                IdReady.put(playerId,isReady);
                break;
            case MessageType.mapSelected:
                MapSelected.MapSelectedBody mapSelectedBody=new Gson().fromJson(body,MapSelected.MapSelectedBody.class);
                board=mapSelectedBody.getMap();
                break;
            case MessageType.yourCards:
                YourCards.YourCardsBody yourCardsBody=new Gson().fromJson(body, YourCards.YourCardsBody.class);
                cards=yourCardsBody.getCardsInHand();
                break;
            case MessageType.cardSelected:
                CardSelected.CardSelectedBody cardSelectedBody=new Gson().fromJson(body, CardSelected.CardSelectedBody.class);
                playerId=cardSelectedBody.getClientID();
                register=cardSelectedBody.getRegister();
                isFilled=cardSelectedBody.isFilled();
                if(isFilled){
                    sendMessage(new SelectionFinished(playerId).toString());
                }
                break;
            case MessageType.pickDamage:
                PickDamage.PickDamageBody pickDamageBody=new Gson().fromJson(body, PickDamage.PickDamageBody.class);
                damageDecks=pickDamageBody.getAvailablePiles();
                damageCount=pickDamageBody.getCount();
                break;
            case MessageType.startingPointTaken:
                StartingPointTaken.StartingPointTakenBody startingPointTakenBody=new Gson().fromJson(body,
                        StartingPointTaken.StartingPointTakenBody.class);
                int takenX=startingPointTakenBody.getX();
                int takenY=startingPointTakenBody.getY();
                playerId=startingPointTakenBody.getClientID();
                if(takenX==1 && takenY==1){
                    startNumbers.add(1);
                    IdStartPoint.put(playerId,1);
                }
                if(takenX==3 && takenY==0){
                    startNumbers.add(2);
                    IdStartPoint.put(playerId,2);
                }
                if(takenX==4 && takenY==1){
                    startNumbers.add(3);
                    IdStartPoint.put(playerId,3);
                }
                if (takenX==5 && takenY==1){
                    startNumbers.add(4);
                    IdStartPoint.put(playerId,4);
                }
                if(takenX==6 && takenY==0 ){
                    startNumbers.add(5);
                    IdStartPoint.put(playerId,5);
                }
                if (takenX==8 && takenY==1){
                    startNumbers.add(6);
                    IdStartPoint.put(playerId,6);
                }
                break;
            case MessageType.timerStarted:
                timerStarted=true;
                break;
            case MessageType.cardPlayed:
                CardPlayed.CardPlayedBody cardPlayedBody=new Gson().fromJson(body, CardPlayed.CardPlayedBody.class);
                cardPlayed=cardPlayedBody.getCard();
                playerId=cardPlayedBody.getClientID();
                IdCardPlayed.put(playerId,cardPlayed);
                break;
            case MessageType.cardsYouGotNow:
                CardsYouGotNow.CardYouGotNowBody cardYouGotNowBody=new Gson().fromJson(body, CardsYouGotNow.CardYouGotNowBody.class);
                filledRegister=cardYouGotNowBody.getCards();
                break;

            case MessageType.movement:
                Movement.MovementBody movementBody=new Gson().fromJson(body,Movement.MovementBody.class);
                playerId=movementBody.getClientID();
                y=movementBody.getX();
                x=movementBody.getY();
                positions=new Integer[]{x,y};
                IdPosition.put(playerId,positions);
                break;
            case MessageType.playerTurning:
                PlayerTurning.PlayerTurningBody playerTurningBody=new Gson().fromJson(body, PlayerTurning.PlayerTurningBody.class);
                turnDirection=playerTurningBody.getRotation();
                break;
            case MessageType.animation:
                Animation.AnimationBody animationBody=new Gson().fromJson(body,Animation.AnimationBody.class);
                animationType=animationBody.getType();
                break;
            case MessageType.activePhase:
                ActivePhase.ActivePhaseBody activePhaseBody=new Gson().fromJson(body,ActivePhase.ActivePhaseBody.class);
                activePhaseNumber=activePhaseBody.getPhase();
                break;
            case MessageType.reboot:
                Reboot.RebootBody rebootBody=new Gson().fromJson(body, Reboot.RebootBody.class);
                rebootClientId=rebootBody.getClientID();
                break;

        }
    }

    public void receiveChat(String msg){
        String fromName = this.getNameById(getFromId());
        if(LobbyViewModel.getWindowName() == "Lobby") {
            Platform.runLater(() -> {
                LobbyViewModel.show(fromName + ": " + msg);
            });
        }
        else{
            Platform.runLater(() -> {
                GameViewModel.show(fromName + ": " + msg);
            });
        }
    }

    public int getFigure() {
        return figure;
    }

    public Map<Integer, Integer> getIdRobot() {
        return IdRobot;
    }

    public int getRobotById(int id){
        return getIdRobot().get(id);
    }

    public Map<Integer, Integer> getIdStartPoint() {
        return IdStartPoint;
    }

    public int getStartPointById(int id){ return  getIdStartPoint().get(id);}

    public int getIdByName(String name){
        return getIdName().get(name);
    }
    public String getNameById(int id){
        String name="";
        for(String key: getIdName().keySet()){
            if(getIdName().get(key).equals(id)){
                name=key;
            }
        }
        return name;
    }

    public int getFromId(){
        return fromId;
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

    public String[] getMaps() {
        return maps;
    }

    public Socket getSocket(){return socket;}

    public int getClientID() {
        return this.clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public List<Integer> getRobotNumbers() {
        return robotNumbers;
    }

    public List<Integer> getStartNumbers(){
        return startNumbers;
    }

     public Map<Integer,Integer[]> getIdPosition(){
        return IdPosition;
     }
    public Map<String, Integer> getIdName() {
        return IdName;
    }

    public List<Boolean> getReadyList() {
        return readyList;
    }

    public String getBoard() {
        return board;
    }

    public Map<Integer, Boolean> getIdReady() {
        return IdReady;
    }

    public Integer[] getPositionById(int ID) {
        return getIdPosition().get(ID);
    }

    public boolean isTimerStarted() {
        return timerStarted;
    }

    public String[] getFilledRegister() {
        return filledRegister;
    }

    public List<Integer> getIdList() {
        return IdList;
    }

    public String[] getCards() {
        return cards;
    }
}