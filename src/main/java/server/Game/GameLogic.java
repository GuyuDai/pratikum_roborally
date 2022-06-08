package server.Game;

import java.util.concurrent.CopyOnWriteArrayList;
import server.Player.Player;

public interface GameLogic {

    Player getPlayerInCurrentTurn();
    void setPlayerInCurrentTurn();
    CopyOnWriteArrayList<Player> getActivePlayers();
}
