package server.BoardElement;
import server.CardTypes.Move;
import server.CardTypes.TurnRight;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Nargess Ahmadi
 * Gears rotate robots resting on them 90 degrees in the direction of the arrows.
 * Green gears rotate right.
 */
public class GearGreen extends BoardElem implements Move {
  public GearGreen(RR currentGame) {
    super("GearGreen", currentGame);
  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

  }

  @Override
  public void move(Robot robot) {
    TurnRight turnRight = new TurnRight();
    turnRight.move(robot);
  }
}

