package server.BoardElement;



import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;

public class RotatingBeltLeft extends BoardElem implements Move {
    public RotatingBeltLeft(RR currentGame) {
        super("RotatingBelt ", currentGame);
    }

    @Override
    public void action() {
        move(currentGame.getPlayerInCurrentTurn().getOwnRobot());


    }

    @Override
    public void move(Robot robot) {

        Direction arrowDirection = this.getPosition().getFaceDirection();
        if (robot.getLastPosition().getTile().getName() == "ConveyBeltBeltDoubleArrow"
                || robot.getLastPosition().getTile().getName() == "ConveyBeltBeltDoubleArrow") {
            if (arrowDirection.equals(robot.getPosition().getFaceDirection().turnLeft())) {
                Direction NewFaceDirection = robot.getPosition().getFaceDirection().turnLeft();
                robot.setPosition(new Position(robot.getPosition().getX(), robot.getPosition().getY(), NewFaceDirection));
            }
            robot.getPosition().stay();

        }
    }
}
