package server.CardTypes;
import server.Player.Robot;

public class Again extends Card {

  /**Repeat the programming in your previous register. If your previous register was a damage card,
   * draw a card from the top of your deck, and play that card this register.
   * If you used an upgrade in your previous register that allowed you to play multiple programming cards,
   * re-execute the second card. This card cannot be played in the first register.
   * @author Li*/

  public Again(){
    super("Again");
  }


  @Override
  public void action() {
    int i=this.getOwner().getRegister().indexOf(this);
    Card lastCard=this.getOwner().getRegister().get(i-1);
    if(lastCard instanceof Spam||lastCard instanceof Trojan||lastCard instanceof Virus||lastCard instanceof Worm){
      this.getOwner().getOwnDeck().getRemainingCards().get(0).action();
      this.getOwner().getOwnDeck().getRemainingCards().remove(0);

    }
    lastCard.action();

  }

}

