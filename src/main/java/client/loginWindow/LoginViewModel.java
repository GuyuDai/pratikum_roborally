package client.loginWindow;


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


public class LoginViewModel {

    public static String window = "Login";

    private static LoginViewModel instance;
    @FXML
    public AnchorPane container;
    @FXML
    private TextField nameInput;
    @FXML
    private Button sendNameButton;


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

    }

    /**
     * function when robot is selected
     */
    public void selectBot(ActionEvent actionEvent){
        //String message = "";
        if(buttonHammer.isSelected()){
        }
        else if (buttonHulk.isSelected()){
        }
        else if (buttonSpin.isSelected()){
        }
        else if (buttonSquash.isSelected()){
        }
        else if (buttonTwonkey.isSelected()){
        }
        else if (buttonTwitch.isSelected()){
        }
    }

    public void initPlayer(ActionEvent actionEvent) {
        String name = model.getTextFieldContent().get();
        //Client.getPlayerOnline().setPlayer(name);
        nameInput.getText();

        openLobbyWindow();

        //Client.getPlayerOnline().sendToAll("SERVER: " + name + " has entered the chat!");

        Stage stage = (Stage) sendNameButton.getScene().getWindow();
        stage.close();
        setWindowName("Lobby");
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
}
