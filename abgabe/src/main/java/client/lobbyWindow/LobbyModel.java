package client.lobbyWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

/**
 * Holds the data of the application and manages all the logic for the lobby window.
 *
 * @author Nargess Ahmadi, Felicia Saruba, Minghao Li
 */

public class LobbyModel {

    private static volatile LobbyModel instance;

    private static Pane selectRobot;

    private LobbyModel(){
    }


    /**
     * Model for the MVVM of Lobby
     */
    public static LobbyModel getInstance() {
        if (instance == null) {
            synchronized (LobbyModel.class) {
                if (instance == null) {
                    instance = new LobbyModel();
                }
            }
        }
        return instance;
    }

    private final ObservableList<String> listContent = FXCollections.observableArrayList();

    /**
     * Holds the list of strings we want to show on the screen.
     */
    public ObservableList<String> getListContentProperty() {
        return listContent;
    }

    /**
     * This property holds the user's current input.
     */
    private final StringProperty textFieldContent = new SimpleStringProperty("");

    public StringProperty getTextFieldContent(){
        return textFieldContent;
    }

    /**
     * Adds a string as a new item to the list.
     * @param listItem the string to add
     */
    public void addNewListItem(String listItem) {
        listContent.add(listItem);
    }

}