package Client;

import CardTypes.Card;
import Deck.ProgrammingDeck;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player implements PlayerAction{
  private ProgrammingDeck ownDeck;
  public Robot ownRobot;
  private CopyOnWriteArrayList<Card> hands;

  private CopyOnWriteArrayList<Card> register;

  public Robot getOwnRobot() {
    return ownRobot;
  }

  public void setOwnRobot(Robot ownRobot) {
    this.ownRobot = ownRobot;
  }

  public CopyOnWriteArrayList<Card> getRegister() {
    return register;
  }

  @Override
  public void draw() {
    for(int i = 9; i > 0; i--){
      hands.add(ownDeck.getRemainingCards().get(0));
      ownDeck.getRemainingCards().remove(0);
    }
  }

  @Override
  public void programming() {

  }
}
