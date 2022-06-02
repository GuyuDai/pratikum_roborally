package BoardElement;

import CardTypes.Move;
import Game.RR;
import Player.Robot;

/**
 * @author Nargess Ahmadi, Nassrin Djafari
 * Conveyor belts move your robot in the direction of the arrows.
 * Double-arrowed conveyor belts move robots two spaces and activate before single-arrowed conveyor belts,
 * which move robots one space. Once a robot has moved off a belt, the belt no longer affects that robot.
 * See the examples below.
 */

public class ConveyBelt extends BoardElem implements Move {
  public ConveyBelt (RR currentGame) {
    super("ConveyBelt",currentGame);
  }

  @Override
  public void action() {
  }


  @Override
  public void move(Robot robot) {

  }
}
