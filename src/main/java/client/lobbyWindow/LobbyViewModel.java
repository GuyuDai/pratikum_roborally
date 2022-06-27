package client.lobbyWindow;

import java.io.BufferedReader;
import java.io.IOException;

import client.Client;
import client.ClientReceive;
import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import transfer.Player;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import transfer.request.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import transfer.request.Message;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
 */


public class LobbyViewModel {



    public static String window = "Lobby";
    public static void setWindowName (String name){
        window = name;
    }
    public static String getWindowName(){
        return window;
    }


    private static LobbyModel model;
    private static LobbyViewModel instance;
    private Gson gson = new Gson();
    private static Player currentPlayer;

    boolean isAvailable;

    @FXML
    public VBox container;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField input;
    //@FXML
    //private Button sendButton;
    @FXML
    private ToggleGroup ToggleGroupRobot;
    @FXML
    private ToggleButton buttonHammer;
    @FXML
    private ToggleButton buttonHulk;
    @FXML
    private ToggleButton buttonSpin;
    @FXML
    private ToggleButton buttonSquash;
    @FXML
    private ToggleButton buttonTwitch;
    @FXML
    private ToggleButton buttonTwonkey;
    @FXML
    private Pane selectRobot;
    @FXML
    private Text buttonText;
    @FXML
    private Button sendBtn;
    @FXML
    private Button playButton;
    @FXML
    private Button newGameBtn;


    public void initialize() {
        list.itemsProperty().set(model.getListContentProperty());
        input.textProperty().bindBidirectional(model.getTextFieldContent());
        playButton.disableProperty().bind(Bindings.isNull(ToggleGroupRobot.selectedToggleProperty()));
    }

    public void setNodeElements(VBox container, ListView<String> list, TextField input, Button sendBtn) {
        this.container = container;
        this.list = list;
        this.input = input;
        this.sendBtn = sendBtn;
    }

    String[] robots = {"hammer", "hulk", "spin", "Squash bot", "Twonkey", "Twitch"};
    List valid = Arrays.asList(robots);     //convert array to a list

    public void isRobotAvailable () {

    }

    //ToDo: Robot not available after player selected
    //ToDo: Connect Robot to Player
    //ToDo: Upgrade Shop
    //ToDo: create class for map elements
    //ToDo: Only first player should select Map


    /**
     * toggle button message for robot selection
     */
    @FXML
    void RobotButton(ActionEvent event) {
        if(buttonHammer.isSelected()) {
            if (valid.contains("hammer")) {
                robots[0] = "leer";
                buttonText.setText("You selected Hammer bot");
            } else {
                buttonText.setText("This robot has already been selected by another player.");
            }
        }
        else if (buttonHulk.isSelected()){
            buttonText.setText("You selected " + robots[1]);
        }
        else if (buttonSpin.isSelected()){
            buttonText.setText("You selected " + robots[2]);
        }
        else if (buttonSquash.isSelected()){
            buttonText.setText("You selected " + robots[3]);
        }
        else if (buttonTwonkey.isSelected()){
            buttonText.setText("You selected " + robots[4]);
        }
        else if (buttonTwitch.isSelected()){
            buttonText.setText("You selected " + robots[5]);
        }
        else {
            buttonText.setText("");
        }
    }


    /**
     * when play button is clicked, a message about the selected robot appears in the chat and game window starts
     */
    public void playButtonAction(ActionEvent actionEvent) throws IOException{
        String message = "";
        if(buttonHammer.isSelected()){
            message = "I SELECTED HAMMER BOT.";
            buttonHammer.setVisible(false);
        }
        else if (buttonHulk.isSelected()){
            message = "I SELECTED HULK X90 BOT.";
            buttonHulk.setVisible(false);
        }
        else if (buttonSpin.isSelected()){
            message = "I SELECTED SPIN BOT.";
        }
        else if (buttonSquash.isSelected()){
            message = "I SELECTED SQUASH BOT.";
        }
        else if (buttonTwonkey.isSelected()){
            message = "I SELECTED TWONKEY.";
        }
        else if (buttonTwitch.isSelected()){
            message = "I SELECTED TWITCH.";
        }
        checkInput(message);
        openGameWindow();

        //close Lobby
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();
        setWindowName("Game");
    }

    public void openGameWindow(){
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/Game.fxml"));
            Parent rootGame = (Parent) fxmlLoaderGame.load();
            Stage stageGame = new Stage();
            stageGame.setTitle("Dizzy Highway");
            stageGame.setScene(new Scene(rootGame));
            stageGame.show();
        } catch (Exception e){
        }
    }


    public void openMapWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Map.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map Selection");
            stage.setScene(new Scene(rootMap));
            stage.show();
        } catch (Exception e){
        }
    }


    @FXML
    public void showRobotMap(ActionEvent actionEvent) {
        selectRobot.setVisible(true);
        newGameBtn.setVisible(false);

        //TODo: only for first player who clicks on "start new game":
        openMapWindow();

    }

    public static void setCurrentPlayer(Player player){
        currentPlayer = player;
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


    @FXML
    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        String message = model.getTextFieldContent().get();

        checkInput(message);

        model.getTextFieldContent().set("");
        input.requestFocus();
    }

    /**
     * checks if it is a direct message or a message for all
     */
    public void checkInput(String message){
        String sendableRequest = "";

         if(message.startsWith("@")) {
             sendableRequest = createDirectMessage(message);

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

    /**
     * send messages using keyboard "Enter" key
     */
    @FXML
    public void keyboardAction(KeyEvent keyEvent) throws IOException{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    @FXML
    public void chooseMap(ActionEvent actionEvent) throws IOException {

    }



}