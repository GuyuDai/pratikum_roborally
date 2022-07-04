package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Game.RR;
import server.Player.Robot;

public class PushPanel extends BoardElem implements Move {

  public PushPanel(RR currentGame, String push, Direction direction) {

    super("PushPanel", currentGame);
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

  @Override
  public String toString(){
    String result = "";
    result += "PushPanel, speed:" + this.speed + ", direction:" + this.direction;
    return result;
  }
}
