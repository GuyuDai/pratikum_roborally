package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;

public class UTurn extends Card implements Move{

  /**
   * @author dai
   * turn round
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.setFaceDirection(robot.getFaceDirection().turn180());
  }
}
