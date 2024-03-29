package client.gameWindow;

import client.*;
import client.loginWindow.LoginViewModel;
import com.google.gson.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.*;
import javafx.util.*;
import protocol.*;
import server.BoardElement.*;
import server.BoardTypes.*;
import server.Control.*;
import server.Deck.*;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Binds the data coming from the {@link GameModel} with its respective GUI-elements and manages the presentation logic
 * for the game window
 * @author Nargess Ahmadi, Felicia Saruba, Li MingHao
 */


public class GameViewModel {

    public static final String ANSI_GREEN = "\u001B[32m";

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


    List<Integer> takenStartNumbers=new ArrayList<>();

    public Map<Integer,Integer[]> robotOldPosition=new HashMap<>();

    public Map<Integer,ImageView> robotOgPicture=new HashMap<>();



    @FXML
    private Label timer;

    @FXML
    private ImageView image1;
    @FXML
    private ImageView  roboRallyImage;


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
    @FXML
    private Button moveRobotButton;


    /**
     * starting points
     */
    @FXML
    private Button start1, start2, start3, start4, start5, start6;
    @FXML
    private Button startPointOK;


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

    @FXML
    private MediaView sound;

    /**
     * other players mat
     */
    @FXML
    ImageView playerBot1, playerBot2, playerBot3,playerBot4, playerBot5, playerBot6;
    @FXML
    Label playerName1, playerName2, playerName3, playerName4, playerName5, playerName6 ;
    @FXML
    private Pane cp1,  cp2, cp3, cp4, cp5, cp6;


    /**
     * others
     */
    @FXML
    private AnchorPane gameContainer;
    @FXML
    private Label Text;
    @FXML
    private ImageView phaseView;
    @FXML
    private Label yourBotText;
    @FXML
    private ImageView yourBot;
    @FXML
    GridPane gameboard, startBoard, robotBoard, checkBoard;


    @FXML
    Button showDamage;




    public String window = "Game";
    public ProgrammingDeck deck = new ProgrammingDeck();

    ArrayList<String> tempDrawDamage = new ArrayList<String>();



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


    URL urlSpam=getClass().getResource("/damagingCards/spam.png");

    Image imageSpam=new Image(urlSpam.toString());

    URL urlPowerUp = getClass().getResource("/programmingCards/powerUp.png");
    Image imagePowerUp = new Image(urlPowerUp.toString());


    //URL urlCardHidden = getClass().getResource("/programmingCards/cardHidden.png");
    //Image imageCardHidden = new Image(urlCardHidden.toString());

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

    int ID=Client.getClientReceive().getClientID();


    /**
     * URL adress to png file font
     */
    URL activation = getClass().getResource("/fonts/ActivationPhase.png");
    Image imageActivation = new Image(activation.toString());

    URL programming = getClass().getResource("/fonts/ProgrammingPhase.png");
    Image imageProgramming = new Image(programming.toString());


    /**
     *  This method initialize board elements for loading the game window.
     */
    public void initialize(Client client) {
        this.client = client;
        list.itemsProperty().set(model.getListContentProperty());
        input.textProperty().bindBidirectional(model.getTextFieldContent());
        hand.getChildren().add(new ImageView());
    }


    /**
     * This method add node elements for game window.
     */
    public void setNodeElements(VBox container, ListView<String> list, TextField input, Button sendBtn) {
        this.gameContainer = gameContainer;
        this.container = container;
        this.list = list;
        this.input = input;
        this.sendBtn = sendBtn;
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

    /**
     * This method is used to send messages in the chatroom by clicking on the send button.
     */
    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        String message = input.getText();
        checkInput(message);
        input.setText("");
        input.requestFocus();
        list.setItems(model.getListContentProperty());
    }


    /**
     * This method checks if it is a direct message or a message for all.
     */
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
     * This method is used to send messages by using the keyboard "Enter" key event.
     */
    @FXML
    public void keyboardAction(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendButtonAction(null);
        }
    }

    /**
     * This method opens the chat window by clicking the OPEN CHAT Button
     */
    public void chatBtnAction(ActionEvent actionEvent) {
        if (container.isVisible()) {
            container.setVisible(false);
            roboRallyImage.setVisible(true);
            chatBtn.setText("OPEN CHAT");
        } else {
            container.setVisible(true);
            roboRallyImage.setVisible(false);
            chatBtn.setText("CLOSE CHAT");
        }
    }

    /**
     * This method is used to exit game and close the thread.
     */
    public void exitGame(ActionEvent actionEvent) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        //stage.close();
        LoginViewModel.closeGUI();

        int yourId = Client.getClientReceive().getClientID();
        String exitMessage = new ConnectionUpdate(yourId, false, "").toString();
        Client.getClientReceive().sendMessage(exitMessage);

        switch (yourId){
            case 1:
                Client.getClientReceive().getIdList().remove(0);
                break;
            case 2:
                Client.getClientReceive().getIdList().remove(1);
                break;
        }
    }

    /**
     * This method displays your choosen robot icon on the gameboard
     */
    public void setYourBotIcon(){
        yourBotText.setVisible(true);
        int yourId = Client.getClientReceive().getClientID();
        String yourName = Client.getClientReceive().getNameById(yourId);
        int yourRobotNumber = Client.getClientReceive().getRobotById(yourId);
        Image robotIcon = null;
        //int aiID=AIClient.getAiClientReceive().getClientID();
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
        yourBotText.setText(yourName);
    }


    /**
     * This method displays other players robot icon, name and checkpoints on gameboard.
     */
    public void setOthersBotIcon() {
        if (playerBot1.getImage() != (null)){
            playerBot1.setImage(null);
            playerName1.setText("");
        }
        if (playerBot2.getImage() != (null)){
            playerBot2.setImage(null);
            playerName2.setText("");
        }
        if (playerBot3.getImage() != (null)){
            playerBot3.setImage(null);
            playerName3.setText("");
        }
        if (playerBot4.getImage() != (null)){
            playerBot4.setImage(null);
            playerName4.setText("");
        }
        if (playerBot5.getImage() != (null)){
            playerBot5.setImage(null);
            playerName5.setText("");
        }
        if (playerBot6.getImage() != (null)){
            playerBot6.setImage(null);
            playerName6.setText("");
        }
        for (int id : Client.getClientReceive().getIdRobot().keySet()) {
            if (id != Client.getClientReceive().getClientID()) {
                int otherRobotNumber = Client.getClientReceive().getIdRobot().get(id);
                String playerName = Client.getClientReceive().getNameById(id);
                Image otherRobotIcon = null;
                switch (otherRobotNumber) {
                    case 1:
                        otherRobotIcon = imageHulk;
                        break;
                    case 2:
                        otherRobotIcon = imageSpin;
                        break;
                    case 3:
                        otherRobotIcon = imageSquash;
                        break;
                    case 4:
                        otherRobotIcon = imageHammer;
                        break;
                    case 5:
                        otherRobotIcon = imageTwonkey;
                        break;
                    case 6:
                        otherRobotIcon = imageTwitch;
                        break;
                }

                switch (otherRobotNumber) {
                    case 1:
                        playerBot1.setImage(otherRobotIcon);
                        playerName1.setText(playerName);
                        cp1.setVisible(true);
                        break;
                    case 2:
                        playerBot2.setImage(otherRobotIcon);
                        playerName2.setText(playerName);
                        cp2.setVisible(true);
                        break;
                    case 3:
                        playerBot3.setImage(otherRobotIcon);
                        playerName3.setText(playerName);
                        cp3.setVisible(true);
                        break;
                    case 4:
                        playerBot4.setImage(otherRobotIcon);
                        playerName4.setText(playerName);
                        cp4.setVisible(true);
                        break;
                    case 5:
                        playerBot5.setImage(otherRobotIcon);
                        playerName5.setText(playerName);
                        cp5.setVisible(true);
                        break;
                    case 6:
                        playerBot6.setImage(otherRobotIcon);
                        playerName6.setText(playerName);
                        cp6.setVisible(true);
                        break;
                }
            }
        }
    }



    /**
     *  This method is printing nine random cards from a deck of 20.
     */
    public void printCards() {
        playProgrammingPhaseBegins();
        Text.setText("Select 5 of these cards for your register");
        //make 9 hands visible
        hand1Button.setVisible(true);
        hand2Button.setVisible(true);
        hand3Button.setVisible(true);
        hand4Button.setVisible(true);
        hand5Button.setVisible(true);
        hand6Button.setVisible(true);
        hand7Button.setVisible(true);
        hand8Button.setVisible(true);
        hand9Button.setVisible(true);
        //set Register count back to 0 for each round
        setRegisterCount(0);
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
                case"Spam":
                    cardImage= imageSpam;
                //default:
                  //  cardImage = imageCardHidden;

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

    public void setRegisterCount(int count) { this.registerCount = count; }

    public int getRegisterCount() { return registerCount; }


    /**
     * When the 5th register is chosen, the timer will start and afterwards the play cards button will appear.
     */
    private void handle5thRegister() {
        Text.setText("Players have 30 seconds left to finish their register");
        PauseTransition thirty = new PauseTransition(Duration.seconds(30));
        thirty.setOnFinished(e -> playCardBtn.setVisible(true));
        thirty.play();
        PauseTransition textAppear2 = new PauseTransition(Duration.seconds(30));
        textAppear2.setOnFinished(e -> Text.setText("Click on 'PLAY REGISTER'"));
        textAppear2.play();
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 1 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand1ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand1.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else {
                    register1.setImage(hand1.getImage());
                    hand1Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[0],0, ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand1.getImage());
                hand1Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[0], 4,ID).toString());
                //after 30 seconds the play Cards button will appear
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 2 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand2ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand2.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else {
                    register1.setImage(hand2.getImage());
                    hand2Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[1],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand2.getImage());
                hand2Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[1], 4, ID).toString());
                //after 30 seconds the play Cards button will appear
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 3 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand3ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand3.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else {
                    register1.setImage(hand3.getImage());
                    hand3Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[2],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand3.getImage());
                hand3Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[2], 4, ID).toString());
                //after 30 seconds the play Cards button will appear
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 4 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand4ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand4.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else {
                    register1.setImage(hand4.getImage());
                    hand4Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[3],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand4.getImage());
                hand4Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[3],4,ID).toString());
                //after 30 seconds the play Cards button will appear
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 5 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand5ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand5.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else {
                    register1.setImage(hand5.getImage());
                    hand5Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[4],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand5.getImage());
                hand5Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[4],4,ID).toString());
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 6 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand6ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand6.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else {
                    register1.setImage(hand6.getImage());
                    hand6Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[5],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand6.getImage());
                hand6Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[5], 4, ID).toString());
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 7 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand7ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand7.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else{
                    register1.setImage(hand7.getImage());
                    hand7Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[6],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand7.getImage());
                hand7Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[6], 4, ID).toString());
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 8 is chosen.
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand8ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand8.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else{
                    register1.setImage(hand8.getImage());
                    hand8Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[7],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand8.getImage());
                hand8Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[7],4,ID).toString());
                handle5thRegister();
                break;
        }
    }

    /**
     * Pick your register of 5 cards from your hands of 9 cards: if hand 9 is chosen
     * If the first choosen Card is Again, an error message will be displayed for the user.
     */
    @FXML
    void hand9ButtonAction(ActionEvent event) {
        setRegisterCount(getRegisterCount() + 1);
        switch (getRegisterCount()) {
            case 1:
                if (hand9.getImage().equals(imageAgain)){
                    Text.setText("You can't play AGAIN as your first register!");
                    setRegisterCount(getRegisterCount() - 1);
                } else{
                    register1.setImage(hand9.getImage());
                    hand9Button.setVisible(false);
                    Client.getClientReceive().sendMessage(new SelectedCard(cards[8],0,ID).toString());
                }
                break;
            case 2:
                register2.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],1,ID).toString());
                break;
            case 3:
                register3.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],2,ID).toString());
                break;
            case 4:
                register4.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],3,ID).toString());
                break;
            case 5:
                register5.setImage(hand9.getImage());
                hand9Button.setVisible(false);
                Client.getClientReceive().sendMessage(new SelectedCard(cards[8],4,ID).toString());
                handle5thRegister();
                break;
        }
    }



    /**
     * This method is called when the timer of 30s is up.
     * The registers of the players who have not filled their register with 5 cards in time, will be filled
     * automatically from the remaining hand cards.
     */
    public void autoFillRegister() {
        for (int i = 1; i < 10; i++) {
            if (getRegisterCount() < 5) {
                Text.setText("Time is gone! Your register will be filled automatically!");
            } else {
            }
            if (getRegisterCount() <= 5) {
                switch (i) {
                    case 1:
                        try {
                            hand1Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 2:
                        try {
                            hand2Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 3:
                        try {
                            hand3Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 4:
                        try {
                            hand4Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 5:
                        try {
                            hand5Button.fire();
                        } catch (Exception e) {
                        }
                        break;

                    case 6:
                        try {
                            hand6Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 7:
                        try {
                            hand7Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 8:
                        try {
                            hand8Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                    case 9:
                        try {
                            hand9Button.fire();
                        } catch (Exception e) {
                        }
                        break;
                }
            } else {
                playCardBtn.setVisible(true);
            }
        }
    }

    /**
     * This methos is called by pressing the PRINT MAP  button
     * The choosen map is printed and the starting point selection appears.
     */
    public void printMapButtonAction(ActionEvent actionEvent) {
        String map = Client.getClientReceive().getBoard();
        printMapGUI(map);

        Text.setText("Select a starting point");
        //roboRallyImage.setVisible(false);
        selectStartingPoint.setVisible(true);
        printMapButton.setVisible(false);

        URL empty = getClass().getResource("/Empty.png");
        Image imageEmpty = new Image(empty.toString());

        ImageView emptyView = new ImageView(imageEmpty);
        emptyView.setFitWidth(43);
        emptyView.setFitHeight(43);

        robotBoard.add(emptyView, 0, 0);
        setOk(1);

    }

    /**
     * This method is called when the START GAME button is pressed
     * the GET CARDS button will be displayed for the user.
     */
    public void startGameButtonAction(ActionEvent actionEvent) {
        if (Client.getClientReceive().getIdList().size() > Client.getClientReceive().getStartNumbers().size()) {
            Text.setText("Wait for the other players to choose a starting point");
        }
        else
        {
            for (int id : Client.getClientReceive().getIdList()) {
                int robotNumber = Client.getClientReceive().getRobotById(id);
                int startingPointNumber = Client.getClientReceive().getStartPointById(id);
                Integer[] position = Client.getClientReceive().getPositionById(id);
                robotOldPosition.put(robotNumber, position);
                setOtherRobotOnBoard(robotNumber, startingPointNumber);
            }
            getCardsButton.setVisible(true);
            startGameButton.setVisible(false);
            Text.setText("Click on 'GET CARDS'");
        }
    }


    /**
     * The matching images are added to each board element.
     * Selected map gets printed on the GUI as well as the player characters
     */
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
            case "Twister":
                board = new Twister();
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
                 * green Conveyor belt for Death trap and Lost Bearings
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

                /**
                * URLs for Twister
                */
                URL blueRBDownRight = getClass().getResource("/RBTwister/blueRBDownRight.png");
                Image imageBlueRBDownRight = new Image(blueRBDownRight.toString());

                URL blueRBUpLeft = getClass().getResource("/RBTwister/blueRBUpLeft.png");
                Image imageBlueRBUpLeft = new Image(blueRBUpLeft.toString());



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
                                        if (board.getName().equals("ExtraCrispy")) {
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
                                        } else if (board.getName().equals("Twister")) {
                                            switch (direction1) {
                                                case RIGHT:
                                                    elmImage = imageBlueRotatingUpRight;
                                                    break;

                                                case LEFT:
                                                    elmImage = imageBlueRotatingDownLeft;
                                                    break;

                                                case DOWN:
                                                    elmImage = imageBlueRBDownRight;
                                                    break;

                                                case UP:
                                                    elmImage = imageBlueRBUpLeft;
                                                    break;
                                            }
                                        } else {
                                            switch (direction1) {
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
                                        }
                                        break;
                                    case 1:
                                        if (board.getName().equals("LostBearings")) {
                                            switch (direction1) {
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
                                                            elmImage = imageRBGreenDownLeft3;
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenDownLeft2;
                                                            break;
                                                    }
                                                    break;
                                            }
                                        } else if (board.getName().equals("DeathTrap")) {
                                            switch (direction1) {
                                                case RIGHT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageRBGreenUpRight2; //
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
                                                            elmImage = imageRBGreenUpLeft2;
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
                                        } else if (board.getName().equals("ExtraCrispy")) {
                                            switch (direction1) {
                                                case RIGHT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageRBGreenUpRight2;
                                                            break;
                                                        case DOWN:
                                                            elmImage = imageRBGreenDownLeft2;
                                                            break;
                                                    }
                                                    break;
                                                case LEFT:
                                                    switch (direction2) {
                                                        case UP:
                                                            elmImage = imageRBGreenUpLeft2;
                                                            break;
                                                        case DOWN:
                                                            elmImage = imageRBGreenDownLeft;
                                                            break;
                                                    }
                                                    break;
                                                case UP:
                                                    switch (direction2) {
                                                        case RIGHT:
                                                            elmImage = imageRBGreenUpRight;
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenUpLeft2;
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

                                        } else {
                                            switch (direction1) {
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
                                                            elmImage = imageRBGreenDownLeft3;
                                                            break;
                                                        case LEFT:
                                                            elmImage = imageRBGreenDownLeft2; //upper
                                                            break;
                                                    }
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

                        if(board.getName().equals("ExtraCrispy")){
                             gameboard.add(checkPointFour,5,2);
                             gameboard.add(checkPointOne,10,2);
                             gameboard.add(checkPointTwo,5,7);
                             gameboard.add(checkPointThree,10,7);
                        }

                        if(board.getName().equals("Twister")){
                            checkBoard.add(checkPointFour,9,7);
                            checkBoard.add(checkPointOne,10,1);//
                            checkBoard.add(checkPointThree,5,3);//
                            checkBoard.add(checkPointTwo,6,7);
                        }
                    }

                }

            }


    int startingPointCount;
    int x;
    int y;

    /**
     * help counter for starting point selection
     */
    public void setStartingPointCount(int count){
        this.startingPointCount = count;
    }

    public int getStartingPointCount(){
        return startingPointCount;
    }

    /**
     * This method is used to move the checkpoints on Conveyorbelt in Twister Map
     */
    public void moveCheckpointTwister(int moveNumber){

        URL checkPoint1 = getClass().getResource("/Checkpoints/Checkpoint1.png");
        Image imageCheckPoint1 = new Image(checkPoint1.toString());

        URL checkPoint2 = getClass().getResource("/Checkpoints/Checkpoint2.png");
        Image imageCheckPoint2 = new Image(checkPoint2.toString());

        URL checkPoint3 = getClass().getResource("/Checkpoints/Checkpoint3.png");
        Image imageCheckPoint3 = new Image(checkPoint3.toString());

        URL checkPoint4 = getClass().getResource("/Checkpoints/Checkpoint4.png");
        Image imageCheckPoint4 = new Image(checkPoint4.toString());

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


        switch(moveNumber){
            case 1:
                checkBoard.getChildren().clear();

                checkBoard.add(checkPointOne,11,2);
                checkBoard.add(checkPointTwo,5,8);
                checkBoard.add(checkPointThree,4,2);
                checkBoard.add(checkPointFour,10,6);
                break;
            case 2:
                checkBoard.getChildren().clear();

                checkBoard.add(checkPointOne,10,3);
                checkBoard.add(checkPointTwo,4,7);
                checkBoard.add(checkPointThree,5,1);
                checkBoard.add(checkPointFour,11,7);
                break;
            case 3:
                checkBoard.getChildren().clear();

                checkBoard.add(checkPointOne,9,2);
                checkBoard.add(checkPointTwo,5,6);
                checkBoard.add(checkPointThree,6,2);
                checkBoard.add(checkPointFour,10,8);
                break;
            case 4: //like beginning
                checkBoard.getChildren().clear();

                checkBoard.add(checkPointOne,10,1);
                checkBoard.add(checkPointTwo,6,7);
                checkBoard.add(checkPointThree,5,3);
                checkBoard.add(checkPointFour,9,7);
                break;
        }
    }


    /**
     * This method is used to display your choosen robot on the starting point.
     * @return imageView of your own robot
     */
    public ImageView checkYourbotImageView(){
        int yourId=Client.getClientReceive().getClientID();
        int yourRobotNumber=Client.getClientReceive().getRobotById(yourId);
        Image robotSelectStartPoint = null;
        switch (yourRobotNumber){
            case 1:
                robotSelectStartPoint=imageHulk;//hulk
                break;
            case 2:
                robotSelectStartPoint=imageSpin;//spin
                break;
            case 3:
                robotSelectStartPoint=imageSquash;//squash
                break;
            case 4:
                robotSelectStartPoint=imageHammer;//hammer
                break;
            case 5:
                robotSelectStartPoint=imageTwonkey;//twonkey
                break;
            case 6:
                robotSelectStartPoint=imageTwitch;//twitch
                break;
        }
        ImageView robotSelectStartPointView = new ImageView(robotSelectStartPoint);
        robotSelectStartPointView.setFitWidth(43);
        robotSelectStartPointView.setFitHeight(43);
        return robotSelectStartPointView;
    }


    /**
     * When clicking on starting point button 1, user will see where the robot would be on the map
     */
    public void startingPoint1Action(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")){
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 11,1);
            checkStart();
            setStartingPointCount(1);
        } else {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 1, 1);
            checkStart();
            setStartingPointCount(1);
        }
        startPointOK.setVisible(true);
    }

    /**
     * When clicking on starting point button 2, user will see where the robot would be on the map
     */
    public void startingPoint2Action(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")) {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 12, 3);
            checkStart();
            setStartingPointCount(2);
        } else {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 0, 3);
            checkStart();
            setStartingPointCount(2);
        }
        startPointOK.setVisible(true);
    }

    /**
     * When clicking on starting point button 3, user will see where the robot would be on the map
     */
    public void startingPoint3Action(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")) {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 11, 4);
            checkStart();
            setStartingPointCount(3);
        } else {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 1, 4);
            checkStart();
            setStartingPointCount(3);
        }
        startPointOK.setVisible(true);
    }

    /**
     * When clicking on starting point button 4, user will see where the robot would be on the map
     */
    public void startingPoint4Action(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")) {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 11, 5);
            checkStart();
            setStartingPointCount(4);
        } else {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 1, 5);
            checkStart();
            setStartingPointCount(4);
        }
        startPointOK.setVisible(true);
    }

    /**
     * When clicking on starting point button 5, user will see where the robot would be on the map
     */
    public void startingPoint5Action(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")) {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 12, 6);
            checkStart();
            setStartingPointCount(5);
        } else {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 0, 6);
            checkStart();
            setStartingPointCount(5);
        }
        startPointOK.setVisible(true);
    }

    /**
     * When clicking on starting point button 6, user will see where the robot would be on the map
     */
    public void startingPoint6Action(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")) {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 11, 8);
            checkStart();
            setStartingPointCount(6);
        } else {
            robotBoard.getChildren().remove(1);
            robotBoard.add(checkYourbotImageView(), 1, 8);
            checkStart();
            setStartingPointCount(6);
        }
        startPointOK.setVisible(true);
    }


    /**
     *  OK Button -> starting point will be selected and robot will be set on the map
     */
    public void startPointOKAction(ActionEvent actionEvent) {
        robotBoard.getChildren().remove(1);
        String selectedMap = Client.getClientReceive().getBoard();
           switch (getStartingPointCount()){
               case 1:
                   if (selectedMap.equals("DeathTrap")) {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(1,11).toString());
                   } else {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(1,1).toString());
                   }
                   setRobotOnBoard(1);
                   break;
               case 2:
                   if (selectedMap.equals("DeathTrap")) {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(3,12).toString());
                   } else {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(3,0).toString());
                   }
                   setRobotOnBoard(2);
                    break;
               case 3:
                   if (selectedMap.equals("DeathTrap")) {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(4,11).toString());
                   } else {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(4,1).toString());
                   }
                   setRobotOnBoard(3);
                   break;
               case 4:
                   if (selectedMap.equals("DeathTrap")) {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(5,11).toString());
                   } else {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(5,1).toString());
                   }
                   setRobotOnBoard(4);
                   break;
               case 5:
                   if (selectedMap.equals("DeathTrap")) {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(6,12).toString());
                   } else {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(6,0).toString());
                   }
                   setRobotOnBoard(5);
                   break;
               case 6:
                   if (selectedMap.equals("DeathTrap")) {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(8,11).toString());
                   } else {
                       Client.getClientReceive().sendMessage(new SetStartingPoint(8,1).toString());
                   }
                   setRobotOnBoard(6);
                   break;
           }
        startGameButton.setVisible(true);
        selectStartingPoint.setVisible(false);
        Text.setText("Click on 'START GAME'");
    }


    /**
     * This method is used to show the robot movement on the map.
     */
    public void moveRobot() {
        robotBoard.getChildren().clear();
        startBoard.getChildren().clear();
        for (int clientId : Client.getClientReceive().getIdPosition().keySet()) {
            int moveX = Client.getClientReceive().getPositionById(clientId)[0];
            int moveY = Client.getClientReceive().getPositionById(clientId)[1];
            int robotNumber=Client.getClientReceive().getRobotById(clientId);
           // int oldX=robotOldPosition.get(robotNumber)[0];
           // int oldY=robotOldPosition.get(robotNumber)[1];
            ImageView botView=robotOgPicture.get(robotNumber);
            if(Client.getClientReceive().getIdDirection()!=null &&
                    Client.getClientReceive().getIdDirection().containsKey(clientId)) {
                rotateRobot(botView,clientId);
                ImageView finalBotView = botView;
                robotOgPicture.put(clientId,finalBotView);
                robotBoard.add(finalBotView, moveX, moveY);
            }
            else {
                // remove.setOnFinished(e -> robotBoard.add(emptyView,oldX,oldY));
                robotBoard.getChildren().remove(botView);
                //PauseTransition move1 = new PauseTransition(Duration.seconds(1));
                ImageView finalBotView = botView;
                robotBoard.add(finalBotView, moveX, moveY);
                //move1.play();
            }
        }
        Client.getClientReceive().setIdDirection(new HashMap<>());
        if(Client.getClientReceive().isPickDamage()){
            showDamage.setVisible(true);
            Text.setText("Pick Damage Cards");
            //TODO: set a text to remind player pickDamageCard
        }

        if(Client.getClientReceive().isGameEnded()){
            if (Client.getClientReceive().getClientID()==Client.getClientReceive().getWinnerID()){
                openWinnerWindow();
            }
            else {
                openLoserWindow();
            }
        }
        printRobotLaser();
    }


    /**
     * disable starting point button when selected by another player
     */
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


    /**
     * This method sets your robot on its starting point on the GUI
     */
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
        String selectedMap = Client.getClientReceive().getBoard();
        if (selectedMap.equals("DeathTrap")){
            robotPic.setRotate(270);
        } else {
            robotPic.setRotate(90);
        }
        switch (startingPointNumber){
            case 1:
                if (selectedMap.equals("DeathTrap")) {
                    robotBoard.add(robotPic,11,1);
                } else {
                    robotBoard.add(robotPic,1,1);
                }
                break;
            case 2:
                if (selectedMap.equals("DeathTrap")) {
                    robotBoard.add(robotPic,12,3);
                } else {
                    robotBoard.add(robotPic,0,3);
                }
                break;
            case 3:
                if (selectedMap.equals("DeathTrap")) {
                    robotBoard.add(robotPic,11,4);
                } else {
                    robotBoard.add(robotPic,1,4);
                }
                break;
            case 4:
                if (selectedMap.equals("DeathTrap")) {
                    robotBoard.add(robotPic,11,5);
                } else {
                    robotBoard.add(robotPic,1,5);
                }
                break;
            case 5:
                if (selectedMap.equals("DeathTrap")) {
                    robotBoard.add(robotPic,12,6);
                } else {
                    robotBoard.add(robotPic,0,6);
                }
                break;
            case 6:
                if (selectedMap.equals("DeathTrap")) {
                    robotBoard.add(robotPic,11,8);
                } else {
                    robotBoard.add(robotPic,1,8);
                }
                break;
        }
    }


    /**
     * Set other robots on their starting point on the GUI
     */
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
                String selectedMap = Client.getClientReceive().getBoard();
                if (selectedMap.equals("DeathTrap")){
                    robotPic.setRotate(270);
                } else {
                    robotPic.setRotate(90);
                }
                robotOgPicture.put(otherRobotNumber,robotPic);
                switch (startingPointNumber) {
                    case 1:
                        if (selectedMap.equals("DeathTrap")) {
                            robotBoard.add(robotPic,11,1);
                        } else {
                            robotBoard.add(robotPic,1,1);
                        }
                        break;
                    case 2:
                        if (selectedMap.equals("DeathTrap")) {
                            robotBoard.add(robotPic,12,3);
                        } else {
                            robotBoard.add(robotPic,0,3);
                        }
                        break;
                    case 3:
                        if (selectedMap.equals("DeathTrap")) {
                            robotBoard.add(robotPic,11,4);
                        } else {
                            robotBoard.add(robotPic,1,4);
                        }
                        break;
                    case 4:
                        if (selectedMap.equals("DeathTrap")) {
                            robotBoard.add(robotPic,11,5);
                        } else {
                            robotBoard.add(robotPic,1,5);
                        }
                        break;
                    case 5:
                        if (selectedMap.equals("DeathTrap")) {
                            robotBoard.add(robotPic,12,6);
                        } else {
                            robotBoard.add(robotPic,0,6);
                        }
                        break;
                    case 6:
                        if (selectedMap.equals("DeathTrap")) {
                            robotBoard.add(robotPic,11,8);
                        } else {
                            robotBoard.add(robotPic,1,8);
                        }
                        break;
                }
    }




    /**
     * counter helper
     */
    int clickCounter = 1;

    public void setClickCounter(int count){
        this.clickCounter = count;
    }

    public int getClickCounter(){
        return clickCounter;
    }

    /**
     * function of the play register button/ new round button
     */
    public void playCardBtnAction(ActionEvent actionEvent) {
         playCardBtn.setText("PLAY REGISTER 1");
         Client.getLogger().info("Send out message: ");

         switch(getClickCounter()){
             case 1:
                 setClickCounter(getClickCounter() + 1);
                 Client.getClientReceive().sendMessage(new PlayCard("PlayedCard").toString());
                 phaseView.setImage(imageActivation);
                 playActivationPhaseBegins();
                 register1.setImage(register2.getImage());
                 register2.setImage(register3.getImage());
                 register3.setImage(register4.getImage());
                 register4.setImage(register5.getImage());
                 register5.setImage(null);
                 playCardBtn.setText("PLAY REGISTER 2");
                 //remove remaining cards from hand
                 hand1Button.setVisible(false);
                 hand2Button.setVisible(false);
                 hand3Button.setVisible(false);
                 hand4Button.setVisible(false);
                 hand5Button.setVisible(false);
                 hand6Button.setVisible(false);
                 hand7Button.setVisible(false);
                 hand8Button.setVisible(false);
                 hand9Button.setVisible(false);
                 moveRobotButton.setVisible(true);
                 playCardBtn.setVisible(false);
                 break;
             case 2:
                 setClickCounter(getClickCounter() + 1);
                 Client.getClientReceive().sendMessage(new PlayCard("PlayedCard").toString());
                 register1.setImage(register2.getImage());
                 register2.setImage(register3.getImage());
                 register3.setImage(register4.getImage());
                 register4.setImage(null);
                 playCardBtn.setText("PLAY REGISTER 3");
                 moveRobotButton.setVisible(true);
                 playCardBtn.setVisible(false);
                 break;
             case 3:
                 setClickCounter(getClickCounter() + 1);
                 Client.getClientReceive().sendMessage(new PlayCard("PlayedCard").toString());
                 register1.setImage(register2.getImage());
                 register2.setImage(register3.getImage());
                 register3.setImage(null);
                 playCardBtn.setText("PLAY REGISTER 4");
                 moveRobotButton.setVisible(true);
                 playCardBtn.setVisible(false);
                 break;
             case 4:
                 setClickCounter(getClickCounter() + 1);
                 Client.getClientReceive().sendMessage(new PlayCard("PlayedCard").toString());
                 register1.setImage(register2.getImage());
                 register2.setImage(null);
                 playCardBtn.setText("PLAY REGISTER 5");
                 moveRobotButton.setVisible(true);
                 playCardBtn.setVisible(false);
                 break;
             case 5:
                 setClickCounter(getClickCounter() + 1);
                 Client.getClientReceive().sendMessage(new PlayCard("PlayedCard").toString());
                 register1.setImage(null);
                 playCardBtn.setText("NEXT ROUND!");
                 moveRobotButton.setVisible(true);
                 playCardBtn.setVisible(false);
                 break;
             case 6:
                 phaseView.setImage(imageProgramming);
                 //set back counters for next round
                 setClickCounter(1);
                 setRegisterCount(0);
                 playCardBtn.setVisible(false);
                 printCards();
                 break;
         }
    }

    /**
     * Show 9 hand cards to choose from
     */
    public void getCardsButtonAction(ActionEvent actionEvent) {
        getCardsButton.setVisible(false);
        phaseView.setVisible(true);

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
    }


    /**
     * if only one player is left in the game he automatically wins and the winner window opens
     */
    public void checkAmountPlayer(){
        if(playerBot1.getImage() == null && playerBot2.getImage() == null && playerBot3.getImage() == null && playerBot4.getImage() == null && playerBot5.getImage() == null && playerBot6.getImage() == null && ok == 1) {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/DefaultWinner.fxml"));
                Parent rootMap = (Parent) fxmlLoader.load();
                Stage stageLobby = new Stage();
                stageLobby.setTitle("Winner");
                stageLobby.setScene(new Scene(rootMap));
                stageLobby.show();
            } catch (Exception e) {
                Client.getLogger().severe( "Window can not open.");
            }
        }

        else{
        }
    }


    /**
     * Mouse event for updating timer
     */
    public void MouseAction(MouseEvent mouseEvent) {
        //timer only appears for the players that need to finish their register
        if(getRegisterCount() < 5){
            timer30Sec();
        } else {
        }
        try{
            checkStart();
        }
        catch (Exception e){
        }
        try{
            setYourBotIcon();
        }
        catch (Exception e){
        }
        try {
            setOthersBotIcon();
        }
        catch (Exception e){
        }
        try {
            checkAmountPlayer();
        }
        catch (Exception e){
        }
    }

    /**
     * rotation movement of the robot
     */
    public void rotateRobot(ImageView robotPic,int id) {
            switch (Client.getClientReceive().getIdDirection().get(id)) {
                case "counterclockwise":
                    robotPic.setRotate(robotPic.getRotate()-90);
                    break;
                case "clockwise":
                    robotPic.setRotate(robotPic.getRotate()+90);
                    break;
                case "turn 180":
                    robotPic.setRotate(robotPic.getRotate()+180);
                    break;
                default:
                    break;
            }
    }

    /**
     * sound effect for beginning of Programming Phase
     */
    public void playProgrammingPhaseBegins() {
        try {
            String fileName = getClass().getResource("/sounds/ProgrammingPhase.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
    }


    /**
     * sound effect for beginning of Activation Phase
     */
    public void playActivationPhaseBegins() {
        try {
            String fileName = getClass().getResource("/sounds/ActivationPhase.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
    }

    /**
     * timer 30 seconds when first player finished selecting register
     */
    public void timer30Sec() {
        if (Client.getClientReceive().isTimerStarted()) {
            timerText.setVisible(true);
            Client.getClientReceive().setTimer(false);
            Text.setText("Players have 30 seconds left to finish their register");
            timer.setText("30");

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

            PauseTransition timegone = new PauseTransition(Duration.seconds(31));
            timegone.setOnFinished(e -> timerText.setText(""));
            timegone.play();

            PauseTransition timegone2 = new PauseTransition(Duration.seconds(31));
            timegone2.setOnFinished(e -> timer.setText(""));
            timegone2.play();

            PauseTransition timegone4 = new PauseTransition(Duration.seconds(31));
            timegone4.setOnFinished(e -> autoFillRegister());
            timegone4.play();

            PauseTransition textAppear2 = new PauseTransition(Duration.seconds(33));
            textAppear2.setOnFinished(e -> Text.setText("Click on 'PLAY REGISTER'"));
            textAppear2.play();
        } else {

        }
    }


    int checkpointMoveCounter = 1;

    public void setcheckpointMoveCounter(int count){
        this.checkpointMoveCounter = count;
    }


    /**
     * show movement of all robots and board elements
     */
    public void moveRobotButtonAction(ActionEvent actionEvent) {
        String selectedMap = Client.getClientReceive().getBoard();
        if(selectedMap.equals("Twister")){
            switch (checkpointMoveCounter) {
                case 1:
                    setcheckpointMoveCounter(2);
                    moveCheckpointTwister(1);
                    break;
                case 2:
                    setcheckpointMoveCounter(3);
                    moveCheckpointTwister(2);
                    break;
                case 3:
                    setcheckpointMoveCounter(4);
                    moveCheckpointTwister(3);
                    break;
                case 4:
                    setcheckpointMoveCounter(1);
                    moveCheckpointTwister(4);
                    break;
            }
        } else {
        }
        moveRobotButton.setVisible(false);
        playCardBtn.setVisible(true);
        moveRobot();
    }


    /**
     * if one robot faces another it will shoot robot laser towards the other one
     */
  public void printRobotLaser() {
      URL robotLaser = getClass().getResource("/robotLaser.png");
      Image imageRobotLaser = new Image(robotLaser.toString());
      URL robotLaserVertical = getClass().getResource("/robotLaserVertical.png");
      Image imageRobotLaserVertical = new Image(robotLaserVertical.toString());
      List<Integer> xList = new ArrayList<Integer>();
      List<Integer> yList = new ArrayList<Integer>();
      List<int[]> result = new ArrayList<int[]>();
      if (Client.getClientReceive().isDoRobotLaser()) {
          for (int clientId : Client.getClientReceive().getIdPosition().keySet()) {
              int robotX = Client.getClientReceive().getPositionById(clientId)[0];
              xList.add(robotX);
              int robotY = Client.getClientReceive().getPositionById(clientId)[1];
              yList.add(robotY);
          }

          for (int index = 0; index < xList.size(); index++) {
              int xPointer = xList.get(index);
              int yPointer = yList.get(index);
              for (int i = index + 1; i < xList.size(); i++) {
                  int tempX = xList.get(i);
                  int tempY = yList.get(i);
                  if (xPointer == tempX || yPointer == tempY) {
                      result.add(new int[]{xPointer,yPointer});
                      result.add(new int[]{tempX, tempY});
                      xList.remove((Integer) tempX);
                      yList.remove((Integer) tempY);
                  }
              }
          }
          int printX = result.get(0)[0];
          int printY = result.get(0)[1];
          int printX2 = result.get(1)[0];
          int printY2 = result.get(1)[1];
          if (printX == printX2) {
              if (printY < printY2) {
                  for (int i = printY+1; i < printY2; i++) {
                      ImageView laserVertical = new ImageView(imageRobotLaserVertical);
                      startBoard.add(laserVertical,printX,i);
                      playLaserSound();
                  }
              }
                  if (printY > printY2) {
                      for (int i = printY2+1; i < printY; i++) {
                          ImageView laserVertical = new ImageView(imageRobotLaserVertical);
                          startBoard.add(laserVertical,printX,i);
                          playLaserSound();
                      }
                  }
          }
          else {
              if (printX < printX2) {
                  for (int i = printX+1; i < printX2; i++) {
                      ImageView laser = new ImageView(imageRobotLaser);
                      startBoard.add(laser, i, printY);
                      playLaserSound();
                  }
              }
                  if (printX > printX2) {
                      for (int i = printX2+1; i < printX; i++) {
                          ImageView laser = new ImageView(imageRobotLaser);
                          startBoard.add(laser, i, printY);
                          playLaserSound();
                      }
                  }
              }

          Client.getClientReceive().setDoRobotLaser(false);
      }
  }
  int clickDamageCounter=1;
    @FXML
    private Button registerDamage;
    @FXML
    private Button spam;
    @FXML
    private Button worm;
    @FXML
    private Button virus;
    @FXML
    private Button trojan;


    /**
     * if player chooses Spam card
     */
    public void  playSpamCards(ActionEvent event){
        if(clickDamageCounter<=Client.getClientReceive().getDamageCount()) {
            tempDrawDamage.add("Spam");
            clickCounter++;
        }
        else {
            registerDamage.setVisible(true);
        }
    }

    /**
     * if player chooses Virus card
     */
    public void  playVirusCards(ActionEvent event){
        if(clickCounter<=Client.getClientReceive().getDamageCount()){
            tempDrawDamage.add("Virus");
            clickCounter++;
        }
        else {
            registerDamage.setVisible(true);
        }
    }

    /**
     * if player chooses Worm card
     */
    public void  playWormCards(ActionEvent event){
        if(clickCounter<=Client.getClientReceive().getDamageCount()){
            tempDrawDamage.add("Worm");
            clickCounter++;
        }
        else {
            registerDamage.setVisible(true);
        }
    }

    /**
     * if player chooses Trojan Horse card
     */
    public void  playTrojanCards(ActionEvent event){
        if(clickCounter<=Client.getClientReceive().getDamageCount()){
            tempDrawDamage.add("Trojan");
            clickCounter++;
        }
        else {
            registerDamage.setVisible(true);
        }
    }

    /**
     * when shot by laser, damage Action is activated
     */
    public void registerDamageAction(ActionEvent event) {
        String[] selectedDamageCards = tempDrawDamage.toArray(new String[tempDrawDamage.size()]);
        Client.getClientReceive().sendMessage(new SelectedDamage(selectedDamageCards).toString());
        tempDrawDamage = new ArrayList<>();
        clickCounter = 1;
        spam.setVisible(false);
        worm.setVisible(false);
        trojan.setVisible(false);
        virus.setVisible(false);
        Client.getClientReceive().setPickDamage(false);
        showDamage.setVisible(false);
        registerDamage.setVisible(false);
    }

    /**
     * damage cards will appear on game window
     */
    public void showDamageCards(ActionEvent event) {

        for(String damageCard: Client.getClientReceive().getDamageDecks()){
            switch (damageCard){
                case "Spam":
                    spam.setVisible(true);
                    break;
                case "Worm":
                    worm.setVisible(true);
                    break;
                case "Trojan":
                    trojan.setVisible(true);
                    break;
                case"Virus":
                    virus.setVisible(true);
                    break;
            }
        }
    }

    /**
     * open winner window to player who wins
     */
    public void openWinnerWindow(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Winner.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stageLobby = new Stage();
            stageLobby.setTitle("Winner");
            stageLobby.setScene(new Scene(rootMap));
            stageLobby.show();
        } catch (Exception e) {
            Client.getLogger().severe( "Window can not open.");
        }
    }

    /**
     * open loser window for player who loses
     */
    public void openLoserWindow(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Loser.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stageLobby = new Stage();
            stageLobby.setTitle("Loser");
            stageLobby.setScene(new Scene(rootMap));
            stageLobby.show();
        } catch (Exception e) {
            Client.getLogger().severe( "Window can not open.");
        }
    }



    /**
     * sound effect for robot Laser
     */
    public void playLaserSound() {
        try {
            String fileName = getClass().getResource("/sounds/laser.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        sound.getMediaPlayer().play();
    }

    int ok = 0;

    public void setOk(int ok){
        this.ok = ok;
    }
}
