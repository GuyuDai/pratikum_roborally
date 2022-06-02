package server.BoardElement;

import server.Controller.Position;
import server.Game.RR;

public abstract class BoardElem {
  public String name;
  public Position position;
  protected RR currentGame;


  public BoardElem(String name,RR currentGame){
     this.name=name;
     this.currentGame=currentGame;


  }

  public abstract void action();

  //public abstract void show();  //do we need a method to show how a BoardElement looks like


  public String getName() {
    return name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }


}

