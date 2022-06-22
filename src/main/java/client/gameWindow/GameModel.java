package client.gameWindow;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;



/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
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

    public StringProperty getTextFieldContent(){ return textFieldContent; }

    public void addNewListItem(String listItem) {
        listContent.add(listItem);
    }

    /*
   private ObservableList<Image> handObservableList;
     */

}
