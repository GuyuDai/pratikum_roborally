package client.AI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import server.Control.Position;

public class AIController {
  private Position currentPosition;
  private String board;

  private Map<Integer,int[]> checkPoints = new HashMap<Integer, int[]>();

  public AIController(String board){
    this.board = board;

    switch (board){
      case "DizzyHighway":
        checkPoints.put(1, new int[]{3,12});
        break;

      case "ExtraCrispy":
        checkPoints.put(1, new int[]{2,10});
        checkPoints.put(2, new int[]{7,5});
        checkPoints.put(3, new int[]{7,10});
        checkPoints.put(4, new int[]{2,5});
        break;

      case "DeathTrap":
        checkPoints.put(1, new int[]{7,1});
        checkPoints.put(2, new int[]{4,4});
        checkPoints.put(3, new int[]{8,7});
        checkPoints.put(4, new int[]{2,8});
        checkPoints.put(5, new int[]{1,0});
        break;

      case "LostBearings":
        checkPoints.put(1, new int[]{4,11});
        checkPoints.put(2, new int[]{5,4});
        checkPoints.put(3, new int[]{2,8});
        checkPoints.put(4, new int[]{7,8});
        break;

      case "Twister":
        checkPoints.put(1, new int[]{1,10});
        checkPoints.put(2, new int[]{7,6});
        checkPoints.put(3, new int[]{3,5});
        checkPoints.put(4, new int[]{7,9});
        break;
    }
  }

  public CopyOnWriteArrayList<String> programm(String[] hands){





    return null;
  }

}
