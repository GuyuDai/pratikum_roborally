package Player;

import BoardElement.BoardElem;
import BoardElement.Nothing;
import Game.RR;
import CardTypes.Card;
import Controller.MovementController;
import Controller.Position;

public class Robot extends BoardElem implements RobotAction{
  public String name;

  public Position position;
  public RR currectGame;
  public MovementController MC;

  public Player owner;

  public Robot(RR currectGame){
    this.currectGame = currectGame;
    this.MC = currectGame.getMC();
  }

  /**
   * @author dai
   * please don't use this function!!
   */
  @Override
  public void effect() {
    return;
  }

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
      this.getPosition().setTile(new Nothing());
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  private void goBackwards(){
    Position togo = new Position
        (this.position.getX(), this.position.getY()-1, this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Nothing());
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  private void goLeft(){
    Position togo = new Position
        (this.position.getX()-1, this.position.getY(), this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Nothing());
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  private void goRight(){
    Position togo = new Position
        (this.position.getX()+1, this.position.getY(), this.position.getOrientation());
    boolean flag = this.MC.check(togo);
    if(flag){
      this.getPosition().setTile(new Nothing());
      this.setPosition(togo);
      togo.setTile(this);
    }
  }

  @Override
  public void act() {
    for(Card card : owner.getRegister()){
      card.effect();
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
