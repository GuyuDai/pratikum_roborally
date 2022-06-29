package transfer;

import server.Player.Player;

import java.net.Socket;

public class PlayerOnline {
    private Socket playerSocket;
    private transfer.Player player;

    private Player gamePlayer;

    public PlayerOnline(Socket playerSocket, transfer.Player player){
        this.playerSocket = playerSocket;
        this.player = player;
        this.gamePlayer=new Player(player.getName());
    }

    public transfer.Player getPlayer(){return player;}

    public Socket getPlayerSocket(){return playerSocket;}

    /**
     public boolean identifyPlayer(String name){
     return player.getName().contentEquals(name);
     }
     **/
}