package server.Player;

import java.util.Random;
import server.CardTypes.Card;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;

public class Robot implements RobotAction {

  public String name;
  public int hp = 10;
  public Boolean isAlive;
  public int lives = 3;

  private Position currentPosition;
  private Position lastPosition = null;
  private Direction faceDirection;  //direction the robot faces to

  public RR currentGame;

  public GamePlayer owner;
  private Position startPosition;

  public Robot(String name){
    this.name=name;
  }

  public Direction getFaceDirection() {
    return faceDirection;
  }
  public void setFaceDirection(Direction faceDirection) {
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

  public GamePlayer getOwner() {
    return owner;
  }

  public RR getCurrentGame() {
    return currentGame;
  }

  public Position getStartPosition() {
    return startPosition;
  }

  public void setStartPosition(){
    Boolean flag = true;
    Random random = new Random();
    while (flag){
      Position desiredPosition = new Position(random.nextInt(this.getCurrentGame().getGameBoard().getWidth()),
              random.nextInt(this.getCurrentGame().getGameBoard().getHeight()));
      Boolean flag2 = currentGame.getController().robotStartingPositionCheck(desiredPosition);
      //flag2: whether the desired position is valid
      if (flag2){
        this.startPosition = desiredPosition;
        flag = false;
      }
    }
  }

  private void moveOneStep() {
    Position togo = this.getCurrentPosition().getNextPosition(this.getFaceDirection());
    boolean flag = this.currentGame.getController().movementCheck(this, this.getFaceDirection());
    if (flag) {
      this.setLastPosition(this.getCurrentPosition());
      this.getCurrentPosition().setOccupiedRobot(null);
      togo.setOccupiedRobot(this);
      this.setCurrentPosition(togo);
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
    for (int i = 0; i < step; step++) {
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

  public void reboot(){
    //need to be implemented
  }
  private void bePushedOneStep(Direction direction) {
    Position togo = this.getCurrentPosition().getNextPosition(direction);
    boolean flag = this.currentGame.getController().movementCheck(this, direction);
    if (flag) {
      this.setLastPosition(this.getCurrentPosition());
      this.getCurrentPosition().setOccupiedRobot(null);
      togo.setOccupiedRobot(this);
      this.setCurrentPosition(togo);
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
    for (int i = 0; i < step; step++) {
      Position nextPosition = targetRobot.getCurrentPosition().getNextPosition(direction);
      Boolean flag = this.getCurrentGame().getController().positionOutOfBound(nextPosition);
      if(flag){
        targetRobot.reboot();
      }
      targetRobot.bePushedOneStep(direction);
    }
  }
}



