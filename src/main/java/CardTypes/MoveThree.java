package CardTypes;

import Player.Robot;

public class MoveThree extends Card implements Move{

  /**
   * @author dai
   * to move forwards three steps
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(3);
  }
}
