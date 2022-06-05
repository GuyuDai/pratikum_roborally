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

  public Position currentPosition;
  public Position lastPosition = null;
  public RR currentGame;

  public Player owner;

  public Robot(String name, RR currentGame) {
    this.name = name;
    this.currentGame = currentGame;
    this.isAlive = true;
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

  private void moveOneStep() {
    Position togo = this.getCurrentPosition().getNextPosition();
    boolean flag = this.currentGame.getController().movementCheck(this);
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

  public void reboot(){
    Boolean flag = true;
    Random random = new Random();
    while (flag){
      Position desiredPosition = new Position(random.nextInt(this.getCurrentGame().getGameboard().getWidth()),
          random.nextInt(this.getCurrentGame().getGameboard().getHeight()), Direction.UP);
      if (currentGame.getController().robotStartingPositionCheck(desiredPosition)){
        this.getCurrentPosition().setOccupiedRobot(null);
        this.setCurrentPosition(desiredPosition);
        desiredPosition.setOccupiedRobot(this);
        flag = false;
      }
    }
  }
}

