package server.CardTypes;

public class Worm extends Card implements DamageCards {
  public Worm(){
    super("Worm");
  }


  /** @author Nik
   * When you program a worm damage card, you must immediately reboot your robot.*/

  @Override
  public void action() {
    this.getOwner().getOwnRobot().reboot();
  }
}
