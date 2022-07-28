package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;

/**
 * @author Dai, Li
 * move a robot one step back. The robot does not change the direction it is facing.
 */
public class BackUp extends Card implements Move{
  public BackUp(){
    super("BackUp");
  }


  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }


  @Override
  public void move(Robot robot) {
    robot.push(robot, robot.getFaceDirection().turn180(), 1);
  }
}
