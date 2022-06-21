package client.mapWindow;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.Objects;
/**
 * author Felicia Saruba
 */

public class MapViewModel {

    @FXML
    private AnchorPane rootPaneMap;

    @FXML
    private Button map1Image;

    @FXML
    private Button map2Image;

    @FXML
    private Button map3Image;

    @FXML
    private Button map4Image;

    @FXML
    private ToggleButton map4Btn;

    @FXML
    private ToggleGroup ToggleGroupMap;

    @FXML
    private ToggleButton DizzyHighwayBtn;

    @FXML
    private ToggleButton map2Btn;

    @FXML
    private ToggleButton map3Btn;

    //when clicked on Map Selection, the window closes
    @FXML
    public void selectDizzyHighway(ActionEvent actionEvent) {
        Stage stage = (Stage) DizzyHighwayBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void select2(ActionEvent actionEvent) {
        Stage stage = (Stage) map2Btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void select3(ActionEvent actionEvent) {
        Stage stage = (Stage) map3Btn.getScene().getWindow();
        stage.close();
    }
    public void select4(ActionEvent actionEvent) {
        Stage stage = (Stage) map4Btn.getScene().getWindow();
        stage.close();
    }

    public void map1ImageAction (ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Image1.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dizzy Highway");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }

    public void map2ImageAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Image1.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map 2");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }

    public void map3ImageAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Image1.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map 3");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }

    public void map4ImageAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Image1.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map 4");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }
}
