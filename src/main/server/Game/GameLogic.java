package Game;

import Player.Player;

public interface GameLogic {

    Player getPlayerInCurrentTurn();
    void setPlayerInCurrentTurn();
}
