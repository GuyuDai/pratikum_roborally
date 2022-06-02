package server.BoardElement;

import Game.RR;

/**
 * @author Nargess Ahmadi, Nassrin Djafari
 * Goal is to land on each of the checkpoints in your chosen course in numerical order.
 * If you’re the first to do so, you win! In order to complete a checkpoint,
 * you must be on it at the end of a register, and you may enter a checkpoint from
 * any side. Sometimes you’ll need to reach only one, and sometimes you’ll need to reach six!
 * When you complete a checkpoint, take a checkpoint token.
 */

public class CheckPoint extends BoardElem {
  public CheckPoint (RR currentGame) {
    super("CheckPoint",currentGame);
  }

  @Override
  public void action() {
  }



}
