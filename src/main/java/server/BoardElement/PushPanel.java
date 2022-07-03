package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Game.RR;
import server.Player.Robot;

public class PushPanel extends BoardElem implements Move {

  public PushPanel(RR currentGame, String push, Direction direction) {

    super("PushPanelOne", currentGame);
    this.direction=direction;
    this.push=push;
  }



  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(this.getSpeed());

  }
}
