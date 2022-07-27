package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;

public class BackUp extends Card implements Move{
  public BackUp(){
    super("BackUp");
  }
  /**
   * @author dai
   * move a robot one step back. The robot does not change the direction it is facing.
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  /**
   * @author dai
   * @param robot
   */
  @Override
  public void move(Robot robot) {
    robot.push(robot, robot.getFaceDirection().turn180(), 1);
  }
}
