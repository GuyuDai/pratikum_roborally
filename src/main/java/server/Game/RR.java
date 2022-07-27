package server.Game;

import protocol.*;
import protocol.ProtocolFormat.*;
import server.BoardElement.*;
import server.BoardElement.CheckPoint;
import server.BoardTypes.*;
import server.CardTypes.*;
import server.Control.Timer;
import server.Control.*;
import server.Deck.*;
import server.Player.*;
import server.*;

import java.util.*;
import java.util.concurrent.*;

public class RR extends Thread implements GameLogic {
  private Boolean isGoingOn;
  public Controller controller;
  protected CopyOnWriteArrayList<Player> activePlayers = new CopyOnWriteArrayList<Player>();
  protected CopyOnWriteArrayList<ServerThread> activeClients = new CopyOnWriteArrayList<ServerThread>();
  protected Player playerInCurrentTurn;
  protected Board gameBoard;
  public GameDeck gameDeck;
  protected GameState currentState;
  protected Position positionReboot;
  protected Position positionAntenna;
  protected int playerInListPosition = 0;
  protected int activePhase;  //0 => Aufbauphase, 1 => Upgradephase, 2 => Programmierphase, 3 => Aktivierungsphase
  private int finishedPlayers = 0;  //this attribution is used to check whether all player have down a certain behavior
  protected CopyOnWriteArrayList<Integer> checkPoints = new CopyOnWriteArrayList<Integer>();
  private CopyOnWriteArrayList<String> activeCards = new CopyOnWriteArrayList<String>();
  private int checkPointMovementCount = 0;



  public RR(Board gameBoard) {
    this.gameBoard = gameBoard;
    this.isGoingOn = false;
    this.gameDeck = new GameDeck();

    for (int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for (int j = 0; j <= this.getGameBoard().getWidth(); j++) {
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem.getName().equals("Antenna")) {
            //this.positionAntenna = boardElem.getPosition();
            this.positionAntenna = new Position(j,i,gameBoard);
          }
        }
      }
    }

    for (int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for (int j = 0; j <= this.getGameBoard().getWidth(); j++) {
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem.getName().equals("Reboot")) {
            //this.positionCheckPoint = boardElem.getPosition();
            this.positionReboot = new Position(j,i,gameBoard);
          }
        }
      }
    }
  }

  @Override
  public void run() {
    this.setName("GameThread");
    this.controller = new Controller(this);
    sendMessageToAll(new GameStarted(this.gameBoard));
    gameProgress();
  }

  public synchronized void startGame() {
    this.currentState = GameState.GameInitializing;
    this.start();
  }

  public Player getPlayerInCurrentTurn() {
    return playerInCurrentTurn;
  }

  public void setPlayerInCurrentTurn(Player player) {
    this.playerInCurrentTurn = player;
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

  public Position getPositionReboot() {
    return positionReboot;
  }

  public void setCurrentState(GameState currentState) {
    this.currentState = currentState;
  }

  public CopyOnWriteArrayList<ServerThread> getActiveClients() {
    return activeClients;
  }

  public CopyOnWriteArrayList<String> getActiveCards() {
    return activeCards;
  }

  public void setActiveCards(CopyOnWriteArrayList<String> activeCards) {
    this.activeCards = activeCards;
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

  public synchronized void gameProgress() {  //reminder synchronized
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
        doGameEnding();
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
    BoardElem activeBoardElem1 = playerInCurrentTurn.getOwnRobot().getCurrentPosition().getTile();
    BoardElem activeBoardElem2 = playerInCurrentTurn.getOwnRobot().getCurrentPosition().getSecondTile();
    if(activeBoardElem1 != null){
      activeBoardElem1.action();
    }
    if(activeBoardElem2 != null){
      activeBoardElem2.action();
    }
  }

  public void setPriority() {
    System.out.println("enter setPriority");  //test
    for (Player player : activePlayers) {
      player.setPriority(Math.abs(this.getPositionAntenna().getX() - player.getOwnRobot().getCurrentPosition().getX())
              + Math.abs(this.getPositionAntenna().getY() - player.getOwnRobot().getCurrentPosition().getY()));
    }
    System.out.println("leave SP");  //test
  }

  public void reorderPlayer() {
    System.out.println("entry RP");  //test
    CopyOnWriteArrayList<Player> temp = new CopyOnWriteArrayList<Player>();
    int i = 1;
    while (activePlayers.size() > 0) {
      for (Player player : activePlayers) {
        if (player.getPriority() == i) {
          temp.add(player);
          activePlayers.remove(player);
        }
      }
      i++;
      System.out.println(i);//test
    }
    activePlayers = temp;
    System.out.println("leave RP");  //test

  }

  public void doGameInitializing(){
    for(ServerThread serverThread : getActiveClients()){
      activePlayers.add(serverThread.getPlayer());
    }
    for(Player player : getActivePlayers()){
      player.setCurrentGame(this);
    }

    switch (gameBoard.getName()){
      case "DizzyHighway":
        checkPoints.add(1);
        break;

      case "ExtraCrispy":
      case "LostBearings":
      case "Twister":
        checkPoints.add(1);
        checkPoints.add(2);
        checkPoints.add(3);
        checkPoints.add(4);
        break;

      case "DeathTrap":
        checkPoints.add(1);
        checkPoints.add(2);
        checkPoints.add(3);
        checkPoints.add(4);
        checkPoints.add(5);
        break;
    }
    for(Player player : getActivePlayers()){
      player.setCheckPoints(this.checkPoints);
    }
    nextGameState();
  }

  public void doBulidingPhase() {
    System.out.println("enter building phase");  //test
    this.activePhase = 0;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("choose your starting position",-1,false));

    while (finishedPlayers < activeClients.size()){
      finishedPlayers = 0;
      for(ServerThread serverThread : getActiveClients()){
        if(serverThread.getPlayer().getOwnRobot().getStartPosition() != null){
          finishedPlayers ++;
        }
      }
    }
    System.out.println("leave while");//test

    for(Player player : getActivePlayers()){
      player.getOwnRobot().setCurrentPosition(player.getOwnRobot().getStartPosition());
    }
    System.out.println("leave for");//test
    setPriority();
    reorderPlayer();
    nextGameState();
    System.out.println("leave building phase");  //test
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
      player.draw(9-player.getHands().size());
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
        //System.out.println(player.getName() + player.getRegister().size());  //test
        if(player.getRegister().size() == 5){
          finishedPlayers++;
        }
      }
    }
    finishedPlayers=0;
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
    for(ServerThread serverThread : activeClients){
      serverThread.setRegisterPointer(0);
    }
    nextGameState();
  }
  public void DoActivationPhase () {
    this.activePhase = 3;
    sendMessageToAll(new ActivePhase(activePhase));
    sendMessageToAll(new ReceivedChat("Activation Phase starts",-1,false));

    //if(flagInActivation && i < 5){}  //reminder
    for (int i = 0; i < 5; i++) {  //round i
      sendMessageToAll(new ReceivedChat("Round: " + i,-1,false));
      //wait for each player click playRegister
      notifyCurrentPlayer();
      System.out.println("enter while loop");//test
      while(true){
        if(activeCards.size() == activePlayers.size()){
          break;
        }
      }
      //send protocol message  //from now on, only activePlayers can be used, because it has the correct order
      ActiveCard[] messageActiveCards = new ActiveCard[activePlayers.size()];
      int index = 0;
      for(Player player : activePlayers){
        ActiveCard activeCard = new ActiveCard(player.clientID,player.getRegister().get(0));
        messageActiveCards[index] = activeCard;
        index++;
      }
      sendMessageToAll(new CurrentCards(messageActiveCards));
      //active
      for (Player player : activePlayers) {
        setPlayerInCurrentTurn(player);
        //sendMessageToAll(new CurrentPlayer(playerInCurrentTurn.clientID));
        sendMessageToAll(new ReceivedChat("Player" + player.clientID + " actions",-1,false));
        playerInCurrentTurn.getRegister().get(i).action();
        checkPointMove();  //before interact with tiles
        interactWithTile();
        //this.getServerThreadById(player.getClientId()).setClickPlayCard(false);
      }
      //send protocol message
      for(Player player : activePlayers){
        controller.robotLaserController(player.getOwnRobot());
        int newIndex = 0;
        for(Card card : player.getRegister()){
          sendMessageToClient(new ReplaceCard(newIndex,card.getCardName(),player.clientID),getServerThreadById(player.clientID));
          newIndex++;
        }
      }
      //reorder players
      activeCards = new CopyOnWriteArrayList<String>();
      setPriority();
      reorderPlayer();
    }
    nextGameState();
  }

  public void doGameEnding() {

  }

  private void checkPointMove(){
    if(gameBoard.getName().equals("Twister")){
      int tempID;
      checkPointMovementCount++;
      switch (checkPointMovementCount){
        case 0:
          tempID = gameBoard.getBoardElem(2,6,1).getCount();
          gameBoard.getMap()[2][6][1] = new Empty(this);
          gameBoard.getMap()[3][5][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,3,5));

          tempID = gameBoard.getBoardElem(2,9,1).getCount();
          gameBoard.getMap()[2][9][1] = new Empty(this);
          gameBoard.getMap()[1][10][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,1,10));

          tempID = gameBoard.getBoardElem(6,5,1).getCount();
          gameBoard.getMap()[6][5][1] = new Empty(this);
          gameBoard.getMap()[7][6][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,7,6));

          tempID = gameBoard.getBoardElem(8,10,1).getCount();
          gameBoard.getMap()[8][10][1] = new Empty(this);
          gameBoard.getMap()[7][9][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,7,9));
          break;

        case 1:
          tempID = gameBoard.getBoardElem(3,5,1).getCount();
          gameBoard.getMap()[3][5][1] = new Empty(this);
          gameBoard.getMap()[2][4][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,2,4));

          tempID = gameBoard.getBoardElem(1,10,1).getCount();
          gameBoard.getMap()[1][10][1] = new Empty(this);
          gameBoard.getMap()[2][11][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,2,11));

          tempID = gameBoard.getBoardElem(7,6,1).getCount();
          gameBoard.getMap()[7][6][1] = new Empty(this);
          gameBoard.getMap()[8][5][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,8,5));

          tempID = gameBoard.getBoardElem(7,9,1).getCount();
          gameBoard.getMap()[7][9][1] = new Empty(this);
          gameBoard.getMap()[6][10][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,6,10));

          break;

        case 2:
          tempID = gameBoard.getBoardElem(2,4,1).getCount();
          gameBoard.getMap()[2][4][1] = new Empty(this);
          gameBoard.getMap()[1][5][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,1,5));

          tempID = gameBoard.getBoardElem(2,11,1).getCount();
          gameBoard.getMap()[2][11][1] = new Empty(this);
          gameBoard.getMap()[3][10][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,3,10));

          tempID = gameBoard.getBoardElem(8,5,1).getCount();
          gameBoard.getMap()[8][5][1] = new Empty(this);
          gameBoard.getMap()[7][4][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,7,4));

          tempID = gameBoard.getBoardElem(6,10,1).getCount();
          gameBoard.getMap()[6][10][1] = new Empty(this);
          gameBoard.getMap()[7][11][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,7,11));

          break;

        case 3:
          tempID = gameBoard.getBoardElem(1,5,1).getCount();
          gameBoard.getMap()[1][5][1] = new Empty(this);
          gameBoard.getMap()[2][6][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,2,6));

          tempID = gameBoard.getBoardElem(3,10,1).getCount();
          gameBoard.getMap()[3][10][1] = new Empty(this);
          gameBoard.getMap()[2][9][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,2,9));

          tempID = gameBoard.getBoardElem(7,4,1).getCount();
          gameBoard.getMap()[7][4][1] = new Empty(this);
          gameBoard.getMap()[6][5][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,6,5));

          tempID = gameBoard.getBoardElem(7,11,1).getCount();
          gameBoard.getMap()[7][11][1] = new Empty(this);
          gameBoard.getMap()[8][10][1] = new CheckPoint(this,tempID);
          sendMessageToAll(new CheckPointMoved(tempID,8,10));
          break;
      }
      //reset the counter
      if(checkPointMovementCount >= 3){
        checkPointMovementCount = 0;
      }
    }
  }

  public void notifyCurrentPlayer(){
    setPlayerInCurrentTurn(activePlayers.get(playerInListPosition));
    sendMessageToClient(new CurrentPlayer(playerInCurrentTurn.clientID),getServerThreadById(playerInCurrentTurn.clientID));
    playerInListPosition++;
    if (playerInListPosition >= activePlayers.size()) {
      playerInListPosition = 0;
    }

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

  public String[] getAvailablePiles(){
    ArrayList<String> tempAvailablePiles = new ArrayList<String>();
    if(!controller.isCardListEmpty(gameDeck.getSpamPile())){
      tempAvailablePiles.add("Spam");
    }
    if(!controller.isCardListEmpty(gameDeck.getWormPile())){
      tempAvailablePiles.add("Worm");
    }
    if(!controller.isCardListEmpty(gameDeck.getVirusPile())){
      tempAvailablePiles.add("Virus");
    }
    if(!controller.isCardListEmpty(gameDeck.getTrojanPile())){
      tempAvailablePiles.add("Trojan");
    }
    String[] availablePiles = tempAvailablePiles.toArray(new String[tempAvailablePiles.size()]);
    return availablePiles;
  }
}

