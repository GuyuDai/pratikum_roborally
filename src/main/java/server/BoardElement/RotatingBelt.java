package server.BoardElement;



import server.CardTypes.Move;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;
import server.Player.Robot;
/**
 * @author Minghao Li
 * RotatingBelt is a kind of ConveyBelt,but it can make robot turn its faceDirection when it moves in the same direction
 * as RotatingBelt turnDirection arrow.
 */
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
    /**
     * @author Minghao Li
     * blue belt speed is 2,grenn is 1,if robot moves out of Belt,it will stop being pushed,if robot moveDirection is not
     * correct,its faceDirection can't be changed,RotatingBelt will do ConveyBelt action
     */
    @Override
    public void move(Robot robot) {
        Direction straightDirection=this.getDirection();//arrowDirection
        Direction curvedDirection=this.getDirection2();//anotherDirection  for example：  ↵ ,← is straightDirection ↓is curvedDirection
        int speed=this.getSpeed();
        if((robot.getLastPosition().getTile().getName().equals("ConveyBelt") ||
                robot.getLastPosition().getTile().getName().equals("RotatingBelt")) &&
                robot.getLastPosition().getTile().getDirection().equals(curvedDirection) ){
                robot.setFaceDirection(straightDirection);
                if (speed == 1) {
                    robot.push(robot, straightDirection, 1);
                }
                if (speed == 2) {
                    robot.push(robot, straightDirection, 1);
                    if (robot.getCurrentPosition().getTile().getName().equals("ConveyBelt")) {
                        robot.push(robot, straightDirection, 1);
                    }
                    if (robot.getCurrentPosition().getTile().getName().equals("RotatingBelt")) {
                        Direction moveDirection = robot.getCurrentPosition().getTile().getDirection();
                        robot.setFaceDirection(moveDirection);
                        robot.push(robot, moveDirection, 1);
                    } else {
                        robot.stay();
                    }
                }
        }
            else {
                if(speed==1) {
                    robot.push(robot, straightDirection, speed);
                }
                if(speed==2){
                    robot.push(robot, straightDirection, 1);
                    if(robot.getCurrentPosition().getTile().getName().equals("ConveyBelt")){
                        robot.push(robot,straightDirection, 1);
                    }
                    if(robot.getCurrentPosition().getTile().getName().equals("RotatingBelt")){
                        Direction moveDirection=robot.getCurrentPosition().getTile().getDirection();
                        robot.setFaceDirection(moveDirection);
                        robot.push(robot,moveDirection,1);
                    }
                    else {
                        robot.stay();
                    }
                }
            }

    }

    @Override
    public String toString(){
        String result = "";
        result += "RotatingBelt, speed:" + this.speed + ", in:" + this.direction2 + ", out:" + this.direction;
        return result;
    }
}
