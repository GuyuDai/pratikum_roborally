package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Nargess Ahmadi, Nassrin Djafari
 * Conveyor belts move your robot in the direction of the arrows.
 * Double-arrowed conveyor belts move robots two spaces and activate before single-arrowed conveyor belts,
 * which move robots one space. Once a robot has moved off a belt, the belt no longer affects that robot.
 * See the examples below.
 */

public class ConveyBeltSingleArrow extends BoardElem implements Move {
  public ConveyBeltSingleArrow(RR currentGame) {

    super("ConveyBeltSingleArrow",currentGame);

  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

  }


  @Override
  public void move(Robot robot) {
    Position elemPosition =this.getPosition();
    Direction arrowDirection=elemPosition.getFaceDirection();
    Direction robotFaceDirection=robot.getCurrentPosition().getFaceDirection();
    if (arrowDirection.equals(robotFaceDirection)){
      robot.move(1);
    }
    robot.getCurrentPosition().setFaceDirection(arrowDirection);
    robot.move(1);
    robot.getCurrentPosition().setFaceDirection(robotFaceDirection);
  }
}
