package transfer.request;

import java.net.Socket;
import transfer.Player;

public class PlayerSelection {
    private String selection;  // "Guard" or "Thomas"
    private Socket playerSocket;
    private Player player;

    public PlayerSelection(String selection, Socket playerSocket, Player player) {
        this.selection = selection;
        this.playerSocket = playerSocket;
        this.player = player;
    }

    public String getSelection() {
        return selection;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public Player getPlayer() {
        return player;
    }

    public void refresh() {
        this.selection = null;
        this.playerSocket = null;
        this.player = null;
    }
}
