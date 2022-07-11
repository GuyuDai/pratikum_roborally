package client.loginWindow;


import client.*;
import client.AI.AI;
import com.google.gson.*;
import javafx.beans.binding.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import protocol.*;
import protocol.ProtocolFormat.*;

import java.io.*;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        nameInput.textProperty().bindBidirectional(model.getTextFieldContent());
        sendNameButton.disableProperty().bind(nameInput.textProperty().isEmpty().or (Bindings.isNull(ToggleGroupRobot.selectedToggleProperty())));
       // AIButton.disableProperty().bind();
        checkRobot();
    }

    /**
     * function when robot is selected
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
     * disable button when it is selected by a player
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
    public Button AIButton;

    //init AI with Robot and Name
    public void initAI(ActionEvent actionEvent) throws IOException {
        new AI().init();
        //AI newAIClient=new AI();
        nameInput.setText("AI");
        String name = nameInput.getText();
        for (int j = 1; j <= 6; j++) {
            freeRobotNumber.add(j);
        }
        int i = 0;
        while (i < freeRobotNumber.size()) {
            for (int number : Client.getClientReceive().getRobotNumbers()) {
                if (freeRobotNumber.get(i) == number) {
                    freeRobotNumber.remove(i);
                    i=0;
                }
                else{
                    i++;
            }
        }
    }
        figure=freeRobotNumber.get(0);
        AI.getAiReceive().sendMessage(new PlayerValues(name,figure).toString());
        AI.getAiReceive().sendMessage(new SetStatus(true).toString());
    }
    public void openGameWindow(){
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/Views/Game.fxml"));
            Parent rootGame = (Parent) fxmlLoaderGame.load();
            Stage stageGame = new Stage();
            stageGame.setTitle("Dizzy Highway");
            stageGame.setScene(new Scene(rootGame));
            stageGame.show();
        } catch (Exception e){
        }
    }
}
