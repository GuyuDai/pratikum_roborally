package CardTypes;

import Client.Robot;

public class MoveThree extends Card implements Move{

  /**
   * @author dai
   * to move forwards three steps
   */
  @Override
  public void effect() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(3);
  }
}
