package server;

import com.google.gson.Gson;
import protocol.Welcome;
import server.BoardTypes.DizzyHighway;

public class MethodTest {

  public static void main(String[] args) {
    DizzyHighway map = new DizzyHighway();
    System.out.println(new Gson().toJson(map));
  }
}
