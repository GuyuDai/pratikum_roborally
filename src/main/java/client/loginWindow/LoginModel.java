package client.loginWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {

    private static volatile LoginModel instance;

    private LoginModel(){
    }

    public static LoginModel getInstance() {
        if (instance == null) {
            synchronized (LoginModel.class) {
                if (instance == null) {
                    instance = new LoginModel();
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