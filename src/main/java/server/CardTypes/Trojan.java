package server.CardTypes;

public class Trojan extends Card implements DamageCards {
  public Trojan(){
    super("Trojan");
  }
  /**When you program a Trojan horse damage card, you must immediately take two SPAM damage cards.*/

  @Override
  public void action() {
    this.getOwner().drawDamage("Spam",2);
  }
}
