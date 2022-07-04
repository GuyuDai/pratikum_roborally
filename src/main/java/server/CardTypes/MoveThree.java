package server.CardTypes;

import server.Player.Robot;

public class MoveThree extends Card implements Move{

  public MoveThree(){
    super("MoveThree");
  }
  /**
   * @author dai,Li
   * to move forwards three steps
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(3);
  }
}
