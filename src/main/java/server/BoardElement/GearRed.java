package server.BoardElement;

import server.CardTypes.TurnLeft;
import server.Game.RR;
import server.Player.Robot;


/**
 * @author Nargess Ahmadi, Nassrin Djafari
 * Gears rotate robots resting on them 90 degrees in the direction of the arrows.
 * Green gears rotate left.
 */

public class GearRed extends BoardElem{
  public GearRed(RR currentGame) {
    super("GearRed", currentGame);
  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
  }

  public void move(Robot robot){
    TurnLeft turnLeft = new TurnLeft();
    turnLeft.move(robot);
  }
}
