package transfer;

import java.net.Socket;

public class PlayerOnline {
    private Socket playerSocket;
    private Player player;

    public PlayerOnline(Socket playerSocket, Player player){
        this.playerSocket = playerSocket;
        this.player = player;
    }

    public Player getPlayer(){return player;}

    public Socket getPlayerSocket(){return playerSocket;}

    /**
     public boolean identifyPlayer(String name){
     return player.getName().contentEquals(name);
     }
     **/
}