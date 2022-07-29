package server.CardTypes;


/**
 * @author Dai, Li
 * When you program a Trojan horse damage card, you must immediately take two SPAM damage cards
 */
public class Trojan extends Card implements DamageCards {
  public Trojan(){
    super("Trojan");
  }


  @Override
  public void action() {
    this.getOwner().drawDamage("Spam",2);
  }
}
