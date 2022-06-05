package server.BoardTypes;

import server.BoardElement.BoardElem;

public abstract class Board {
  public String name;
  public int width;  //x
  public int height;  //y
  public BoardElem[][] map;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}
