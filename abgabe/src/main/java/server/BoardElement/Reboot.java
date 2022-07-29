package server.BoardElement;

import server.Control.Direction;
import server.Game.RR;

/**
 * @author Minghao Li
 * It's a place for robot to reboot by moving on this position and stay until next Round.
 */
public class Reboot extends BoardElem {
        public Reboot (RR currentGame, Direction direction) {

            super("Reboot",currentGame);
            this.direction = direction;
        }

        @Override
        public void action() {
        }

  @Override
  public String toString(){
    String result = "";
    result += "Reboot";
    return result;
  }

    }

