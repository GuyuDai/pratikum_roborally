package server.Deck;

import server.CardTypes.*;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Minghao Li
 */
public class GameDeck {
  public CopyOnWriteArrayList<Card> damageCards;
  public CopyOnWriteArrayList<Card> upgradeCards;
  public CopyOnWriteArrayList<Card> specialProgrammingCards;
  public CopyOnWriteArrayList<Card> SpamPile;
  public CopyOnWriteArrayList<Card> VirusPile;
  public CopyOnWriteArrayList<Card> TrojanPile;
  public CopyOnWriteArrayList<Card> WormPile;

  /**
   * @author Minghao Li
   * intialize DamageCardPile for all players
   */
  public GameDeck(){
    this.SpamPile=new CopyOnWriteArrayList<Card>();
    this.VirusPile=new CopyOnWriteArrayList<Card>();
    this.TrojanPile=new CopyOnWriteArrayList<Card>();
    this.WormPile= new CopyOnWriteArrayList<Card>();
    // damageCards have 4 separate piles
    for(int i=0;i<=38;i++){
      SpamPile.add(new Spam());
    }
    //38 Spam Cards
    for(int i=0;i<=18;i++){
      VirusPile.add(new Virus());
    }
    //18 Virus Cards
    for (int i=0;i<=12;i++){
      TrojanPile.add(new Trojan());
    }
    //12 Trojan Cards
    for(int i=0;i<=6;i++){
      WormPile.add(new Worm());
    }
    //6 Worm Cards

  }

  public CopyOnWriteArrayList<Card> getSpamPile() {
    return SpamPile;
  }

  public CopyOnWriteArrayList<Card> getVirusPile() {
    return VirusPile;
  }

  public CopyOnWriteArrayList<Card> getTrojanPile() {
    return TrojanPile;
  }
  public CopyOnWriteArrayList<Card> getWormPile() {
    return WormPile;
  }
}

