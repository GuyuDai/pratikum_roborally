package server.CardTypes;

import server.Player.Robot;

/**
 * @author Dai, Li
 */
public class MoveTwo extends Card implements Move{
  public MoveTwo(){
    super("MoveTwo");
  }
  /**
   * @author dai,Li
   * to move forwards two steps
   */
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(2);
  }
}
