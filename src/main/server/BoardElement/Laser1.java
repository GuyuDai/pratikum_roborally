package BoardElement;
import CardTypes.DamageCards;
import CardTypes.Spam;
import Game.RR;

public class Laser1 extends BoardElem implements DamageCards {

    public Laser1(RR currentGame){
        super("Laser",currentGame);

    }


  @Override
  public void action() {
          currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());

  }
}
