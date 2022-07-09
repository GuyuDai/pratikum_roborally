package client.gameWindow;

import client.Client;
import client.ClientReceive;
import client.MapBuilder;
import client.lobbyWindow.LobbyViewModel;
import com.google.gson.Gson;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import client.mapWindow.MapViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import protocol.SelectedCard;
import protocol.SendChat;
import protocol.SetStartingPoint;
import protocol.YourCards;
import server.BoardElement.BoardElem;
import server.BoardTypes.*;
import server.CardTypes.*;
import server.Control.Direction;
import server.Control.Timer;
import server.Deck.ProgrammingDeck;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author Nargess Ahmadi, Felicia Saruba, Li MingHao
 */
public class GameViewModel {

    private static GameModel model;
    private Client client;
    private static GameViewModel instance;

    private Gson gson = new Gson();

    public String mapSelected;

    public String[] cards = {"", "", "", "", "", "", "", "", ""};


    /**
     * timer
     */

    @FXML
    private Label timerText;

    @FXML
    private TextField timer30;



    /**
     * starting point selection
     */
    @FXML
    private Pane selectStartingPoint;
    @FXML
    private ToggleGroup StartingPoint;
    @FXML
    private ToggleButton startingPoint1;
    @FXML
    private ToggleButton startingPoint2;
    @FXML
    private ToggleButton startingPoint3;
    @FXML
    private ToggleButton startingPoint4;
    @FXML
    private ToggleButton startingPoint5;
    @FXML
    private ToggleButton startingPoint6;
    @FXML
    private Label textStartingPoint;

    @FXML
    private Button startingPointOK;

    List<Integer> takenStartNumbers=new ArrayList<>();

    List<Integer> allStartNumbers=new ArrayList<>();




    @FXML
    private Label timer;

    @FXML
    private ImageView image1;


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
    private Button startGameButton;

    @FXML
    private Button selectMapButton;
    @FXML
    private Button getCardsButton;

    @FXML
    private Button printMapButton;


    /**
     * starting points
     */
    @FXML
    private Button start1;

    @FXML
    private Button start2;

    @FXML
    private Button start3;

    @FXML
    private Button start4;

    @FXML
    private Button start5;

    @FXML
    private Button start6;


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
    private Label yourBotText;

    @FXML
    private ImageView yourBot;

    @FXML
    GridPane gameboard, startBoard, robotBoard, checkBoard;

    public String window = "Game";
    public ProgrammingDeck deck = new ProgrammingDeck();

    String[] allTakenStaringPoint;


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

    URL hammer = getClass().getResource("/Robots/hammer bot.png");
    Image imageHammer = new Image(hammer.toString());

    URL hulk = getClass().getResource("/Robots/hulk x90.png");
    Image imageHulk = new Image(hulk.toString());

    URL spin = getClass().getResource("/Robots/spin bot.png");
    Image imageSpin = new Image(spin.toString());

    URL squash = getClass().getResource("/Robots/squash bot.png");
    Image imageSquash = new Image(squash.toString());

    URL twitch = getClass().getResource("/Robots/twitch.png");
    Image imageTwitch = new Image(twitch.toString());

    URL twonkey = getClass().getResource("/Robots/twonkey.png");
    Image imageTwonkey = new Image(twonkey.toString());


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
     * sets your own robot icon
     */
    public void setYourBotIcon(){
        yourBotText.setVisible(true);
        int yourId=Client.getClientReceive().getClientID();
        int yourRobotNumber=Client.getClientReceive().getRobotById(yourId);
        Image robotIcon=null;
        switch (yourRobotNumber) {
            case 1:
                robotIcon = imageHulk;//hulk
                break;
            case 2:
                robotIcon = imageSpin;//spin
                break;
            case 3:
                robotIcon = imageSquash;//squash
                break;
            case 4:
                robotIcon = imageHammer;//hammer
                break;
            case 5:
                robotIcon = imageTwonkey;//twonkey
                break;
            case 6:
                robotIcon = imageTwitch;//twitch
                break;
        }
        yourBot.setImage(robotIcon);
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
     * print map button -> map gets printed and starting point selection appears
     */
    public void printMapButtonAction(ActionEvent actionEvent) {
        String map=Client.getClientReceive().getBoard();
        printMapGUI(map);

        Text.setText("Select a starting point.");
        selectStartingPoint.setVisible(true);
        printMapButton.setVisible(false);
        setYourBotIcon();
    }

    /**
     * start game button -> show button get Cards
     */

    public void startGameButtonAction(ActionEvent actionEvent) {
        for(int id:Client.getClientReceive().getIdList()){
               int robotNumber=Client.getClientReceive().getRobotById(id);
               int startingPointNumber=Client.getClientReceive().getStartPointById(id);
                setOtherRobotOnBoard(robotNumber,startingPointNumber);
        }
        getCardsButton.setVisible(true);
        startGameButton.setVisible(false);

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

                URL pp135Down = getClass().getResource("/pushpanel/pp135Down.png");
                Image imagePP135Down = new Image(pp135Down.toString());

                URL pp135Up = getClass().getResource("/pushpanel/pp135Up.png");
                Image imagePP135Up = new Image(pp135Up.toString());

                URL pp135Right = getClass().getResource("/pushpanel/pp135Right.png");
                Image imagePP135Right = new Image(pp135Right.toString());

                URL pp135Left = getClass().getResource("/pushpanel/pp135Left.png");
                Image imagePP135Left = new Image(pp135Left.toString());

                URL pp24Down = getClass().getResource("/pushpanel/pp24Down.png");
                Image imagePP24Down = new Image(pp24Down.toString());

                URL pp24Up = getClass().getResource("/pushpanel/pp24Up.png");
                Image imagePP24Up = new Image(pp24Up.toString());

                URL pp24Right = getClass().getResource("/pushpanel/pp24Right.png");
                Image imagePP24Right = new Image(pp24Right.toString());

                URL pp24Left = getClass().getResource("/pushpanel/pp24Left.png");
                Image imagePP24Left = new Image(pp24Left.toString());




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
                                            }} else switch (direction1) {
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
                                    case 3:
                                        if(board.getName().equals("LostBearings")) {
                                            switch(direction1){
                                                case UP:
                                                    switch (direction2) {
                                                        case RIGHT:
                                                            elmImage = imageRBGreenUpRight2;
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenUpLeft2;
                                                            break;
                                                    }
                                                    break;


                                                case DOWN:
                                                    switch (direction2) {
                                                        case RIGHT:
                                                            elmImage = imageRBGreenDownLeft3; //change
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenDownLeft2;
                                                            break;
                                                    }
                                                    break;
                                            }
                                        }
                                    case 4:
                                        if(board.getName().equals("DeathTrap")) {
                                            switch(direction1) {
                                                case RIGHT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageRBGreenUpRight2;
                                                            break;
                                                        case DOWN:
                                                            elmImage = imageRBGreenDownRight;
                                                            break;
                                                    }
                                                    break;

                                                case LEFT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageRBGreenUpLeft;
                                                            break;
                                                        case DOWN:
                                                            elmImage = imageRBGreenDownLeft2;
                                                            break;
                                                    }
                                                    break;
                                                case UP:
                                                    switch (direction2) {
                                                        case RIGHT:
                                                            elmImage = imageRBGreenUpRight;
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenUpLeft2; //
                                                            break;
                                                    }
                                                    break;


                                                case DOWN:
                                                    switch (direction2) {
                                                        case RIGHT:
                                                            elmImage = imageRBGreenDownLeft3;
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenDownLeft;
                                                            break;
                                                    }
                                                    break;
                                            }
                                        }

                                }
                                break;
                            case"Antenna":
                                elmImage=imageAntenna;
                                break;
                            case"PushPanel":
                                String pushPanelReg=boardElem1.getPush();
                                Direction pushDirection = boardElem1.getDirection();
                                if (pushPanelReg.equals("1,3,5")){
                                    switch (pushDirection) {
                                        case UP:
                                            elmImage = imagePP135Up;
                                            break;
                                        case DOWN:
                                            elmImage = imagePP135Down;
                                            break;
                                        case RIGHT:
                                            elmImage = imagePP135Right;
                                            break;
                                        case LEFT:
                                            elmImage = imagePP135Left;
                                    }
                                }

                                if(pushPanelReg.equals("2,4")){
                                    switch (pushDirection){
                                        case UP:
                                            elmImage = imagePP24Up;
                                            break;
                                        case DOWN:
                                            elmImage = imagePP24Down;
                                            break;
                                        case RIGHT:
                                            elmImage = imagePP24Right;
                                            break;
                                        case LEFT:
                                            elmImage = imagePP24Left;
                                    }
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


//just for testing:



    int startingPointCount;
    int x;
    int y;

    public void setStartingPointCount(int count){
        this.startingPointCount = count;
    }

    public int getStartingPointCount(){
        return startingPointCount;
    }

    public void startingPoint1Action(ActionEvent actionEvent) {
        checkStart();
        setStartingPointCount(1);
        x = 1;
        y = 1;
    }

    public void startingPoint2Action(ActionEvent actionEvent) {
        checkStart();
        setStartingPointCount(2);
        x = 3;
        y = 0;
    }

    public void startingPoint3Action(ActionEvent actionEvent) {
        checkStart();
        setStartingPointCount(3);
        x = 4;
        y = 1;
    }

    public void startingPoint4Action(ActionEvent actionEvent) {
        checkStart();
        setStartingPointCount(4);
        x = 5;
        y = 1;
    }

    public void startingPoint5Action(ActionEvent actionEvent) {
        checkStart();
        setStartingPointCount(5);
        x = 6;
        y = 0;
    }

    public void startingPoint6Action(ActionEvent actionEvent) {
        checkStart();
        setStartingPointCount(6);
        x = 8;
        y = 1;
    }


    public void startPointOKAction(ActionEvent actionEvent) {
           switch (getStartingPointCount()){
               case 1:
                   Client.getClientReceive().sendMessage(new SetStartingPoint(1,1).toString());
                   setRobotOnBoard(1);
                   break;
               case 2:
                   Client.getClientReceive().sendMessage(new SetStartingPoint(3,0).toString());
                   setRobotOnBoard(2);
                    break;
               case 3:
                   Client.getClientReceive().sendMessage(new SetStartingPoint(4,1).toString());
                   setRobotOnBoard(3);
                   break;
               case 4:
                   Client.getClientReceive().sendMessage(new SetStartingPoint(5,1).toString());
                   setRobotOnBoard(4);
                   break;
               case 5:
                   Client.getClientReceive().sendMessage(new SetStartingPoint(6,0).toString());
                   setRobotOnBoard(5);
                   break;
               case 6:
                   Client.getClientReceive().sendMessage(new SetStartingPoint(8,1).toString());
                   setRobotOnBoard(6);
                   break;
           }
        //checkStart();
        startGameButton.setVisible(true);
        selectStartingPoint.setVisible(false);


        //Todo put TIMER to right place in code (card selection), here is just for testing
        //timerText.setVisible(true);
       // Text.setText("You have 30 seconds left to finish selecting your register. Hurry!!");
       // timer.setText("30");
       // timer30Sec();


       // moveRobot(); //movement just for testing

    }


    public void moveRobot() {


        ImageView hammerView = new ImageView(imageHammer);
        ImageView hulkView = new ImageView(imageHulk);
        ImageView spinView = new ImageView(imageSpin);
        ImageView squashView = new ImageView(imageSquash);
        ImageView twitchView = new ImageView(imageTwitch);
        ImageView twonkeyView = new ImageView(imageTwonkey);

        hammerView.setFitWidth(43);
        hammerView.setFitHeight(43);
        hulkView.setFitWidth(43);
        hulkView.setFitHeight(43);
        spinView.setFitWidth(43);
        spinView.setFitHeight(43);
        squashView.setFitWidth(43);
        squashView.setFitHeight(43);
        twitchView.setFitWidth(43);
        twitchView.setFitHeight(43);
        twonkeyView.setFitWidth(43);
        twonkeyView.setFitHeight(43);



        //rotating image view
        //ImageView testViewRotate = new ImageView(test);
        //testViewRotate.setRotate(90);


        int x = 5;
        int y = 3;


        robotBoard.add(hammerView, x, y);

        PauseTransition move1d = new PauseTransition(Duration.seconds(2));
        move1d.setOnFinished(e -> robotBoard.add(hammerView, x, y +1));
        move1d.play();

        /*
        PauseTransition move2 = new PauseTransition(Duration.seconds(3));
        move2.setOnFinished(e -> robotBoard.add(testView, x, y+2));
        move2.play();

        PauseTransition move3 = new PauseTransition(Duration.seconds(4));
        move3.setOnFinished(e -> robotBoard.add(testView, x +1 , y+2));
        move3.play();

        PauseTransition move4 = new PauseTransition(Duration.seconds(5));
        move4.setOnFinished(e -> robotBoard.add(testView, x +1 , y+2));
        move4.play();

         */
    }


    public void checkStart() {
        takenStartNumbers = Client.getClientReceive().getStartNumbers();
        for (int number : takenStartNumbers) {
            switch(number) {
                case 1:
                    startingPoint1.setDisable(true);
                    break;
                case 2:
                    startingPoint2.setDisable(true);
                    break;
                case 3:
                    startingPoint3.setDisable(true);
                    break;
                case 4:
                    startingPoint4.setDisable(true);
                    break;
                case 5:
                    startingPoint5.setDisable(true);
                    break;
                case 6:
                    startingPoint6.setDisable(true);
                    break;
            }
        }
    }

    public void setRobotOnBoard(int startingPointNumber){
        int yourId=Client.getClientReceive().getClientID();
        int yourRobotNumber=Client.getClientReceive().getRobotById(yourId);
        Image robotImage=null;
        switch (yourRobotNumber){
            case 1:
                robotImage=imageHulk;//hulk
                break;
            case 2:
                robotImage=imageSpin;//spin
                break;
            case 3:
                robotImage=imageSquash;//squash
                break;
            case 4:
                robotImage=imageHammer;//hammer
                break;
            case 5:
                robotImage=imageTwonkey;//twonkey
                break;
            case 6:
                robotImage=imageTwitch;//twitch
                break;
        }
        ImageView robotPic=new ImageView(robotImage);
        robotPic.setFitWidth(43);
        robotPic.setFitHeight(43);
        switch (startingPointNumber){
            case 1:
                robotBoard.add(robotPic,1,1);
                break;
            case 2:
                robotBoard.add(robotPic,0,3);
                break;
            case 3:
                robotBoard.add(robotPic,1,4);
                break;
            case 4:
                robotBoard.add(robotPic,1,5);
                break;
            case 5:
                robotBoard.add(robotPic,0,6);
                break;
            case 6:
                robotBoard.add(robotPic,1,8);
                break;

        }
    }


    public void setOtherRobotOnBoard(int otherRobotNumber,int startingPointNumber){
                Image otherRobotImage = null;
                switch (otherRobotNumber) {
                    case 1:
                        otherRobotImage = imageHulk;//hulk
                        break;
                    case 2:
                        otherRobotImage = imageSpin;//spin
                        break;
                    case 3:
                        otherRobotImage = imageSquash;//squash
                        break;
                    case 4:
                        otherRobotImage = imageHammer;//hammer
                        break;
                    case 5:
                        otherRobotImage = imageTwonkey;//twonkey
                        break;
                    case 6:
                        otherRobotImage = imageTwitch;//twitch
                        break;
                }
                ImageView robotPic = new ImageView(otherRobotImage);
                robotPic.setFitWidth(43);
                robotPic.setFitHeight(43);
                switch (startingPointNumber) {
                    case 1:
                        robotBoard.add(robotPic, 1, 1);
                        break;
                    case 2:
                        robotBoard.add(robotPic, 0, 3);
                        break;
                    case 3:
                        robotBoard.add(robotPic, 1, 4);
                        break;
                    case 4:
                        robotBoard.add(robotPic, 1, 5);
                        break;
                    case 5:
                        robotBoard.add(robotPic, 0, 6);
                        break;
                    case 6:
                        robotBoard.add(robotPic, 1, 8);
                        break;
                }


    }



    public void playDamageCards(ActionEvent actionEvent) {
    }



    public void playCardBtnAction(ActionEvent actionEvent) {

    }

    public void getCardsButtonAction(ActionEvent actionEvent) {
        getCardsButton.setVisible(false);

        //make hand buttons visible
        hand1Button.setVisible(true);
        hand2Button.setVisible(true);
        hand3Button.setVisible(true);
        hand4Button.setVisible(true);
        hand5Button.setVisible(true);
        hand6Button.setVisible(true);
        hand7Button.setVisible(true);
        hand8Button.setVisible(true);
        hand9Button.setVisible(true);

        Text.setText("Select 5 of these cards for your register.");
        printCards();
    }

    public void MouseAction(MouseEvent mouseEvent) {
        System.out.println("hello this is a test");
        //TODO connect to refresher here!
    }



    /**
     * timer 30 seconds when first player finished selecting register
     */
    public void timer30Sec () {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        pauseTransition.setOnFinished(e -> timer.setText("29"));
        pauseTransition.play();

        PauseTransition pauseTransition28 = new PauseTransition(Duration.seconds(2));
        pauseTransition28.setOnFinished(e -> timer.setText("28"));
        pauseTransition28.play();

        PauseTransition pauseTransition27 = new PauseTransition(Duration.seconds(3));
        pauseTransition27.setOnFinished(e -> timer.setText("27"));
        pauseTransition27.play();

        PauseTransition pauseTransition26 = new PauseTransition(Duration.seconds(4));
        pauseTransition26.setOnFinished(e -> timer.setText("26"));
        pauseTransition26.play();

        PauseTransition pauseTransition25 = new PauseTransition(Duration.seconds(5));
        pauseTransition25.setOnFinished(e -> timer.setText("25"));
        pauseTransition25.play();

        PauseTransition pauseTransition24 = new PauseTransition(Duration.seconds(6));
        pauseTransition24.setOnFinished(e -> timer.setText("24"));
        pauseTransition24.play();

        PauseTransition pauseTransition23 = new PauseTransition(Duration.seconds(7));
        pauseTransition23.setOnFinished(e -> timer.setText("23"));
        pauseTransition23.play();

        PauseTransition pauseTransition22 = new PauseTransition(Duration.seconds(8));
        pauseTransition22.setOnFinished(e -> timer.setText("22"));
        pauseTransition22.play();

        PauseTransition pauseTransition21 = new PauseTransition(Duration.seconds(9));
        pauseTransition21.setOnFinished(e -> timer.setText("21"));
        pauseTransition21.play();

        PauseTransition pauseTransition20 = new PauseTransition(Duration.seconds(10));
        pauseTransition20.setOnFinished(e -> timer.setText("20"));
        pauseTransition20.play();

        PauseTransition pauseTransition19 = new PauseTransition(Duration.seconds(11));
        pauseTransition19.setOnFinished(e -> timer.setText("19"));
        pauseTransition19.play();

        PauseTransition pauseTransition18 = new PauseTransition(Duration.seconds(12));
        pauseTransition18.setOnFinished(e -> timer.setText("18"));
        pauseTransition18.play();

        PauseTransition pauseTransition17 = new PauseTransition(Duration.seconds(13));
        pauseTransition17.setOnFinished(e -> timer.setText("17"));
        pauseTransition17.play();

        PauseTransition pauseTransition16 = new PauseTransition(Duration.seconds(14));
        pauseTransition16.setOnFinished(e -> timer.setText("16"));
        pauseTransition16.play();

        PauseTransition pauseTransition15 = new PauseTransition(Duration.seconds(15));
        pauseTransition15.setOnFinished(e -> timer.setText("15"));
        pauseTransition15.play();

        PauseTransition pauseTransition14 = new PauseTransition(Duration.seconds(16));
        pauseTransition14.setOnFinished(e -> timer.setText("14"));
        pauseTransition14.play();

        PauseTransition pauseTransition13 = new PauseTransition(Duration.seconds(17));
        pauseTransition13.setOnFinished(e -> timer.setText("13"));
        pauseTransition13.play();

        PauseTransition pauseTransition12 = new PauseTransition(Duration.seconds(18));
        pauseTransition12.setOnFinished(e -> timer.setText("12"));
        pauseTransition12.play();

        PauseTransition pauseTransition11 = new PauseTransition(Duration.seconds(19));
        pauseTransition11.setOnFinished(e -> timer.setText("11"));
        pauseTransition11.play();

        PauseTransition pauseTransition10 = new PauseTransition(Duration.seconds(20));
        pauseTransition10.setOnFinished(e -> timer.setText("10"));
        pauseTransition10.play();

        PauseTransition pauseTransition9 = new PauseTransition(Duration.seconds(21));
        pauseTransition9.setOnFinished(e -> timer.setText("9"));
        pauseTransition9.play();

        PauseTransition pauseTransition8 = new PauseTransition(Duration.seconds(22));
        pauseTransition8.setOnFinished(e -> timer.setText("8"));
        pauseTransition8.play();

        PauseTransition pauseTransition7 = new PauseTransition(Duration.seconds(23));
        pauseTransition7.setOnFinished(e -> timer.setText("7"));
        pauseTransition7.play();

        PauseTransition pauseTransition6 = new PauseTransition(Duration.seconds(24));
        pauseTransition6.setOnFinished(e -> timer.setText("6"));
        pauseTransition6.play();

        PauseTransition pauseTransition5 = new PauseTransition(Duration.seconds(25));
        pauseTransition5.setOnFinished(e -> timer.setText("5"));
        pauseTransition5.play();

        PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(26));
        pauseTransition4.setOnFinished(e -> timer.setText("4"));
        pauseTransition4.play();

        PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(27));
        pauseTransition3.setOnFinished(e -> timer.setText("3"));
        pauseTransition3.play();

        PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(28));
        pauseTransition2.setOnFinished(e -> timer.setText("2"));
        pauseTransition2.play();

        PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(29));
        pauseTransition1.setOnFinished(e -> timer.setText("1"));
        pauseTransition1.play();

        PauseTransition pauseTransition0 = new PauseTransition(Duration.seconds(30));
        pauseTransition0.setOnFinished(e -> timer.setText("0"));
        pauseTransition0.play();
    }



        /*
        Timer timer30 = new Timer();
        try {
            timerStarted = true;
            int timeInSeconds = 30;
            while (timerStarted && timeInSeconds > 0) {

                timer.setText("" + timeInSeconds);
                TimeUnit.SECONDS.sleep(1);
                timeInSeconds--;
            }
            timerStarted = false;

         */

}



