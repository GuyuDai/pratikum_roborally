package Deck;

import CardTypes.*;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProgrammingDeck {
  private CopyOnWriteArrayList<Card> remainingCards;
  private CopyOnWriteArrayList<Card> discardPile;

  public ProgrammingDeck(){
    this.remainingCards = new CopyOnWriteArrayList<Card>();
    this.discardPile = new CopyOnWriteArrayList<Card>();

    remainingCards.add(new MoveOne());  //1 move1
    remainingCards.add(new MoveOne());  //2 move1
    remainingCards.add(new MoveOne());  //3 move1
    remainingCards.add(new MoveOne());  //4 move1
    remainingCards.add(new MoveOne());  //5 move1
    remainingCards.add(new MoveTwo());  //1 move2
    remainingCards.add(new MoveTwo());  //2 move2
    remainingCards.add(new MoveTwo());  // 3 move2
    remainingCards.add(new MoveThree());  //1 move3
    remainingCards.add(new TurnRight());  //1 turn right
    remainingCards.add(new TurnRight());  //2 turn right
    remainingCards.add(new TurnRight());  //3 turn right
    remainingCards.add(new TurnLeft());  //1 turn left
    remainingCards.add(new TurnLeft());  //2 turn left
    remainingCards.add(new TurnLeft());  //3 turn left
    remainingCards.add(new BackUp());  //1 back up
    remainingCards.add(new PowerUp());  //1 power up
    remainingCards.add(new Again());  //1 again
    remainingCards.add(new Again());  //2 again
    remainingCards.add(new UTurn());  //1 u-turn

    Collections.shuffle(remainingCards);
  }

  public void shuffle(){
    this.remainingCards = this.discardPile;
    Collections.shuffle(remainingCards);
    this.discardPile = new CopyOnWriteArrayList<Card>();
  }

  public void addDamageCard();
  public void addUpgradeCard();
  public void addSpecialProgrammingCard();

  public CopyOnWriteArrayList<Card> getRemainingCards() {
    return remainingCards;
  }

  public void setRemainingCards(CopyOnWriteArrayList<Card> remainingCards) {
    this.remainingCards = remainingCards;
  }
}
