package server.Control;

import server.BoardElement.*;
import server.Game.RR;
import server.Player.Player;
import server.Player.Robot;

public class Controller {
  public RR currentGame;

  /**
   * @author dai
   * @param robot which robot wants to move
   * @return true if the robot can move to the next position
   */
  public boolean movementCheck(Robot robot){
    Position currentPosition = robot.getCurrentPosition();
    Position nextPosition = currentPosition.getNextPosition();
    BoardElem currentBoardElem = currentPosition.getTile();
    BoardElem nextBoardElem = nextPosition.getTile();

    Boolean canMoveIn = false;
    Boolean canMoveOut = false;

    switch (robot.getCurrentPosition().getFaceDirection()){
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
}
