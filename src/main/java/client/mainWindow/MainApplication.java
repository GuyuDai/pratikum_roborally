package client.mainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import client.Client;

/**
 * @author Nargess Ahmadi, Nassrin Djafari, Felicia Saruba
 *
 */

public class MainApplication extends Application {

    private static final boolean CREATE_VIEW_FROM_FXML = true;

    public static Stage window;
    public static Scene lobbyScene;
    public static Scene loginScene;
    //public static Scene gameScene;


    @Override
    public void stop() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        new Client().init();
        window = primaryStage;
        window.setTitle("RoboRally");
        lobbyScene = loadScene(0);
        loginScene = loadScene(1);
        //gameScene = loadScene(2);
        window.setScene(loginScene);
        window.show();
    }

    public static Scene getScene(int scene_number) {
        switch(scene_number) {
            case 0:
                return lobbyScene;
            case 1:
                return loginScene;
           // case 2:
             //   return gameScene;
        }
        return null;
    }

    public Scene loadScene(int scene_number) throws IOException {
        Parent root = null;
        if (CREATE_VIEW_FROM_FXML) {
            switch(scene_number) {
                case 0:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Lobby.fxml")));
                    break;
                case 1:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
                    break;
                //case 3:
                  //  root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Game.fxml")));
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