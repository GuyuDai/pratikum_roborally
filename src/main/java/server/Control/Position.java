package server.Control;

import server.BoardElement.BoardElem;
import server.BoardElement.Direction;
import server.Player.Robot;

public class Position {
  private int x;  //colum
  private int y;  //row
  private Direction faceDirection;  //direction the robot faces to
  //0:forwards; 1:left; 2:right; 3:backwards

  public Boolean frontWall;
  public Boolean leftWall;
  public Boolean rightWall;
  public Boolean backWall;

  public Robot occupiedRobot = null;

  public BoardElem tile;  //which BoardElement is at this position


  public Position(int x, int y, Direction faceDirection) {
    this.x = x;
    this.y = y;
    this.faceDirection = faceDirection;
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
  public void setOrientation(Direction faceDirection) {
    this.faceDirection = faceDirection;
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
    switch (this.faceDirection){
      case UP:
        return new Position(this.getX(), this.getY()+1, this.getFaceDirection());

      case LEFT:
        return new Position(this.getX()-1, this.getY(), this.getFaceDirection());

      case RIGHT:
        return new Position(this.getX()+1, this.getY(), this.getFaceDirection());

      case DOWN:
        return new Position(this.getX(), this.getY()-1, this.getFaceDirection());
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
