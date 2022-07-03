package server.BoardTypes;
import server.Control.Direction;
import server.BoardElement.BoardElem;
import server.Game.RR;
import server.Control.Position;

public abstract class Board {
  public String name;
  public int width;  //x
  public int height;  //y
  public BoardElem[][][] map;

  public Direction direction;

  public RR currentGame;

  public Position position;


  public Board(String name){
    this.name=name;
  }


  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BoardElem[][][] getMap() {
    return map;
  }

  public BoardElem getBoardElem(int x,int y, int z){
    return map[x][y][z];
  }




}



