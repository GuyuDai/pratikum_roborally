package client.gameWindow;

import client.Client;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import server.CardTypes.*;
import server.Deck.ProgrammingDeck;
import transfer.Player;
import transfer.PlayerOnline;
import transfer.request.Message;
import transfer.request.MessageTypes;
import transfer.request.RequestType;
import transfer.request.RequestWrapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private static GameModel model;
    private Client client;
    private static GameViewModel instance;
    private Gson gson = new Gson();
    private static Player currentPlayer;

    public String[] cards = {"", "", "", "", "", "", "", "", ""};


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
    private Button playCardBtn;

    public String window = "Game";

    public ProgrammingDeck deck = new ProgrammingDeck();


    URL move1 = getClass().getResource("/programmingCards/move1.png");
    Image imageMove1 = new Image(move1.toString());

    URL urlMove2 = getClass().getResource("/programmingCards/move2.png");
    Image imageMove2 = new Image(urlMove2.toString());


    URL urlMove3 = getClass().getResource("/programmingCards/move3.png");
    Image imageMove3 = new Image(urlMove3.toString());


    URL urlAgain = getClass().getResource("/programmingCards/again.png");
    Image imageAgain = new Image(urlAgain.toString());


    URL urlBackUp = getClass().getResource("/programmingCards/backUp.png");
    Image imageBackUp = new Image(urlBackUp.toString());


    URL urlTurnLeft = getClass().getResource("/programmingCards/turnLeft.png");
    Image imageBTurnLeft = new Image(urlTurnLeft.toString());


    URL urlTurnRight = getClass().getResource("/programmingCards/turnRight.png");
    Image imageBTurnRight = new Image(urlTurnRight.toString());


    URL urlUTurn = getClass().getResource("/programmingCards/uTurn.png");
    Image imageUTurn = new Image(urlUTurn.toString());


    URL urlPowerUp = getClass().getResource("/programmingCards/powerUp.png");
    Image imagePowerUp = new Image(urlPowerUp.toString());

    URL urlCardHidden = getClass().getResource("/programmingCards/cardHidden.png");
    Image imageCardHidden = new Image(urlCardHidden.toString());





    public void initialize(Client client) {
        this.client = client;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Lobby.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stageLobby = new Stage();
            stageLobby.setTitle("Lobby");
            stageLobby.setScene(new Scene(rootMap));
            stageLobby.show();
        } catch (Exception e) {
            System.out.println("not working");
        }
        LobbyViewModel.setWindowName("Lobby");
    }

    CopyOnWriteArrayList<Card> nineCardsFromServer = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Card> programmingDecK = deck.getRemainingCards();
    CopyOnWriteArrayList<Card> registerPile =new CopyOnWriteArrayList<>();


    public void printCards() {

        for (int i = 0; i < 9; i++) {
            nineCardsFromServer.add(programmingDecK.get(i));
        }
        for (int i = 0; i < 9; i++) {
            String card = nineCardsFromServer.get(i).getCardName();
            Image cardImage = null;
            cards[i] = card;
            switch (card) {
                case "MoveOne":
                    cardImage = imageMove1;
                    break;
                case "MoveTwo":
                    cardImage = imageMove2;
                    break;
                case "MoveThree":
                    cardImage = imageMove3;
                    break;
                case "Again":
                    cardImage = imageAgain;
                    break;
                case "BackUp":
                    cardImage = imageBackUp;
                    break;
                case "PowerUp":
                    cardImage = imagePowerUp;
                    break;
                case "TurnLeft":
                    cardImage = imageBTurnLeft;
                    break;
                case "TurnRight":
                    cardImage = imageBTurnRight;
                    break;
                case "UTurn":
                    cardImage = imageUTurn;
                    break;
                default:
                    cardImage = imageCardHidden;

            }
            switch (i) {
                case 0:
                    hand1.setImage(cardImage);
                    break;
                case 1:
                    hand2.setImage(cardImage);
                    break;
                case 2:
                    hand3.setImage(cardImage);
                    break;
                case 3:
                    hand4.setImage(cardImage);
                    break;
                case 4:
                    hand5.setImage(cardImage);
                    break;
                case 5:
                    hand6.setImage(cardImage);
                    break;
                case 6:
                    hand7.setImage(cardImage);
                    break;
                case 7:
                    hand8.setImage(cardImage);
                    break;
                case 8:
                    hand9.setImage(cardImage);
                    break;
            }


            for(Node dragCard: hand.getChildren()) {
                dragCard.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Dragboard db = dragCard.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(((ImageView)dragCard).getImage());
                        db.setContent(content);
                        mouseEvent.consume();
                    }
                });

                dragCard.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if (dragEvent.getGestureSource()!= dragCard && dragEvent.getDragboard().hasImage()) {
                            dragEvent.acceptTransferModes(TransferMode.MOVE);
                        }
                        dragEvent.consume();
                    }
                });

                dragCard.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                       if(((ImageView)dragCard).getImage() == null && register1.getImage() != imageAgain){

                       }
                    }
                });

            }
        }
    }







    public void showCardBtnAction() {
        printCards();
    }




}

