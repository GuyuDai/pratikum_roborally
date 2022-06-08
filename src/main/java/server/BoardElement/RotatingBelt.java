package server.BoardElement;



import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;

public class RotatingBelt extends BoardElem implements Move {
    public RotatingBelt(RR currentGame, int speed, Direction direction,String turnDirection) {
        super("RotatingBelt ", currentGame);
        this.direction=direction;
        this.speed=speed;
        this.turnDirection=turnDirection;
    }

    @Override
    public void action() {
        move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

    }

    @Override
    public void move(Robot robot) {

        Direction arrowDirection = this.getDirection();
        Direction faceDirection=robot.getFaceDirection();
        if (this.getTurnDirection()=="turnLeft"){
            if (robot.getLastPosition().getTile().getName() == "ConveyBelt") {
                if (arrowDirection.equals(faceDirection.turnLeft())) {
                    robot.setFaceDirection(faceDirection.turnLeft());
                    robot.setCurrentPosition(new Position(robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()));
                }
                robot.push(robot, arrowDirection, this.getSpeed());

            }
            robot.getCurrentPosition().stay();
        }
        if (this.getTurnDirection()=="turnRight"){
            if (robot.getLastPosition().getTile().getName() == "ConveyBelt") {
                if (arrowDirection.equals(faceDirection.turnRight())) {
                    robot.setFaceDirection(faceDirection.turnRight());
                    robot.setCurrentPosition(new Position(robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()));
                }
                robot.push(robot,arrowDirection,this.getSpeed());

            }
            robot.getCurrentPosition().stay();
        }

    }
}
