package server.Control;

import server.BoardElement.*;
import server.Game.RR;
import server.Player.Robot;

public class Controller {
  public RR currentGame;

  public boolean movementCheck(Robot robot){
    Position currentPosition = robot.getPosition();
    Position nextPosition = currentPosition.getNextPosition();
    BoardElem currentBoardElem = currentPosition.getTile();
    BoardElem nextBoardElem = nextPosition.getTile();

    Boolean canMoveIn = false;
    Boolean canMoveOut = false;

    switch (robot.getPosition().getFaceDirection()){
      case DOWN:
        canMoveIn = !nextPosition.getBackWall();
        canMoveOut = !currentPosition.getFrontWall();
        break;

      case LEFT:
        canMoveIn = !nextPosition.getRightWall();
        canMoveOut = !currentPosition.getLeftWall();
        break;

      case RIGHT:
        canMoveIn = !nextPosition.getLeftWall();
        canMoveOut = !currentPosition.getRightWall();
        break;

      case UP:
        canMoveIn = !nextPosition.getFrontWall();
        canMoveOut = !currentPosition.getBackWall();
        break;
    }

    if(!canMoveIn || !canMoveOut){
      return false;
    }

    if(nextBoardElem instanceof Empty){
      return true;
    }

    System.out.println("unsettled situation in movementCheck()");
    return false;
  }

}
