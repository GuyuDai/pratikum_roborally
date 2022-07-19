package server.Control;

import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import protocol.Animation;
import protocol.CheckPointReached;
import protocol.ErrorMessage;
import protocol.GameFinished;
import protocol.PickDamage;
import protocol.ReceivedChat;
import protocol.ShuffleCoding;
import server.BoardElement.*;
import server.CardTypes.Card;
import server.Game.RR;
import server.Player.Player;
import server.Player.Robot;


/**
 * @author dai
 * detect the potential problems in the game flow
 */
public class Controller {
  public RR currentGame;

  public Controller(RR game){
    this.currentGame = game;
  }

  /**
   * @param position
   * @return true if the checked position is out of the bound of the game board
   */
  public Boolean positionOutOfBound(Position position) {
    int tempX = position.getX();
    int tempY = position.getY();
    if (tempX >= currentGame.getGameBoard().getWidth() || tempX < 0
            || tempY >= currentGame.getGameBoard().getHeight() || tempY < 0) {
      return true;
    }
    return false;
  }

  /**
   * @param robot     robot to move
   * @param direction the direction the robot will move
   * @return true if the robot can move to the next position
   */
  public boolean movementCheck(Robot robot, Direction direction) {
    Position currentPosition = robot.getCurrentPosition();
    //System.out.println(currentPosition.getX() + "check");  //test
    Position nextPosition = currentPosition.getNextPosition(direction);
    if(positionOutOfBound(nextPosition)){
      return false;
    }
    BoardElem currentBoardElem1 = currentPosition.getTile();
    BoardElem currentBoardElem2 = currentPosition.getSecondTile();//we put all walls in[x][y][1]
    BoardElem nextBoardElem1 = nextPosition.getTile();
    BoardElem nextBoardElem2 = nextPosition.getSecondTile();

    boolean canMoveIn = false;
    boolean canMoveOut = false;

    switch (direction) {
      case UP:
        canMoveIn = !(nextBoardElem2.getName().equals("Wall") && nextBoardElem2.getDirection().equals(Direction.DOWN));
        canMoveOut = !(currentBoardElem2.getName().equals("Wall") && currentBoardElem2.getDirection().equals(Direction.UP));
        break;

      case LEFT:
        canMoveIn = !(nextBoardElem2.getName().equals("Wall") && nextBoardElem2.getDirection().equals(Direction.RIGHT));
        canMoveOut = !(currentBoardElem2.getName().equals("Wall") && currentBoardElem2.getDirection().equals(Direction.LEFT));
        break;

      case RIGHT:
        canMoveIn = !(nextBoardElem2.getName().equals("Wall") && nextBoardElem2.getDirection().equals(Direction.LEFT));
        canMoveOut = !(currentBoardElem2.getName().equals("Wall") && currentBoardElem2.getDirection().equals(Direction.RIGHT));
        break;

      case DOWN:
        canMoveIn = !(nextBoardElem2.getName().equals("Wall") && nextBoardElem2.getDirection().equals(Direction.UP));
        canMoveOut = !(currentBoardElem2.getName().equals("Wall") && currentBoardElem2.getDirection().equals(Direction.DOWN));
        break;
    }
    /**change WallController.
     * @author Minghao Li*/

    if (!canMoveIn || !canMoveOut) {
      return false;
    }

    if (nextBoardElem1.getName().equals("AirWall") || nextBoardElem1.getName().equals("Antenna")) {
      return false;
    }//we put all AirWall and Antenna in [x][y][0]

    if (isOccupied(nextPosition)) {
      return false;
    }

    if (positionOutOfBound(nextPosition)) {
      return false;
    }

    return true;
  }

  /**
   * check if the robot will get hurt
   * @param robot
   */
  public void positionDamageCheck(Robot robot){
    BoardElem currentElem1 = robot.getCurrentPosition().getTile();
    BoardElem currentElem2 = robot.getCurrentPosition().getSecondTile();
    if(currentElem1.getName().equals("Pit")){
      currentGame.sendMessageToClient(new PickDamage
          (1, currentGame.getAvailablePiles()),currentGame.getServerThreadById(robot.getOwner().clientID));
    }
  }

  /**
   * @param position usually is robot.getCurrentPosition().getNextPosition(Direction direction)
   * @return true if this position is occupied by a robot
   */
  public Boolean isOccupied(Position position) {
    if (position.getOccupiedRobot() == null) {
      return false;
    }
    return true;
  }

  /**
   * @param name user's desired name
   * @return true if the name is valid
   */
  public boolean setPlayerNameCheck(String name) {
    for (Player player : currentGame.getActivePlayers()) {
      if (player.getName().equals(name)) {
        return false;
      }
    }
    return true;
  }


  public void boardLaserController(Robot robot){
    Position currentPosition = robot.getCurrentPosition();
    BoardElem currentBoardElem1=currentPosition.getTile();
    BoardElem currentBoardElem2 = currentPosition.getSecondTile();
    if(currentBoardElem1.getName().equals("Laser")){
      currentBoardElem1.action();
    }
    if(currentBoardElem2.getName().equals("Laser")){
      currentBoardElem2.action();
    }

  }

  public void robotLaserController(Robot robot){
    for(Player player:currentGame.getActivePlayers()){
      Direction laserDirection=player.getOwnRobot().getFaceDirection();
      int X=player.getOwnRobot().getCurrentPosition().getX();
      int Y=player.getOwnRobot().getCurrentPosition().getY();
      int robotX=robot.getCurrentPosition().getX();
      int robotY=robot.getCurrentPosition().getY();
      if(robotX==X && robotY<Y && laserDirection.equals(Direction.LEFT)){
        for(int i=robotY;i<Y;i++){
          if(!(currentGame.getGameBoard().getBoardElem(X,i,0).getName().equals("Antenna")
                  || currentGame.getGameBoard().getBoardElem(X,i,1).getName().equals("Wall"))) {
            robot.getOwner().drawDamage("Spam",1);
            currentGame.sendMessageToAll(new Animation("RobotLaser"));
          }
        }
      }
      if(robotX==X && robotY>Y && laserDirection.equals(Direction.RIGHT)){
        for(int i=Y;i<robotY;i++){
          if(!(currentGame.getGameBoard().getBoardElem(X,i,0).getName().equals("Antenna")
                  || currentGame.getGameBoard().getBoardElem(X,i,1).getName().equals("Wall"))) {
            robot.getOwner().drawDamage("Spam",1);
            currentGame.sendMessageToAll(new Animation("RobotLaser"));
          }
        }
      }
      if(robotY==Y && robotX<X && laserDirection.equals(Direction.UP)){
        for(int i=robotX;i<X;i++){
          if(!(currentGame.getGameBoard().getBoardElem(i,Y,0).getName().equals("Antenna")
                  || currentGame.getGameBoard().getBoardElem(i,Y,1).getName().equals("Wall"))) {
            robot.getOwner().drawDamage("Spam",1);
            currentGame.sendMessageToAll(new Animation("RobotLaser"));
          }
        }
      }
      if(robotY==Y && robotX>X && laserDirection.equals(Direction.DOWN)){
        for(int i=X;i<robotX;i++){
          if(!(currentGame.getGameBoard().getBoardElem(i,Y,0).getName().equals("Antenna")
                  || currentGame.getGameBoard().getBoardElem(i,Y,1).getName().equals("Wall"))){
            robot.getOwner().drawDamage("Spam",1);
            currentGame.sendMessageToAll(new Animation("RobotLaser"));
          }
        }
      }
    }

  }

  public boolean robotOnPositionCheck(Position position) {
    for (Player player:currentGame.getActivePlayers()){
      if (player.getOwnRobot().getCurrentPosition().equals(position)){
        return true;
      }
      else return false;
    }

    return false;
  }

  public boolean selectRobotCheck(String name){
    for(Player player: currentGame.getActivePlayers()){
      if(player.getOwnRobot().getName().equals(name)){
        return true;
      }
      return false;
    }
    return false;
  }

  //public Boolean selectRobotCheck(){}  //need to be implemented

  /**
   * @param desiredPosition
   * @return true if the desired position is empty and no robot here
   */
  public Boolean robotStartingPositionCheck(Position desiredPosition){
    if(desiredPosition.getTile().getName().equals("Empty") && desiredPosition.getOccupiedRobot() == null){
      return true;
    }
    return false;
  }

  /**
   * @param robot
   * @return true if the robot will be still alive
   * or the owner of this robot will be moved out of active Player list
   */
  public boolean robotDeadCheck(Robot robot){
    if(robot.getLives() - 1 == 0){
      for(Player player : currentGame.getActivePlayers()){
        if(player == robot.getOwner()){
          currentGame.getActivePlayers().remove(player);
          return false;
        }
      }
    }
    return true;
  }

  /**
   * @return true if one robot ends a register on the final checkpoint
   */
  public boolean ifGameEnd(){
    boolean flag = false;
    if(currentGame.getActivePlayers().size() == 0){
      currentGame.sendMessageToAll(new ReceivedChat("Game end. No winner",-1,false));
      flag = true;
    }
    if(currentGame.getActivePlayers().size()==1){
      for(Player player : currentGame.getActivePlayers()) {
        currentGame.sendMessageToAll(new GameFinished(player.clientID));
      }
    }
    for(Player player : currentGame.getActivePlayers()){
      BoardElem elem1 = player.getOwnRobot().getCurrentPosition().getTile();
      BoardElem elem2 = player.getOwnRobot().getCurrentPosition().getSecondTile();
      if(elem1.getName().equals("CheckPoint")){
        int checkPointNum1 = elem1.getCount();
        player.getCheckPoints().remove((Integer) checkPointNum1);
        currentGame.sendMessageToAll(new CheckPointReached(player.clientID, elem1.getCount()));
      }
      if(elem2.getName().equals("CheckPoint")){
        int checkPointNum2 = elem2.getCount();
        player.getCheckPoints().remove((Integer) checkPointNum2);
        currentGame.sendMessageToAll(new CheckPointReached(player.clientID, elem2.getCount()));
      }
      if(player.getCheckPoints().size() == 0){
        flag = true;
        currentGame.sendMessageToAll(new GameFinished(player.clientID));
      }
    }
    return flag;
  }

  /**
   * to check if a player is valid to join the game
   * @param player
   * @return if the player is valid, return playerOnline of this player, or null
*/
  public Player joinGameCheck(Player player){
    for(Player playerInList : currentGame.getActivePlayers()){
      if(playerInList.identifyPlayer(player.getName())){
        //currentGame.sendToPlayer(new ErrorMessage("you are already in the game"), player);
        return null;
      }
    }
    for(Player playerInList :currentGame.getActivePlayers()){
      if(playerInList.identifyPlayer(player.getName())){
        return player;
      }
    }
   // currentGame.sendToAll(new ErrorMessage("this name is already used : " + player.getPlayer().getName()));
    return null;
  }


  public boolean isCardListEmpty(CopyOnWriteArrayList<Card> target){
    if(target.size() == 0){
      return true;
    }
    return false;
  }

  /**
   * check if a player's programming deck is empty. if true, shuffle
   * @param player
   */
  public void drawCardCheck(Player player){
    if(isCardListEmpty(player.getOwnDeck().getRemainingCards())){
      player.shuffle();
      currentGame.sendMessageToClient(new ShuffleCoding(player.clientID), currentGame.getServerThreadById(player.clientID));
    }
  }

  public Player getPlayerByName(String target){
    Player result = null;
    for(Player player : currentGame.getActivePlayers()){
      if(target.equals(player.getName())){
        result = player;
      }
    }
    return result;
  }

  public Robot getRobotByName(String target){
    Robot result = null;
    for(Player player : currentGame.getActivePlayers()){
      if(target.equals(player.getOwnRobot().getName())){
        result = player.getOwnRobot();
      }
    }
    return result;
  }

}

