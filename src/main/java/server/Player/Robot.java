package server.Player;

import java.util.Random;
import java.util.logging.Logger;

import client.ClientReceive;
import protocol.Animation;
import protocol.Energy;
import protocol.Movement;
import protocol.PlayerTurning;
import protocol.Reboot;
import protocol.ReceivedChat;
import server.CardTypes.Card;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;


/**
 * @author Dai
 */
public class Robot implements RobotAction {

  private static final Logger logger = Logger.getLogger(Robot.class.getName());
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";

  public String name;
  public int hp = 10;
  public Boolean isAlive;
  public int lives = 3;
  private Position currentPosition;
  private Position lastPosition = null;
  private Direction faceDirection;  //direction the robot faces to

  public RR currentGame;
  public Player owner;
  private Position startPosition;
  private Direction rebootDirection = Direction.UP;


  public Robot(String name){
    this.name=name;
    this.faceDirection=Direction.RIGHT;
  }

  public void setDeathTrapDirection(){
    this.faceDirection=Direction.LEFT;
  }
  public void setOwner(Player player){
    this.owner=player;
  }
  public Direction getFaceDirection() {
    return faceDirection;
  }
  public void setFaceDirection(Direction faceDirection) {
    Direction currentDirection = this.faceDirection;
    int difference = faceDirection.getAngel()-currentDirection.getAngel();
    String rotation = "";
    switch (difference){
      case -90:
      case 270:
        rotation += "counterclockwise";
        break;

      case 90:
      case -270:
        rotation += "clockwise";
        break;

      case 180:
      case -180:
        rotation += "turn 180";
        break;
    }
    if(currentPosition.equals(startPosition)){
      stay();
    }
    currentGame.sendMessageToAll(new PlayerTurning(owner.clientID, rotation));
    this.faceDirection = faceDirection;
  }
  public Position getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(Position currentPosition) {
    this.currentPosition = currentPosition;
  }

  public Position getLastPosition() {
    return lastPosition;
  }

  public void setLastPosition(Position lastPosition) {
    this.lastPosition = lastPosition;
  }

  public String getName() {
    return name;
  }

  public int getHp() {
    return hp;
  }

  /**
   * @author dai
   * @param difference can be positive or negative
   */
  public void setHp(int difference) {
    if(this.hp + difference <= 0){
      if(currentGame.getController().robotDeadCheck(this)){
        this.lives -= 1;
        reboot();
        return;
      }else{
        this.isAlive = false;
        this.getCurrentPosition().setOccupiedRobot(null);
        return;
      }
    }
    this.hp = hp + difference;
  }

  public Boolean getAlive() {
    return isAlive;
  }

  public int getLives() {
    return lives;
  }

  public Player getOwner() {
    return owner;
  }

  public RR getCurrentGame() {
    return currentGame;
  }

  public void setCurrentGame(RR currentGame) {
    this.currentGame = currentGame;
  }

  public Position getStartPosition() {
    return startPosition;
  }

  public void setStartPosition(Position startPosition) {
    this.startPosition = startPosition;
  }

  public Direction getRebootDirection() {
    return rebootDirection;
  }

  public void setRebootDirection(Direction rebootDirection) {
    this.rebootDirection = rebootDirection;
  }

  private void moveOneStep() {
    Position togo = this.getCurrentPosition().getNextPosition(this.getFaceDirection());
    boolean flag = this.currentGame.getController().movementCheck(this, this.getFaceDirection());
    if (flag) {
      //send protocol message
      String type1 = togo.getTile().getName();
      String type2 = togo.getSecondTile().getName();
      switch (type1){
        case "ConveyBelt":
        case "Gear":
        case "Laser":
        case "PushPanel":
        case "CheckPoint":
          currentGame.sendMessageToClient(new Animation(type1),currentGame.getServerThreadById(owner.clientID));
        case "EnergySpace":
          currentGame.sendMessageToClient(new Energy
              (owner.clientID,togo.getTile().count,"EnergySpace"),currentGame.getServerThreadById(owner.clientID));
          break;
      }
      switch (type2){
        case "Laser":
          currentGame.sendMessageToClient(new Animation(type2),currentGame.getServerThreadById(owner.clientID));
          break;
      }
      //do movement
      this.setLastPosition(this.getCurrentPosition());
      this.getCurrentPosition().setOccupiedRobot(null);
      togo.setOccupiedRobot(this);
      this.setCurrentPosition(togo);
      currentGame.sendMessageToAll(new Movement(owner.clientID, togo.getX(), togo.getY()));
    }else {
      boolean pushFlag = this.currentGame.getController().isOccupied(togo);
      if(pushFlag){
        Robot target = togo.getOccupiedRobot();
        push(target, this.faceDirection,1);
      }
      stay();
    }
  }

  @Override
  public void act() {
    for (Card card : owner.getRegister()) {
      card.action();
    }
  }

  /**
   * @author dai
   * move the robot
   * @param step how many steps the robot should be moved
   */
  @Override
  public void move(int step) {
    for (int i = 0; i < step; i++) {
      moveOneStep();
    }
  }

  public void setRobotToStartPosition(Direction direction){
    if(startPosition != null){
      this.getCurrentPosition().setOccupiedRobot(null);
      this.setCurrentPosition(this.getStartPosition());
      startPosition.setOccupiedRobot(this);
      this.setFaceDirection(direction);
    }
  }

  public synchronized void reboot(){
    currentGame.sendMessageToAll(new Reboot(owner.clientID));
    owner.drawDamage("Spam",2);
    owner.discardRegister();
    Position rebootPosition = currentGame.getPositionReboot();
    boolean flag = currentGame.getController().isOccupied(rebootPosition);
    currentGame.sendMessageToClient(new ReceivedChat
        ("decide your reboot direction", -1, true), currentGame.getServerThreadById(owner.clientID));
    try {
      wait(5000);  //wait player to make decision
    } catch (InterruptedException e) {
      logger.warning(ANSI_YELLOW + "error in reboot");
    }
    this.faceDirection = rebootDirection;
    if(flag){
      Robot occupiedRobot = rebootPosition.getOccupiedRobot();
      push(occupiedRobot, rebootPosition.getTile().getDirection(),1);
    }
    this.lastPosition = currentPosition;
    this.currentPosition = rebootPosition;
    currentGame.sendMessageToAll(new Movement
        (owner.clientID, currentPosition.getX(), currentPosition.getY()));
  }
  private void bePushedOneStep(Direction direction) {
    Position togo = this.getCurrentPosition().getNextPosition(direction);
    boolean flag = this.currentGame.getController().movementCheck(this, direction);
    if (flag) {
      //send protocol message
      String type1 = togo.getTile().getName();
      String type2 = togo.getSecondTile().getName();
      switch (type1){
        case "ConveyBelt":
        case "Gear":
        case "Laser":
        case "PushPanel":
        case "CheckPoint":
          currentGame.sendMessageToClient(new Animation(type1),currentGame.getServerThreadById(owner.clientID));
        case "EnergySpace":
          currentGame.sendMessageToClient(new Energy
              (owner.clientID,togo.getTile().count,"EnergySpace"),currentGame.getServerThreadById(owner.clientID));
          break;
      }
      switch (type2){
        case "Laser":
          currentGame.sendMessageToClient(new Animation(type2),currentGame.getServerThreadById(owner.clientID));
          break;
      }
      this.setLastPosition(this.getCurrentPosition());
      this.getCurrentPosition().setOccupiedRobot(null);
      togo.setOccupiedRobot(this);
      this.setCurrentPosition(togo);
      currentGame.sendMessageToAll(new Movement(owner.clientID, togo.getX(), togo.getY()));
    }else {
      stay();
    }
  }

  /**
   * @author dai
   * push another robot
   * @param targetRobot the robot which will be pushed
   * @param direction pushed direction
   * @param step how many steps the target robot will be pushed
   */
  public void push(Robot targetRobot, Direction direction, int step){
    for (int i = 0; i < step; i++) {
      Position nextPosition = targetRobot.getCurrentPosition().getNextPosition(direction);
      Boolean flag = this.getCurrentGame().getController().positionOutOfBound(nextPosition);
      if(flag && !this.name.equals(targetRobot.getName())){
        targetRobot.reboot();
        return;
      }
      targetRobot.bePushedOneStep(direction);
    }
  }

  /**
   * @author dai
   * robot stay where it is
   */
  public void stay() {
    this.lastPosition = currentPosition;
    currentGame.sendMessageToAll(new Movement
        (owner.clientID, currentPosition.getX(), currentPosition.getY()));
  }
}



