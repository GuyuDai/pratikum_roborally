package server.BoardElement;

import server.Game.RR;
import server.Player.Robot;

/**
 * @author Minghao Li
 * If  robot walks into a Pit, it will reboot.
 */
public class Pit extends BoardElem{
  public Pit(RR currentGame) {
    super("Pit", currentGame);
  }

  @Override
  public void action() {
    currentGame.getPlayerInCurrentTurn().getOwnRobot().reboot();
  }

  public void move(Robot robot){
    robot.reboot();
  }

  @Override
  public String toString(){
    String result = "";
    result += "Pit";
    return result;
  }
}
