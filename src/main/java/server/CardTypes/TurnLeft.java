package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;

public class TurnLeft extends Card implements Move{

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
    Direction faceDirection = robot.getCurrentPosition().getFaceDirection().turnLeft();


    robot.setCurrentPosition(new Position
        (robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY(), faceDirection));
  }
}
