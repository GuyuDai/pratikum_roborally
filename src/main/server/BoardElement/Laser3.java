package BoardElement;
import CardTypes.DamageCards;
import CardTypes.Spam;
import Game.RR;

public class Laser3 extends BoardElem implements DamageCards {

    public Laser3(RR currentGame){
        super("Laser",currentGame);

    }


    @Override
    public void action() {
        currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());
        currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());
        currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());

    }
}