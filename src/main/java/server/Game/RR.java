package server.Game;

import server.Control.Controller;
import server.Player.Player;

public class RR implements GameLogic {
  public Controller controller;
  public Player player;


  public Player getPlayerInCurrentTurn() {
    return player;
  }

  @Override
  public void setPlayerInCurrentTurn() {

  }

  public RR(){
    this.controller = new Controller();
  }

  public Controller getController() {
    return controller;
  }
}
