package BoardElement;
import CardTypes.Move;
import CardTypes.TurnRight;
import Player.Robot;

/**
 * @author Nargess Ahmadi
 * Gears rotate robots resting on them 90 degrees in the direction of the arrows.
 * Green gears rotate right.
 */

public class GearGreen extends BoardElem implements Move {

  @Override
  public void move(Robot robot) {
    TurnRight turnRight = new TurnRight();
    turnRight.move(robot);
  }


  @Override
  public void action() {
  }
}
