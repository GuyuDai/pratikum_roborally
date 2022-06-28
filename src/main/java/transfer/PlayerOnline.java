package transfer;

import server.Player.GamePlayer;

import java.net.Socket;

public class PlayerOnline {
    private Socket playerSocket;
    private Player player;

    private GamePlayer gamePlayer;

    public PlayerOnline(Socket playerSocket, Player player){
        this.playerSocket = playerSocket;
        this.player = player;
        this.gamePlayer=new GamePlayer(player.getName());
    }

    public Player getPlayer(){return player;}

    public Socket getPlayerSocket(){return playerSocket;}

    /**
     public boolean identifyPlayer(String name){
     return player.getName().contentEquals(name);
     }
     **/
}