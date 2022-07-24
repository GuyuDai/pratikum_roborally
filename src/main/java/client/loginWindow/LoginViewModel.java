package client.loginWindow;


import client.*;
import com.google.gson.*;
import javafx.beans.binding.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.*;
import protocol.*;
import protocol.ProtocolFormat.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Felicia Saruba, Nargess Ahmadi, Minghao Li
 */

public class LoginViewModel {

    public static String window = "Login";
    public int figure;
    public boolean robotSelected=false;
    private static LoginViewModel instance;
    @FXML
    public AnchorPane container;
    @FXML
    private TextField nameInput;
    @FXML
    private Button sendNameButton;

    List<Integer> takenRobotNumbers=new ArrayList<>();

    List<Integer> freeRobotNumber=new ArrayList<>();


    /**
     * Toggle Buttons Robots
     */
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


    private LoginModel model;

    public LoginModel getModel() {
        return model;
    }

    public LoginViewModel() {
        model = LoginModel.getInstance();
    }

    /**
     * can only press OK button when both a robot is selected and a name inserted
     */
    public void initialize() {
        playWelcome();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        nameInput.textProperty().bindBidirectional(model.getTextFieldContent());
        sendNameButton.disableProperty().bind(nameInput.textProperty().isEmpty().or (Bindings.isNull(ToggleGroupRobot.selectedToggleProperty())));
       // AIButton.disableProperty().bind();
        checkRobot();
    }

    /**
     * function when robot button is selected
     */
    public void selectBot(ActionEvent actionEvent){
        if(buttonHammer.isSelected()){
            figure=4;
            robotSelected=true;
            checkRobot();
        }
        else if (buttonHulk.isSelected()){
            figure=1;
            robotSelected=true;
            checkRobot();
        }
        else if (buttonSpin.isSelected()){
            figure=2;
            robotSelected=true;
            checkRobot();
        }
        else if (buttonSquash.isSelected()){
            figure=3;
            robotSelected=true;
            checkRobot();
        }
        else if (buttonTwonkey.isSelected()){
            figure=5;
            robotSelected=true;
            checkRobot();
        }
        else if (buttonTwitch.isSelected()){
            figure=6;
            robotSelected=true;
            checkRobot();
        }
    }


    /**
     * check which robots are already taken and disable button accordingly
     */
      public void checkRobot() {
          takenRobotNumbers = Client.getClientReceive().getRobotNumbers();
          for (int number : takenRobotNumbers) {
              switch(number) {
                  case 1: // Hulk
                      buttonHulk.setDisable(true);
                      break;
                  case 2: //Spin
                      buttonSpin.setDisable(true);
                      break;
                  case 3: //Squash
                      buttonSquash.setDisable(true);
                      break;
                  case 4: //Hammer
                      buttonHammer.setDisable(true);
                      break;
                  case 5: //Twonkey
                      buttonTwonkey.setDisable(true);
                      break;
                  case 6: //Twitch
                      buttonTwitch.setDisable(true);
                      break;
              }
          }
      }

    /**
     * initialize player with chosen name and robot -> Lobby window opens
     */
    public void initPlayer(ActionEvent actionEvent) throws IOException {
        String name =nameInput.getText();
        Message message = new PlayerValues(name, figure);
        String clientMessage = message.toString();
        Client.getClientReceive().getWriteOutput().write(clientMessage);
        Client.getClientReceive().getWriteOutput().newLine();
        Client.getClientReceive().getWriteOutput().flush();
        Stage stage = (Stage) sendNameButton.getScene().getWindow();
        stage.close();
        setWindowName("Lobby");
        openLobbyWindow();
    }


    public void openLobbyWindow(){
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/views/Lobby.fxml"));
            Parent rootGame = (Parent) fxmlLoaderGame.load();
            Stage stageGame = new Stage();
            stageGame.setTitle("Lobby");
            stageGame.setScene(new Scene(rootGame));
            stageGame.show();
        } catch (Exception e){
        }
    }


    public static LoginViewModel getInstance() {
        return instance;
    }

    public static void setWindowName (String name){
        window = name;
    }

    public static String getWindowName(){
        return window;
    }

    @FXML
    private MediaView sound;

    @FXML
    Button soundOn;
    @FXML
    Button soundOff;


    public void playHulk() {
        try {
            String fileName = getClass().getResource("/sounds/Hulk x 90.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
        buttonSquash.setStyle("-fx-border-color: #ffffff");
        buttonHulk.setStyle("-fx-border-color: #f11919");
        buttonHammer.setStyle("-fx-border-color: #ffffff");
        buttonSpin.setStyle("-fx-border-color: #ffffff");
        buttonTwitch.setStyle("-fx-border-color: #ffffff");
        buttonTwonkey.setStyle("-fx-border-color: #ffffff");
    }

    public void playSpinbot() {
        try {
            String fileName = getClass().getResource("/sounds/Spin bot.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
        buttonSquash.setStyle("-fx-border-color: #ffffff");
        buttonHulk.setStyle("-fx-border-color: #ffffff");
        buttonHammer.setStyle("-fx-border-color: #ffffff");
        buttonSpin.setStyle("-fx-border-color: #f11919");
        buttonTwitch.setStyle("-fx-border-color: #ffffff");
        buttonTwonkey.setStyle("-fx-border-color: #ffffff");
    }

    public void playHammerbot() {
        try {
            String fileName = getClass().getResource("/sounds/Hammer bot.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
        buttonSquash.setStyle("-fx-border-color: #ffffff");
        buttonHulk.setStyle("-fx-border-color: #ffffff");
        buttonHammer.setStyle("-fx-border-color: #f11919");
        buttonSpin.setStyle("-fx-border-color: #ffffff");
        buttonTwitch.setStyle("-fx-border-color: #ffffff");
        buttonTwonkey.setStyle("-fx-border-color: #ffffff");
    }

    public void playSquashbot() {
        try {
            String fileName = getClass().getResource("/sounds/Squash bot.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
        buttonSquash.setStyle("-fx-border-color: #f11919");
        buttonHulk.setStyle("-fx-border-color: #ffffff");
        buttonHammer.setStyle("-fx-border-color: #ffffff");
        buttonSpin.setStyle("-fx-border-color: #ffffff");
        buttonTwitch.setStyle("-fx-border-color: #ffffff");
        buttonTwonkey.setStyle("-fx-border-color: #ffffff");
    }

    public void playTwonkey() {
        try {
            String fileName = getClass().getResource("/sounds/Twonkey.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
        buttonSquash.setStyle("-fx-border-color: #ffffff");
        buttonHulk.setStyle("-fx-border-color: #ffffff");
        buttonHammer.setStyle("-fx-border-color: #ffffff");
        buttonSpin.setStyle("-fx-border-color: #ffffff");
        buttonTwitch.setStyle("-fx-border-color: #ffffff");
        buttonTwonkey.setStyle("-fx-border-color: #f11919");
    }

    public void playTwitch() {
        try {
            String fileName = getClass().getResource("/sounds/Twitch.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
        buttonSquash.setStyle("-fx-border-color: #ffffff");
        buttonHulk.setStyle("-fx-border-color: #ffffff");
        buttonHammer.setStyle("-fx-border-color: #ffffff");
        buttonSpin.setStyle("-fx-border-color: #ffffff");
        buttonTwitch.setStyle("-fx-border-color: #f11919");
        buttonTwonkey.setStyle("-fx-border-color: #ffffff");
    }

    public void playWelcome() {
        try {
            String fileName = getClass().getResource("/sounds/Welcome.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
    }

}
