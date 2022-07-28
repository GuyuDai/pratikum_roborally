package client.gameWindow;


import client.lobbyWindow.LobbyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;


/**
 * Holds the data of the application and manages all the logic for the game window.
 *
 * @author Nargess Ahmadi, Felicia Saruba, Minghao Li, Nassrin Djafari
 */

public class GameModel {

    private static volatile GameModel instance;

    private static AnchorPane gameContainer;

    private GameModel(){
    }

    /**
     * Model for the MVVM of Game
     */
    public static GameModel getInstance() {
        if (instance == null) {
            synchronized (GameModel.class) {
                if (instance == null) {
                    instance = new GameModel();
                }
            }
        }
        return instance;
    }

    /**
     * Holds the list of strings we want to show on the screen.
     */
    private final ObservableList<String> listContent = FXCollections.observableArrayList();

    public ObservableList<String> getListContentProperty() { return listContent; }

    /**
     * This property holds the user's current input.
     */
    private final StringProperty textFieldContent = new SimpleStringProperty("");

    public StringProperty getTextFieldContent(){
        return textFieldContent; }


    /**
     * Adds a string as a new item to the list.
     * @param listItem the string to add
     */
    public void addNewListItem(String listItem) {
        listContent.add(listItem);
    }
}