package client.mainWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainModel {

    private static volatile MainModel instance;

    private MainModel(){
    }

    public static MainModel getInstance() {
        if (instance == null) {
            synchronized (MainModel.class) {
                if (instance == null) {
                    instance = new MainModel();
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