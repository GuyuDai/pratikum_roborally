package client;

import client.gameWindow.*;
import client.lobbyWindow.*;
import com.google.gson.*;
import javafx.application.*;
import protocol.*;
import protocol.PlayerAdded.*;
import protocol.ProtocolFormat.*;
import protocol.ReceivedChat.*;
import protocol.Welcome.*;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.*;

/**
 * When a client receive a protocol message from Server, it handles and keep the information here and update the GUI
 *
 * @author: Li MingHao, Yixue Dai, Niklas Nissl
 */

public class ClientReceive extends Thread{

    public static final String ANSI_GREEN = "\u001B[32m";

    protected int clientID;
    protected Socket socket;
    protected BufferedReader readInput;
    protected BufferedWriter writeOutput;
    protected static final String PROTOCOL = "Version 2.0";
    protected static final String GROUP = "Origionelle Oktopusse";
    protected int playerId;
    protected String playerName;

    protected int figure;
    protected String chatMsg;
    protected int checkPointNumber;
    protected int checkPointXPosition;
    protected int checkPointYPosition;
    protected int energyStorage;
    protected int fromId;

    protected int register;
    protected int damageCount;
    protected boolean isPrivate;
    protected boolean isReady;
    protected boolean isFilled;
    protected boolean timerStarted = false;
    protected String cardPlayed;
    protected Map<Integer,String> IdCardPlayed = new HashMap<>();
    protected Map<Integer,Boolean> IdReady = new HashMap<>();
    protected List<Integer> robotNumbers = new ArrayList<>();
    protected List<Integer> startNumbers = new ArrayList<>();
    protected Map<String,Integer> IdName = new HashMap<>();
    protected List<Boolean> readyList=new ArrayList<>();
    protected String[] maps;
    protected String[] cards = new String[9];
    protected String[]damageDecks;
    protected String board;
    protected String[] filledRegister;
    protected Integer[] positions;
    protected String turnDirection;
    protected String animationType;
    protected int x;
    protected int y;
    protected int activePhaseNumber;
    protected int rebootClientId;
    protected Map<Integer,Integer[]> IdPosition = new HashMap<>();
    protected List<Integer> IdList = new ArrayList<>();
    protected Map<Integer,Integer> IdRobot = new HashMap<>();
    protected Map<Integer,Integer>IdStartPoint = new HashMap<>();
    protected Map<Integer,String> IdDirection=new HashMap<>();

    protected int counterRegister = 0;

    protected boolean doRobotLaser=false;

    protected boolean pickDamage =false;

    protected  boolean gameEnded=false;

    protected  int winnerID;

    protected boolean gameStarted = false;

    protected Map<Integer, Integer[]> checkPointsPosition = new HashMap<Integer, Integer[]>();

    protected Map<Integer,List<Integer[]>> IdMovePosition=new HashMap<>();

    List<Integer[]> positionList=new ArrayList<>();


    public void setCounterRegister(int count){
        this.counterRegister = count;
    }

    public int getCounterRegister(){
        return counterRegister;
    }

    public void setCheckPointXPosition(int checkPointXPosition) {
        this.checkPointXPosition = checkPointXPosition;
    }

    public void setCheckPointYPosition(int checkPointYPosition) {
        this.checkPointYPosition = checkPointYPosition;
    }

    /**
     * sets the positions of the checkpoints
     * @author  Nik, Dai
     */
    public void setCheckPointsPosition(){
        switch (this.board){
            case "DizzyHighway":
                checkPointsPosition.put(1,new Integer[]{3,12});
                break;

            case "ExtraCrispy":
                checkPointsPosition.put(1,new Integer[]{2,10});
                checkPointsPosition.put(2,new Integer[]{7,5});
                checkPointsPosition.put(3,new Integer[]{7,10});
                checkPointsPosition.put(4,new Integer[]{2,5});
                break;

            case "LostBearings":
                checkPointsPosition.put(1,new Integer[]{4,11});
                checkPointsPosition.put(2,new Integer[]{5,4});
                checkPointsPosition.put(3,new Integer[]{2,8});
                checkPointsPosition.put(4,new Integer[]{7,8});
                break;

            case "Twister":
                checkPointsPosition.put(1,new Integer[]{1,10});
                checkPointsPosition.put(2,new Integer[]{7,6});
                checkPointsPosition.put(3,new Integer[]{3,5});
                checkPointsPosition.put(4,new Integer[]{7,9});
                break;

            case "DeathTrap":
                checkPointsPosition.put(1,new Integer[]{7,1});
                checkPointsPosition.put(2,new Integer[]{4,4});
                checkPointsPosition.put(3,new Integer[]{3,12});
                checkPointsPosition.put(4,new Integer[]{8,7});
                checkPointsPosition.put(5,new Integer[]{1,0});
                break;
        }
    }

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
                Message message = wrapMessage(serverMessage);
                //System.out.println("--------------------------------------------------------------");  //test
                Client.getLogger().info( ANSI_GREEN + message + "wrapped message");  //test
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
    /**
     * @author dai
     * @param input String form reader.readLine()
     * @return corresponding protocol message
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
        if(input.contains("\"messageType\":\"Boink\",\"messageBody\"")){
            return new Gson().fromJson(input, Boink.class);
        }
        if(input.contains("\"messageType\":\"ChooseRegister\",\"messageBody\"")){
            return new Gson().fromJson(input, ChooseRegister.class);
        }
        if(input.contains("\"messageType\":\"RegisterChosen\",\"messageBody\"")){
            return new Gson().fromJson(input, RegisterChosen.class);
        }
        if(input.contains("\"messageType\":\"ReturnCards\",\"messageBody\"")){
            return new Gson().fromJson(input, ReturnCards.class);
        }
        if(input.contains("\"messageType\":\"CheckPointMoved\",\"messageBody\"")){
            return new Gson().fromJson(input, CheckPointMoved.class);
        }
        if(input.contains("\"messageType\":\"YourCards\",\"messageBody\"")){
            return new Gson().fromJson(input, YourCards.class);
        }
        if(input.contains("\"messageType\":\"DiscardSome\",\"messageBody\"")){
            return new Gson().fromJson(input, YourCards.class);
        }

        return new ErrorMessage("Error when parsing String to Message");
    }

    /**
     * @author: Li, Nik
     * Client handle protocol message from Server.
     */

    private void identifyMessage(Message message) {
        String type = message.getMessageType();
        switch (type){
            case MessageType.helloClient:
                break;

            case MessageType.alive:
                sendMessage(new Alive().toString());
                break;

            case MessageType.welcome:
                Welcome welcome = (Welcome) message;
                WelcomeBody welcomeBody = welcome.getMessageBody();
                clientID=welcomeBody.getClientID();
                sendMessage(new HelloServer(GROUP,false,PROTOCOL,clientID).toString());
                break;

            case MessageType.playerAdded:
                PlayerAdded playerAdded = (PlayerAdded) message;
                PlayerAddedBody playerAddedBody = playerAdded.getMessageBody();
                playerId = playerAddedBody.getClientID();
                playerName = playerAddedBody.getName();
                int tempFigure = playerAddedBody.getFigure();
                if(!gameStarted) {
                    IdList.add(playerId);
                    robotNumbers.add(tempFigure);
                    IdRobot.put(playerId, tempFigure);
                    if(IdName.containsKey(playerName)){
                        playerName=playerName+"(ID:"+playerId+")";
                        IdName.put(playerName,playerId);
                    }
                    else {
                        IdName.put(playerName, playerId);
                    }
                    if (playerId == clientID) {
                        figure = tempFigure;
                    }
                }
                break;

            case MessageType.receivedChat:
                ReceivedChat receivedChat = (ReceivedChat) message;
                ReceivedChatBody receivedChatBody = receivedChat.getMessageBody();
                chatMsg=receivedChatBody.getMessage();
                fromId=receivedChatBody.getFrom();
                isPrivate=receivedChatBody.isPrivate();
                receiveChat(chatMsg);
                break;

            case MessageType.selectMap:
                SelectMap selectMap = (SelectMap) message;
                SelectMap.SelectMapBody selectMapBody = selectMap.getMessageBody();
                maps = selectMapBody.getAvailableMaps();
                break;

            case MessageType.playerStatus:
                PlayerStatus playerStatus = (PlayerStatus) message;
                PlayerStatus.PlayerStatusBody playerStatusBody = playerStatus.getMessageBody();
                isReady = playerStatusBody.isReady();
                playerId = playerStatusBody.getClientID();
                if(isReady) {
                    readyList.add(isReady);
                    IdReady.put(playerId, isReady);
                }
                else{
                    readyList.remove(0);
                    IdReady.put(playerId,isReady);
                }
                break;

            case MessageType.mapSelected:
                MapSelected mapSelected = (MapSelected) message;
                MapSelected.MapSelectedBody mapSelectedBody = mapSelected.getMessageBody();
                board = mapSelectedBody.getMap();
                setCheckPointsPosition();
                break;

            case MessageType.yourCards:
                YourCards yourCards = (YourCards) message;
                YourCards.YourCardsBody yourCardsBody = yourCards.getMessageBody();
                cards=yourCardsBody.getCardsInHand();
                break;

            case MessageType.cardSelected:
                CardSelected cardSelected = (CardSelected) message;
                CardSelected.CardSelectedBody cardSelectedBody = cardSelected.getMessageBody();
                playerId=cardSelectedBody.getClientID();
                register=cardSelectedBody.getRegister();
                isFilled=cardSelectedBody.isFilled();
                if(isFilled){
                    sendMessage(new SelectionFinished(playerId).toString());
                }
                break;

            case MessageType.pickDamage:
                PickDamage pickDamage1 = (PickDamage) message;
                PickDamage.PickDamageBody pickDamageBody = pickDamage1.getMessageBody();
                damageDecks = pickDamageBody.getAvailablePiles();
                damageCount = pickDamageBody.getCount();
                pickDamage = true;
                break;

            case MessageType.startingPointTaken:
                StartingPointTaken startingPointTaken = (StartingPointTaken) message;
                StartingPointTaken.StartingPointTakenBody startingPointTakenBody = startingPointTaken.getMessageBody();
                int takenX = startingPointTakenBody.getX();
                int takenY = startingPointTakenBody.getY();
                playerId = startingPointTakenBody.getClientID();
                startingPositionAdd(takenX,takenY, playerId);
                break;

            case MessageType.timerStarted:
                timerStarted=true;
                break;

            case MessageType.timerEnded:
                timerStarted = false;
                break;

            case MessageType.cardPlayed:
                CardPlayed cardPlayed1 = (CardPlayed) message;
                CardPlayed.CardPlayedBody cardPlayedBody = cardPlayed1.getMessageBody();
                cardPlayed=cardPlayedBody.getCard();
                playerId=cardPlayedBody.getClientID();
                IdCardPlayed.put(playerId,cardPlayed);
                break;

            case MessageType.cardsYouGotNow:
                CardsYouGotNow cardsYouGotNow = (CardsYouGotNow) message;
                CardsYouGotNow.CardYouGotNowBody cardYouGotNowBody = cardsYouGotNow.getMessageBody();
                filledRegister=cardYouGotNowBody.getCards();
                break;

            case MessageType.movement:
                Movement movement = (Movement) message;
                Movement.MovementBody movementBody = movement.getMessageBody();
                playerId=movementBody.getClientID();
                y=movementBody.getX();
                x=movementBody.getY();
                positions=new Integer[]{x,y};
                positionList.add(positions);
                IdPosition.put(playerId,positions);
                break;

            case MessageType.playerTurning:
                PlayerTurning playerTurning = (PlayerTurning) message;
                PlayerTurning.PlayerTurningBody playerTurningBody = playerTurning.getMessageBody();
                turnDirection=playerTurningBody.getRotation();
                playerId=playerTurningBody.getClientID();
                IdDirection.put(playerId,turnDirection);
                break;

            case MessageType.animation:
                Animation animation = (Animation) message;
                Animation.AnimationBody animationBody = animation.getMessageBody();
                animationType=animationBody.getType();
                if(animationType.equals("RobotLaser")){
                    doRobotLaser=true;
                }
                break;

            case MessageType.activePhase:
                ActivePhase activePhase = (ActivePhase) message;
                ActivePhase.ActivePhaseBody activePhaseBody = activePhase.getMessageBody();
                activePhaseNumber=activePhaseBody.getPhase();
                if(activePhaseNumber==0){
                    gameStarted=true;
                }
                break;

            case MessageType.reboot:
                Reboot reboot = (Reboot) message;
                Reboot.RebootBody rebootBody = reboot.getMessageBody();
                rebootClientId=rebootBody.getClientID();
                break;

            case MessageType.energy:
                Energy energy = (Energy) message;
                Energy.EnergyBody energyBody = energy.getMessageBody();
                int supposedClient = energyBody.getClientID();
                int amount = energyBody.getCount();
                //If its the you the energy will be added to your storage
                if (supposedClient == clientID) {
                    energyStorage = energyStorage + amount;
                }
                break;

            case MessageType.checkpointReached:
                CheckPointReached checkPointReached = (CheckPointReached) message;
                //Saves the number of Checkpoints reached
                CheckPointReached.CheckPointReachedBody checkPointReachedBody = checkPointReached.getMessageBody();
                int clientIDCheckReached = checkPointReachedBody.getClientID();
                int numberOfCheckpointsReached = checkPointReachedBody.getNumber();
                //Sets the number of checkpoints reached
                if(clientIDCheckReached == clientID){
                    checkPointNumber = numberOfCheckpointsReached;
                }
                break;

            case MessageType.boink:
                break;

            case MessageType.checkPointMoved:
                CheckPointMoved checkPointMoved = (CheckPointMoved) message;
                //If a checkpoint moves its position
                CheckPointMoved.CheckPointMovedBody checkPointMovedBody= checkPointMoved.getMessageBody();
                int movedCheckPointID = checkPointMovedBody.getCheckPointID();
                int newXPosition = checkPointMovedBody.getX();
                int newYPosition = checkPointMovedBody.getY();
                this.checkPointsPosition.replace(movedCheckPointID,new Integer[]{newXPosition,newYPosition});
                break;

            case MessageType.gameFinished:
                GameFinished gameFinished= (GameFinished) message;
                GameFinished.GameFinishedBody gameFinishedBody=gameFinished.getMessageBody();
                winnerID=gameFinishedBody.getClientID();
                gameEnded=true;
                break;

            case MessageType.registerChosen:
                assert message instanceof RegisterChosen;
                RegisterChosen registerChosen = (RegisterChosen) message;
                //Der Server quittiert die Auswahl und schickt diese zur Information an alle Clients.
                RegisterChosen.RegisterChosenBody registerChosenBody= registerChosen.getMessageBody();
                int clientID = registerChosenBody.getClientID();
                int register = registerChosenBody.getRegister();
                break;

            case MessageType.connectionUpdate:
                ConnectionUpdate connectionUpdate = (ConnectionUpdate) message;
                int targetID = connectionUpdate.getMessageBody().getClientID();
                removeDisconnectedClient(targetID);
                break;
        }
    }

    /**
     * @author Minghao Li
     * handle chat message,connect to Gui to show the chat messaage.
     */
    public void receiveChat(String msg){
        String fromName = this.getNameById(getFromId());
        if(LobbyViewModel.getWindowName().equals("Lobby")) {
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
    /**
     * @author: Li, Nik
     * @param takenX takenY playerID
     * when Client receive a startingPoint x,y from other players,keep them in a Hashmap and a list.
     */
    public void startingPositionAdd(int takenX, int takenY, int playerId){
        if(board.equals("DeathTrap")){
            if(takenX==1 && takenY==11){
                startNumbers.add(1);
                IdStartPoint.put(playerId,1);
            }
            if(takenX==3 && takenY==12){
                startNumbers.add(2);
                IdStartPoint.put(playerId,2);
            }
            if(takenX==4 && takenY==11){
                startNumbers.add(3);
                IdStartPoint.put(playerId,3);
            }
            if (takenX==5 && takenY==11){
                startNumbers.add(4);
                IdStartPoint.put(playerId,4);
            }
            if(takenX==6 && takenY==12){
                startNumbers.add(5);
                IdStartPoint.put(playerId,5);
            }
            if (takenX==8 && takenY==11){
                startNumbers.add(6);
                IdStartPoint.put(playerId,6);
            }
        }else {
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

    public String getTurnDirection() {
        return turnDirection;
    }

    public String getDirectionById(int id){
        return getIdDirection().get(id);
    }

    public Map<Integer, String> getIdDirection() {
        return IdDirection;
    }

    public void setIdDirection(Map<Integer, String> idDirection) {
        IdDirection = idDirection;
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

    public void setTimer(boolean settingTimer){
        this.timerStarted = settingTimer;
    }

    public String[] getFilledRegister() {
        return filledRegister;
    }

    public List<Integer> getIdList() {
        return IdList;
    }

    public boolean isDoRobotLaser() {
        return doRobotLaser;
    }

    public String[] getDamageDecks() {
        return damageDecks;
    }

    public int getDamageCount() {
        return damageCount;
    }

    public boolean isPickDamage() {
        return pickDamage;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setMaps(String[] maps) {
        this.maps = maps;
    }

    public int getWinnerID() {
        return winnerID;
    }
    public void setPickDamage(boolean pickDamage){
        this.pickDamage = pickDamage;
    }

    public void setDamageCount(int damageCount) {
        this.damageCount = damageCount;
    }

    public void setDamageDecks(String[] damageDecks) {
        this.damageDecks = damageDecks;
    }

    public void setDoRobotLaser(boolean doRobotLaser) {
        this.doRobotLaser = doRobotLaser;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public String[] getCards() {
        return cards;
    }

    /*
    protected Map<Integer,Integer[]> IdPosition = new HashMap<>();
    protected List<Integer> IdList = new ArrayList<>();
    protected Map<Integer,Integer> IdRobot = new HashMap<>();
    protected Map<Integer,Integer>IdStartPoint = new HashMap<>();
    protected Map<Integer,String> IdDirection=new HashMap<>();
    protected Map<Integer,String> IdCardPlayed = new HashMap<>();
    protected Map<Integer,Boolean> IdReady = new HashMap<>();
    protected Map<String,Integer> IdName = new HashMap<>();
    ?? protected List<Integer> robotNumbers = new ArrayList<>();
    ?? protected List<Integer> startNumbers = new ArrayList<>();
    ?? protected List<Boolean> readyList=new ArrayList<>();
     */
    /**
     * @author dai,Li
     * when a Client disconnect,remove his information which is kept in other ClientReceive
     */
    public void removeDisconnectedClient(int targetID){
        if(IdStartPoint.containsKey(targetID)){
            this.startNumbers.remove((Integer) getStartPointById(targetID));
        }
        this.IdName.remove(getNameById(targetID));
        this.IdPosition.remove(targetID);
        this.IdRobot.remove(targetID);
        this.IdStartPoint.remove(targetID);
        this.IdDirection.remove(targetID);
        this.IdCardPlayed.remove(targetID);
        this.IdReady.remove(targetID);
        this.IdList.remove((Integer) targetID);
    }
}