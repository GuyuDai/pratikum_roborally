package client.mainWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuModel {

    private static volatile MenuModel instance;

    private MenuModel(){
    }

    public static MenuModel getInstance() {
        if (instance == null) {
            synchronized (MenuModel.class) {
                if (instance == null) {
                    instance = new MenuModel();
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