package server.BoardElement;
import server.CardTypes.Move;
import server.CardTypes.TurnLeft;
import server.CardTypes.TurnRight;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Nargess Ahmadi,Minghao Li
 * Gears rotate robots resting on them 90 degrees in the direction of the arrows.
 * Green gears rotate right.
 * Green gears rotate left.
 */
public class Gear extends BoardElem implements Move {
  public Gear(RR currentGame, String turnDirection) {
    super("Gear", currentGame);
    this.turnDirection=turnDirection;
  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

  }

  @Override
  public void move(Robot robot) {
    if (this.getTurnDirection()=="turnRight") {
      robot.setFaceDirection(robot.getFaceDirection().turnRight());

    }
    if (this.getTurnDirection()=="turnLeft") {
      robot.setFaceDirection(robot.getFaceDirection().turnLeft());
    }

  }
}

