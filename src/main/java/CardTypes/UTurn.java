package CardTypes;

import Client.Robot;
import Controller.Position;

public class UTurn extends Card implements Move{

  /**
   * @author dai
   * turn round
   */
  @Override
  public void effect() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    int orientation = robot.getPosition().getOrientation();
    switch (orientation){
      case 0:
        orientation = 3;
        break;

      case 1:
        orientation = 2;
        break;

      case 2:
        orientation = 1;
        break;

      case 3:
        orientation = 0;
        break;
    }
    robot.setPosition(new Position
        (robot.getPosition().getX(), robot.getPosition().getY(), orientation));
  }
}
