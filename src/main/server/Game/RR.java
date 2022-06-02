package Game;

import Controller.MovementController;
import Player.Player;

public class RR implements GameLogic {
  public MovementController MC;
  public Player player;


  public Player getPlayerInCurrentTurn() {
    return player;
  }

  @Override
  public void setPlayerInCurrentTurn() {

  }

  public RR(){
    this.MC = new MovementController();
  }

  public MovementController getMC() {
    return MC;
  }
}
