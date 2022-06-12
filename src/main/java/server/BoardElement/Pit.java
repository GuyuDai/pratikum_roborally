package server.BoardElement;

import server.Game.RR;
import server.Player.Robot;

public class Pit extends BoardElem{
  public Pit(RR currentGame) {
    super("Pit", currentGame);
  }

  @Override
  public void action() {
    currentGame.getPlayerInCurrentTurn().getOwnRobot();


  }

  public void move(Robot robot){
    robot.reboot();
  }
}
