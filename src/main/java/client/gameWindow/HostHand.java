package client.gameWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import server.CardTypes.Card;
import server.Player.Player;
import server.Deck.ProgrammingDeck;

import java.io.File;

/**
 * author NargessAhmadi
 */

public class HostHand extends GameViewModel{

    //public HostHand(){
    //  new Player();
    //setHand();
    //}


    @Override
    public void initialize() {
        super.initialize();
        new Player("AI");
        setHand();
    }

    private ObservableList<Image> handObservableList;


    /**
     * get the programming cards and
     * add the images to the handObservableList
     * @param card Card of the player
     */
    public void getHandImage(Card card){

        switch (card.getCardName()){
            case "MoveOne":
                handObservableList.add(getImage("move1"));
                break;
            case "MoveTwo":
                handObservableList.add(getImage("move2"));
                break;
            case "MoveThree":
                handObservableList.add(getImage("move3"));
                break;
            case "Again":
                handObservableList.add(getImage("again"));
                break;
            case "BackUp":
                handObservableList.add(getImage("backUp"));
                break;
            case "PowerUp":
                handObservableList.add(getImage("powerUp"));
                break;
            case "TurnLeft":
                handObservableList.add(getImage("TurnLeft"));
                break;
            case "TurnRight":
                handObservableList.add(getImage("TurnRight"));
                break;
            case "uTurn":
                handObservableList.add(getImage("uTurn"));
                break;
            default:
                handObservableList.add(getImage("cardHidden"));
                break;
        }
    }


    /**
     * Helper method to get the right path
     * @param cardString
     * @return Image of card
     */
    public Image getImage(String cardString){
        return new Image(new File(new File("").getAbsolutePath() + "src/main/java/resources/programmingCards" + cardString + ".png").toURI().toString());
    }


    /**
     * Helper method to clear the displayed cards
     */
    public void clearHand(){
        handObservableList.clear();
    }

    /**
     * gets the current hand and adds it to the observableList
     */
    public void setHand(){
        clearHand();
        for(int i = 0; i<9; i++){
            //getHandImage(Player.getHands().get(i));
        }
        updateHand();
    }

    /**
     * updates all imageviews accordingly
     */
    public void updateHand(){
        hand1.setImage(handObservableList.get(0));
        hand2.setImage(handObservableList.get(1));
        hand3.setImage(handObservableList.get(2));
        hand4.setImage(handObservableList.get(3));
        hand5.setImage(handObservableList.get(4));
        hand6.setImage(handObservableList.get(5));
        hand7.setImage(handObservableList.get(6));
        hand8.setImage(handObservableList.get(7));
        hand9.setImage(handObservableList.get(8));
    }


    @FXML
    HBox hboxHand;
    @FXML
    ImageView hand1;
    @FXML
    ImageView hand2;
    @FXML
    ImageView hand3;
    @FXML
    ImageView hand4;
    @FXML
    ImageView hand5;
    @FXML
    ImageView hand6;
    @FXML
    ImageView hand7;
    @FXML
    ImageView hand8;
    @FXML
    ImageView hand9;
    @FXML
    HBox hboxDiscard;

}
