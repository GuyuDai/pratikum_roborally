package server.BoardElement;

import server.Game.RR;

public class EnergySpace extends BoardElem{
  public EnergySpace (RR currentGame,int count) {

    super("EnergySpace",currentGame);
    this.count=count;
  }

  @Override
  public void action() {

  }

}
