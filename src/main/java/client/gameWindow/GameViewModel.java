package client.gameWindow;

import client.Client;
import client.MapBuilder;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import server.CardTypes.*;
import server.Deck.ProgrammingDeck;
import java.io.IOException;
import java.net.URL;
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


    public String[] cards = {"", "", "", "", "", "", "", "", ""};


    /**
     * Buttons
     */
    @FXML
    private Button chatBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button sendBtn;
    @FXML
    private Button playCardBtn;


    /**
     * Buttons for hand
     */
    @FXML
    private Button hand1Button;
    @FXML
    private Button hand2Button;
    @FXML
    private Button hand3Button;
    @FXML
    private Button hand4Button;
    @FXML
    private Button hand5Button;
    @FXML
    private Button hand6Button;
    @FXML
    private Button hand7Button;
    @FXML
    private Button hand8Button;
    @FXML
    private Button hand9Button;



    /**
     * ImageView for 9 cards from server
     */
    @FXML
    private GridPane hand;
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


    /**
     * ImageView for 5 cards picked
     */
    @FXML
    private GridPane register;
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


    /**
     * Chat properties
     */
    @FXML
    public VBox container;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField input;
    @FXML
    private Label chatwindow;


    /**
     * others
     */
    @FXML
    private AnchorPane gameContainer;
    @FXML
    private Label Text;

    public String window = "Game";
    public ProgrammingDeck deck = new ProgrammingDeck();



    /**
     * Map Image Views
     */

    @FXML
    private ImageView mapImage00;

    @FXML
    private ImageView mapImage01;

    @FXML
    private ImageView mapImage02;

    @FXML
    private ImageView mapImage03;

    @FXML
    private ImageView mapImage10;

    @FXML
    private ImageView mapImage100;

    @FXML
    private ImageView mapImage101;

    @FXML
    private ImageView mapImage102;

    @FXML
    private ImageView mapImage103;

    @FXML
    private ImageView mapImage11;

    @FXML
    private ImageView mapImage110;

    @FXML
    private ImageView mapImage111;

    @FXML
    private ImageView mapImage112;

    @FXML
    private ImageView mapImage113;

    @FXML
    private ImageView mapImage12;

    @FXML
    private ImageView mapImage120;

    @FXML
    private ImageView mapImage121;

    @FXML
    private ImageView mapImage122;

    @FXML
    private ImageView mapImage123;

    @FXML
    private ImageView mapImage13;

    @FXML
    private ImageView mapImage20;

    @FXML
    private ImageView mapImage21;

    @FXML
    private ImageView mapImage22;

    @FXML
    private ImageView mapImage23;

    @FXML
    private ImageView mapImage30;

    @FXML
    private ImageView mapImage31;

    @FXML
    private ImageView mapImage32;

    @FXML
    private ImageView mapImage33;

    @FXML
    private ImageView mapImage40;

    @FXML
    private ImageView mapImage41;

    @FXML
    private ImageView mapImage42;

    @FXML
    private ImageView mapImage43;

    @FXML
    private ImageView mapImage50;

    @FXML
    private ImageView mapImage51;

    @FXML
    private ImageView mapImage52;

    @FXML
    private ImageView mapImage53;

    @FXML
    private ImageView mapImage60;

    @FXML
    private ImageView mapImage61;

    @FXML
    private ImageView mapImage62;

    @FXML
    private ImageView mapImage63;

    @FXML
    private ImageView mapImage70;

    @FXML
    private ImageView mapImage71;

    @FXML
    private ImageView mapImage72;

    @FXML
    private ImageView mapImage73;

    @FXML
    private ImageView mapImage80;

    @FXML
    private ImageView mapImage81;

    @FXML
    private ImageView mapImage82;

    @FXML
    private ImageView mapImage83;

    @FXML
    private ImageView mapImage90;

    @FXML
    private ImageView mapImage91;

    @FXML
    private ImageView mapImage92;

    @FXML
    private ImageView mapImage93;







    /**
     * URL adress to .png file
     */
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


    /**
     * TEST
     * @param client
     */




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
     * open ChatWindow by clicking the Open Chat Button
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

    /**
     * print 9 random cards from a deck of 20
     */
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
        }
    }


    int registerCount = 0;

    public void setRegisterCount (int count){
        this.registerCount = count;
    }

    public int getRegisterCount(){
        return registerCount;
    }


    /**
     * pick your register of 5 cards from your hands of 9 cards
     */
    @FXML
    void hand1ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()){
            case 1:
                register1.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand2ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()) {
            case 1:
                register1.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand3ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()){
            case 1:
                register1.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand4ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()){
            case 1:
                register1.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand5ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()){
            case 1:
                register1.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                break;
        };
    }

    @FXML
    void hand6ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()){
            case 1:
                register1.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand7ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()){
            case 1:
                register1.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand8ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()) {
            case 1:
                register1.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                break;
        }
    }

    @FXML
    void hand9ButtonAction(ActionEvent event) {
        setRegisterCount( getRegisterCount() + 1);
        switch(getRegisterCount()) {
            case 1:
                register1.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                break;
            case 2:
                register2.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                break;
            case 3:
                register3.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                break;
            case 4:
                register4.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                break;
            case 5:
                register5.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                break;
        }
    }

    public void showCardBtnAction() {
        printCards();
        Text.setText("Select 5 of these cards for your register.");

        ;

    }
}

