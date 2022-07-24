package server;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import client.ClientReceive;
import client.Log;
import protocol.*;
import com.google.gson.Gson;
import protocol.ActivePhase.ActivePhaseBody;
import protocol.Animation.AnimationBody;
import protocol.CardPlayed.CardPlayedBody;
import protocol.CardSelected.CardSelectedBody;
import protocol.ChooseRegister.ChooseRegisterBody;
import protocol.HelloServer.HelloServerBody;
import protocol.PlayCard.PlayCardBody;
import protocol.PlayerValues.PlayerValuesBody;
import protocol.ProtocolFormat.Message;
import protocol.RebootDirection.RebootDirectionBody;
import protocol.SelectedDamage.SelectedDamageBody;
import protocol.ProtocolFormat.MessageType;
import protocol.SendChat.SendChatBody;
import protocol.SetStartingPoint.SetStartingPointBody;
import protocol.SetStatus.SetStatusBody;
import protocol.MapSelected.MapSelectedBody;
import protocol.SelectedCard.SelectedCardBody;
import server.BoardElement.BoardElem;
import server.BoardTypes.*;
import server.CardTypes.Card;
import server.Control.Direction;
import server.Control.DisconnectionController;
import server.Control.Position;
import server.Control.Timer;
import server.Game.GameState;
import server.Game.RR;
import server.Player.Player;


public class ServerThread implements Runnable {

    public static final String ANSI_GREEN = "\u001B[32m";
    private static final String PROTOCOL = "Version 2.0";
    private static final String[] MAPS = new String[]
        {"DizzyHighway","ExtraCrispy","DeathTrap","LostBearings","Twister"};
    private Socket clientSocket;
    private BufferedReader readInput;
    private  BufferedWriter writeOutput;
    public static boolean gameActive = false;
    private boolean alive;
    private static List<ServerThread> connectedClients = new ArrayList<ServerThread>();
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
    private String[] damageCards;
    private int registerPointer = 0;

    public ServerThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        connectedClients = Server.getConnectedClients();
        try {
            readInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writeOutput = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Message helloClient = new HelloClient(PROTOCOL);
            String HelloClient = helloClient.toString();
            sendMessage(HelloClient);
        } catch (IOException e) {
            elegantClose();
        }
    }
    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String clientMessage = readInput.readLine();
                //System.out.println(clientMessage + "----------original message");  //test
                Message message = wrapMessage(clientMessage);
                //System.out.println("-----------------------------------------------------");  //test
                Server.getLogger().info(ANSI_GREEN + message.toString() + "wrapped message");  //test
                identifyMessage(message);
            }
            elegantClose();
        } catch (IOException e) {
            elegantClose();
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

        return new ErrorMessage("Error when parsing String to Message");
    }

    private synchronized void identifyMessage(Message message) {
        /*
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        Gson gson = gsonBuilder.create();

         */

        String messageType = message.getMessageType();
        //MessageBody messageBody = gson.fromJson(body,MessageBody.class);

        switch (messageType){
            case MessageType.helloServer:
                HelloServer helloServer = (HelloServer) message;
                HelloServerBody helloServerBody = helloServer.getMessageBody();
                String clientProtocol = helloServerBody.getProtocol();

                if(PROTOCOL.equals(clientProtocol)){
                    clientID = helloServerBody.getClientID();
                    group = helloServerBody.getGroup();
                    isAI = helloServerBody.isAI();
                    setAI(isAI);
                    /*
                    if(isAI){
                        player = new Player("AI_" + clientID, clientID);
                        int availableFigure = 0;
                        outer: while(true){
                            inner: for(ServerThread serverThread : connectedClients){
                                if(availableFigure == serverThread.getFigure()){
                                    availableFigure++;
                                }else {
                                    break inner;
                                }
                            }
                            break outer;
                        }
                        player.setOwnRobot(figure);
                    }

                     */
                    //send information from clients who are already connected in to the new client
                    for (ServerThread serverThread: connectedClients){
                        int othersID = serverThread.getID();
                        String othersName = serverThread.getName();
                        int othersFigure = serverThread.getFigure();
                        if(othersID != clientID && othersFigure!=0){
                            sendMessage( new PlayerAdded(othersID,othersName,othersFigure).toString());
                        }
                    }
                    if(isAI) {
                        sendMessage(new ReceivedChat("Choose your robot", -1, true).toString());
                    }
                    Timer.countDown(5);
                    sendMessage(new Alive().toString());
                }else {
                    sendMessage(new ErrorMessage("your protocol version is unsupported").toString());
                }
                break;

            case MessageType.alive:
                DisconnectionController.updateAlive(this);
                //Timer.countDown(5);
                //sendMessage(new Alive().toString());
                break;

            case MessageType.playerValues:
                PlayerValues playerValues = (PlayerValues) message;
                PlayerValuesBody playerValuesBody = playerValues.getMessageBody();
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
                    player.getOwnRobot().setOwner(player);
                    sendToAll(new PlayerAdded(clientID,name,figure).toString());
                    for (ServerThread serverThread: connectedClients) {
                        int othersID = serverThread.getID();
                        boolean othersReady = serverThread.isReady();
                        if (othersID != clientID && othersReady) {
                            sendMessage(new PlayerStatus(othersID,othersReady).toString());
                        }
                    }
                }else {
                    sendMessage(new ErrorMessage("this robot has been taken").toString());
                }
                break;

            case MessageType.setStatus:
                SetStatus setStatus = (SetStatus) message;
                SetStatusBody setStatusBody = setStatus.getMessageBody();
                ready = setStatusBody.isReady();
                player.setReady(ready);
                if(ready) {
                    sendToAll(new PlayerStatus(clientID, true).toString());
                    if(firstPlayerReady() != null){
                        if (firstPlayerReady().equals(player)) {
                            sendMessage(new SelectMap(MAPS).toString());
                        }
                    }
                    if (allPlayerReady() && connectedClients.size()>=2 && board != null){
                        startGame(this.board);
                    }
                }
                else{
                    sendToAll(new PlayerStatus(clientID, false).toString());
                    if(nextPlayerReady() != null){
                        int playerID=nextPlayerReady().getClientId();
                        sendPrivate(new SelectMap(MAPS).toString(),playerID);
                    }
                }

                break;

            case MessageType.mapSelected:
                MapSelected mapSelected = (MapSelected) message;
                MapSelectedBody mapSelectedBody = mapSelected.getMessageBody();
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
                    case "Twister":
                        board = new Twister();
                        sendToAll(new MapSelected("Twister").toString());
                        break;
                }
                for(ServerThread serverThread : connectedClients){
                    serverThread.setBoard(this.board);
                    if (this.board.getName().equals("DeathTrap")){
                        serverThread.getPlayer().getOwnRobot().setDeathTrapDirection();
                    }
                }
                if (allPlayerReady() && connectedClients.size() >= 2 && board != null){
                    startGame(this.board);
                }
                break;

            case MessageType.sendChat:
                SendChat sendChat = (SendChat) message;
                SendChatBody sendChatBody = sendChat.getMessageBody();
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
                PlayCard playCard = (PlayCard) message;
                PlayCardBody playCardBody = playCard.getMessageBody();
                card = playCardBody.getCard();
                currentGame.getActiveCards().add(card);
                currentGame.notifyCurrentPlayer();
                //notifyAll();
                sendToAll(new CardPlayed(clientID,card).toString());
                break;

            case MessageType.setStartingPoint:
                SetStartingPoint setStartingPoint = (SetStartingPoint) message;
                SetStartingPointBody setStartingPointBody = setStartingPoint.getMessageBody();
                int x = setStartingPointBody.getX();
                int y = setStartingPointBody.getY();
                Position tempPosition = new Position(x,y,board);
                Server.getLogger().info(ANSI_GREEN + "enter here");//test
                //System.out.println(tempPosition.getX());  //test
                /*boolean flagInSetStartingPoint = true;
                for(ServerThread serverThread : connectedClients){
                    if(serverThread.getStartingPosition() != null) {
                        if (serverThread.getStartingPosition().equals(tempPosition)) {
                            flagInSetStartingPoint = false;
                        }
                    }
                }

                 */
               // if(flagInSetStartingPoint){
                    startingPosition = tempPosition;
                    Server.getLogger().info(ANSI_GREEN + startingPosition.getX());  //test
                    sendToAll(new StartingPointTaken(x,y,clientID).toString());
                    this.player.getOwnRobot().setStartPosition(this.startingPosition);
                    Server.getLogger().info(ANSI_GREEN + player.getOwnRobot().getStartPosition().getX());
               // }else {
                    //sendMessage(new ErrorMessage("this position has been taken").toString());
                //}
                break;

            case MessageType.activePhase:
                ActivePhase activePhase = (ActivePhase) message;
                ActivePhaseBody activePhaseBody = activePhase.getMessageBody();
                phase = activePhaseBody.getPhase();
                break;

            case MessageType.animation:
                Animation animation = (Animation) message;
                AnimationBody animationBody = animation.getMessageBody();
                type = animationBody.getType();
                break;

            case MessageType.cardPlayed:
                CardPlayed cardPlayed = (CardPlayed) message;
                CardPlayedBody cardPlayedBody = cardPlayed.getMessageBody();
                clientID = cardPlayedBody.getClientID();
                card = cardPlayedBody.getCard();
                break;

            case MessageType.cardSelected:
                CardSelected cardSelected = (CardSelected) message;
                CardSelectedBody cardSelectedBody = cardSelected.getMessageBody();
                clientID = cardSelectedBody.getClientID();
                register = cardSelectedBody.getRegister();
                break;

            case MessageType.selectedCard:
                SelectedCard selectedCard = (SelectedCard) message;
                SelectedCardBody selectedCardBody = selectedCard.getMessageBody();
                if(currentGame != null && currentGame.getCurrentState().equals(GameState.ProgrammingPhase)){
                    register = selectedCardBody.getRegister();
                    card = selectedCardBody.getCard();
                    int playerID = selectedCardBody.getClientID();
                    if(playerID == clientID) {
                        int i = 0;

                        while (i < player.getHands().size()) {
                            Card currentCard = player.getHands().get(i);
                            if (currentCard != null && currentCard.getCardName().equals(card)) {
                                player.getRegister().add(currentCard);
                                player.getHands().remove(currentCard);
                                i = 0;
                                break;
                            } else {  //if remove one Card in handsList,don't do i++
                                i++;
                            }
                        }
                        String cardSelectedMsg = "";
                        if (registerPointer < 4) {
                            cardSelectedMsg = new CardSelected(clientID, register, false).toString();
                        } else {
                            cardSelectedMsg = new CardSelected(clientID, register, true).toString();
                        }
                        registerPointer++;
                        sendToAll(cardSelectedMsg);
                    }
                }
                break;

            case MessageType.selectionFinished:
                sendToAll(message.toString());
                sendToAll(new TimerStarted().toString());
                break;

            case MessageType.selectedDamage:
                SelectedDamage selectedDamage = (SelectedDamage) message;
                SelectedDamageBody selectedDamageBody = selectedDamage.getMessageBody();
                if(currentGame != null){
                 damageCards = selectedDamageBody.getCards();
                 if(damageCards!=null) {
                     for (String damageCard : damageCards) {
                         player.drawDamage(damageCard, 1);
                     }
                 }


                }
                break;

            case MessageType.rebootDirection:
                RebootDirection rebootDirection = (RebootDirection) message;
                RebootDirectionBody rebootDirectionBody = rebootDirection.getMessageBody();
                String tempDirection = rebootDirectionBody.getDirection().toLowerCase().trim();
                switch (tempDirection){
                    case "up":
                        this.player.getOwnRobot().setRebootDirection(Direction.UP);
                        break;
                    case "right":
                        this.player.getOwnRobot().setRebootDirection(Direction.RIGHT);
                        break;
                    case "down":
                        this.player.getOwnRobot().setRebootDirection(Direction.DOWN);
                        break;
                    case "left":
                        this.player.getOwnRobot().setRebootDirection(Direction.LEFT);
                        break;
                }
                break;

            case MessageType.chooseRegister:
                ChooseRegister chooseRegister = (ChooseRegister) message;
                ChooseRegisterBody chooseRegisterBody = chooseRegister.getMessageBody();
                int tempRegister = chooseRegisterBody.getRegister();
                sendToAll(new RegisterChosen(clientID,tempRegister).toString());
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
                serverThread.setBoard(board);
                serverThread.setCurrentGame(game);
                serverThread.getPlayer().setCurrentGame(game);
                serverThread.getPlayer().getOwnRobot().setCurrentGame(game);
                game.getActiveClients().add(serverThread);
            }
            for(BoardElem[][] elem2d : board.map){
                for(BoardElem[] elem1d : elem2d){
                    for(BoardElem elem : elem1d){
                        elem.setGameBoard(board);
                        elem.setCurrentGame(game);
                    }
                }
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
    public Player nextPlayerReady(){
        int readyPlayer = 0;
        Player firstReadyPlayer = null;
        for(ServerThread target: connectedClients) {
            if (target.isReady()) {
                readyPlayer++;
                firstReadyPlayer = target.getPlayer();
                break;
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setRegisterPointer(int registerPointer) {
        this.registerPointer = registerPointer;
    }

    public void elegantClose(){
        connectedClients.remove(this);
        sendToAll(new ConnectionUpdate(clientID,false,"remove").toString());
        try {
            readInput.close();
            writeOutput.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}