package client.lobbyWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

/**
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

    public ObservableList<String> getListContentProperty() {
        return listContent;
    }

    private final StringProperty textFieldContent = new SimpleStringProperty("");

    public StringProperty getTextFieldContent(){
        return textFieldContent;
    }

    /**
     * add new message to the chat which is a ListView
     */
    public void addNewListItem(String listItem) {
        listContent.add(listItem);
    }

}