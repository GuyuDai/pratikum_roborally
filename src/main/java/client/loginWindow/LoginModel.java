package client.loginWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Holds the data of the application and manages all the logic for the login window.
 * @author Nargess Ahmadi, Felicia Saruba, Minghao Li
 */

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

    /**
     * This property holds the user's current input.
     */
    private final StringProperty textFieldContent = new SimpleStringProperty("");
    private final String textContent = "";

    public StringProperty getTextFieldContent(){
        return textFieldContent;
    }
    public String getTextContent(){
        return textContent;
    }
}