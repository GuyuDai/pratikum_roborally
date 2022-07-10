package client.mapWindow;


import client.Client;
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
import protocol.MapSelected;


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

    public static String mapSelection;

    @FXML
    public void selectDizzyHighway(ActionEvent actionEvent) {
        Stage stage = (Stage) DizzyHighwayBtn.getScene().getWindow();
        stage.close();
        setMapSelection("Dizzy Highway");
        Client.getClientReceive().sendMessage(new MapSelected("DizzyHighway").toString());
        openGameWindow();
    }
    @FXML
    public void selectDeathTrap(ActionEvent actionEvent) {
        Stage stage = (Stage) map2Btn.getScene().getWindow();
        stage.close();
        setMapSelection("Death Trap");
        Client.getClientReceive().sendMessage(new MapSelected("DeathTrap").toString());
        openGameWindow();
    }
    @FXML
    public void selectExtraCrispy(ActionEvent actionEvent) {
        Stage stage = (Stage) map3Btn.getScene().getWindow();
        stage.close();
        setMapSelection("Extra Crispy");
        Client.getClientReceive().sendMessage(new MapSelected("ExtraCrispy").toString());
        openGameWindow();
    }
    public void selectLostBearings(ActionEvent actionEvent) {
        Stage stage = (Stage) map4Btn.getScene().getWindow();
        stage.close();
        setMapSelection("Lost Bearings");
        Client.getClientReceive().sendMessage(new MapSelected("LostBearings").toString());
        openGameWindow();
    }

    /**
     * by clicking on each of the map images, it will appear as an enlarged image
     */
    public void map1ImageAction (ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/DizzyHigway.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/DeathTrap.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Death Trap");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }

    public void map3ImageAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ExtraCrispy.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Extra Crispy");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }

    public void map4ImageAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LostBearings.fxml"));
            Parent rootMap1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Lost Bearings");
            stage.setScene(new Scene(rootMap1));
            stage.show();
        } catch (Exception e){
        }
    }
    public void openGameWindow(){
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("/Views/Game.fxml"));
            Parent rootGame = (Parent) fxmlLoaderGame.load();
            Stage stageGame = new Stage();
            stageGame.setTitle("Robo Rally");
            stageGame.setScene(new Scene(rootGame));
            stageGame.show();
        } catch (Exception e){
        }
    }

    public static void setMapSelection(String name){
        mapSelection = name;
    }

    public static String getMapSelection(){
        return mapSelection;
    }
}
