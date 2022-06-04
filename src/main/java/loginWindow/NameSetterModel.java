package loginWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NameSetterModel {

    private static volatile NameSetterModel instance;

    private NameSetterModel(){
    }

    public static NameSetterModel getInstance() {
        if (instance == null) {
            synchronized (NameSetterModel.class) {
                if (instance == null) {
                    instance = new NameSetterModel();
                }
            }
        }
        return instance;
    }

    private final StringProperty textFieldContent = new SimpleStringProperty("");
    private final String textContent = "";

    public StringProperty getTextFieldContent(){
        return textFieldContent;
    }
    public String getTextContent(){
        return textContent;
    }
}