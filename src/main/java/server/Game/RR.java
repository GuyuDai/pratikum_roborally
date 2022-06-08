package server.Game;

import java.util.concurrent.CopyOnWriteArrayList;
import server.BoardTypes.Board;
import server.Control.Controller;
import server.Player.Player;

public class RR extends Thread implements GameLogic {
  private Boolean isGoingOn;
  public Controller controller;
  protected CopyOnWriteArrayList<Player> activePlayers;
  protected Player playerInCurrentTurn;
  protected Board gameBoard;

  protected GameState currentState;


  public RR(Board gameBoard){
    this.gameBoard = gameBoard;
    this.controller = new Controller();
    this.isGoingOn = false;
  }

  @Override
  public void run() {
    this.setName("GameThread");
    if(currentState == GameState.GameInitializing){
      //do the initializing work
    }
    while(isGoingOn){
      //game flow
    }
    //some behavior
  }

  public synchronized void startGame() {
    this.currentState = GameState.GameInitializing;
    this.start();
  }

  public Player getPlayerInCurrentTurn() {
    return playerInCurrentTurn;
  }

  @Override
  public void setPlayerInCurrentTurn() {
    //need to be implemented
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

  public void setCurrentState(GameState currentState) {
    this.currentState = currentState;
  }

  public void nextGameState(){
    switch (currentState){
      case GameInitializing:
        this.isGoingOn = true;
        setCurrentState(GameState.GameStarting);
        break;

      case GameStarting:
        setCurrentState(GameState.RoundStarting);
        break;

      case RoundStarting:
        setCurrentState(GameState.UpgradePhase);
        break;

      case UpgradePhase:
        setCurrentState(GameState.ProgrammingPhase);
        break;

      case ProgrammingPhase:
        setCurrentState(GameState.ActivationPhase);
        break;

      case ActivationPhase:
        setCurrentState(GameState.RoundEnding);
        break;

      case RoundEnding:
        if(controller.ifGameEnd()){
          setCurrentState(GameState.GameEnding);
          return;
        }
        setCurrentState(GameState.RoundStarting);
        break;

      case GameEnding:
        this.isGoingOn = false;
        break;
    }
  }
}
