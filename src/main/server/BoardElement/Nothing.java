package BoardElement;

import Game.RR;

/**
 * @author dai
 * This class is used to represent that there is nothing at a certain position
 * in order to prevent null printer exception.
 */
public class Nothing extends BoardElem{
  public Nothing(RR currentGame) {
    super("Nothing", currentGame);
  }

  @Override
  public void action() {

  }
}
