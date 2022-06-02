package BoardElement;

import CardTypes.Move;
import Game.RR;
import Player.Robot;

public class RotatingBeltRight extends BoardElem implements Move {
  public RotatingBeltRight(RR currentGame) {
    super("RotatingBelt ", currentGame);
  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

  }

  @Override
  public void move(Robot robot) {

  }
}
