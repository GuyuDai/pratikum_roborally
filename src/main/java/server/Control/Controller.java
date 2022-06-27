package server.Control;

import server.BoardElement.*;
import server.Game.RR;
import server.Player.Player;
import server.Player.Robot;

public class Controller {
  public RR currentGame;

  /**
   * @author dai
   * @param position
   * @return true if the checked position is out of the bound of the game board
   */
  public Boolean positionOutOfBound(Position position){
    int tempX = position.getX();
    int tempY = position.getY();
    if(tempX >= currentGame.getGameBoard().getWidth() || tempX < 0
        || tempY >= currentGame.getGameBoard().getHeight() || tempY < 0){
      return true;
    }
    return false;
  }

  /**
   * @author dai
   * @param robot robot to move
   * @param direction the direction the robot will move
   * @return true if the robot can move to the next position
   */
  public boolean movementCheck(Robot robot, Direction direction){
    Position currentPosition = robot.getCurrentPosition();
    Position nextPosition = currentPosition.getNextPosition(direction);
    BoardElem currentBoardElem = currentPosition.getTile();
    BoardElem nextBoardElem = nextPosition.getTile();

    Boolean canMoveIn = false;
    Boolean canMoveOut = false;

    switch (direction){
      case UP:
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

      case DOWN:
        canMoveIn = !nextPosition.getFrontWall();
        canMoveOut = !currentPosition.getBackWall();
        break;
    }

    if(!canMoveIn || !canMoveOut){
      return false;
    }

    if(nextBoardElem instanceof AirWall || nextBoardElem instanceof Antenna){
      return false;
    }

    if(isOccupied(nextPosition)){
      return false;
    }

    if(positionOutOfBound(nextPosition)){
      return false;
    }

    return true;
  }

  /**
   * @author dai
   * @param position usually is robot.getCurrentPosition().getNextPosition(Direction direction)
   * @return true if this position is occupied by a robot
   */
  public Boolean isOccupied(Position position){
    if(position.getOccupiedRobot() == null){
      return false;
    }
    return true;
  }

  /**
   * @author dai
   * @param name user's desired name
   * @return true if the name is valid
   */
  public Boolean setPlayerNameCheck(String name){
    for(Player player: currentGame.getActivePlayers()) {
      if (player.getName().equals(name)) {
        return false;
      }
    }
    return true;
  }

  /**
   * @author dai
   * @param name user's desired name of robot
   * @return true if the name is valid
   */
  public Boolean setRobotNameCheck(String name){
    if(name.equals("AirWall") || name.equals("Antenna") || name.equals("CheckPoint")
        || name.equals("ConveyBelt") || name.equals("Empty") || name.equals("EnergySpace")
        || name.equals("GearGreen") || name.equals("GearRed") || name.equals("Laser1")
        || name.equals("Laser2") || name.equals("Laser3") || name.equals("Pit")
        || name.equals("PushPanelOne") || name.equals("PushPanelTwo")
        || name.equals("RotatingBeltLeft") || name.equals("RotatingBeltRight")){
      return false;
    }
    for(Player player: currentGame.getActivePlayers()){
      if(player.getOwnRobot().getName().equals(name)){return false;}
    }
    return true;
  }

  /**
   * @author dai
   * @param desiredPosition
   * @return true if the desired position is empty and no robot here
   */
  public Boolean robotStartingPositionCheck(Position desiredPosition){
    if(desiredPosition.getTile() instanceof Empty && desiredPosition.getOccupiedRobot() == null){
      return true;
    }
    return false;
  }

  /**
   * @author
   * @param robot
   * @return true if the robot will be still alive
   * or the owner of this robot will be moved out of active Player list
   */
  public Boolean robotDeadCheck(Robot robot){
    if(robot.getLives() - 1 == 0){
      currentGame.getActivePlayers().remove(robot.getOwner());
      return false;
    }
    return true;
  }

  /**
   * @author dai
   * @return true if one robot ends a register on the final checkpoint
   */
  public Boolean ifGameEnd(){
    //need to be implemented
    return false;
  }
}
