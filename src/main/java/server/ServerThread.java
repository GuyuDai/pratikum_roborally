package server;


import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
import protocol.SelectedDamage.SelectedDamageBody;
import protocol.ProtocolFormat.MessageType;
import protocol.SendChat.SendChatBody;
import protocol.SetStartingPoint.SetStartingPointBody;
import protocol.SetStatus.SetStatusBody;
import protocol.MapSelected.MapSelectedBody;
import protocol.SelectedCard.SelectedCardBody;
import server.BoardTypes.*;
import server.CardTypes.Card;
import server.Control.Position;
import server.Control.Timer;
import server.Game.GameState;
import server.Game.RR;
import server.Player.Player;


public class ServerThread implements Runnable {

    private static final String PROTOCOL = "Version 1.0";
    private Socket clientSocket;
    private BufferedReader readInput;
    private  BufferedWriter writeOutput;
    public static boolean gameActive = false;
    private boolean alive;
    private static  List<ServerThread> connectedClients = new ArrayList<ServerThread>();
    private int phase;
    private String type;
    private int clientID;
    private String card;
    private int figure;
    private String name;
    private int register;
    private String group;
    private boolean isAI;
    private boolean ready;
    public static RR currentGame;
    private Board board;
    private String map;
    private Player player;
    private Position startingPosition;


    public ServerThread(Socket clientSocket) throws IOException {
        this. clientSocket = clientSocket;
        connectedClients = Server.getConnectedClients();
        try {
            readInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Message helloClient = new HelloClient(PROTOCOL);
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
                //System.out.println(clientMessage + "----------original message");  //test
                Message message = wrapMessage(clientMessage);
                //System.out.println("-----------------------------------------------------");  //test
                System.out.println(message.toString() + "wrapped message");  //test
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

    private synchronized void identifyMessage(Message message) {
        /*
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        Gson gson = gsonBuilder.create();

         */

        String messageType = message.getMessageType();
        String body = message.getMessageBody();
        //MessageBody messageBody = gson.fromJson(body,MessageBody.class);

        switch (messageType){
            case MessageType.helloServer:
                HelloServerBody helloServerBody = new Gson().fromJson(body,HelloServerBody.class);
                String clientProtocol = helloServerBody.getProtocol();

                if(PROTOCOL.equals(clientProtocol)){
                    clientID = helloServerBody.getClientID();
                    group = helloServerBody.getGroup();
                    isAI = helloServerBody.isAI();
                    setAI(isAI);
                    if(isAI){
                        player = new Player("AI", clientID);
                        player.setAI(isAI);
                    }
                    for (ServerThread serverThread: connectedClients){
                        int othersID=serverThread.getID();
                        String othersName=serverThread.getName();
                        int othersFigure=serverThread.getFigure();
                        if(othersID != clientID){
                            sendMessage( new PlayerAdded(othersID,othersName,othersFigure).toString());
                        }
                    }

                    Timer.countDown(5);
                    sendMessage(new Alive().toString());
                }else {
                    sendMessage(new ErrorMessage("your protocol version is unsupported").toString());
                }
                break;

            case MessageType.alive:
                Timer.countDown(5);
                sendMessage(new Alive().toString());
                break;

            case MessageType.playerValues:
                PlayerValuesBody playerValuesBody = new Gson().fromJson(body,PlayerValuesBody.class);
                boolean flagInPlayerValues = true;
                String tempName = playerValuesBody.getName();
                int tempFigure = playerValuesBody.getFigure();
                for(ServerThread serverThread: connectedClients){
                    if(serverThread.getFigure() == tempFigure){
                        flagInPlayerValues = false;
                    }
                }
                if(flagInPlayerValues){
                    this.name = tempName;
                    this.figure = tempFigure;
                    player = new Player(name, clientID);
                    player.setOwnRobot(figure);
                    sendToAll(
                        new PlayerAdded(clientID,name,figure).toString()
                );
                for (ServerThread serverThread: connectedClients) {
                    int othersID = serverThread.getID();
                    boolean othersReady = serverThread.isReady();
                    if (othersID != clientID && othersReady) {
                        sendMessage(new PlayerStatus(othersID,othersReady).toString());
                    }
                }

                    ;
                }else {
                    sendMessage(new ErrorMessage("this robot has been taken").toString());
                }
                break;

            case MessageType.setStatus:
                SetStatusBody setStatusBody = new Gson().fromJson(body,SetStatusBody.class);
                ready = setStatusBody.isReady();
                player.setReady(ready);
                sendToAll(new PlayerStatus(clientID,true).toString());
                if(firstPlayerReady() != null){
                    if (firstPlayerReady().equals(player)) {
                        sendMessage(new SelectMap(
                            new String[]{"DizzyHighway","ExtraCrispy","DeathTrap","LostBearings"}).toString());
                    }
                }
                if (allPlayerReady() && connectedClients.size()>=2 && board != null){
                    startGame(this.board);
                }
                break;

            case MessageType.mapSelected:
                MapSelectedBody mapSelectedBody = new Gson().fromJson(body, MapSelectedBody.class);
                map = mapSelectedBody.getMap();
                switch (map){
                    case "DizzyHighway":
                        board = new DizzyHighway();
                        sendToAll(new MapSelected("DizzyHighway").toString());
                        break;
                    case "ExtraCrispy":
                        board = new ExtraCrispy();
                        sendToAll(new MapSelected("ExtraCrispy").toString());
                        break;
                    case "LostBearings":
                        board = new LostBearings();
                        sendToAll(new MapSelected("LostBearings").toString());
                        break;
                    case "DeathTrap":
                        board = new DeathTrap();
                        sendToAll(new MapSelected("DeathTrap").toString());
                        break;
                }
                if (allPlayerReady() && connectedClients.size() >= 2 && board != null){
                    startGame(this.board);
                }
                break;

            case MessageType.sendChat:
                SendChatBody sendChatBody = new Gson().fromJson(body,SendChatBody.class);
                String msg = sendChatBody.getMessage();
                int targetId = sendChatBody.getTo();
                if(targetId == -1){
                    sendToAll(new ReceivedChat(msg,clientID,false).toString());
                }
                else {
                    sendPrivate(new ReceivedChat(msg,clientID,true).toString(),targetId);
                }
                break;

            case MessageType.playCard:
                PlayCardBody playCardBody = new Gson().fromJson(body,PlayCardBody.class);
                card = playCardBody.getCard();
                sendToAll(new CardPlayed(clientID,card).toString());
                break;

            case MessageType.setStartingPoint:
                SetStartingPointBody setStartingPointBody = new Gson().fromJson(body,SetStartingPointBody.class);
                int x = setStartingPointBody.getX();
                int y = setStartingPointBody.getY();
                Position tempPosition = new Position(x,y);
                System.out.println(tempPosition.getX());
                boolean flagInSetStartingPoint = true;
                for(ServerThread serverThread : connectedClients){
                    if(serverThread.getStartingPosition()!=null) {
                        if (serverThread.getStartingPosition().equals(tempPosition)) {
                            flagInSetStartingPoint = false;
                        }
                    }
                }
                if(flagInSetStartingPoint){
                    startingPosition = tempPosition;
                    System.out.println(startingPosition.getX());
                    sendToAll(new StartingPointTaken(x,y,clientID).toString());
                    this.player.getOwnRobot().setStartPosition(this.startingPosition);
                    System.out.println(player.getOwnRobot().getStartPosition().getX());
                }else {
                    sendMessage(new ErrorMessage("this position has been taken").toString());
                }
                break;

            case MessageType.activePhase:
                ActivePhaseBody activePhaseBody = new Gson().fromJson(body,ActivePhaseBody.class);
                phase = activePhaseBody.getPhase();
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

            case MessageType.selectCard:
                SelectedCardBody selectedCardBody = new Gson().fromJson(body,SelectedCardBody.class);
                if(currentGame != null && currentGame.getCurrentState().equals(GameState.ProgrammingPhase)){
                    register = selectedCardBody.getRegister();
                    card = selectedCardBody.getCard();
                    int i = 0;
                    while (i < player.getHands().size()) {
                        Card currentCard = player.getHands().get(i);
                        if (currentCard != null && currentCard.getCardName().equals(card)) {
                            player.getRegister().add(currentCard);
                            player.getHands().remove(currentCard);
                        }   //if remove one Card in handsList,don't do i++
                        else {
                            i++;
                        }
                        //TODO change DoProgrammingPhase later,player can do card selection here;
                    }
                    int filledCardNumber = player.getRegister().size();
                    String cardSelected="";
                    if (filledCardNumber<5){
                        cardSelected = new CardSelected(clientID, register, false).toString();
                    }
                    else {
                        cardSelected = new CardSelected(clientID, register, true).toString();
                    }
                    sendToAll(cardSelected);
                }
                break;
            case MessageType.selectionFinished:
                  sendToAll(new TimerStarted().toString());

            case MessageType.selectedDamage:
                SelectedDamageBody selectedDamageBody=new Gson().fromJson(body,SelectedDamageBody.class);
                if(currentGame!=null){
                 String[] damageCards=selectedDamageBody.getCards();
                 for(String damageCard : damageCards){
                     player.drawDamage(damageCard,1);
                    }
                }
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
    public void sendToAll(String msg){
        try {
            for (ServerThread serverThread : connectedClients) {
                serverThread.getWriteOutput().write(msg);
                serverThread.getWriteOutput().newLine();
                serverThread.getWriteOutput().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPrivate(String msg,int id){
        try {
            for (ServerThread serverThread : connectedClients) {
                int targetId=serverThread.getID();
                if(targetId==id) {
                    serverThread.getWriteOutput().write(msg);
                    serverThread.getWriteOutput().newLine();
                    serverThread.getWriteOutput().flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame(Board board){
        if(currentGame == null){
            RR game = new RR(board);
            for(ServerThread serverThread : connectedClients){
                serverThread.setCurrentGame(game);
                serverThread.getPlayer().setCurrentGame(game);
                serverThread.getPlayer().getOwnRobot().setCurrentGame(game);
                game.getActiveClients().add(serverThread);
            }
            game.startGame();
        }else {
            sendMessage(new ErrorMessage("game already started").toString());
        }
    }

    public Player firstPlayerReady(){
        int readyPlayer = 0;
        Player firstReadyPlayer = null;
        for(ServerThread target: connectedClients) {
            if (target.isReady()) {
                readyPlayer++;
                firstReadyPlayer = target.getPlayer();
            }
        }
        if (readyPlayer==1){
            return firstReadyPlayer;
        }
        return null;
    }

    public boolean allPlayerReady(){
      for(ServerThread target: connectedClients){
          if(!target.isReady()){
              return false;
          }
      }
      return true;
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

    public void setCurrentGame(RR currentGame) {
        this.currentGame = currentGame;
    }

    public boolean isReady() {
        return ready;
    }

    public String getName() {
        return name;
    }

    public int getFigure() {
        return figure;
    }

    public Player getPlayer(){
        return player;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }
}