
package server.BoardElement;

import server.Game.*;
/**
 * @author Minghao Li
 * It's a place for robot to start.
 */
public class StartingPoint extends BoardElem {

    public  StartingPoint(RR currentGame){
        super("StartPoint",currentGame);
        this.direction=direction;
    }

    @Override
    public void action() {

    }

    @Override
    public String toString(){
        String result = "";
        result += "StartingPoint, direction:" + this.direction;
        return result;
    }
}