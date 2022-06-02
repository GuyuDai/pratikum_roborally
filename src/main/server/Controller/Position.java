package Controller;

import BoardElement.BoardElem;

public class Position {
  private int x;  //colum
  private int y;  //row
  private int orientation;  //direction the robot faces to
  //0:forwards; 1:left; 2:right; 3:backwards

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

  public BoardElem getTile() {
    return tile;
  }

  public void setTile(BoardElem tile) {
    this.tile = tile;
  }

  public void setOrientation(int orientation) {
    this.orientation = orientation;
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
}
