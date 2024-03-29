package server.BoardElement;

import server.Game.RR;

/**
 * @author Minghao Li
 * This class is used to represent that there is nothing at a certain position
 * in order to prevent null printer exception.
 */
public class Empty extends BoardElem{
  public Empty(RR currentGame) {
    super("Empty", currentGame);
  }

  @Override
  public void action() {

  }

  @Override
  public String toString(){
    String result = "";
    result += "Empty";
    return result;
  }
}
