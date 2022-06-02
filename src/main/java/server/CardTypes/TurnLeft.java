package server.CardTypes;

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
    int orientation = robot.getPosition().getFaceDirection();
    switch (orientation){
      case 0:
        orientation = 1;
        break;

      case 1:
        orientation = 3;
        break;

      case 2:
        orientation = 0;
        break;

      case 3:
        orientation = 2;
        break;
    }
    robot.setPosition(new Position
        (robot.getPosition().getX(), robot.getPosition().getY(), orientation));
  }
}
