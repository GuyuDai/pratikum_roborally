package client.popupWindows;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;


/**
 * @author Felicia Saruba, Nargess Ahmadi
 *
 * window for winner of the game if only one player is left
 */

public class DefaultWinnerViewModel {

    @FXML
    private Label nameBot;
    @FXML
    private ImageView imageBot;
    @FXML
    private MediaView sound;


    /**
     * robot images
     */
    URL hammer = getClass().getResource("/Robots/hammer bot.png");
    Image imageHammer = new Image(hammer.toString());

    URL hulk = getClass().getResource("/Robots/hulk x90.png");
    Image imageHulk = new Image(hulk.toString());

    URL spin = getClass().getResource("/Robots/spin bot.png");
    Image imageSpin = new Image(spin.toString());

    URL squash = getClass().getResource("/Robots/squash bot.png");
    Image imageSquash = new Image(squash.toString());

    URL twitch = getClass().getResource("/Robots/twitch.png");
    Image imageTwitch = new Image(twitch.toString());

    URL twonkey = getClass().getResource("/Robots/twonkey.png");
    Image imageTwonkey = new Image(twonkey.toString());


    /**
     * initialize elements for loading the winner window
     */
    public void initialize(){
        playWinnerTune();
        setYourBotIcon();
    }
    /**
     * sets your own bot icon
     */
    public void setYourBotIcon(){
        int yourId = Client.getClientReceive().getClientID();
        String yourName = Client.getClientReceive().getNameById(yourId);
        int yourRobotNumber = Client.getClientReceive().getRobotById(yourId);
        Image robotIcon = null;
        switch (yourRobotNumber) {
            case 1:
                robotIcon = imageHulk;//hulk
                break;
            case 2:
                robotIcon = imageSpin;//spin
                break;
            case 3:
                robotIcon = imageSquash;//squash
                break;
            case 4:
                robotIcon = imageHammer;//hammer
                break;
            case 5:
                robotIcon = imageTwonkey;//twonkey
                break;
            case 6:
                robotIcon = imageTwitch;//twitch
                break;
        }
        imageBot.setImage(robotIcon);
        nameBot.setText(yourName);
    }

    /**
     * play winnter tune
     */
    public void playWinnerTune() {
        try {
            String fileName = getClass().getResource("/sounds/winnerTune.mp3").toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            sound.setMediaPlayer(player);
        } catch (Exception e) {
        }
        sound.getMediaPlayer().play();
    }


}
