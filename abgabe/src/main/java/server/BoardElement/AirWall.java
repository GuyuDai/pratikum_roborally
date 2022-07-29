package server.BoardElement;
import server.Game.RR;
import server.Player.Robot;
import server.CardTypes.Move;

/**
 * @author Dai, Nargess Ahmadi
 * this class is used to represent that there is nothing in a certain position
 * but the robot cannot reach or go through or distroy it or...
 */

public class AirWall extends BoardElem implements Move{

    public AirWall(RR currentGame) {
        super("AirWall",currentGame);
    }
    @Override
    public void action() {
        move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
    }

    @Override
    public void move(Robot robot) {
        robot.move(0);
    }
}
