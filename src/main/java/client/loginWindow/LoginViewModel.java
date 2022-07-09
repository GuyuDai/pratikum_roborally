package client.loginWindow;


import client.Client;
import com.google.gson.GsonBuilder;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import protocol.PlayerValues;
import protocol.ProtocolFormat.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("test ");
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

    public void initAI(ActionEvent actionEvent) {


    }
}
