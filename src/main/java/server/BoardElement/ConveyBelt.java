package server.BoardElement;

import server.CardTypes.Move;
import server.Control.Direction;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Minghao Li
 * Conveyor belts move your robot in the direction of the arrows.
 * Double-arrowed conveyor belts move robots two spaces and activate before single-arrowed conveyor belts,
 * which move robots one space. Once a robot has moved off a belt, the belt no longer affects that robot.
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

  /**
   * @author Minghao Li
   * ConveyBelt move robot method,if next step is no longer on Belt,it will stop pushing Robot
   * if nextstep is on a RotatingBelt,it will do RotatingBelt move method.
   * robot will move 1 step,and use if-condition to check next step is on Belt or not.
   */
  @Override
  public void move(Robot robot) {
    Direction arrowDirection=this.getDirection();
    int speed=this.getSpeed();
    if(speed==1) {
      robot.push(robot, arrowDirection, speed);
    }
    if(speed==2){
      robot.push(robot, arrowDirection, 1);
      if(robot.getCurrentPosition().getTile().getName().equals("ConveyBelt")){
        robot.push(robot, arrowDirection, 1);
      }
      if(robot.getCurrentPosition().getTile().getName().equals("RotatingBelt")){
        Direction curvedDirection=robot.getCurrentPosition().getTile().getDirection2();
        Direction moveDirection=robot.getCurrentPosition().getTile().getDirection();
          if(robot.getLastPosition().getTile().getDirection().equals(curvedDirection)){
            robot.setFaceDirection(moveDirection);
            robot.push(robot,moveDirection,1);
          }
        robot.push(robot,moveDirection,1);
      }
      else {
        robot.stay();
      }
    }

  }

  @Override
  public String toString(){
    String result = "";
    result += "Conveybelt, speed:" + this.speed + ", orientations: " + this.direction;
    return result;
  }

}
