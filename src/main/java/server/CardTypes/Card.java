package server.CardTypes;

import server.Game.RR;
import server.Player.GamePlayer;

public abstract class Card {
  public String name;
  public RR currentGame;
  private GamePlayer owner;

  public Card(String name){
    this.name=name;
  }

  public abstract void action();

  public GamePlayer getOwner() {
    return owner;
  }

  public void setOwner(GamePlayer owner) {
    this.owner = owner;
  }

  public String getCardName(){
    return name;
  }
}

