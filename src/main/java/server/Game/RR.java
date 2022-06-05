package server.Game;

import java.util.concurrent.CopyOnWriteArrayList;
import server.BoardTypes.Board;
import server.Control.Controller;
import server.Player.Player;

public class RR implements GameLogic {
  public Controller controller;
  protected CopyOnWriteArrayList<Player> activePlayers;
  protected Player playerInCurrentTurn;
  protected Board gameboard;


  public RR(Board gameboard){
    this.gameboard = gameboard;
    this.controller = new Controller();
  }

  public Player getPlayerInCurrentTurn() {
    return playerInCurrentTurn;
  }

  @Override
  public void setPlayerInCurrentTurn() {

  }

  public CopyOnWriteArrayList<Player> getActivePlayers() {
    return activePlayers;
  }

  public Board getGameboard() {
    return gameboard;
  }

  public Controller getController() {
    return controller;
  }
}
