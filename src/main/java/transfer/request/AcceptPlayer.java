package transfer.request;

import client.gameWindow.GameViewModel;
import client.lobbyWindow.LobbyViewModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import client.lobbyWindow.MainApplication;
import transfer.Player;
import java.net.Socket;

/**
 * @author Felicia Saruba
 *
 * Answer to the client request for applying to join the server
  */

public class AcceptPlayer {
    private String message;
    private boolean accepted;
    private Player acceptedPlayer;

    public AcceptPlayer(String message, boolean accepted, Player acceptedPlayer){
        this.message = message;
        this.accepted = accepted;
        this.acceptedPlayer = acceptedPlayer;
    }

    public String getMessage(){
        return this.message;
    }

    public boolean isAccepted(){
        return this.accepted;
    }

    public Player getAcceptedPlayer(){return this.acceptedPlayer;}

    public void handleRequest(Socket playerSocket) {
        if(this.isAccepted()){
            handleAcceptedPlayer();
        } else {
            handleRejectedPlayer();
        }
    }

    /**
     * setting the name of the newly entered Player for Chatroom
     */
    private void handleAcceptedPlayer(){
        if(LobbyViewModel.getWindowName() == "Lobby") {
            //LobbyViewModel.setCurrentPlayer(this.getAcceptedPlayer());
            //GameViewModel.setCurrentPlayer(this.getAcceptedPlayer());
            //LobbyViewModel.show(this.getMessage());
           // Platform.runLater(() -> {MainApplication.window.setScene(MainApplication.getScene(0));});
        }
        else {
            GameViewModel.show(this.getMessage());
        }
    }

    private void handleRejectedPlayer(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(this.getMessage());
            alert.show();});
    }
}
