package server.Game;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import protocol.ActivePhase;
import protocol.CurrentCards;
import protocol.NotYourCards;
import protocol.ProtocolFormat.ActiveCard;
import protocol.ProtocolFormat.Message;
import protocol.ReceivedChat;
import protocol.ReplaceCard;
import protocol.TimerEnded;
import protocol.TimerStarted;
import protocol.YourCards;
import server.BoardElement.*;
import server.BoardTypes.*;
import server.CardTypes.Card;
import server.Control.*;
import server.Deck.GameDeck;
import server.Player.*;

import java.util.concurrent.*;
import server.ServerThread;

public class RR extends Thread implements GameLogic {
  private Boolean isGoingOn;
  public Controller controller;
  protected CopyOnWriteArrayList<Player> activePlayers = new CopyOnWriteArrayList<Player>();
  protected CopyOnWriteArrayList<ServerThread> activeClients = new CopyOnWriteArrayList<ServerThread>();
  protected Player playerInCurrentTurn;
  protected Board gameBoard;
  public GameDeck gameDeck;
  protected GameState currentState;
  protected Position positionCheckPoint;
  protected Position positionAntenna;
  protected int PlayerInListPosition = -1;
  protected int activePhase;  //0 => Aufbauphase, 1 => Upgradephase, 2 => Programmierphase, 3 => Aktivierungsphase
  private int finishedPlayers = 0;  //this attribution is used to check whether all player have down a certain behavior



  public RR(Board gameBoard) {
    this.gameBoard = gameBoard;
    this.isGoingOn = false;

    for (int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for (int j = 0; j <= this.getGameBoard().getWidth(); j++) {
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem.getName().equals("Antenna")) {
            //this.positionAntenna = boardElem.getPosition();
            this.positionAntenna = new Position(j,i);
          }
        }
      }
    }

    for (int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for (int j = 0; j <= this.getGameBoard().getWidth(); j++) {
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem.getName().equals("CheckPoint")) {
            //this.positionCheckPoint = boardElem.getPosition();
            this.positionCheckPoint = new Position(j,i);
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
    finishedPlayers = 0;
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
        boolean isGameEnd = controller.ifGameEnd();
        if(isGameEnd){
          setCurrentState(GameState.GameEnding);
        }else {
          setCurrentState(GameState.UpgradePhase);
        }
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

    while (finishedPlayers < activeClients.size()){
      finishedPlayers = 0;
      for(ServerThread serverThread : getActiveClients()){
        if(serverThread.getStartingPosition() != null){
          finishedPlayers ++;
        }
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
    this.activePhase = 2;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("Programming Phase starts",-1,false));
    //each player draws 9 cards
    for(Player player : getActivePlayers()){
      player.draw();
      String[] cards = new String[9];
      int i = 0;
      for(Card card : player.getHands()){
        cards[i] = card.getCardName();
        i++;
      }
      sendMessageToClient(new YourCards(cards),getServerThreadById(player.clientID));
      sendMessageWithout(new NotYourCards(player.clientID, player.getHands().size()),getServerThreadById(player.clientID));
    }
    //start programming
    sendMessageToAll(new ReceivedChat("fill your register",-1,false));
    //if there is a player already finished programming
    while(finishedPlayers == 0){
      for(Player player : activePlayers){
        if(player.getRegister().size() == 5){
          finishedPlayers++;
        }
      }
    }
    //timer starts
    sendMessageToAll(new TimerStarted());
    Timer.countDown(30);
    int[] clients = new int[activeClients.size()];
    int i = 0;
    for(ServerThread serverThread : activeClients){
      clients[i] = serverThread.getID();
      i++;
    }
    sendMessageToAll(new TimerEnded(clients));
    //timer ends and automatically fill registers which are not filled
    int count = 0;
    for(Player player : activePlayers){
      count = player.getRegister().size();
      while(count < 5){
        player.getRegister().add(count,player.getHands().get(0));
        player.getHands().remove(0);
        count++;
      }
    }
    //discard the remaining cards for each player
    for(Player player : activePlayers){
      player.discardHands();
    }
    //next phase
    nextGameState();
  }
  public void DoActivationPhase () {
    this.activePhase = 3;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("Programming Phase starts",-1,false));

    for (int i = 0; i < 5; i++) {  //round i
      //send protocol message
      ActiveCard[] activeCards = new ActiveCard[activePlayers.size()];
      int index = 0;
      for(Player player : activePlayers){
        ActiveCard activeCard = new ActiveCard(player.clientID,player.getRegister().get(0));
        activeCards[index] = activeCard;
        index++;
      }
      sendMessageToAll(new CurrentCards(activeCards));
      //active
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
        controller.robotLaserController(ownRobot);
        PlayerInListPosition++;
        if (PlayerInListPosition >= activePlayers.size()) {
          PlayerInListPosition = 0;
        }
      }
      //send protocol message
      for(Player player : activePlayers){
        int newIndex = 0;
        for(Card card : player.getRegister()){
          sendMessageToClient(new ReplaceCard(newIndex,card,player.clientID),getServerThreadById(player.clientID));
          newIndex++;
        }
      }
      //reorder players
      setPriority();
      reorderPlayer();
    }
    nextGameState();
  }

  public void DoGameEnding () {

  }

  public void sendMessageToClient(Message msg, ServerThread client) {
    client.sendMessage(msg.toString());
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

