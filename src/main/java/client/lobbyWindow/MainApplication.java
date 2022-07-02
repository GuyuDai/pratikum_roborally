package client.lobbyWindow;

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


    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Lobby.fxml"));
            Parent rootMap = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map Selection");
            stage.setScene(new Scene(rootMap));
            stage.show();
        } catch (Exception e) {
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}


    /*

    private static final boolean CREATE_VIEW_FROM_FXML = true;

    public static Stage window;
    public static Scene lobbyScene;
    public static Scene loginScene;

    public static Scene gameScene;

    private static Client client;
    @Override
    public void stop() {
    }

    //TODo Ändere Namen
    @Override
    public void start(Stage primaryStage) throws IOException {
        client=new Client();
        client.init();
        window = primaryStage;
        window.setTitle("RoboRally");
        lobbyScene = loadScene(0);
        loginScene = loadScene(1);
        window.setScene(loginScene);
        window.show();
    }

    public static Scene getScene(int scene_number) {
        switch(scene_number) {
            case 0:
                return lobbyScene;
            case 1:
                return loginScene;
        }
        return null;
    }

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
     */
