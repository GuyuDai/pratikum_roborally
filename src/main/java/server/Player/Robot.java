package server.Player;

import server.BoardElement.BoardElem;
import server.CardTypes.Card;
import server.Control.Position;
import server.Game.RR;

public class Robot extends BoardElem implements RobotAction {

  public String name;

  public Position position;
  public RR currentGame;

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
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  private void moveOneStep() {
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

  /** @author Niklas
   * Reboot action: Take two SPAM damage cards, and place them in your discard pile.
   * Regardless of the current register, your programming is canceled
   * for the round, and you may not complete remaining registers.
   * Immediately discard any programming cards (including damage cards) in your remaining registers,
   * as well as those in your hand.
   * You must wait until the next round to program your robot.
   * Place your robot on the reboot token that is on the same board where your robot was when it rebooted.
   * You may turn your robot to face any direction.
   * If you rebooted from the start board, place your robot on the space where you started the game.
   */
  public void reboot(){
    discardPile.add(new Spam());
    discardPile.add(new Spam());
    discardPile.removeAll();
    setPosition(rebootToken);
  }
}

