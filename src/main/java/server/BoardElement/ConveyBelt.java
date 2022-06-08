package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Nargess Ahmadi, Nassrin Djafari,Minghao Li
 * Conveyor belts move your robot in the direction of the arrows.
 * Double-arrowed conveyor belts move robots two spaces and activate before single-arrowed conveyor belts,
 * which move robots one space. Once a robot has moved off a belt, the belt no longer affects that robot.
 * See the examples below.
 */

public class ConveyBelt extends BoardElem implements Move {
  public ConveyBelt(RR currentGame,int speed,Direction direction) {

    super("ConveyBelt",currentGame);
    this.speed=speed;
    this.direction=direction;

  }

  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

  }


  @Override
  public void move(Robot robot) {
    Direction arrowDirection=this.getDirection();
    int speed=this.getSpeed();
    robot.push(robot,arrowDirection,speed);
  }
}
