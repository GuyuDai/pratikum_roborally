package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Main class to start the application. This implementation is very ugly and shouldn't be used anymore.
 */
public class App extends Application {

  /**
   * {@code true} - the view is created with fxml & css <p>
   * {@code false} - the view is solely created with java code ({@link View})
   */
  private static final boolean CREATE_VIEW_FROM_FXML = true;

  public static void main(String[] args) {
    System.out.println("Execution order");
    launch(args);
  }

  @Override
  public void init() {
    // do some preparation
    System.out.println("1: init()");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    // initialize the GUI
    System.out.println("2: start()");

    // setting stage title and icon
    primaryStage.setTitle("Love Letter Game");
    primaryStage.getIcons().add(loadTitle());

    // loading scene into primaryStage
    Scene scene = loadScene();
    primaryStage.setScene(scene);

    // showing the stage on screen
    primaryStage.show();
  }