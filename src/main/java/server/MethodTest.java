package server;

import client.ClientReceive;
import com.google.gson.Gson;
import java.util.HashMap;
import protocol.HelloServer;
import protocol.ProtocolFormat.Message;
import protocol.ProtocolFormat.ProtocolParser;
import server.BoardElement.BoardElem;
import server.BoardElement.ConveyBelt;
import server.BoardTypes.Board;
import server.BoardTypes.DizzyHighway;
import server.BoardTypes.ExtraCrispy;
import server.CardTypes.Card;
import server.CardTypes.MoveOne;
import server.Control.Direction;

import java.util.logging.Logger;

public class MethodTest {

  private static final Logger logger = Logger.getLogger(MethodTest.class.getName());
  public static final String ANSI_GREEN = "\u001B[32m";

  public static void main(String[] args) {
    //Message msg = new HelloServer("OO",false,"Version 1.0",1);
    Card msg = new MoveOne();
    ProtocolParser p = new ProtocolParser
        (new Gson().toJson(msg));
    System.out.println(p.parse());
  }
}
