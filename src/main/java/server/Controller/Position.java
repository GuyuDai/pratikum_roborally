package server.Controller;

import server.BoardElement.BoardElem;
import server.BoardElement.Direction;

public class Position {
  private int x;  //colum
  private int y;  //row
  private Direction faceDirection;  //direction the robot faces to
  //0:forwards; 1:left; 2:right; 3:backwards

  public BoardElem tile;  //which BoardElement is at this position


  public Position(int x, int y, Direction faceDirection) {
    this.x = x;
    this.y = y;
    this.faceDirection =faceDirection;
  }

  /**
   * @author dai
   * robot stay where it is
   */
  public Position stay() {
    return new Position(x,y, faceDirection);
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


  public Direction getFaceDirection() {
    return faceDirection;
  }

  public BoardElem getTile() {
    return tile;
  }

  public void setTile(BoardElem tile) {
    this.tile = tile;
  }

  public void setFaceDirection(Direction faceDirection) {
    this.faceDirection = faceDirection;
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
