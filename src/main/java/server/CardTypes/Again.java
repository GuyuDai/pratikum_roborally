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
    int i = this.getOwner().getRegister().indexOf(this);
    if(i != 0){
      Card lastCard = this.getOwner().getRegister().get(i-1);
      if(lastCard.getCardName().equals("Spam")
          || lastCard.getCardName().equals("Trojan")
          || lastCard.getCardName().equals("Virus")
          || lastCard.getCardName().equals("Worm")){
        Card activeCard = this.getOwner().getOwnDeck().getRemainingCards().get(0);
        activeCard.action();
        this.getOwner().getOwnDeck().getRemainingCards().remove(activeCard);
        this.getOwner().getOwnDeck().getDiscardPile().add(activeCard);
      }else {
        lastCard.action();
      }
    }
  }

}

