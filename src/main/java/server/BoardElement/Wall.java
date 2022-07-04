package server.BoardElement;
import server.CardTypes.Move;
import server.Control.Direction;
import server.Game.RR;
import server.Player.Robot;

public class Wall extends BoardElem {

    public  Wall(RR currentGame, Direction direction){
        super("Wall",currentGame);
        this.direction=direction;
    }

    @Override
    public void action() {

    }

    @Override
    public String toString(){
        String result = "";
        result += "Wall, direction:" + this.direction;
        return result;
    }
}
