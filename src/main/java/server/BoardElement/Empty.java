package server.BoardElement;

import server.Game.RR;

/**
 * @author dai
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
}
