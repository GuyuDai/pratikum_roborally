package BoardElement;

import CardTypes.Move;
import Game.RR;
import Player.Robot;

public class PushPanelTwo extends BoardElem implements Move {
    public PushPanelTwo(RR currentGame) {
        super("PushPanelTwo", currentGame);
    }
    @Override
    public void action() {
        move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
    }


    @Override
    public void move(Robot robot) {
        robot.move(2);
    }
}
