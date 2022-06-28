package client.gameWindow;

import client.Client;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import server.CardTypes.*;
import transfer.Player;
import transfer.request.Message;
import transfer.request.MessageTypes;
import transfer.request.RequestType;
import transfer.request.RequestWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 * @author Nargess Ahmadi, Felicia Saruba
 */
public class GameViewModel {


    //public GameViewModel(){
    //  new HostHand();
    //}

    private static GameModel model;
    private static GameViewModel instance;
    private Gson gson = new Gson();
    private static Player currentPlayer;


    @FXML
    private Button chatBtn;
    @FXML
    private Button exitBtn;
    @FXML
    public VBox container;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField input;
    @FXML
    private Button sendBtn;
    @FXML
    private Label chatwindow;
    @FXML
    private AnchorPane gameContainer;
    @FXML
    private TilePane dizzyHighway;
    @FXML
    private ImageView hand1;
    @FXML
    private ImageView hand2;
    @FXML
    private ImageView hand3;
    @FXML
    private ImageView hand4;
    @FXML
    private ImageView hand5;
    @FXML
    private ImageView hand6;
    @FXML
    private ImageView hand7;
    @FXML
    private ImageView hand8;
    @FXML
    private ImageView hand9;
    @FXML
    private ImageView register1;
    @FXML
    private ImageView register2;
    @FXML
    private ImageView register3;
    @FXML
    private ImageView register4;
    @FXML
    private ImageView register5;

    @FXML
    private GridPane register;
    @FXML
    private GridPane hand;
    @FXML
    private StackPane maps;
    @FXML
    private Button playCardBtn;

    public String window = "Game";


    public void initialize() {
        list.itemsProperty().set(model.getListContentProperty());
        input.textProperty().bindBidirectional(model.getTextFieldContent());
        hand.getChildren().add(new ImageView());
    }


    public void setNodeElements(VBox container, ListView<String> list, TextField input, Button sendBtn) {
        this.gameContainer = gameContainer;
        this.container = container;
        this.list = list;
        this.input = input;
        this.sendBtn = sendBtn;
        this.hand = hand;

        //this.currentPlayer = currentPlayer;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public GameViewModel() {
        model = GameModel.getInstance();
    }

    public static GameViewModel getInstance() {
        return instance;
    }

    public GameModel getModel() {
        return model;
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
    private void checkInput(String message) {
        String sendableRequest = "";


        if (message.startsWith("@")) {
            sendableRequest = createDirectMessage(message);

        /*
         } else if (message.startsWith("!")){
            sendableRequest = createCommandRequest(message);
        */

        } else {
            sendableRequest = createMessage(message);
        }
        if (!sendableRequest.isEmpty()) {
            try {
                Client.getClientReceive().getWriteOutput().write(sendableRequest);
                Client.getClientReceive().getWriteOutput().newLine();
                Client.getClientReceive().getWriteOutput().flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String createMessage(String message) {

        String createMessageWrapped = gson.toJson(new RequestWrapper(new Message(currentPlayer.getName(), message, MessageTypes.CLIENT_MESSAGE), RequestType.MESSAGE));
        return createMessageWrapped;
    }

    private String createDirectMessage(String message) {
        message = message.replace("@", "");
        String[] splittingTarget = message.split(" ");
        StringBuilder realMessage = new StringBuilder("");
        for (int i = 1; i < splittingTarget.length; i++) {
            realMessage.append(splittingTarget[i]).append(" ");
        }
        String createMessageWrapped = gson.toJson(new RequestWrapper(new Message("Private Message from " + currentPlayer.getName(), splittingTarget[0].trim(), realMessage.toString().trim(), MessageTypes.PRIVATE_MESSAGE), RequestType.MESSAGE));
        return createMessageWrapped;
    }


    //send messages using keyboard "Enter" key
    @FXML
    public void keyboardAction(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    /**
     * opens ChatWindow by clicking the Open Chat Button
     *
     * @param actionEvent
     */
    public void chatBtnAction(ActionEvent actionEvent) {
        if (container.isVisible()) {
            container.setVisible(false);
            chatBtn.setText("Open Chat");
        } else {
            container.setVisible(true);
            chatBtn.setText("Close Chat");
        }
    }

    public void exitGame(ActionEvent actionEvent) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Lobby.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stageLobby = new Stage();
            stageLobby.setTitle("Lobby");
            stageLobby.setScene(new Scene(rootMap));
            stageLobby.show();
        } catch (Exception e) {
        }
        LobbyViewModel.setWindowName("Lobby");
    }

    ArrayList<Card> nineCardsFromServer = new ArrayList<>();
    ArrayList<Card> discardPile;

    public void printCards() {
        nineCardsFromServer.add(new MoveOne());
        nineCardsFromServer.add(new MoveOne());
        nineCardsFromServer.add(new MoveOne());
        nineCardsFromServer.add(new MoveOne());
        nineCardsFromServer.add(new MoveOne());     // 5 x Move One

        nineCardsFromServer.add(new MoveTwo());
        nineCardsFromServer.add(new MoveTwo());
        nineCardsFromServer.add(new MoveTwo());     // 3 x Move Two

        nineCardsFromServer.add(new MoveThree());   // 1 x Move Three

        nineCardsFromServer.add(new Again());
        nineCardsFromServer.add(new Again());       // 2 x Again

        nineCardsFromServer.add(new BackUp());      // 1 x BackUp

        nineCardsFromServer.add(new TurnLeft());
        nineCardsFromServer.add(new TurnLeft());
        nineCardsFromServer.add(new TurnLeft());    // 3 x Turn Left

        nineCardsFromServer.add(new TurnRight());
        nineCardsFromServer.add(new TurnRight());
        nineCardsFromServer.add(new TurnRight());   // 3 x Turn Right

        nineCardsFromServer.add(new UTurn());       // 1 x UTurn

        nineCardsFromServer.add(new PowerUp());     // 1 x PowerUp

        Collections.shuffle(nineCardsFromServer);


        URL url1 = getClass().getResource("/programmingCards/move1.png");
        if (url1 == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image image1 = new Image(url1.toString());
        hand1.setImage(image1);


        URL url2 = getClass().getResource("/programmingCards/move2.png");
        if (url2 == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image image2 = new Image(url2.toString());
        hand2.setImage(image2);


        URL urlMove3 = getClass().getResource("/programmingCards/move3.png");
        if (urlMove3 == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imageMove3 = new Image(urlMove3.toString());
        hand3.setImage(imageMove3);


        URL urlAgain = getClass().getResource("/programmingCards/again.png");
        if (urlAgain == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imageAgain = new Image(urlAgain.toString());
        hand4.setImage(imageAgain);


        URL urlBackUp = getClass().getResource("/programmingCards/backUp.png");
        if (urlBackUp == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imageBackUp = new Image(urlBackUp.toString());
        hand5.setImage(imageBackUp);


        URL urlTurnLeft = getClass().getResource("/programmingCards/turnLeft.png");
        if (urlTurnLeft == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imageBTurnLeft = new Image(urlTurnLeft.toString());
        hand6.setImage(imageBTurnLeft);


        URL urlTurnRight = getClass().getResource("/programmingCards/turnRight.png");
        if (urlTurnRight == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imageBTurnRight = new Image(urlTurnRight.toString());
        hand7.setImage(imageBTurnRight);


        URL urlUTurn = getClass().getResource("/programmingCards/uTurn.png");
        if (urlUTurn == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imageUTurn = new Image(urlUTurn.toString());
        hand8.setImage(imageUTurn);


        URL urlPowerUp = getClass().getResource("/programmingCards/powerUp.png");
        if (urlPowerUp == null) {
            throw new IllegalArgumentException("File not found: /programmingCards/" + nineCardsFromServer.get(0).getCardName());
        }
        Image imagePowerUp = new Image(urlPowerUp.toString());
        hand9.setImage(imagePowerUp);

    }

    public void showCardBtnAction() {
        printCards();
    }


}
