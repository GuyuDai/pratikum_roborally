package client.loginWindow;

import client.Client;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import transfer.request.PlayerInitialisation;
import transfer.request.RequestType;
import transfer.request.RequestWrapper;

public class LoginController {

    private static LoginController instance;
    @FXML
    public AnchorPane container;
    @FXML
    private TextField nameInput;
    @FXML
    private Text text;
    @FXML
    private Button sendNameButton;

    private LoginModel model;

    public LoginModel getModel() {
        return model;
    }

    public LoginController() {
        model = LoginModel.getInstance();
    }

    public void initialize() {
        nameInput.textProperty().bindBidirectional(model.getTextFieldContent());
        sendNameButton.disableProperty().bind(nameInput.textProperty().isEmpty());
    }

    public void setNodeElements(AnchorPane container, TextField nameInput, Text text, Button sendNameButton) {
        this.container = container;
        this.nameInput = nameInput;
        this.text = text;
        this.sendNameButton = sendNameButton;
    }

    public static LoginController getInstance() {
        return instance;
    }

    @FXML
    /*
     * Function of the "set name" button in the NameSetter Window
     */
    public void sendNameButtonAction(ActionEvent actionEvent) throws Exception {
        String name = model.getTextFieldContent().get();
        String sendMessage = new Gson().toJson(new RequestWrapper(new PlayerInitialisation(name), RequestType.PLAYER_INITIALISATION));
        Client.getClientReceive().getWriteOutput().write(sendMessage);
        Client.getClientReceive().getWriteOutput().newLine();
        Client.getClientReceive().getWriteOutput().flush();
        model.getTextFieldContent().set("");
    }
}
