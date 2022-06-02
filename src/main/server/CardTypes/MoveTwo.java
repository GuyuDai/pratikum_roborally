package CardTypes;

import Player.Robot;

public class MoveTwo extends Card implements Move{

  /**
   * @author dai
   * to move forwards two steps
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(2);
  }
}
