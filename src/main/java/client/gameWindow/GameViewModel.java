package client.gameWindow;

import client.Client;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import transfer.Player;
import transfer.request.Message;
import transfer.request.MessageTypes;
import transfer.request.RequestType;
import transfer.request.RequestWrapper;
import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import transfer.request.AcceptPlayer;





/**
 * @author Nargess Ahmadi, Felicia Saruba
 */
public class GameViewModel  {


    //public GameViewModel(){
    //  new HostHand();
    //}

    private static GameModel model;
    private static GameViewModel instance;
    private Gson gson = new Gson();
    private static Player currentPlayer;
    boolean isAvailable;


    @FXML
    private Button chatBtn;
    @FXML
    private Button exitBtn;
    @FXML
    public VBox container;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField input;
    @FXML
    private Button sendBtn;
    @FXML
    private Label chatwindow;
    @FXML
    private AnchorPane gameContainer;
    @FXML
    private TilePane dizzyHighway;
    @FXML
    private ImageView hand1;
    @FXML
    private ImageView hand2;
    @FXML
    private ImageView hand3;
    @FXML
    private ImageView hand4;
    @FXML
    private ImageView hand5;
    @FXML
    private ImageView hand6;
    @FXML
    private ImageView hand7;
    @FXML
    private ImageView hand8;
    @FXML
    private ImageView hand81;
    @FXML
    private ImageView hand82;
    @FXML
    private ImageView hand83;
    @FXML
    private ImageView hand84;
    @FXML
    private ImageView hand85;
    @FXML
    private ImageView hand9;
    @FXML
    private HBox hboxDiscard;
    @FXML
    private HBox hboxHand;
    @FXML
    private StackPane maps;
    @FXML
    private TilePane discardDeck;
    @FXML
    private TilePane hand;

    public String window = "Game";


    public void initialize() {
        list.itemsProperty().set(model.getListContentProperty());
        input.textProperty().bindBidirectional(model.getTextFieldContent());
    }

    public void setNodeElements(VBox container, ListView<String> list, TextField input, Button sendBtn) {
        this.gameContainer = gameContainer;
        this.container = container;
        this.list = list;
        this.input = input;
        this.sendBtn = sendBtn;
        this.currentPlayer = currentPlayer;
    }

    //toDo: Fix chat in game view -> current player not working
    public static void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public GameViewModel() {
        model = GameModel.getInstance();
    }

    public static GameViewModel getInstance() {
        return instance;
    }

    public GameModel getModel() {
        return model;
    }

    public static void show(String message) {
        model.addNewListItem(message);
    }


    @FXML
    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        String message = model.getTextFieldContent().get();

        checkInput(message);
        System.out.println(message);

        model.getTextFieldContent().set("");
        input.requestFocus();
    }

    //takes message from textfield
    private void checkInput(String message){
        String sendableRequest = "";


        if(message.startsWith("@")) {
            sendableRequest = createDirectMessage(message);

            /*
         } else if (message.startsWith("!")){
            sendableRequest = createCommandRequest(message);
         } else {

*/
        }else {
            sendableRequest = createMessage(message);
        }
        if(!sendableRequest.isEmpty()){
            try {
                Client.getClientReceive().getWriteOutput().write(sendableRequest);
                Client.getClientReceive().getWriteOutput().newLine();
                Client.getClientReceive().getWriteOutput().flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //send messages in the chat
    private String createMessage(String message){
        String createMessageWrapped = gson.toJson(new RequestWrapper(new Message(currentPlayer.getName(), message, MessageTypes.CLIENT_MESSAGE), RequestType.MESSAGE));
        return createMessageWrapped;
    }

    private String createDirectMessage(String message){
        message = message.replace("@", "");
        String [] splittingTarget = message.split(" ");
        StringBuilder realMessage = new StringBuilder("");
        for(int i = 1; i < splittingTarget.length; i++){
            realMessage.append(splittingTarget[i]).append(" ");
        }
        String createMessageWrapped = gson.toJson(new RequestWrapper(new Message("Private Message from "+ currentPlayer.getName(), splittingTarget[0].trim(), realMessage.toString().trim(), MessageTypes.PRIVATE_MESSAGE), RequestType.MESSAGE));
        return createMessageWrapped;
    }


    //send messages using keyboard "Enter" key
    @FXML
    public void keyboardAction(KeyEvent keyEvent) throws IOException{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    /*
     * opens ChatWindow by clicking the Open Chat Button
     * @param actionEvent
     */
    public void chatBtnAction(ActionEvent actionEvent){
        if (container.isVisible()) {
            container.setVisible(false);
            chatBtn.setText("Open Chat");
        } else {
            container.setVisible(true);
            chatBtn.setText("Close Chat");
        }
    }

    public void exitGame(ActionEvent actionEvent) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Lobby.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stageLobby = new Stage();
            stageLobby.setTitle("Lobby");
            stageLobby.setScene(new Scene(rootMap));
            stageLobby.show();
        } catch (Exception e){
        }
    }
}
