package BoardElement;

import Player.Robot;

/**
 * @author dai, Nargess Ahmadi
 * this class is used to represent that there is nothing in a certain position
 * but the robot cannot reach or go through or distroy it or...
 */

public class AirWall extends Wall{
    @Override
    public void action() {
    }

    @Override
    public void move(Robot robot) {
        robot.move(0);
    }
}
