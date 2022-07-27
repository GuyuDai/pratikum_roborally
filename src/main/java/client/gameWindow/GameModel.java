package client.gameWindow;


import client.lobbyWindow.LobbyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;


/**
 * @author Nargess Ahmadi, Felicia Saruba, Minghao Li, Nassrin Djafari
 */

public class GameModel {

    private static volatile GameModel instance;

    private static AnchorPane gameContainer;

    private GameModel(){
    }

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

    private final ObservableList<String> listContent = FXCollections.observableArrayList();

    public ObservableList<String> getListContentProperty() { return listContent; }


    private final StringProperty textFieldContent = new SimpleStringProperty("");

    public StringProperty getTextFieldContent(){
        return textFieldContent; }

    public void addNewListItem(String listItem) {
        listContent.add(listItem);
    }
}