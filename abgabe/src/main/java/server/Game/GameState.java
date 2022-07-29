package server.Game;

/**
 * @author Dai
 * mark which state the game has reached
 */
public enum GameState {
  GameInitializing,
  GameStarting,
  RoundStarting,
  UpgradePhase,  //Use energy cubes to purchase upgrades for your robot
  ProgrammingPhase,  //Draw cards from your programming deck
  //and arrange them on your player mat to plot the moves you want your robot to make
  ActivationPhase,  //Activate your robot, and carry out its programming. Board elements will also activate
  RoundEnding,
  GameEnding;
}
