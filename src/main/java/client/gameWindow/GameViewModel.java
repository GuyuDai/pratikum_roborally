package client.gameWindow;

import client.Client;
import client.ClientReceive;
import client.MapBuilder;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import client.mapWindow.MapViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import protocol.SelectedCard;
import protocol.SendChat;
import protocol.YourCards;
import server.BoardElement.BoardElem;
import server.BoardTypes.*;
import server.CardTypes.*;
import server.Control.Direction;
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

    public String mapSelected;

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

    @FXML
    private Button selectMap;

    @FXML
    private Button startGameButton;

    @FXML
    private Button selectMapButton;


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

    @FXML
    GridPane gameboard;

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
        checkInput(message);
        model.getTextFieldContent().set("");
        input.requestFocus();
    }

    public void checkInput(String message){
        String chatToSend = "";

        if(message.startsWith("@")) {
            chatToSend = createDirectMessage(message);

        }else {
            chatToSend = createMessage(message);
        }
        if(!chatToSend.isEmpty()){
            try {
                Client.getClientReceive().getWriteOutput().write(chatToSend);
                Client.getClientReceive().getWriteOutput().newLine();
                Client.getClientReceive().getWriteOutput().flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private String createMessage(String message){
        String sendChat=new SendChat(message,-1).toString();
        return sendChat;
    }

    private String createDirectMessage(String message){
        message = message.replace("@", "");
        String [] splittingTarget = message.split(" ");
        StringBuilder realMessage = new StringBuilder("");
        for(int i = 1; i < splittingTarget.length; i++){
            realMessage.append(splittingTarget[i]).append(" ");
        }
        String target=splittingTarget[0].trim();
        int to=Client.getClientReceive().getIdByName(target);
        String messageToSend=realMessage.toString().trim();
        String sendChat=new SendChat(messageToSend,to).toString();

        return sendChat;
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

    /**
     * exit game and return to Lobby
     */
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




    /**
     * print 9 random cards from a deck of 20
     */
    public void printCards() {
        cards=Client.getClientReceive().getCards();
        for (int i = 0; i < 9; i++) {
            String card = cards[i];
            Image cardImage = null;
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


    /**
     * registerCount for 5 picks
     */
    int registerCount = 0;

    public void setRegisterCount(int count) {
        this.registerCount = count;
    }

    public int getRegisterCount() {
        return registerCount;
    }


    /**
     * pick your register of 5 cards from your hands of 9 cards
     */
    @FXML
    void hand1ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],0).toString());
                break;
            case 2:
                register2.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],1).toString());
                break;
            case 3:
                register3.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],2).toString());
                break;
            case 4:
                register4.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],3).toString());
                break;
            case 5:
                register5.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],4).toString());

                break;
        }
    }

    @FXML
    void hand2ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],0).toString());

                break;
            case 2:
                register2.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],1).toString());

                break;
            case 3:
                register3.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],2).toString());

                break;
            case 4:
                register4.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],3).toString());

                break;
            case 5:
                register5.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],4).toString());

                break;
        }
    }

    @FXML
    void hand3ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],0).toString());

                break;
            case 2:
                register2.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],1).toString());

                break;
            case 3:
                register3.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],2).toString());

                break;
            case 4:
                register4.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],3).toString());

                break;
            case 5:
                register5.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],4).toString());

                break;
        }
    }

    @FXML
    void hand4ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],0).toString());
                break;
            case 2:
                register2.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],1).toString());
                break;
            case 3:
                register3.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],2).toString());
                break;
            case 4:
                register4.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],3).toString());
                break;
            case 5:
                register5.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],4).toString());
                break;
        }
    }

    @FXML
    void hand5ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],0).toString());

                break;
            case 2:
                register2.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],1).toString());

                break;
            case 3:
                register3.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],2).toString());

                break;
            case 4:
                register4.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],3).toString());

                break;
            case 5:
                register5.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],4).toString());
                break;
        }
        ;
    }

    @FXML
    void hand6ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],0).toString());
                break;
            case 2:
                register2.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],1).toString());
                break;
            case 3:
                register3.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],2).toString());

                break;
            case 4:
                register4.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],3).toString());

                break;
            case 5:
                register5.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],4).toString());

                break;
        }
    }

    @FXML
    void hand7ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],0).toString());

                break;
            case 2:
                register2.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],1).toString());

                break;
            case 3:
                register3.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],2).toString());

                break;
            case 4:
                register4.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],3).toString());

                break;
            case 5:
                register5.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],4).toString());

                break;
        }
    }

    @FXML
    void hand8ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],0).toString());
                break;
            case 2:
                register2.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],1).toString());

                break;
            case 3:
                register3.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],2).toString());

                break;
            case 4:
                register4.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],3).toString());

                break;
            case 5:
                register5.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],4).toString());

                break;
        }
    }

    @FXML
    void hand9ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                register1.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],0).toString());

                break;
            case 2:
                register2.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],1).toString());

                break;
            case 3:
                register3.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],2).toString());

                break;
            case 4:
                register4.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],3).toString());

                break;
            case 5:
                register5.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],4).toString());

                break;
        }
    }



    /**
     * start game button -> map gets printed and register appears.
     */

    public void startGameButtonAction(ActionEvent actionEvent) {

        String map=Client.getClientReceive().getBoard();
        printMapGUI(map);

        hand1Button.setVisible(true);
        hand2Button.setVisible(true);
        hand3Button.setVisible(true);
        hand4Button.setVisible(true);
        hand5Button.setVisible(true);
        hand6Button.setVisible(true);
        hand7Button.setVisible(true);
        hand8Button.setVisible(true);
        hand9Button.setVisible(true);

        printCards();
        Text.setText("Select 5 of these cards for your register.");
        playCardBtn.setVisible(true);
    }


    public void printMapGUI(String setMapSelection) {
        Board board=null;
        switch (setMapSelection) {
            case "DizzyHighway":
                board = new DizzyHighway();
                break;
            case "ExtraCrispy":
                board = new ExtraCrispy();
                break;
            case "DeathTrap":
                board = new DeathTrap();
                break;
            case "LostBearings":
                board = new LostBearings();
                break;
        }

                /**
                 * checkpoints
                 */
                URL checkPoint1 = getClass().getResource("/Checkpoints/Checkpoint1.png");
                Image imageCheckPoint1 = new Image(checkPoint1.toString());

                URL checkPoint2 = getClass().getResource("/Checkpoints/Checkpoint2.png");
                Image imageCheckPoint2 = new Image(checkPoint2.toString());

                URL checkPoint3 = getClass().getResource("/Checkpoints/Checkpoint3.png");
                Image imageCheckPoint3 = new Image(checkPoint3.toString());

                URL checkPoint4 = getClass().getResource("/Checkpoints/Checkpoint4.png");
                Image imageCheckPoint4 = new Image(checkPoint4.toString());

                URL checkPoint5 = getClass().getResource("/Checkpoints/Checkpoint5.png");
                Image imageCheckPoint5 = new Image(checkPoint5.toString());

                URL checkPoint6 = getClass().getResource("/Checkpoints/Checkpoint6.png");
                Image imageCheckPoint6 = new Image(checkPoint6.toString());

                /**
                 * Conveyor belts
                 */
                URL blueBeltBottom = getClass().getResource("/ConveyorBelts/BlueBeltBottom.png");
                Image imageBlueBeltBottom = new Image(blueBeltBottom.toString());

                URL blueBeltLeft = getClass().getResource("/ConveyorBelts/BlueBeltLeft.png");
                Image imageBlueBeltLeft = new Image(blueBeltLeft.toString());

                URL blueBeltRight = getClass().getResource("/ConveyorBelts/BlueBeltRight.png");
                Image imageBlueBeltRight = new Image(blueBeltRight.toString());

                URL blueBeltTop = getClass().getResource("/ConveyorBelts/BlueBeltTop.png");
                Image imageBlueBeltTop = new Image(blueBeltTop.toString());

                URL blueRotating = getClass().getResource("/ConveyorBelts/blueRotating.png");
                Image imageBlueRotating = new Image(blueRotating.toString());

                URL greenBeltBottom = getClass().getResource("/ConveyorBelts/GreenBeltBottom.png");
                Image imageGreenBeltBottom = new Image(greenBeltBottom.toString());

                URL greenBeltLeft = getClass().getResource("/ConveyorBelts/GreenBeltLeft.png");
                Image imageGreenBeltLeft = new Image(greenBeltLeft.toString());

                URL greenBeltRight = getClass().getResource("/ConveyorBelts/GreenBeltRight.png");
                Image imageGreenBeltRight = new Image(greenBeltRight.toString());

                URL greenBeltTop = getClass().getResource("/ConveyorBelts/GreenBeltTop.png");
                Image imageGreenBeltTop = new Image(greenBeltTop.toString());

                URL greenRotating = getClass().getResource("/ConveyorBelts/GreenRotating.png");
                Image imageGreenRotating = new Image(greenRotating.toString());

                URL rbBottom = getClass().getResource("/ConveyorBelts/RB Bottom.png");
                Image imageRBDownLeft = new Image(rbBottom.toString());

                URL rbBottom2 = getClass().getResource("/ConveyorBelts/RB Bottom 2.png");
                Image imageRBDownRight = new Image(rbBottom2.toString());

                URL rbLeft = getClass().getResource("/ConveyorBelts/RB left.png");
                Image imageRBLeftUp = new Image(rbLeft.toString());

                URL rbLeft2 = getClass().getResource("/ConveyorBelts/RB left2.png");
                Image imageRBLeftDown = new Image(rbLeft2.toString());

                URL rbRight2 = getClass().getResource("/ConveyorBelts/RB right2.png");
                Image imageRBRightUp = new Image(rbRight2.toString());

                URL rbTop = getClass().getResource("/ConveyorBelts/RB top.png");
                Image imageRBUpRight = new Image(rbTop.toString());

                URL rbTop2 = getClass().getResource("/ConveyorBelts/RB top2.png");
                Image imageRBUpLeft = new Image(rbTop2.toString());

                URL RBGreenUpRight = getClass().getResource("/ConveyorBelts/RBGreenUpRight.png");
                Image imageRBGreenUpRight = new Image(RBGreenUpRight.toString());

                URL RBGreenDownLeft = getClass().getResource("/ConveyorBelts/RBGreenDownLeft.png");
                Image imageRBGreenDownLeft = new Image(RBGreenDownLeft.toString());

                URL RBGreenDownRight = getClass().getResource("/ConveyorBelts/RBGreenDownRight.png");
                Image imageRBGreenDownRight = new Image(RBGreenDownRight.toString());

                URL RBGreenUpLeft = getClass().getResource("/ConveyorBelts/RBGreenUpLeft.png");
                Image imageRBGreenUpLeft = new Image(RBGreenUpLeft.toString());

                URL rbRight = getClass().getResource("/img.png");
                Image imageRBRightDown = new Image(rbRight.toString());



                /**
                 * Board Images
                 */
                URL wallDown = getClass().getResource("/BoardImages/WallDown.png");
                Image imageWallDown = new Image(wallDown.toString());

                URL wallRight = getClass().getResource("/BoardImages/WallRight.png");
                Image imageWallRight = new Image(wallRight.toString());

                URL wallLeft = getClass().getResource("/BoardImages/WallLeft.png");
                Image imageWallLeft = new Image(wallLeft.toString());

                URL antenna = getClass().getResource("/BoardImages/antenna.png");
                Image imageAntenna = new Image(antenna.toString());

                URL pit = getClass().getResource("/BoardImages/Pit.png");
                Image imagePit = new Image(pit.toString());

                URL startPoint = getClass().getResource("/BoardImages/StartPoint.png");
                Image imageStartPoint = new Image(startPoint.toString());

                URL wallup = getClass().getResource("/BoardImages/Wall.png");
                Image imageWallUp = new Image(wallup.toString());

                URL wallEcke = getClass().getResource("/BoardImages/WallEcke.png");
                Image imageWallEcke = new Image(wallEcke.toString());


                /**
                 * EnergyImg
                 */
                URL energyOff = getClass().getResource("/EnergyImg/energyOff.png");
                Image imageenergyOff = new Image(energyOff.toString());

                URL energyOn = getClass().getResource("/EnergyImg/energyOn.png");
                Image imageenergyOn = new Image(energyOn.toString());


                /**
                 * Laser
                 */
                URL laser1 = getClass().getResource("/Laser/Laser1.png");
                Image imagehorilaser1 = new Image(laser1.toString());

                URL laser2 = getClass().getResource("/Laser/Laser2.png");
                Image imagehorilaser2 = new Image(laser2.toString());

                URL laser3 = getClass().getResource("/Laser/Laser3.png");
                Image imagehorilaser3 = new Image(laser3.toString());

                URL wallLaser1 = getClass().getResource("/Laser/WallLaser1.png");
                Image imagewallRightLaser1 = new Image(wallLaser1.toString());

                URL wallLaser2 = getClass().getResource("/Laser/WallLaser2.png");
                Image imagewallRightLaser2 = new Image(wallLaser2.toString());

                URL wallLaser3 = getClass().getResource("/Laser/WallLaser3.png");
                Image imagewallRightLaser3 = new Image(wallLaser3.toString());

                URL laser1Vertical = getClass().getResource("/Laser/Laser1Vertical.png");
                Image imagelaser1Vertical = new Image(laser1Vertical.toString());

                URL laser2Vertical = getClass().getResource("/Laser/Laser2Vertical.png");
                Image imagelaser2Vertical = new Image(laser2Vertical.toString());

                URL laser3Vertical = getClass().getResource("/Laser/Laser3Vertical.png");
                Image imagelaser3Vertical = new Image(laser3Vertical.toString());

                URL wallLaser1Vertical = getClass().getResource("/Laser/WallLaser1Vertical.png");
                Image imageWallLaser1Vertical = new Image(wallLaser1Vertical.toString());

                URL wallLaser2Vertical = getClass().getResource("/Laser/WallLaser2Vertical.png");
                Image imageWallLaser2Vertical = new Image(wallLaser2Vertical.toString());

                URL wallLaser3Vertical = getClass().getResource("/Laser/WallLaser3Vertical.png");
                Image imageWallLaser3Vertical = new Image(wallLaser3Vertical.toString());

                URL wallLaser1Vertical2 = getClass().getResource("/Laser/WallLaser1Vertical2.png");
                Image imageWallLaser1Vertical2 = new Image(wallLaser1Vertical2.toString());

                URL wallLaser2Vertical2 = getClass().getResource("/Laser/WallLaser2Vertical2.png");
                Image imageWallLaser2Vertical2 = new Image(wallLaser2Vertical2.toString());

                URL wallLaser3Vertical2 = getClass().getResource("/Laser/WallLaser3Vertical2.png");
                Image imageWallLaser3Vertical2 = new Image(wallLaser3Vertical2.toString());

                URL wallLaser1Right = getClass().getResource("/Laser/WallLaser1Right.png");
                Image imagewallLaser1Right = new Image(wallLaser1Right.toString());



                /**
                 * others
                 */
                URL gearLeft = getClass().getResource("/others/gearLeft.png");
                Image imageGearLeft = new Image(gearLeft.toString());

                URL gearRight = getClass().getResource("/others/gearRight.png");
                Image imageGearRight = new Image(gearRight.toString());

                URL PP24 = getClass().getResource("/others/PP24.png");
                Image imagePP24 = new Image(PP24.toString());

                URL PP135 = getClass().getResource("/others/PP135.png");
                Image imagePP135 = new Image(PP135.toString());

                URL reboot = getClass().getResource("/others/reboot.png");
                Image imageReboot = new Image(reboot.toString());

                URL empty = getClass().getResource("/Empty.png");
                Image imageEmpty = new Image(empty.toString());



                /**
                 * URLs for Extra Crispy
                 */

                URL blueRotatingDownLeft = getClass().getResource("/bluePanelExtraCrispy/blueRotatingDownLeft.png");
                Image imageBlueRotatingDownLeft = new Image(blueRotatingDownLeft.toString());

                URL blueRotatingDownRight = getClass().getResource("/bluePanelExtraCrispy/blueRotatingDownRight.png");
                Image imageBlueRotatingDownRight = new Image(blueRotatingDownRight.toString());

                URL blueRotatingUpLeft = getClass().getResource("/bluePanelExtraCrispy/blueRotatingUpLeft.png");
                Image imageBlueRotatingUpLeft = new Image(blueRotatingUpLeft.toString());

                URL blueRotatingUpRight = getClass().getResource("/bluePanelExtraCrispy/blueRotatingUpRight.png");
                Image imageBlueRotatingUpRight = new Image(blueRotatingUpRight.toString());



                /**
                 * green Conveyor belt for Death trap
                 */

                URL RBGreenUpLeft2 = getClass().getResource("/greenPanelDeathTrap/RBGreenUpLeft2.png");
                Image imageRBGreenUpLeft2 = new Image(RBGreenUpLeft2.toString());

                URL RBGreenDownLeft2 = getClass().getResource("/greenPanelDeathTrap/RBGreenDownLeft2.png");
                Image imageRBGreenDownLeft2 = new Image(RBGreenDownLeft2.toString());

                URL RBGreenUpRight2 = getClass().getResource("/greenPanelDeathTrap/RBGreenUpRight2.png");
                Image imageRBGreenUpRight2 = new Image(RBGreenUpRight2.toString());

                URL RBGreenDownLeft3 = getClass().getResource("/greenPanelDeathTrap/RBGreenDownLeft3.png");
                Image imageRBGreenDownLeft3 = new Image(RBGreenDownLeft3.toString());

                /**
                 * URLs for Push Panels
                 */

                /*URL PP24Bottom = getClass().getResource("PushPanels/PP24Bottom.png");
                Image imagePP24Bottom = new Image(PP24Bottom.toString());

                URL PP24Left = getClass().getResource("PushPanels/PP24Left.png");
                Image imagePP24Left = new Image(PP24Left.toString());

                URL PP24Right = getClass().getResource("PushPanels/PP24Right.png");
                Image imagePP24Right = new Image(PP24Right.toString());

                URL PP24Top = getClass().getResource("PushPanels/PP24Top.png");
                Image imagePP24Top = new Image(PP24Top.toString());

                URL PP135Bottom = getClass().getResource("PushPanels/PP135Bottom.png");
                Image imagePP135Bottom = new Image(PP135Bottom.toString());

                URL PP135Top = getClass().getResource("PushPanels/PP135Top.png");
                Image imagePP135Top = new Image(PP135Top.toString());

                URL PP135Right = getClass().getResource("PushPanels/PP135Right.png");
                Image imagePP135Right = new Image(PP135Right.toString());

                URL PP135Left = getClass().getResource("PushPanels/PP135Left.png");
                Image imagePP135Left = new Image(PP135Left.toString());

                 */





                //public void printMap() {
                Image elmImage = null;
                Image elmImage2=null;
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 13; y++) {
                        BoardElem boardElem1 = board.getBoardElem(x, y, 0);
                        BoardElem boardElem2 = board.getBoardElem(x, y, 1);
                        String firstBoardElmName = boardElem1.getName();
                        String secondBoardElmName = boardElem2.getName();
                        switch (firstBoardElmName) {
                            case "StartPoint":
                                elmImage = imageStartPoint;
                                break;
                            case "CheckPoint":
                                int number = boardElem1.getCount();
                                switch (number) {
                                    case 1:
                                        elmImage = imageCheckPoint1;
                                        break;
                                    case 2:
                                        elmImage = imageCheckPoint2;
                                        break;
                                    case 3:
                                        elmImage = imageCheckPoint3;
                                        break;
                                    case 4:
                                        elmImage = imageCheckPoint4;
                                        break;
                                    case 5:
                                        elmImage = imageCheckPoint5;
                                        break;
                                    case 6:
                                        elmImage = imageCheckPoint6;
                                        break;
                                }
                                break;
                            case "ConveyBelt":
                                int speed = boardElem1.getSpeed();
                                Direction direction = boardElem1.getDirection();
                                if (speed == 1) {
                                    switch (direction) {
                                        case UP:
                                            elmImage = imageGreenBeltTop;
                                            break;
                                        case DOWN:
                                            elmImage = imageGreenBeltBottom;
                                            break;
                                        case LEFT:
                                            elmImage = imageGreenBeltLeft;
                                            break;
                                        case RIGHT:
                                            elmImage = imageGreenBeltRight;
                                            break;
                                    }
                                }

                                if(speed==2){
                                    switch (direction){
                                        case UP:
                                            elmImage=imageBlueBeltTop;
                                            break;
                                        case DOWN:
                                            elmImage=imageBlueBeltBottom;
                                            break;
                                        case LEFT:
                                            elmImage=imageBlueBeltLeft;
                                            break;
                                        case RIGHT:
                                            elmImage=imageBlueBeltRight;
                                            break;
                                    }
                                }
                                break;
                            case"EnergySpace":
                                elmImage=imageenergyOn;
                                break;
                            case"Laser":
                                int count=boardElem1.getCount();
                                Direction LaserDirection=boardElem1.getDirection();
                                String name=boardElem2.getName();
                                Direction WallDirection=boardElem2.getDirection();
                                if(name.equals("Wall")&&LaserDirection.equals(WallDirection.turn180())) {
                                    switch (count) {
                                        case 1:
                                            if ( LaserDirection.equals(Direction.DOWN)) {
                                                elmImage = imageWallLaser1Vertical;
                                            }
                                            if (LaserDirection.equals(Direction.RIGHT) ){
                                                elmImage = imagewallRightLaser1;
                                            }
                                            if(LaserDirection.equals(Direction.UP)){
                                                elmImage=imageWallLaser1Vertical2;
                                            }
                                            if (LaserDirection.equals(Direction.LEFT)){
                                                elmImage=imagewallLaser1Right;
                                            }
                                            break;
                                        case 2:
                                            if (LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)) {
                                                elmImage = imageWallLaser2Vertical;
                                            }
                                            if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                                elmImage = imagewallRightLaser2;
                                            }
                                            break;
                                        case 3:
                                            if (LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)) {
                                                elmImage = imageWallLaser3Vertical;
                                            }
                                            if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                                elmImage = imagewallRightLaser3;
                                            }
                                            break;
                                    }
                                }
                            else {
                                switch (count) {
                                    case 1:
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                        { elmImage = imagehorilaser1;}
                                        if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                            elmImage = imagelaser1Vertical;
                                        }
                                        break;
                                    case 2:
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                        {  elmImage = imagehorilaser2; }
                                        if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                            elmImage = imagelaser2Vertical;
                                        }
                                        break;
                                    case 3:
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)) {
                                            elmImage = imagehorilaser3;
                                        }
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                            elmImage = imagelaser3Vertical;
                                        }
                                        break;
                                }
                            }
                                break;
                            case"RotatingBelt":
                                Direction direction1=boardElem1.getDirection();
                                Direction direction2=boardElem1.getDirection2();
                                int beltSpeed=boardElem1.getSpeed();
                                switch (beltSpeed) {
                                    case 2:
                                        if(board.getName().equals("ExtraCrispy")) {
                                            switch (direction1) {
                                                case RIGHT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageBlueRotatingUpRight;
                                                            break;
                                                        case DOWN:
                                                            elmImage = imageBlueRotatingDownRight;
                                                            break;
                                                    }
                                                    break;
                                                case LEFT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageBlueRotatingUpLeft;
                                                            break;
                                                        case DOWN:
                                                            elmImage = imageBlueRotatingDownLeft;
                                                            break;
                                                    }
                                                    break;
                                            }
                                        }
                                   else switch (direction1) {
                                        case RIGHT:
                                            switch (direction2) {
                                                case UP:
                                                    elmImage = imageRBRightUp;
                                                    break;
                                                case DOWN:
                                                    elmImage = imageRBRightDown;
                                                    break;

                                            }
                                            break;
                                        case LEFT:
                                            switch (direction2) {
                                                case UP:
                                                    elmImage = imageRBLeftUp;
                                                    break;
                                                case DOWN:
                                                    elmImage = imageRBLeftDown;
                                                    break;
                                            }
                                            break;
                                        case UP:
                                            switch (direction2) {
                                                case RIGHT:
                                                    elmImage = imageRBUpRight;
                                                    break;
                                                case LEFT:
                                                    elmImage = imageRBUpLeft;
                                                    break;
                                            }
                                            break;
                                        case DOWN:
                                            switch (direction2) {
                                                case RIGHT:
                                                    elmImage = imageRBDownRight;
                                                    break;
                                                case LEFT:
                                                    elmImage = imageRBDownLeft;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                                    case 1:
                                        switch(direction1){
                                            case UP:
                                                switch (direction2) {
                                                    case RIGHT:
                                                        elmImage=imageRBGreenUpRight;
                                                        break;
                                                    case LEFT:
                                                        elmImage = imageRBGreenUpLeft;
                                                        break;
                                                }
                                                break;


                                            case DOWN:
                                                switch (direction2) {
                                                    case RIGHT:
                                                        elmImage = imageRBGreenDownRight;
                                                        break;
                                                    case LEFT:
                                                        elmImage = imageRBGreenDownLeft;
                                                        break;
                                                }
                                                break;
                                        }

                                }
                                break;
                            case"Antenna":
                                elmImage=imageAntenna;
                                break;
                            case"PushPanel":
                                String PushPanelReg=boardElem1.getPush();
                                switch (PushPanelReg){
                                    case"1,3,5":
                                        elmImage=imagePP135;
                                        break;
                                    case "2,4":
                                        elmImage=imagePP24;
                                        break;
                                }
                                break;
                            case"Gear":
                                String turnDirection=boardElem1.getTurnDirection();
                                switch (turnDirection){
                                    case "turnLeft":
                                        elmImage=imageGearLeft;
                                        break;
                                    case"turnRight":
                                        elmImage=imageGearRight;
                                        break;
                                }
                                break;
                            case"Reboot":
                                elmImage=imageReboot;
                                break;
                            case"Pit":
                                elmImage=imagePit;
                                break;
                            case "Empty":
                                elmImage=imageEmpty;
                            default:
                                break;
                        }
                        switch(secondBoardElmName){
                            case"Laser":
                                int laserCount=boardElem2.getCount();
                                Direction LaserDirection=boardElem2.getDirection();
                                switch (laserCount){
                                    case 1:
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                        { elmImage2 = imagehorilaser1;}
                                        if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                            elmImage2 = imagelaser1Vertical;
                                        }
                                        break;
                                    case 2:
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                        {  elmImage2 = imagehorilaser2; }
                                        if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                            elmImage2 = imagelaser2Vertical;
                                        }
                                        break;
                                    case 3:
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)) {
                                            elmImage2 = imagehorilaser3;
                                        }
                                        if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                            elmImage2 = imagelaser3Vertical;
                                        }
                                        break;
                                }
                                break;
                            case"Wall":
                                Direction wallDirection=boardElem2.getDirection();
                                switch (wallDirection){
                                    case UP:
                                        elmImage2=imageWallUp;
                                        break;
                                    case LEFT:
                                        elmImage2=imageWallLeft;
                                        break;
                                    case  DOWN:
                                        elmImage2=imageWallDown;
                                        break;
                                    case  RIGHT:
                                        elmImage2=imageWallRight;
                                        break;
                                }
                                break;
                            case"Empty":
                                elmImage2=imageEmpty;
                                break;
                        }
                        ImageView boardElem=new ImageView(elmImage);
                        ImageView boardElemSecond=new ImageView(elmImage2);
                        boardElem.setFitHeight(43);
                        boardElem.setFitWidth(43);
                        boardElemSecond.setFitWidth(43);
                        boardElemSecond.setFitHeight(43);
                        gameboard.add(boardElem,y,x);
                        gameboard.add(boardElemSecond,y,x);
                        if(board.getName().equals("ExtraCrispy")){
                            ImageView checkPointOne=new ImageView(imageCheckPoint1);
                            ImageView checkPointTwo=new ImageView(imageCheckPoint2);
                            ImageView checkPointThree=new ImageView(imageCheckPoint3);
                            ImageView checkPointFour=new ImageView(imageCheckPoint4);
                            checkPointThree.setFitHeight(43);
                            checkPointOne.setFitHeight(43);
                            checkPointTwo.setFitHeight(43);
                            checkPointFour.setFitHeight(43);
                            checkPointOne.setFitWidth(43);
                            checkPointThree.setFitWidth(43);
                            checkPointTwo.setFitWidth(43);
                            checkPointFour.setFitWidth(43);
                             gameboard.add(checkPointFour,5,2);
                             gameboard.add(checkPointOne,10,2);
                             gameboard.add(checkPointTwo,5,7);
                             gameboard.add(checkPointThree,10,7);
                        }
                    }

                }

            }

    }


















/*


                break;
            case "Death Trap":
                break;
            case "Extra Cripsy":
                break;
            case "Lost Bearings":
                break;


        }

    }
}
*/


