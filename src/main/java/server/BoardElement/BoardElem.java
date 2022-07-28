package server.BoardElement;

import server.BoardTypes.Board;
import server.Control.Direction;
import server.Control.Position;
import server.Game.RR;

/**
 * @author Minghao Li
 */
public abstract class BoardElem {
  public String name;
  public Position position;
  public RR currentGame;
  public Direction direction;
  public int speed;
  public int count;
  public String turnDirection;
  public Direction direction2;
  public  String push;
  private Board gameBoard;



  public BoardElem(String name,RR currentGame){
    this.name=name;
    this.currentGame=currentGame;
  }

  public abstract void action();

  //public abstract void show();  //do we need a method to show how a BoardElement looks like


  public String getName() {
    return name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Direction getDirection(){
    return direction;
  }

  public Direction getDirection2(){
    return  direction2;
  }

  public int getSpeed() {
    return speed;
  }

  public int getCount() {
    return count;
  }

  public String getTurnDirection(){
    return turnDirection;
  }

  public String getPush() {
    return push;
  }

  public RR getCurrentGame() {
    return currentGame;
  }

  public void setCurrentGame(RR currentGame) {
    this.currentGame = currentGame;
  }

  public Board getGameBoard() {
    return gameBoard;
  }

  public void setGameBoard(Board gameBoard) {
    this.gameBoard = gameBoard;
  }
}



