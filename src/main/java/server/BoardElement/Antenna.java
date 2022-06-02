package server.BoardElement;

import server.Game.RR;

/**
 * @author nargessahmadi, nassrin
 * The priority antenna helps determine whose turn it is.
 * As a board element, it acts as a wall.
 * Robots cannot move through, push, shoot through, or occupy the same space as the priority antenna.
 */

public class Antenna extends BoardElem{
    public Antenna (RR currentGame) {
        super("Antenna",currentGame);
    }
    @Override
    public void action() {

    }



}
