package transfer.request;

import client.mainWindow.MenuController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import client.mainWindow.MainApplication;
import transfer.Player;

import java.net.Socket;

/**
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

    private void handleAcceptedPlayer(){
        MenuController.setCurrentPlayer(this.getAcceptedPlayer());
        MenuController.show(this.getMessage());
        Platform.runLater(() -> {MainApplication.window.setScene(MainApplication.getScene(0));});
    }

    private void handleRejectedPlayer(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(this.getMessage());
            alert.show();});
    }
}
