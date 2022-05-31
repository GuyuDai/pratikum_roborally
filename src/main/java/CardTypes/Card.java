package CardTypes;

import Game.RR;
import Client.Player;

public abstract class Card {
  public String name;
  public RR currentGame;
  private Player owner;

  public Card(){

  }

  public abstract void effect();

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }
}
