package loginWindow;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class NameSetterView extends AnchorPane{

    public NameSetterView() {
        Label label = new Label("label");

        TextField nameInput = new TextField();
        nameInput.setPromptText("nameInput");
        Text text = new Text();

        Button button = new Button();
        button.setText("buttonSwitch");

        getChildren().addAll(label,nameInput, text, button);

        NameSetterViewModel viewModel = new NameSetterViewModel();
        viewModel.setNodeElements(this, nameInput, text, button);
        viewModel.initialize();

        button.setOnAction(event -> {
            try {
                viewModel.sendNameButtonAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

