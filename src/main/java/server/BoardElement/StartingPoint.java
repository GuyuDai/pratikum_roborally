
package server.BoardElement;

import server.Game.*;

public class StartingPoint extends BoardElem {

    public  StartingPoint(RR currentGame){
        super("Wall",currentGame);
        this.direction=direction;
    }

    @Override
    public void action() {

    }

}