package server.Game;

import server.Player.Player;

public interface GameLogic {

    Player getPlayerInCurrentTurn();
    void setPlayerInCurrentTurn();
}
