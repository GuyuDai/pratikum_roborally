package server.BoardTypes;
import server.Control.Direction;
import server.BoardElement.BoardElem;
import server.Game.RR;
import server.Control.Position;

public abstract class Board {
  public String name;
  public int width = 9;  //x
  public int height = 12;  //y
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

  public String getName() {
    return name;
  }

  @Override
  public String toString(){
    String result = "";
    result += "[" + "\n";
    for(BoardElem[][] elem2d : map){
      result += "  [" + "\n";
      for(BoardElem[] elem1d : elem2d){
        result += "    [";
        result += elem1d[0].toString() + "; ";
        result += elem1d[1].toString();
        result += "]" + "\n";
      }
      result += "  ]" + "\n";
    }
    result += "]";
    return result;
  }
}



