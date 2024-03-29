package client.lobbyWindow;

import client.loginWindow.LoginViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import client.Client;

/**
 * Main Class to start the application
 *
 * @author Nargess Ahmadi, Felicia Saruba, Minghao Li, Nassrin Djafari
 */

public class MainApplication extends Application {



    private static final boolean CREATE_VIEW_FROM_FXML = true;

    public static Stage window;
    public static Scene lobbyScene;
    public static Scene loginScene;

    public static Scene gameScene;


    @Override
    public void stop() {
    }

    /**
     * init a new client and select Login to show up
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        new Client().init();
        window = primaryStage;
        window.setTitle("RoboRally");
        window.setOnCloseRequest(e-> {
            e.consume();
            LoginViewModel.closeGUI();

        });
        lobbyScene = loadScene(0);
        loginScene = loadScene(1);
        window.setScene(loginScene);
        window.show();
    }

    /**
     * get the according scene
     */
    public static Scene getScene(int scene_number) {
        switch(scene_number) {
            case 0:
                return lobbyScene;
            case 1:
                return loginScene;
        }
        return null;
    }

    /**
     * load the appropriate window
     */
    public Scene loadScene(int scene_number) throws IOException {
        Parent root = null;
        if (CREATE_VIEW_FROM_FXML) {
            switch(scene_number) {
                case 0:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Lobby.fxml")));
                    break;
                case 1:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Login.fxml")));
                    break;
            }
        } else {
            root = new LobbyView();
        }
        return new Scene(root);
    }
    public static void main(String[] args) {
        launch(args);
    }
}