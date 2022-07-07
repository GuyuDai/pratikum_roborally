package server.Game;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import protocol.ActivePhase;
import protocol.NotYourCards;
import protocol.ProtocolFormat.Message;
import protocol.ReceivedChat;
import protocol.YourCards;
import server.BoardElement.*;
import server.BoardTypes.*;
import server.CardTypes.Card;
import server.Control.*;
import server.Player.*;

import java.util.concurrent.*;
import server.ServerThread;

public class RR extends Thread implements GameLogic {
  private Boolean isGoingOn;
  public Controller controller;
  protected CopyOnWriteArrayList<Player> activePlayers;
  protected CopyOnWriteArrayList<ServerThread> activeClients;
  protected Player playerInCurrentTurn;
  protected Board gameBoard;
  protected GameState currentState;
  protected Position positionCheckPoint;
  protected Position positionAntenna;
  protected int PlayerInListPosition = -1;
  protected int activePhase;  //0 => Aufbauphase, 1 => Upgradephase, 2 => Programmierphase, 3 => Aktivierungsphase


  public RR(Board gameBoard) {
    this.gameBoard = gameBoard;
    this.isGoingOn = false;

    for (int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for (int j = 0; j <= this.getGameBoard().getWidth(); j++) {
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem instanceof Antenna) {
            this.positionAntenna = boardElem.getPosition();
          }
        }
      }
    }

    for (int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for (int j = 0; j <= this.getGameBoard().getWidth(); j++) {
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem instanceof CheckPoint) {
            this.positionCheckPoint = boardElem.getPosition();
          }
        }
      }
    }
  }

  @Override
  public void run() {
    this.setName("GameThread");
    this.controller = new Controller(this);
    gameProgress();
  }

  public synchronized void startGame() {
    this.currentState = GameState.GameInitializing;
    this.start();
  }

  public Player getPlayerInCurrentTurn() {
    return playerInCurrentTurn;
  }


  public void setPlayerInCurrentTurn(int ListPosition) {
    this.playerInCurrentTurn = activePlayers.get(ListPosition);
  }

  public CopyOnWriteArrayList<Player> getActivePlayers() {
    return activePlayers;
  }

  public Board getGameBoard() {
    return gameBoard;
  }

  public Controller getController() {
    return controller;
  }

  public GameState getCurrentState() {
    return currentState;
  }

  public Position getPositionAntenna() {
    return positionAntenna;
  }

  public Position getPositionCheckPoint() {
    return positionCheckPoint;
  }

  public void setCurrentState(GameState currentState) {
    this.currentState = currentState;
  }

  public CopyOnWriteArrayList<ServerThread> getActiveClients() {
    return activeClients;
  }

  public void nextGameState() {
    switch (currentState) {
      case GameInitializing:
        this.isGoingOn = true;
        //sendToAll(new Gson().toJson(new ActiveGameState(GameState.GameInitializing)));
        setCurrentState(GameState.GameStarting);
        break;

      case GameStarting:
        //sendToAll(new Gson().toJson(new ActiveGameState(GameState.GameStarting)));
        setCurrentState(GameState.UpgradePhase);
        break;


      case UpgradePhase:
        //sendToAll(new Gson().toJson(new ActiveGameState(GameState.UpgradePhase)));
        setCurrentState(GameState.ProgrammingPhase);
        break;

      case ProgrammingPhase:
        //sendToAll(new Gson().toJson(new ActiveGameState(GameState.ProgrammingPhase)));
        setCurrentState(GameState.ActivationPhase);
        break;

      case ActivationPhase:
        //sendToAll(new Gson().toJson(new ActiveGameState(GameState.ActivationPhase)));
        setCurrentState(GameState.GameEnding);
        break;

      case GameEnding:
        //sendToAll(new Gson().toJson(new ActiveGameState(GameState.GameEnding)));
        this.isGoingOn = false;
        break;
    }
  }

  public void setGameRunning(boolean b) {
    isGoingOn = b;
  }

  public boolean isGameRunning() {
    return isGoingOn;
  }

  public boolean joinGame(Player player) {
    player = controller.joinGameCheck(player);
    if (player == null) {
      return false;
    }
    activePlayers.add(player);
    player.setCurrentGame(this);
    return true;
  }

  public void gameProgress() {
    if (currentState == GameState.GameInitializing) {
      doGameInitializing();
    }
    while (isGoingOn) {
      if (currentState == GameState.GameStarting) {
        doBulidingPhase();
      }
      if (currentState == GameState.UpgradePhase) {
        doUpgradePhase();
      }
      if (currentState == GameState.ProgrammingPhase) {
        DoProgrammingPhase();
      }
      if (currentState == GameState.ActivationPhase) {
        DoActivationPhase();
      }
      if (currentState == GameState.GameEnding) {
        DoGameEnding();
      }
    }

  }

  public boolean leaveGame(Player player) {
    Player removePlayer = null;
    for (Player playerInList : activePlayers) {
      if (playerInList.identifyPlayer(player.getName())) {
        removePlayer = playerInList;
      }
    }
    if (removePlayer != null) {
      activePlayers.remove(removePlayer);
      return true;
    } else {
      return false;
    }
  }

  public void interactWithTile() {
    this.getPlayerInCurrentTurn().getOwnRobot().getCurrentPosition().getTile().action();
  }

  public void setPriority() {
    for (Player player : activePlayers) {
      player.setPriority(Math.abs(this.getPositionAntenna().getX() - player.getOwnRobot().getCurrentPosition().getX())
              + Math.abs(this.getPositionAntenna().getY() - player.getOwnRobot().getCurrentPosition().getY()));
    }
  }

  public void reorderPlayer() {
    CopyOnWriteArrayList<Player> temp = new CopyOnWriteArrayList<Player>();
    while (activePlayers.size() > 0) {
      int i = 1;
      for (Player player : activePlayers) {
        if (player.getPriority() == i) {
          temp.add(player);
          activePlayers.remove(player);
        }
      }
      i++;
    }
    activePlayers = temp;
  }

  public void doGameInitializing(){
    for(ServerThread serverThread : getActiveClients()){
      activePlayers.add(serverThread.getPlayer());
    }
    for(Player player : getActivePlayers()){
      player.setCurrentGame(this);
    }
    nextGameState();
  }

  public void doBulidingPhase() {
    this.activePhase = 0;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("choose your starting position",-1,false));

    boolean allFinished = false;  //this part is to check if all player decided their starting position
    while (!allFinished){
      for(ServerThread serverThread : getActiveClients()){
        // TODO: 2022/7/7 there is some problems
        allFinished = (serverThread.getStartingPosition() != null);  //only each player has a valid starting position, this flag will be true
      }
    }

    for(Player player : getActivePlayers()){
      player.getOwnRobot().setCurrentPosition(player.getOwnRobot().getStartPosition());
    }
    setPriority();
    reorderPlayer();
    nextGameState();
  }


  public void doUpgradePhase() {
    this.activePhase = 1;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("Upgrade Phase starts",-1,false));

    //add when we have upgrade cards and upgrade deck

    nextGameState();
  }

  public void DoProgrammingPhase () {
    this.activePhase = 1;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("Programming Phase starts",-1,false));

    for(Player player : getActivePlayers()){  //each player draws 9 cards
      player.draw();
      String[] cards = new String[9];
      int i = 0;
      for(Card card : player.getHands()){
        cards[i] = card.getCardName();
        i ++;
      }
      sendMessageToClient(new YourCards(cards),getServerThreadById(player.clientID));
      sendMessageWithout(new NotYourCards(player.clientID, player.getHands().size()),getServerThreadById(player.clientID));
    }

    sendMessageToAll(new ReceivedChat("fill your register",-1,false));
    boolean timerStart = false;
    boolean allFinished = false;
    // TODO: 2022/7/7 not complete yet
    while(!allFinished){
      for(Player player : getActivePlayers()){
        if(player.getRegister().size() == 5){
          timerStart = true;
        }
      }
    }
  }
  public void DoActivationPhase () {
    for (int i = 0; i < 5; i++) {
      for (Player player : activePlayers) {
        setPlayerInCurrentTurn(PlayerInListPosition);
        player.getRegister().get(i).action();
        Robot ownRobot = player.getOwnRobot();
        int X = ownRobot.getCurrentPosition().getX();
        int Y = ownRobot.getCurrentPosition().getY();
        gameBoard.getBoardElem(X, Y, 0).action();
        if (gameBoard.getBoardElem(X, Y, 1) != null) {
          gameBoard.getBoardElem(X, Y, 1).action();
        }
        player.getCurrentGame().getController().robotLaserController(ownRobot);
        PlayerInListPosition++;
        if (PlayerInListPosition >= activePlayers.size()) {
          PlayerInListPosition = 0;
        }
      }
    }
  }

  public void DoGameEnding () {

  }

  public void sendMessageToClient(Message msg, ServerThread client) {
    try {
      BufferedWriter write = new BufferedWriter(new OutputStreamWriter(client.getClientSocket().getOutputStream()));
      String messageToSend = msg.toString();
      write.write(messageToSend);
      write.newLine();
      write.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendMessageToAll(Message msg){
    for(ServerThread client : this.activeClients){
      sendMessageToClient(msg, client);
    }
  }

  public void sendMessageWithout(Message msg, ServerThread without){
    for(ServerThread client : this.activeClients){
      if(client.getID() != without.getID()){
        sendMessageToClient(msg, client);
      }
    }
  }

  public ServerThread getServerThreadById(int id){
    for(ServerThread target : getActiveClients()){
      if(target.getID() == id){
        return target;
      }
    }
    return null;
  }
}

