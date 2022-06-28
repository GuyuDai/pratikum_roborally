package server.Game;

import java.util.concurrent.CopyOnWriteArrayList;
import server.BoardElement.Antenna;
import server.BoardElement.BoardElem;
import server.BoardElement.CheckPoint;
import server.BoardTypes.Board;
//import server.BoardTypes.DizzyHighway;
import server.Control.Controller;
import server.Control.Position;
//import server.Control.WrappedMessage;
import server.Player.GamePlayer;
//import server.PlayerOnline;
//import server.Control.WrappedMessage;
import server.Player.Robot;

public class RR extends Thread implements GameLogic {
  private Boolean isGoingOn;
  public Controller controller;
  protected CopyOnWriteArrayList<GamePlayer> activePlayers;
  protected GamePlayer playerInCurrentTurn;
  protected Board gameBoard;
  protected GameState currentState;

  protected Position positionCheckPoint;
  protected Position positionAntenna;

  protected int PlayerInListPosition =0;


  public RR(Board gameBoard){
    this.gameBoard = gameBoard;
    this.controller = new Controller();
    this.isGoingOn = false;

    for(int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for(int j=0;j <=this.getGameBoard().getWidth();j++)
        for (BoardElem boardElem : this.getGameBoard().getMap()[j][i]) {
          if (boardElem instanceof Antenna) {
            this.positionAntenna = boardElem.getPosition();
          }
        }
    }

    for(int i = 0; i <= this.getGameBoard().getHeight(); i++) {
      for(int j=0;j <= this.getGameBoard().getWidth();j++){
        for(BoardElem boardElem : this.getGameBoard().getMap()[j][i]){
          if(boardElem instanceof CheckPoint){
            this.positionCheckPoint = boardElem.getPosition();
          }
        }
      }
    }
  }

  @Override
  public void run() {
    this.setName("GameThread");
    gameProgress();
  }

  public synchronized void startGame() {
    this.currentState = GameState.GameInitializing;
    this.start();
  }

  public GamePlayer getPlayerInCurrentTurn() {
    return playerInCurrentTurn;
  }


  public void setPlayerInCurrentTurn(int ListPosition) {
    this.playerInCurrentTurn=activePlayers.get(ListPosition);
  }

  public CopyOnWriteArrayList<GamePlayer> getActivePlayers() {
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

  public void nextGameState(){
    switch (currentState){
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

  public boolean joinGame(GamePlayer player) {
     player = controller.joinGameCheck(player);
    if (player == null) {
      return false;
    }
    activePlayers.add(player);
    player.setCurrentGame(this);
    return true;
  }

  public void gameProgress() {
    if(currentState == GameState.GameInitializing){

    }
    while(isGoingOn) {
      if(currentState==GameState.GameStarting){
        DoGameStarting();
      }
      if (currentState==GameState.UpgradePhase){
        DoUpgradePhase();
      }
      if (currentState==GameState.ProgrammingPhase){
        DoProgrammingPhase();
      }
      if(currentState==GameState.ActivationPhase){
        DoActivationPhase();
      }
      if(currentState==GameState.GameEnding){
        DoGameEnding();
      }
    }

  }

  public boolean leaveGame(GamePlayer player) {
    GamePlayer removePlayer = null;
    for(GamePlayer playerInList : activePlayers){
      if(playerInList.identifyPlayer(player.getName())){
        removePlayer = playerInList;
      }
    }
    if(removePlayer != null){
      activePlayers.remove(removePlayer);
      return true;
    } else {
      return false;
    }
  }

  public void interactWithTile(){
    this.getPlayerInCurrentTurn().getOwnRobot().getCurrentPosition().getTile().action();
  }

  public void setPriority(){
    for(GamePlayer player : activePlayers){
      player.setPriority(Math.abs(this.getPositionAntenna().getX() - player.getOwnRobot().getCurrentPosition().getX())
              + Math.abs(this.getPositionAntenna().getY() - player.getOwnRobot().getCurrentPosition().getY()));
    }
  }

  public void reorderPlayer(){
    CopyOnWriteArrayList<GamePlayer> temp = new CopyOnWriteArrayList<GamePlayer>();
    while(activePlayers.size() > 0){
      int i = 1;
      for(GamePlayer player : activePlayers){
        if(player.getPriority() == i){
          temp.add(player);
          activePlayers.remove(player);
        }
      }
      i++;
    }
    activePlayers = temp;
  }


  public void DoGameStarting(){
    setPriority();
    reorderPlayer();
  }



  public void DoUpgradePhase(){
    //add when we have UpgradeCards

  }

  public void DoProgrammingPhase(){
    for(GamePlayer player:activePlayers){
      player.draw();
      player.showHands();
      String Card="";
      int i=0;
      while( i <player.getHands().size()){
        server.CardTypes.Card currentCard=player.getHands().get(i);
        if(currentCard!=null && currentCard.getCardName().equals(Card)){
          player.getRegister().add(currentCard);
          player.getHands().remove(currentCard);
        }   //if remove one Card in handsList,don't do i++
        else{
          i++;
        }
      }
    }
  }

  public void DoActivationPhase(){
    for(int i=0;i < 5;i++) {
      for (GamePlayer player:activePlayers) {
        setPlayerInCurrentTurn(PlayerInListPosition);
        player.getRegister().get(i).action();
        Robot ownRobot=player.getOwnRobot();
        int X=ownRobot.getCurrentPosition().getX();
        int Y=ownRobot.getCurrentPosition().getY();
        gameBoard.getBoardElem(X,Y,0).action();
        if(gameBoard.getBoardElem(X,Y,1)!=null)
        {gameBoard.getBoardElem(X,Y,1).action();}
        player.getCurrentGame().getController().robotLaserController(ownRobot);
        PlayerInListPosition++;
        if(PlayerInListPosition >=activePlayers.size()){
          PlayerInListPosition =0;
        }
      }


    }
  }

  public void DoGameEnding(){

  }
}

