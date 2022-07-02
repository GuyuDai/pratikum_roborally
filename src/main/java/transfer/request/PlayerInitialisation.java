package transfer.request;

import com.google.gson.Gson;
import server.ServerThread;
import transfer.Player;
import transfer.PlayerOnline;
import transfer.RequestWrapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.stream.Collectors;

/**
 * initializes players and informs all players that someone new joined.
 */
public class PlayerInitialisation{
    private String name;

    public PlayerInitialisation(String name){
        this.name = name;
    }

    public String getName(){return this.name;}

    public void setName(String name){
        this.name = name;
    }

    public void handleRequest(Socket playerSocket) {
        Player currentAddedPlayer = new Player(this.name);
        AcceptPlayer addingRequestResult;
        if(ServerThread.getPlayersOnline().isEmpty()){
            ServerThread.getPlayersOnline().add(new PlayerOnline(playerSocket, currentAddedPlayer));
            addingRequestResult = new AcceptPlayer(createAcceptingMessage(currentAddedPlayer), true, currentAddedPlayer);
        }else {
            boolean isNameUnique = ServerThread.getPlayersOnline().stream()
                    .filter(playersOnline -> playersOnline.getPlayer().getName().equals(currentAddedPlayer.getName())).collect(Collectors.toList()).isEmpty();
            if(!isNameUnique){
                addingRequestResult = new AcceptPlayer(createRejectionMessage(), false, null);
            } else{
                addingRequestResult = acceptingRoutine( playerSocket, currentAddedPlayer);
            }
        }
        write(new Gson().toJson(new RequestWrapper(addingRequestResult, RequestType.ACCEPT_PLAYER)), playerSocket);
    }

    private String createRejectionMessage(){
        return "Player already exists. Please try a different username";
    }

    /**
     * Placing the player object into the players Map
     * @return
     */
    private AcceptPlayer acceptingRoutine(Socket playerThread, Player currentAddedPlayer) {
        ServerThread.getPlayersOnline().add(new PlayerOnline(playerThread, currentAddedPlayer));
        notificationNewPlayerAdded(currentAddedPlayer);
        return new AcceptPlayer(createAcceptingMessage(currentAddedPlayer), true, currentAddedPlayer);
    }
    private String createAcceptingMessage(Player addedPlayer) {
        return "Welcome " + addedPlayer.getName();
    }

    /**
     * Notify all clients about the new player
     */
    private void notificationNewPlayerAdded(Player currentAddedPlayer){
        Message addedMessage = new Message("Server", createNotificationMessage(currentAddedPlayer), MessageTypes.SERVER_MESSAGE);
        String wrappedMessage = new Gson().toJson(new RequestWrapper(addedMessage, RequestType.MESSAGE));
        ServerThread.getPlayersOnline().stream()
                .filter(isNewPlayer -> !isNewPlayer.getPlayer().getName().equals(currentAddedPlayer.getName()))
                .forEach(playerOnline -> write(wrappedMessage, playerOnline.getPlayerSocket()));
    }

    private String createNotificationMessage(Player currentAddedPlayer){
        return currentAddedPlayer.getName() + " just joined the Server!";
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
