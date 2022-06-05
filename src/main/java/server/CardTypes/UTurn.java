package server.CardTypes;

import server.BoardElement.Direction;
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
    Direction faceDirection = robot.getPosition().getFaceDirection().turn180();

    robot.setPosition(new Position
        (robot.getPosition().getX(), robot.getPosition().getY(), faceDirection));
  }
}
