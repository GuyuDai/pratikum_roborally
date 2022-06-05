package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;

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
    Direction arrowDirection = this.getPosition().getFaceDirection();
    if (robot.getLastPosition().getTile().getName()== "ConveyBeltBeltDoubleArrow"
            || robot.getLastPosition().getTile().getName() == "ConveyBeltBeltDoubleArrow") {
      if (arrowDirection.equals(robot.getPosition().getFaceDirection().turnRight())) {
        Direction faceDirection = robot.getPosition().getFaceDirection().turnRight();
        robot.setPosition(new Position(robot.getPosition().getX(), robot.getPosition().getY(), faceDirection));
      }
      robot.getPosition().stay();
    }
  }
}
