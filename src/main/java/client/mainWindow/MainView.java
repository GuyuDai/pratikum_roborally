package client.mainWindow;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainView extends VBox{

    public MainView() {

        Label label = new Label("label");
        ListView<String> list = new ListView<>();
        TextField input = new TextField();
//        input.setPromptText("input");
        Button sendMessageButton = new Button();
//        sendMessageButton.setText("buttonInput");


        getChildren().addAll(label, list, input, sendMessageButton);

        MainViewModel viewModel = new MainViewModel();
        viewModel.setNodeElements(this, list, input, sendMessageButton);
        viewModel.initialize();
        sendMessageButton.setOnAction(event -> {
            try {
                viewModel.sendButtonAction(event);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}