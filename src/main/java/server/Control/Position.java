package server.Control;

import server.BoardElement.BoardElem;
import server.Player.Robot;

public class Position {
  private int x;  //colum
  private int y;  //row
  private int orientation;  //direction the robot faces to
  //0:forwards; 1:left; 2:right; 3:backwards

  public Boolean frontWall;
  public Boolean leftWall;
  public Boolean rightWall;
  public Boolean backWall;

  public Robot occupiedRobot = null;

  public BoardElem tile;  //which BoardElement is at this position


  public Position(int x, int y, int orientation) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }

  /**
   * @author dai
   * robot stay where it is
   */
  public Position stay() {
    return new Position(x,y,orientation);
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


  public int getOrientation() {
    return orientation;
  }
  public void setOrientation(int orientation) {
    this.orientation = orientation;
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

  public Position getNextPosition(){
    switch (this.orientation){
      case 0:
        return new Position(this.getX(), this.getY()+1, this.getOrientation());

      case 1:
        return new Position(this.getX()-1, this.getY(), this.getOrientation());

      case 2:
        return new Position(this.getX()+1, this.getY(), this.getOrientation());

      case 3:
        return new Position(this.getX(), this.getY()-1, this.getOrientation());
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
