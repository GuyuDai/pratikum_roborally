package server;

import client.ClientReceive;
import com.google.gson.Gson;
import java.util.HashMap;
import server.BoardElement.BoardElem;
import server.BoardElement.ConveyBelt;
import server.BoardTypes.Board;
import server.BoardTypes.DizzyHighway;
import server.BoardTypes.ExtraCrispy;
import server.Control.Direction;

import java.util.logging.Logger;

public class MethodTest {

  private static final Logger logger = Logger.getLogger(MethodTest.class.getName());
  public static final String ANSI_GREEN = "\u001B[32m";

  public static void main(String[] args) {
    DizzyHighway map = new DizzyHighway();
    logger.info(ANSI_GREEN + map);
  }
}
