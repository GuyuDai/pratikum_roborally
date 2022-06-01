package BoardElement;

import CardTypes.Move;
import Player.Robot;

public class PushPanelOne extends BoardElem implements Move {

  @Override
  public void action() {

  }


  @Override
  public void move(Robot robot) {
    robot.move(1);

  }
}
