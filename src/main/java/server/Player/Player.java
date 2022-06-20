package server.Player;

import client.gameWindow.HostHand;
import server.CardTypes.Card;
import server.CardTypes.MoveOne;
import server.Deck.ProgrammingDeck;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player implements PlayerAction{
  private ProgrammingDeck ownDeck;
  public Robot ownRobot;
  public static CopyOnWriteArrayList<Card> hands;

  public Player(){
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
    hands.add(new MoveOne());
  }

  private CopyOnWriteArrayList<Card> register;

  public Robot getOwnRobot() {
    return ownRobot;
  }

  public String name;

  public String getName() {
    return name;
  }

  public static CopyOnWriteArrayList<Card> getHands() {
    return hands;
  }

  public void setOwnRobot(Robot ownRobot) {
    this.ownRobot = ownRobot;
  }

  public CopyOnWriteArrayList<Card> getRegister() {
    return register;
  }

  public ProgrammingDeck getOwnDeck() {
    return ownDeck;
  }

  @Override
  public void draw() {
    for(int i = 9; i > 0; i--){
      hands.add(ownDeck.getRemainingCards().get(0));
      ownDeck.getRemainingCards().remove(0);
      new HostHand();
    }
  }

  @Override
  public void programming() {

  }

}
