package client.lobbyWindow;

import java.io.IOException;

import client.Client;
import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import transfer.Player;
import transfer.request.*;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
 */


public class LobbyViewModel {

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
    private ToggleButton buttonTruhole;

    @FXML
    private ToggleButton buttonTwonky;

    @FXML
    private ToggleGroup buttons;

    @FXML
    private Pane selectRobot;

    @FXML
    private Text buttonText;
    @FXML
    private Button sendBtn;
    @FXML
    private Button playButton;


    //toggle button message for selection
    @FXML
    void RobotButton(ActionEvent event) {
        if (event.getSource() == buttonHammer) {
            buttonText.setText("You selected Hammer bot.");
        } else if (event.getSource() == buttonHulk) {
            buttonText.setText("You selected Hulk x90.");
        } else if (event.getSource() == buttonSpin) {
            buttonText.setText("You selected Spin bot.");
        } else if (event.getSource() == buttonSquash) {
            buttonText.setText("You selected Squash bot.");
        } else if (event.getSource() == buttonTwonky) {
            buttonText.setText("You selected Twonky.");
        } else if (event.getSource() == buttonTruhole) {
            buttonText.setText("You selected Truhole bot.");
        }
    }

    private static LobbyModel model;

    private static LobbyViewModel instance;

    private Gson gson = new Gson();

    private static Player currentPlayer;

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

    public static void show(String message) {
        model.addNewListItem(message);
    }

    //Function of the "send message" button in the game window.

    @FXML
    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        String message = model.getTextFieldContent().get();

        checkInput(message);

        model.getTextFieldContent().set("");
        input.requestFocus();

    }

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


    /*
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

    private String createMessage(String message){
        String createMessageWrapped = gson.toJson(new RequestWrapper(new Message(currentPlayer.getName(), message, MessageTypes.CLIENT_MESSAGE), RequestType.MESSAGE));
        return createMessageWrapped;
    }

    /**
     * Function to send messages using keyboard "Enter" key.
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