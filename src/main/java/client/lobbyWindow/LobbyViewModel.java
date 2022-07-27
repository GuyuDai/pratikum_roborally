package client.lobbyWindow;

import java.io.IOException;
import java.net.URL;
import client.Client;
import client.ExitWindow;
import client.loginWindow.LoginViewModel;
import com.google.gson.Gson;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import protocol.ConnectionUpdate;
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

    /**
     * other players mat
     */
    @FXML
    ImageView playerBot1, playerBot2, playerBot3, playerBot4, playerBot5, playerBot6;
    @FXML
    Label playerName1, playerName2, playerName3, playerName4, playerName5, playerName6;
    @FXML
    private Label yourBotText;
    @FXML
    private ImageView yourRobot;

    /**
     * other players mat
     */
    @FXML
    private ImageView yourRobotText;
    @FXML
    private ImageView otherRobotText;
    @FXML
    ImageView box1, box2;


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

    URL hammer = getClass().getResource("/Robots/hammer bot.png");
    Image imageHammer = new Image(hammer.toString());

    URL hulk = getClass().getResource("/Robots/hulk x90.png");
    Image imageHulk = new Image(hulk.toString());

    URL spin = getClass().getResource("/Robots/spin bot.png");
    Image imageSpin = new Image(spin.toString());

    URL squash = getClass().getResource("/Robots/squash bot.png");
    Image imageSquash = new Image(squash.toString());

    URL twitch = getClass().getResource("/Robots/twitch.png");
    Image imageTwitch = new Image(twitch.toString());

    URL twonkey = getClass().getResource("/Robots/twonkey.png");
    Image imageTwonkey = new Image(twonkey.toString());


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

    /**
     * open game window
     */
    public void openGameWindow() {
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/views/Game.fxml"));
            Parent rootGame = (Parent) fxmlLoaderGame.load();
            Stage stageGame = new Stage();
            stageGame.setTitle("Dizzy Highway");
            stageGame.setOnCloseRequest(e -> {
                e.consume();
                closeGUI();
            });
            stageGame.setScene(new Scene(rootGame));
            stageGame.show();
        } catch (Exception e) {
        }
    }

    /**
     * message gets send in the chatroom
     */
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
    public void keyboardAction(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    /**
     * checks if it is a direct message or a message for all
     */
    public void checkInput(String message) {
        String chatToSend = "";

        if (message.startsWith("@")) {
            chatToSend = createDirectMessage(message);

        } else {
            chatToSend = createMessage(message);
        }
        if (!chatToSend.isEmpty()) {
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
    private String createMessage(String message) {
        String sendChat = new SendChat(message, -1).toString();
        return sendChat;
    }

    /**
     * direct message
     */
    private String createDirectMessage(String message) {
        message = message.replace("@", "");
        String[] splittingTarget = message.split(" ");
        StringBuilder realMessage = new StringBuilder("");
        for (int i = 1; i < splittingTarget.length; i++) {
            realMessage.append(splittingTarget[i]).append(" ");
        }
        String target = splittingTarget[0].trim();
        int to = Client.getClientReceive().getIdByName(target);
        String messageToSend = realMessage.toString().trim();
        String sendChat = new SendChat("(private) " + messageToSend, to).toString();

        return sendChat;
    }



    /**
     * click on ready Button -> first player to click will select map the others will get to open the game window
     */
    public void readyButtonAction(ActionEvent actionEvent) {
        if (readyButton.isSelected()){
            checkInput("I AM READY!");
            String readyMessage = new SetStatus(true).toString();
            Client.getClientReceive().sendMessage(readyMessage);
            if (Client.getClientReceive().getReadyList().size() < 1) {
                selectMap.setText("SELECT MAP");
                selectMap.setVisible(true);
            } else {
                selectMap.setText("OPEN GAME");
                selectMap.setVisible(true);
            }
        }
        else{
            checkInput("I AM NOT READY YET!");
            String notReadyMessage = new SetStatus(false).toString();
            Client.getClientReceive().sendMessage(notReadyMessage);
            selectMap.setVisible(false);
            Client.getClientReceive().setMaps(null);
        }
    }

        /**
         * selecting a map by opening map selection window, when at least 2 players are ready and game has not started yet
         */
        public void selectMapAction (ActionEvent actionEvent){
            if (Client.getClientReceive().getReadyList().size() == 1) {
                LobbyText.setVisible(true);
                PauseTransition pauseTransition26 = new PauseTransition(Duration.seconds(3));
                pauseTransition26.setOnFinished(e -> LobbyText.setVisible(false));
                pauseTransition26.play();
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
                    //close Lobby
                    Stage stage = (Stage) selectMap.getScene().getWindow();
                    stage.close();
                    setWindowName("Game");

                } else {
                    if (Client.getClientReceive().isGameStarted()) {
                        openGameWindow();
                    }
                }
                if (Client.getClientReceive().isGameStarted()) {
                    Stage stage = (Stage) selectMap.getScene().getWindow();
                    stage.close();
                    setWindowName("Game");
                }
                else{

                    LobbyText.setText("Wait Map Selection!");
                    LobbyText.setVisible(true);
                    //selectMap.setVisible(false);
                }
            }
        }


    /**
     * Set your own player´s icon and name on board
     */
    public void setYourBotIcon () {
        yourRobotText.setVisible(true);
        yourBotText.setVisible(true);
        int yourId = Client.getClientReceive().getClientID();
        String yourName = Client.getClientReceive().getNameById(yourId);
        int yourRobotNumber = Client.getClientReceive().getRobotById(yourId);
        Image robotIcon = null;
        switch (yourRobotNumber) {
            case 1:
                robotIcon = imageHulk;//hulk
                break;
            case 2:
                robotIcon = imageSpin;//spin
                break;
            case 3:
                robotIcon = imageSquash;//squash
                break;
            case 4:
                robotIcon = imageHammer;//hammer
                break;
            case 5:
                robotIcon = imageTwonkey;//twonkey
                break;
            case 6:
                robotIcon = imageTwitch;//twitch
                break;
        }
        yourRobot.setImage(robotIcon);
        yourBotText.setText(yourName);
        box1.setVisible(true);
    }


    /**
     * Set other player´s icon and name on board
     */
    public void setOthersBotIcon () {
        if (playerBot1.getImage() != (null)){
            playerBot1.setImage(null);
            playerName1.setText("");
        }
        if (playerBot2.getImage() != (null)){
            playerBot2.setImage(null);
            playerName2.setText("");
        }
        if (playerBot3.getImage() != (null)){
            playerBot3.setImage(null);
            playerName3.setText("");
        }
        if (playerBot4.getImage() != (null)){
            playerBot4.setImage(null);
            playerName4.setText("");
        }
        if (playerBot5.getImage() != (null)){
            playerBot5.setImage(null);
            playerName5.setText("");
        }
        if (playerBot6.getImage() != (null)){
            playerBot6.setImage(null);
            playerName6.setText("");
        }
        for (int id : Client.getClientReceive().getIdRobot().keySet()) {
            if (id != Client.getClientReceive().getClientID()) {
                int otherRobotNumber = Client.getClientReceive().getIdRobot().get(id);
                String playerName = Client.getClientReceive().getNameById(id);
                Image otherRobotIcon = null;
                switch (otherRobotNumber) {
                    case 1:
                        otherRobotIcon = imageHulk;
                        break;
                    case 2:
                        otherRobotIcon = imageSpin;
                        break;
                    case 3:
                        otherRobotIcon = imageSquash;
                        break;
                    case 4:
                        otherRobotIcon = imageHammer;
                        break;
                    case 5:
                        otherRobotIcon = imageTwonkey;
                        break;
                    case 6:
                        otherRobotIcon = imageTwitch;
                        break;
                }

                switch (otherRobotNumber) {
                    case 1:
                        playerBot1.setImage(otherRobotIcon);
                        playerName1.setText(playerName);
                        break;
                    case 2:
                        playerBot2.setImage(otherRobotIcon);
                        playerName2.setText(playerName);
                        break;
                    case 3:
                        playerBot3.setImage(otherRobotIcon);
                        playerName3.setText(playerName);
                        break;
                    case 4:
                        playerBot4.setImage(otherRobotIcon);
                        playerName4.setText(playerName);
                        break;
                    case 5:
                        playerBot5.setImage(otherRobotIcon);
                        playerName5.setText(playerName);
                        break;
                    case 6:
                        playerBot6.setImage(otherRobotIcon);
                        playerName6.setText(playerName);
                        break;
                }
                otherRobotText.setVisible(true);
                box2.setVisible(true);
            }
        }
    }

    /**
     * if the first player deselects being ready, the player who was ready as 2nd fastest will be able to choose the map
     */
    public void checkForReady() {
        if(Client.getClientReceive().getReadyList().size() == 1 && readyButton.isSelected()){
            selectMap.setText("SELECT MAP");
        } else {
        }

    }


    /**
     * alert window before exiting the Lobby
     */
    public static void closeGUI() {
        Boolean answer = ExitWindow.display("Exit Game", "Are you sure, you want to quit the game?");
        if (answer) {
            System.exit(0);
            int yourId = Client.getClientReceive().getClientID();
            String exitMessage = new ConnectionUpdate(yourId, false, "").toString();
            Client.getClientReceive().sendMessage(exitMessage);
            //System.out.println(yourId + " has left."); //test
            System.out.println(Client.getClientReceive().getIdList());
            Client.getLogger().info(Client.getClientReceive().getNameById(yourId) + "has left the Game");
            String notReadyMessage = new SetStatus(false).toString();
            Client.getClientReceive().sendMessage(notReadyMessage);
        }
    }

    /**
     * Event to update the other player's board if new player joined
     */
    public void MouseAction(MouseEvent mouseEvent) {
        try {
            setYourBotIcon();
        }
        catch (Exception e){
        }
        try {
            setOthersBotIcon();
        }
        catch (Exception e){
        }
        try {
            checkForReady();
        }
        catch (Exception e){
        }

        if(Client.getClientReceive().getMaps()!=null){
            selectMap.setText("SELECT MAP");
        }
    }

    /**
     * getter setter for window name -> for chat to work in both windows
     */
    public static void setWindowName (String name){
        window = name;
    }

    public static String getWindowName () { return window; }
}