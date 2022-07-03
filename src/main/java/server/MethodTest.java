package server;

import com.google.gson.Gson;
import server.BoardElement.ConveyBelt;
import server.BoardTypes.ExtraCrispy;
import server.Control.Direction;

public class MethodTest {

  public static void main(String[] args) {
    ExtraCrispy map = new ExtraCrispy();
    ConveyBelt element = new ConveyBelt(null,2, Direction.UP);
    System.out.println(new Gson().toJson(element));
  }
}
