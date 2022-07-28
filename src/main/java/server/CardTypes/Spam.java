package server.CardTypes;

/**
 * @author Li
 * This is the simplest form of damage. Robots must take a SPAM damage card when they are shot by a board or robot laser.
 * and player has to take a programming card from the top of his deck, and play it in his current register
 */
public class Spam extends Card implements DamageCards {
  public Spam(){
    super("Spam");
  }


  @Override
  public void action() {
    this.getOwner().getOwnDeck().getRemainingCards().get(0).action();
    this.getOwner().getOwnDeck().getRemainingCards().remove(0);
  }
}
