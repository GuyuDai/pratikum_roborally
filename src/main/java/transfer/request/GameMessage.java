package transfer.request;

import com.google.gson.Gson;
import server.ServerThread;
import transfer.Player;
import transfer.PlayerOnline;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class GameMessage {
    String message;

    public GameMessage(String message){
        this.message = message;
    }

    public void sendToAll() {
        Message publicGameMessage = new Message("Server", message, MessageTypes.SERVER_MESSAGE);
        String wrappedMessage = new Gson().toJson(new RequestWrapper(publicGameMessage, RequestType.MESSAGE));
        ServerThread.getPlayersOnline().stream()
                .forEach(playerOnline -> write(wrappedMessage, playerOnline.getPlayerSocket()));
    }

    public void sendToPlayer(Player targetPlayer) {
        Message publicGameMessage = new Message("Server", message, MessageTypes.SERVER_MESSAGE);
        String wrappedMessage = new Gson().toJson(new RequestWrapper(publicGameMessage, RequestType.MESSAGE));
        for(PlayerOnline playerOnline :  ServerThread.getPlayersOnline()){
            if(playerOnline.getPlayer().getName().equals(targetPlayer.getName())) {
                write(wrappedMessage, playerOnline.getPlayerSocket());
            }
        }
    }

    private void write(String message, Socket thread){
        try{
            BufferedWriter writeOutput = new BufferedWriter(new OutputStreamWriter(thread.getOutputStream()));
            writeOutput.write(message);
            writeOutput.newLine();
            writeOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


