package server.BoardElement;
import server.CardTypes.DamageCards;
import server.CardTypes.Spam;
import server.Control.Direction;
import server.Game.RR;


/**
 * @author Minghao Li
 * If  robot is shot by a board laser, player must take damage in the form of one SPAM damage card
 * each laser on that space and place it in yourdiscard pile.
 */
public class Laser extends BoardElem implements DamageCards {

    public Laser(RR currentGame, int count, Direction direction){
        super("Laser",currentGame);
        this.count=count;
        this.direction=direction;

    }


  @Override
  public void action() {
        for(int i=0;i<this.getCount();i++) {
            currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());
        }

  }

  @Override
  public String toString(){
    String result = "";
    result += "Laser, count:" + this.count + ", direction:" + this.direction;
    return result;
  }
}
