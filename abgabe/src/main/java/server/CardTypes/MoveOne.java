package server.CardTypes;

import server.Player.Robot;

/**
 * @author Dai, Li
 * to move forwards one step
 */
public class
MoveOne extends Card implements Move{
  public MoveOne(){
    super("MoveOne");
  }
  @Override
  public void action() {
    move(this.getOwner().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(1);
  }
}

