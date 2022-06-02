package server.CardTypes;

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
    int orientation1 = robot.getPosition().getFaceDirection();
    switch (orientation1){
      case 0:
        orientation1 = 3;
        break;

      case 1:
        orientation1 = 2;
        break;

      case 2:
        orientation1 = 1;
        break;

      case 3:
        orientation1 = 0;
        break;
    }
    robot.setPosition(new Position
        (robot.getPosition().getX(), robot.getPosition().getY(), orientation1));

    robot.move(1);

    int orientation2 = robot.getPosition().getFaceDirection();
    switch (orientation2){
      case 0:
        orientation2 = 3;
        break;

      case 1:
        orientation2 = 2;
        break;

      case 2:
        orientation2 = 1;
        break;

      case 3:
        orientation2 = 0;
        break;
    }
    robot.setPosition(new Position
        (robot.getPosition().getX(), robot.getPosition().getY(), orientation2));

  }
}
