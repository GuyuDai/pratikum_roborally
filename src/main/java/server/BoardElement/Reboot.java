package server.BoardElement;

import server.Control.Direction;
import server.Game.RR;

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

