package server.BoardElement;



import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;

public class RotatingBelt extends BoardElem implements Move {
    public RotatingBelt(RR currentGame, int speed, Direction direction,Direction direction2) {
        super("RotatingBelt", currentGame);
        this.direction=direction;
        this.speed=speed;
        this.direction2=direction2;
    }

    @Override
    public void action() {
        move(currentGame.getPlayerInCurrentTurn().getOwnRobot());

    }

    @Override
    public void move(Robot robot) {
        Direction straightDirection=this.getDirection();//arrowDirection
        Direction curvedDirection=this.getDirection2();//anotherDirection  for example：  ↵ ,← is straightDirection ↓is curvedDirection
        if(robot.getLastPosition().getTile().getName()=="ConveyBelt"||
                robot.getLastPosition().getTile().getName()=="RotatingBelt"){
            if(robot.getLastPosition().getTile().getDirection().equals(curvedDirection)){
                robot.setFaceDirection(straightDirection);
            }
            robot.getCurrentPosition().stay();
        }
    }

    @Override
    public String toString(){
        String result = "";
        result += "RotatingBelt, speed:" + this.speed + ", in:" + this.direction2 + ", out:" + this.direction;
        return result;
    }
}
