package server.CardTypes;

import server.Control.Direction;
import server.Player.Robot;
import server.Control.Position;


/**
 * @author Dai, Li
 * reset the orientation of a robot's position
 */
public class TurnLeft extends Card implements Move{
  public TurnLeft(){
    super("TurnLeft");
  }


  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.setFaceDirection(robot.getFaceDirection().turnLeft());
  }
}
