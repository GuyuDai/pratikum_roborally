package server.BoardElement;

import server.CardTypes.Move;
import server.Game.RR;
import server.Player.Robot;

public class PushPanelOne extends BoardElem implements Move {

  public PushPanelOne(RR currentGame) {
    super("PushPanelOne", currentGame);
    }



  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(1);

  }
}
