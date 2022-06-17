package client.lobbyWindow;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import client.Client;
import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import server.ServerThread;
import transfer.Player;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import transfer.PlayerOnline;
import transfer.request.*;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import client.Client;
import transfer.request.GameMessage;
import client.mapWindow.MapViewModel;

/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
 */


public class LobbyViewModel {

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
    @FXML
    private Button sendButton;
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

    public void setNodeElements(VBox container, ListView<String> list, TextField input, Button sendButton) {
        this.container = container;
        this.list = list;
        this.input = input;
        this.sendButton = sendButton;
    }

    String[] robots = {"hammer", "hulk", "spin", "Squash bot", "Twonkey", "Twitch"};
    List valid = Arrays.asList(robots);     //convert array to a list


    public void isRobotAvailable () {

    }

    //toggle button message for selection
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



    // when play button is clicked a message about the selected robot appears in the chat and game window starts
    public void playButtonAction(ActionEvent actionEvent) throws IOException{
        String message = "";
        if(buttonHammer.isSelected()){
            message = "I SELECTED HAMMER BOT.";
        }
        else if (buttonHulk.isSelected()){
            message = "I SELECTED HULK X90 BOT.";
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

    }

    @FXML
    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        String message = model.getTextFieldContent().get();

        checkInput(message);

        model.getTextFieldContent().set("");
        input.requestFocus();

    }

    @FXML
    public void showRobotMap(ActionEvent actionEvent) {
        selectRobot.setVisible(true);
        newGameBtn.setVisible(false);

        //only for first player who clicks on "start new game":
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


    public static void show(String message) {
        model.addNewListItem(message);
    }

    //loads window fpr map selection
    public void openMapWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Map.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map Selection");
            stage.setScene(new Scene(rootMap));
            stage.show();
        } catch (Exception e){
            System.out.println("not working");
        }
    }

    //takes message from textfield
    private void checkInput(String message){

        String sendableRequest = "";
/*
         if(message.startsWith("@")){
         sendableRequest = createDirectMessage(message);
         } else if (message.startsWith("!")){
         sendableRequest = createCommandRequest(message);
         } else {
*/
        sendableRequest = createMessage(message);


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


    //send messages using keyboard "Enter" key
    @FXML
    public void keyboardAction(KeyEvent keyEvent) throws IOException{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    @FXML
    public void chooseMap(ActionEvent actionEvent) throws IOException {

    }
/*

     String message;

    //public gameMessage(String message){this.message = message;}


    private void sendToAll(String message) {
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

     private String createCommandRequest(String message){
     message = message.replace("!", "");
     CommandType currentCommandType = identifyCommandType(message);
     if(currentCommandType.equals(CommandType.NO_SUCH_COMMAND_FOUND)){
     LobbyViewModel.show(currentCommandType.getCommandIdentification());
     } else if (currentCommandType.equals(CommandType.SELECT_CARD)) {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer, splitMessage(message)), RequestType.COMMAND_REQUEST));
     } else if (currentCommandType.equals(CommandType.SELECT_PLAYER)) {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer, splitMessage(message)), RequestType.COMMAND_REQUEST));
     } else if (currentCommandType.equals(CommandType.START_GAME)) {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer), RequestType.COMMAND_REQUEST));
     } else if (currentCommandType.equals(CommandType.JOIN_GAME)) {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer), RequestType.COMMAND_REQUEST));
     } else if (currentCommandType.equals(CommandType.LEAVE_GAME)) {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer), RequestType.COMMAND_REQUEST));
     } else if (currentCommandType.equals(CommandType.CREATE_GAME)) {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer), RequestType.COMMAND_REQUEST));
     }else {
     return gson.toJson(new RequestWrapper(new Command(currentCommandType, currentPlayer), RequestType.COMMAND_REQUEST));
     }
     return "";
     }

     private String splitMessage(String message) {
     String[] splited = message.split("\\s");
     if(splited.length!=1) {
     return splited[1];
     }else {
     return "";
     }
     }
     private CommandType identifyCommandType(String message) {
     if(message.equals(CommandType.CREATE_GAME.getCommandIdentification())){
     return CommandType.CREATE_GAME;
     }else if (message.equals(CommandType.JOIN_GAME.getCommandIdentification())){
     return CommandType.JOIN_GAME;
     } else if (message.equals(CommandType.LEAVE_GAME.getCommandIdentification())){
     return CommandType.LEAVE_GAME;
     }else if (message.equals(CommandType.START_GAME.getCommandIdentification())){
     return CommandType.START_GAME;
     }else if (message.equals(CommandType.LEAVE_SERVER.getCommandIdentification())){
     return CommandType.LEAVE_SERVER;
     }else if (message.equals(CommandType.CURRENT_SCORE.getCommandIdentification())){
     return CommandType.CURRENT_SCORE;
     }else if (message.startsWith("play")){
     return CommandType.SELECT_CARD;
     }else if (message.startsWith("select")){
     return CommandType.SELECT_PLAYER;
     }else {
     return CommandType.NO_SUCH_COMMAND_FOUND;
     }
     }
     */


}