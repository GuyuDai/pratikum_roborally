package server.BoardElement;
import server.CardTypes.DamageCards;
import server.CardTypes.Spam;
import server.Game.RR;

public class Laser2 extends BoardElem implements DamageCards {

    public Laser2(RR currentGame){
        super("Laser",currentGame);

    }


    @Override
    public void action() {
        currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());
        currentGame.getPlayerInCurrentTurn().getHands().add(new Spam());
    }
}