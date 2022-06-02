package server.Player;

import server.BoardElement.BoardElem;
import server.BoardElement.Empty;
import server.Game.RR;
import server.CardTypes.Card;
import server.Controller.MovementController;
import server.Controller.Position;

public class Robot extends BoardElem implements RobotAction{
  public String name;

  public Position position;
  public RR currentGame;
  public MovementController MC;

  public Player owner;


  public Robot(RR currentGame){
    super("Robot",currentGame);
    this.MC = currentGame.getMC();


  }
  @Override
  public void action() {}

  /**
   * @author dai
   * please don't use this function!!
   */


  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  private void goForwards(){
    Position togo = new Position
        (this.position.getX(), this.position.getY()+1, this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Empty(currentGame));
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  private void goBackwards(){
    Position togo = new Position
        (this.position.getX(), this.position.getY()-1, this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Empty(currentGame));
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  private void goLeft(){
    Position togo = new Position
        (this.position.getX()-1, this.position.getY(), this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Empty(currentGame));
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  private void goRight(){
    Position togo = new Position
        (this.position.getX()+1, this.position.getY(), this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Empty(currentGame));
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  @Override
  public void act() {
    for(Card card : owner.getRegister()){
      card.action();
    }
  }

  @Override
  public void move(int step) {
    for(int i = 0; i < step; step++){
      Position now = this.getPosition();
      switch (now.getOrientation()){
        case 0:
          goForwards();
          break;

        case 1:
          goLeft();
          break;

        case 2:
          goRight();
          break;

        case 3:
          goBackwards();
          break;
      }
    }
  }
}
