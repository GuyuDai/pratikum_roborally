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


public class MapViewModel {

    @FXML
    private AnchorPane rootPaneMap;

    @FXML
    private Button map1Image;

    @FXML
    private Button map3Image;

    @FXML
    private Button map2Image;

    @FXML
    private ToggleGroup ToggleGroupMap;
    @FXML
    private ToggleButton DizzyHighwayBtn;

    @FXML
    private ToggleButton map2Btn;

    @FXML
    private ToggleButton map3Btn;

    public void selectDizzyHighway(ActionEvent actionEvent) {
    }

    public void select2(ActionEvent actionEvent) {
    }

    public void select3(ActionEvent actionEvent) {
    }


    public void map1ImageAction (ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Image1.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Map 1");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
            System.out.println("not working");
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
            System.out.println("not working");
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
            System.out.println("not working");
        }
    }




}
