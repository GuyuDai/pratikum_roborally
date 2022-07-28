package server.BoardElement;

import com.sun.javafx.geom.DirtyRegionContainer;
import server.CardTypes.Move;
import server.Control.Direction;
import server.Game.RR;
import server.Player.Robot;

/**
 * @author Minghao Li
 * If  robot moves onto a Pushpannel,if players register is same as PushPannel's number, it will be pushed,
 * otherwise the robot will stay on PushPannel.
 */
public class PushPanel extends BoardElem implements Move {

  public PushPanel(RR currentGame, String push, Direction direction) {

    super("PushPanel", currentGame);
    this.direction=direction;
    this.push=push;
  }



  @Override
  public void action() {
    move(currentGame.getPlayerInCurrentTurn().getOwnRobot());
  }

  @Override
  public void move(Robot robot) {
    Direction pushDirection=this.getDirection().turn180();
    if(this.getPush().equals("1,3,5")) {
       switch(currentGame.getPlayerInCurrentTurn().getRegister().size()){
         case 1:
         case 3:
         case 5:
           robot.push(robot,pushDirection,1);
         case 2:
         case 4:
           robot.stay();
       }
       if(this.getPush().equals("2,4")){
         switch(currentGame.getPlayerInCurrentTurn().getRegister().size()){
           case 2:
           case 4:
             robot.push(robot,pushDirection,1);
           case 1:
           case 3:
           case 5:
             robot.stay();
         }
       }
    }
  }

  @Override
  public String toString(){
    String result = "";
    result += "PushPanel, type:" + this.push + ", direction:" + this.direction;
    return result;
  }
}
