package CardTypes;

import Player.Robot;

/**
 * @author dai
 * to move forwards one step
 */
public class MoveOne extends Card implements Move{

  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(1);
  }
}
