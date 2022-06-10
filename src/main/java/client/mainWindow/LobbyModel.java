package client.mainWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
 */

public class LobbyModel {

    private static volatile LobbyModel instance;

    private LobbyModel(){
    }

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

    public void addNewListItem(String listItem) {
        listContent.add(listItem);
    }

}