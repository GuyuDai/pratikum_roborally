package server.Control;
import server.BoardTypes.Board;
import server.BoardElement.BoardElem;
import server.Player.Robot;

public class Position {
  private int x;  //colum
  private int y;  //row

  public Robot occupiedRobot = null;

  public Board board;

  public BoardElem tile;  //which BoardElement is at this position


  public Position(int x, int y, Board board) {
    this.x = x;
    this.y = y;
    this.board = board;
  }

  /**
   * @author dai
   * robot stay where it is
   */
  public Position stay() {
    return new Position(x,y,board);
  }


  public void setBoard(Board board){
    this.board=board;
  }

  public Board getBoard(){
    return board;
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

  public BoardElem getSecondTile(){
    return getBoard().getBoardElem(this.getX(),this.getY(),1);
  }
  //a Position could have 2 Tiles
  public BoardElem getTile() {
    return getBoard().getBoardElem(this.getX(),this.getY(),0);
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
        return new Position(this.getX()+1, this.getY(),getBoard());

      case LEFT:
        return new Position(this.getX(), this.getY()-1,getBoard());

      case RIGHT:
        return new Position(this.getX(), this.getY()+1,getBoard());

      case UP:
        return new Position(this.getX()-1, this.getY(),getBoard());
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


}
