package client.gameWindow;

import client.lobbyWindow.LobbyViewModel;
import client.lobbyWindow.MainApplication;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import transfer.Player;

import java.io.IOException;

/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
 */

public class GameViewModel extends LobbyViewModel {

    //public GameViewModel(){
      //  new HostHand();
    //}

    @FXML
    public AnchorPane container;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField input;
    @FXML
    private Button sendBtn;
    @FXML
    private StackPane maps;
    @FXML
    private TilePane dizzyHighway;
    @FXML
    private TilePane discardDeck;
    @FXML
    private TilePane hand;
    @FXML
    private Button chatBtn;
    @FXML
    private VBox chatWindow;

    private static GameModel model;

    private static GameViewModel instance;

    private Gson gson = new Gson();

    private static Player currentPlayer;

    //ToDo: nur eins von beiden initialize methods
    public void initializeee (){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Game.fxml"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        list.itemsProperty().set(model.getListContentProperty());
        input.textProperty().bindBidirectional(model.getTextFieldContent());
        new HostHand();
    }

    /**
     * opens ChatWindow by clicking the Open Chat Button
     * @param actionEvent
     */
    @FXML
    public void showChat(ActionEvent actionEvent) {
        if (chatWindow.isVisible()) {
            chatWindow.setVisible(false);
            chatBtn.setText("Open Chat");
        } else {
            chatWindow.setVisible(true);
            chatBtn.setText("Close Chat");
        }
    }
}
