package server.Control;

import server.BoardElement.BoardElem;
import server.Player.Robot;

public class Position {
  private int x;  //colum
  private int y;  //row
  public Boolean frontWall;
  public Boolean leftWall;
  public Boolean rightWall;
  public Boolean backWall;

  public Robot occupiedRobot = null;

  public BoardElem tile;  //which BoardElement is at this position


  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @author dai
   * robot stay where it is
   */
  public Position stay() {
    return new Position(x,y);
  }


  public int getX() {
    return x;
  }


  public void setX(int x) {
    this.x = x;
  }


  public int getY() {
    return y;
  }


  public void setY(int y) {
    this.y = y;
  }

  public Robot getOccupiedRobot() {
    return occupiedRobot;
  }

  public void setOccupiedRobot(Robot robot) {
    this.occupiedRobot = robot;
  }
  public BoardElem getTile() {
    return tile;
  }

  public void setTile(BoardElem tile) {
    this.tile = tile;
  }

  /**
   * @author dai
   * get the next position the robot is going to go to
   * not move the robot yet when calling this method
   * @return
   */
  public Position getNextPosition(Direction direction){
    switch (direction){
      case DOWN:
        return new Position(this.getX(), this.getY()+1);

      case LEFT:
        return new Position(this.getX()-1, this.getY());

      case RIGHT:
        return new Position(this.getX()+1, this.getY());

      case UP:
        return new Position(this.getX(), this.getY()-1);
    }
    return null;
  }
  /**
   * @author dai
   * to compare whether two position are the same
   * @param p
   * @return
   */
  public boolean equals(Position p) {
    return p.getX() == this.getX() && p.getY() == this.getY();
  }

  public Boolean getFrontWall() {
    return frontWall;
  }

  public Boolean getLeftWall() {
    return leftWall;
  }

  public Boolean getRightWall() {
    return rightWall;
  }

  public Boolean getBackWall() {
    return backWall;
  }


}
