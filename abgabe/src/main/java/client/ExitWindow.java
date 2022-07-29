package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import protocol.SetStatus;


/**
 * This class manages the presentation logic for the Exit confirmation window.
 *
 * @author Felicia Saruba, Nargess Ahmadi
 */

public class ExitWindow {
    static boolean answer;

    /**
     * This method is called when a player wants to exit the game.
     * @param title
     * @param message
     * @return
     */
    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(e-> {
            Client.getClientReceive().sendMessage(new SetStatus(false).toString());
            answer = true;
            window.close();
        });

        noButton.setOnAction(e-> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: linear-gradient(to right, #ada996, #f2f2f2, #dbdbdb, #eaeaea)");
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}
