package client.mainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import client.Client;

public class MainApplication extends Application {

    private static final boolean CREATE_VIEW_FROM_FXML = true;

    public static Stage window;
    public static Scene gameScene,nameScene;


    @Override
    public void stop() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        new Client().init();
        window = primaryStage;
        window.setTitle("RoboRally");
        gameScene = loadScene(0);
        nameScene = loadScene(1);
        window.setScene(nameScene);
        window.show();
    }

    public static Scene getScene(int scene_number) {
        switch(scene_number) {
            case 0:
                return gameScene;
            case 1:
                return nameScene;
        }
        return null;
    }

    public Scene loadScene(int scene_number) throws IOException {
        Parent root = null;
        if (CREATE_VIEW_FROM_FXML) {
            switch(scene_number) {
                case 0:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoveLetterView.fxml")));
                    break;
                case 1:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
                    break;
            }
        } else {
            root = new MainView();
        }
        return new Scene(root);
    }
    public static void main(String[] args) {
        launch(args);
    }
}