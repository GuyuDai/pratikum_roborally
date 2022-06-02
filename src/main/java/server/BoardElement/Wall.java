package server.BoardElement;

import server.CardTypes.Move;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Nargess Ahmadi, Nassrin Djafari
 * Robots cannot move through walls, and robot and board lasers cannot shoot through walls.
 */
public class Wall extends BoardElem implements Move {
  public Wall(RR currentGame) {
    super("Wall", currentGame);
  }


  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    robot.move(0);
  }
}