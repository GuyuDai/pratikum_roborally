package server.BoardElement;

import server.Game.RR;

public class Reboot extends BoardElem {
        public Reboot (RR currentGame) {

            super("Reboot",currentGame);

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

