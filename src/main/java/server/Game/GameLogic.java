package server.Game;

import java.util.concurrent.CopyOnWriteArrayList;
import server.Player.GamePlayer;

public interface GameLogic {

    GamePlayer getPlayerInCurrentTurn();

    CopyOnWriteArrayList<GamePlayer> getActivePlayers();
}
