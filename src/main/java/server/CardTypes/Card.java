package server.CardTypes;

import server.Game.RR;
import server.Player.Player;

public abstract class Card {
  public String name;
  public RR currentGame;
  private Player owner;

  public Card(){

  }

  public abstract void action();

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public String getCardName(){
    return name;
  }
}
