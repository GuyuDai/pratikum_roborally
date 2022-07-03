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
            figure=0;
            robotSelected=true;
        }
        else if (buttonHulk.isSelected()){
            figure=1;
            robotSelected=true;
        }
        else if (buttonSpin.isSelected()){
            figure=2;
            robotSelected=true;
        }
        else if (buttonSquash.isSelected()){
            figure=3;
            robotSelected=true;
        }
        else if (buttonTwonkey.isSelected()){
            figure=5;
            robotSelected=true;
        }
        else if (buttonTwitch.isSelected()){
            figure=6;
            robotSelected=true;
        }
    }

    public void initPlayer(ActionEvent actionEvent) throws IOException {

        //Client.getPlayerOnline().setPlayer(name);
        String name =nameInput.getText();
        //Client.getPlayerOnline().sendToAll("SERVER: " + name + " has entered the chat!");
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
}
