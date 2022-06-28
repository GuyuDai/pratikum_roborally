package server.CardTypes;

public class Spam extends Card implements DamageCards {
  public Spam(){
    super("Spam");
  }

  /**This is the simplest form of damage. Robots must take a SPAM damage card when they are shot by a board or robot laser.
   * and player has to take a programming card from the top of his deck, and play it in his current register
   * @author Li*/

  @Override
  public void action() {
    this.getOwner().getRegister().add(this.getOwner().getOwnDeck().getRemainingCards().get(0));
    this.getOwner().getOwnDeck().getRemainingCards().remove(0);
  }
}
