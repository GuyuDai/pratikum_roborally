package server.BoardElement;

import server.Game.RR;

public class EnergySpace extends BoardElem{
  public EnergySpace (RR currentGame,int count) {

    super("EnergySpace",currentGame);
    this.count=count;
  }


  @Override
  public void action() {
    int energyCount=currentGame.getPlayerInCurrentTurn().getEnergyCubes()+1;
    currentGame.getPlayerInCurrentTurn().setEnergyCubes(energyCount);
    this.count--;
  }

  @Override
  public String toString(){
    String result = "";
    result += "EnergySpace, count:" + this.count;
    return result;
  }
}

