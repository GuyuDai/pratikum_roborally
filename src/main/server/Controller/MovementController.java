package Controller;

/**
 * deal with the potential errors when moving a robot
 */
public class MovementController {

  public MovementController(){

  }

  public boolean check(Position togo){
    String str = togo.getTile().getName();
    switch (str){
      case "Nothing":
        return true;

      default:
        return false;
    }
  }
}
