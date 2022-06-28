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
    if(speed==1) {
      robot.push(robot, arrowDirection, speed);
    }
    if(speed==2){
      robot.push(robot, arrowDirection, 1);
      if(robot.getCurrentPosition().getTile().getName()=="ConveyBelt"){
        robot.push(robot, arrowDirection, 1);
      }
      if(robot.getCurrentPosition().getTile().getName()=="RotatingBelt"){
        robot.getCurrentPosition().getTile().action();
        Direction moveDirection=robot.getCurrentPosition().getTile().getDirection();
        robot.push(robot,moveDirection,1);
      }
      robot.getCurrentPosition().stay();
    }
    //check if robot moves out of ConveyBelt,it will move 1 steps anyway.
    //but rotatingBelt is also a belt robot may not move in arrowDirection when it meets a rotatingBelt,it will change moveDirection;

  }


}
