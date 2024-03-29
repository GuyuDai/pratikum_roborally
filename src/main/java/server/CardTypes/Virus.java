package server.CardTypes;
import server.Control.Position;
import server.Player.Player;

/**
 * @author Li
 *  When you program a virus damage card, any robot on the board within a six-space radius of you must immediately
 *  take a virus card from the draw pile.
 *  use two for-loops to see if board within a six-space radius has robot
 *  in if condition: Boardelem[i][j] RobotsPosition[x][y] i==x j==y will result true
 */
public class Virus extends Card implements DamageCards {
    public Virus(){
        super("Virus");
    }


    @Override
    public void action() {
        Position position=this.getOwner().getOwnRobot().getCurrentPosition();
        int x=position.getX();
        int y= position.getY();
        for(int i=x-6;i<x+6;i++){
            for(int j=y-6;j<y+6;j++){
                Position checkPosition=new Position(i,j, position.getBoard());
                for(Player player: currentGame.getActivePlayers())
                    if(player.getOwnRobot().getCurrentPosition().equals(checkPosition)){
                        player.getHands().add(new Virus());
                    }
            }
        }

    }
}

