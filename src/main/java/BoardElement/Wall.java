package BoardElement;

import CardTypes.Move;
import Player.Robot;

/**
 * @author Nargess Ahmadi, Nassrin Djafari
 * Robots cannot move through walls, and robot and board lasers cannot shoot through walls.
 */
public class Wall extends BoardElem implements Move {

  @Override
  public void action() {
  }

  @Override
  public void move(Robot robot) {
    robot.move(0);
  }
}
