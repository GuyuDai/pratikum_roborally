package BoardElement;

import Controller.Position;

public abstract class BoardElem {
  public String name;
  public Position position;

  public BoardElem(){

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

