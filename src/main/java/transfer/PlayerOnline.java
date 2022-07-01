package transfer;

import server.Player.Player;
import server.ServerThread;

import java.net.Socket;

public class PlayerOnline {
    private ServerThread serverThread;
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

    public Player getGamePlayer() {
        return gamePlayer;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }
    /**
     public boolean identifyPlayer(String name){
     return player.getName().contentEquals(name);
     }
     **/
}