package Game;

import Controller.MovementController;

public class RR implements GameLogic {
  public MovementController MC;

  public RR(){
    this.MC = new MovementController();
  }

  public MovementController getMC() {
    return MC;
  }
}
