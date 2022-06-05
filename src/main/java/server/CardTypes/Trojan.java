package server.CardTypes;

public class Trojan extends Card implements DamageCards {

  /**When you program a Trojan horse damage card, you must immediately take two SPAM damage cards.*/

  @Override
  public void action() {
    this.getOwner().getHands().add(new Spam());
    this.getOwner().getHands().add(new Spam());
  }
}
