package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;

public class BackUp extends Card implements Move{

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
   * BackUp = UTurn + MoveOne + UTurn
   * @param robot
   */
  @Override
  public void move(Robot robot) {
    Direction moveDirection = robot.getCurrentPosition().getFaceDirection().turn180();
    robot.move(1);
    Direction faceDirection = robot.getCurrentPosition().getFaceDirection().turn180();
    robot.setCurrentPosition(new Position
            (robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY(), faceDirection));

  }
}
