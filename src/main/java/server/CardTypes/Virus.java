package server.CardTypes;
import server.Control.Position;
import server.Player.Player;
import server.Player.Robot;

public class Virus extends Card implements DamageCards {

  /**@author Minghao Li
   * When you program a virus damage card, any robot on the board within a six-space radius of you must immediately
   * take a virus card from the draw pile.
   * use two for-loops to see if board within a six-space radius has robot
   * in if condition: Boardelem[i][j] RobotsPosition[x][y] i==x j==y will result true
   * not so sure,will change it if it doesn't work.*/

  @Override
  public void action() {
    Position position=this.getOwner().getOwnRobot().getCurrentPosition();
    int x=position.getX();
    int y= position.getY();
    for(int i=x-6;i<x+6;i++){
       for(int j=y-6;j<y+6;j++){
         for(Player player: currentGame.getActivePlayers())
         if(this.currentGame.getGameboard().getMap()[i][j].getPosition().getX()==player.getOwnRobot().getCurrentPosition().getX()
         && this.currentGame.getGameboard().getMap()[i][j].getPosition().getY()==player.getOwnRobot().getCurrentPosition().getY()){
            player.getHands().add(new Virus());
         }
       }
    }

}
}
