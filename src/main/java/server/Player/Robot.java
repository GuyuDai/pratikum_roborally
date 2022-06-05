package server.Player;

import server.BoardElement.BoardElem;
import server.CardTypes.Card;
import server.Control.Position;
import server.Game.RR;

public class Robot extends BoardElem implements RobotAction {

  public String name;

  public Position currentPosition;
  public RR currentGame;
  public Position LastPosition;

  public Player owner;

  public Robot(String name, RR currentGame) {
    super(name, currentGame);
  }

  /**
   * @author dai please don't use this function!!
   */
  @Override
  public void action() {
    return;
  }

  public Position getPosition() {
    return currentPosition;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getLastPosition(){
    return LastPosition;
  }

  private void moveOneStep() {
    LastPosition=this.getPosition();
    Position togo = this.getPosition().getNextPosition();
    boolean flag = this.currentGame.getController().movementCheck(this);
    if (flag) {
      this.getPosition().setOccupiedRobot(null);
      togo.setOccupiedRobot(this);
      this.setPosition(togo);
    }
  }

  @Override
  public void act() {
    for (Card card : owner.getRegister()) {
      card.action();
    }
  }

  @Override
  public void move(int step) {
    for (int i = 0; i < step; step++) {
      moveOneStep();
    }
  }
}

