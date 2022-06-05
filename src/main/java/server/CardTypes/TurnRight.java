package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;

public class TurnRight extends Card implements Move{

  /**
   * @author dai
   * reset the orientation of a robot's position
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    Direction faceDirection = robot.getPosition().getFaceDirection().turnRight();
    robot.setPosition(new Position
        (robot.getPosition().getX(), robot.getPosition().getY(),faceDirection));
  }
}
