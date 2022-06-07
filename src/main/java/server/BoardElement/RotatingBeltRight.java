package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;

public class RotatingBeltRight extends BoardElem implements Move {
  public RotatingBeltRight(RR currentGame,Direction direction) {
    super("RotatingBelt ", currentGame);
    this.direction=direction;
  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

  }

  @Override
  public void move(Robot robot) {
    Direction arrowDirection = this.getDirection();
    if (robot.getLastPosition().getTile().getName()== "ConveyBeltBeltDoubleArrow"
            || robot.getLastPosition().getTile().getName() == "ConveyBeltBeltDoubleArrow") {
      if (arrowDirection.equals(robot.getFaceDirection().turnRight())) {
        robot.setFaceDirection(robot.getFaceDirection().turnRight());
        robot.setCurrentPosition(new Position(robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()));
      }
      robot.getCurrentPosition().stay();
    }
  }
}
