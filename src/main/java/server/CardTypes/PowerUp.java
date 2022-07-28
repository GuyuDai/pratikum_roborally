package server.CardTypes;


/**
 * @author Dai, Li
 */
public class PowerUp extends Card{
  public PowerUp(){
    super("PowerUp");
  }
  @Override
  public void action() {
    this.getOwner().setEnergyCubes(this.getOwner().getEnergyCubes()+1);
  }
}

