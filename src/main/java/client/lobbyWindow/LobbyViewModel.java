package client.lobbyWindow;

import java.io.IOException;
import client.Client;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import protocol.SendChat;
import protocol.SetStatus;


/**
 * @author Nargess Ahmadi, Felicia Saruba, Minghao Li
 */


public class LobbyViewModel {

    public static String window = "Lobby";
    private static LobbyModel model;
    private static LobbyViewModel instance;

    private Gson gson = new Gson();

    @FXML
    private CheckBox readyButton;
    @FXML
    private Label LobbyText;

    /**
     * chat properties
     */
    @FXML
    public VBox container;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField input;
    @FXML
    private Button sendBtn;
    @FXML
    private Button selectMap;


    public void initialize() {
        list.itemsProperty().set(model.getListContentProperty());
        input.textProperty().bindBidirectional(model.getTextFieldContent());
        selectMap.setVisible(false);
    }

    public void setNodeElements(VBox container, ListView<String> list, TextField input, Button sendBtn) {
        this.container = container;
        this.list = list;
        this.input = input;
        this.sendBtn = sendBtn;
    }


    public LobbyViewModel() {
        model = LobbyModel.getInstance();
    }

    public static LobbyViewModel getInstance() {
        return instance;
    }

    public LobbyModel getModel() {
        return model;
    }

    public String window() {
        return window();
    }

    public static void show(String message) {
        model.addNewListItem(message);
    }

    public void openGameWindow(){
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/views/Game.fxml"));
            Parent rootGame = (Parent) fxmlLoaderGame.load();
            Stage stageGame = new Stage();
            stageGame.setTitle("Dizzy Highway");
            stageGame.setScene(new Scene(rootGame));
            stageGame.show();
        } catch (Exception e){
        }
    }

    @FXML
    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        String message = model.getTextFieldContent().get();
        System.out.println(message);
        checkInput(message);

        model.getTextFieldContent().set("");
        input.requestFocus();
    }


    /**
     * send messages using keyboard "Enter" key
     */
    @FXML
    public void keyboardAction(KeyEvent keyEvent) throws IOException{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    /**
     * checks if it is a direct message or a message for all
     */
    public void checkInput(String message){
        String chatToSend = "";

        if(message.startsWith("@")) {
            chatToSend = createDirectMessage(message);

        }else {
            chatToSend = createMessage(message);
        }
        if(!chatToSend.isEmpty()){
            try {
                Client.getClientReceive().getWriteOutput().write(chatToSend);
                Client.getClientReceive().getWriteOutput().newLine();
                Client.getClientReceive().getWriteOutput().flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * message for all
     */
    private String createMessage(String message){
        String sendChat = new SendChat(message,-1).toString();
        return sendChat;
    }

    /**
     * direct message
     */
    private String createDirectMessage(String message){
        message = message.replace("@", "");
        String [] splittingTarget = message.split(" ");
        StringBuilder realMessage = new StringBuilder("");
        for(int i = 1; i < splittingTarget.length; i++){
            realMessage.append(splittingTarget[i]).append(" ");
        }
        String target=splittingTarget[0].trim();
        int to=Client.getClientReceive().getIdByName(target);
        String messageToSend = realMessage.toString().trim();
        String sendChat = new SendChat("(private) " + messageToSend,to).toString();

        return sendChat;
    }

    /**
     * click on ready Button -> first player to click will select map
     */
    public void readyButtonAction(ActionEvent actionEvent) {
        String readyMessage=new SetStatus(true).toString();
        Client.getClientReceive().sendMessage(readyMessage);
        if(Client.getClientReceive().getReadyList().size()<1) {
            selectMap.setVisible(true);
        }
        else{
            selectMap.setText("open game");
            selectMap.setVisible(true);
        }
        if(readyButton.isSelected()){
            System.out.println("ok");
        }
        else{
            LobbyText.setText("hui");
        }
    }

    /**
     * selecting a map by opening map selection window, when at least 2 players are online
     */
    public void selectMapAction(ActionEvent actionEvent) {
        if (Client.getClientReceive().getReadyList().size() == 1) {
            LobbyText.setVisible(true);
        } else {
            LobbyText.setVisible(false);
            if (Client.getClientReceive().getMaps() != null) {
                try {
                    FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/views/Map.fxml"));
                    Parent rootGame = (Parent) fxmlLoaderGame.load();
                    Stage stageGame = new Stage();
                    stageGame.setTitle("Map Selection");
                    stageGame.setScene(new Scene(rootGame));
                    stageGame.show();
                } catch (Exception e) {

                }

            } else {
                openGameWindow();
            }
            //close Lobby
            Stage stage = (Stage) selectMap.getScene().getWindow();
            stage.close();
            setWindowName("Game");
        }
    }

    public static void setWindowName (String name){
        window = name;
    }
    public static String getWindowName(){
        return window;
    }
}
