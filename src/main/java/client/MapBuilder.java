package client;

import javafx.scene.image.Image;

import java.net.URL;

public class MapBuilder {

    URL startPoint = getClass().getResource("BoardImages/StartPoint.png");
    Image imageStartPoint = new Image(startPoint.toString());

    URL wall = getClass().getResource("BoardImages/Wall.png");
    Image imageWall = new Image(wall.toString());

    URL wallEcke = getClass().getResource("BoardImages/WallEcke.png");
    Image imageWallEcke = new Image(wallEcke.toString());

    URL checkPoint1 = getClass().getResource("Checkpoints/Checkpoint1.png");
    Image imageCheckPoint1 = new Image(checkPoint1.toString());

    URL checkPoint2 = getClass().getResource("Checkpoints/Checkpoint2.png");
    Image imageCheckPoint2 = new Image(checkPoint2.toString());

    URL checkPoint3 = getClass().getResource("Checkpoints/Checkpoint3.png");
    Image imageCheckPoint3 = new Image(checkPoint3.toString());

    URL checkPoint4 = getClass().getResource("Checkpoints/Checkpoint4.png");
    Image imageCheckPoint4 = new Image(checkPoint4.toString());

    URL checkPoint5= getClass().getResource("Checkpoints/Checkpoint5.png");
    Image imageCheckPoint5 = new Image(checkPoint5.toString());

    URL checkPoint6 = getClass().getResource("Checkpoints/Checkpoint6.png");
    Image imageCheckPoint6 = new Image(checkPoint6.toString());

    URL blueBeltBottom = getClass().getResource("ConveyorBelts/BlueBeltBottom.png");
    Image imageBlueBeltBottom = new Image(blueBeltBottom.toString());

    URL blueBeltLeft= getClass().getResource("ConveyorBelts/BlueBeltLeft.png");
    Image imageBlueBeltLeft = new Image(blueBeltLeft.toString());

    URL blueBeltRight= getClass().getResource("ConveyorBelts/BlueBeltRight.png");
    Image imageBlueBeltRight = new Image(blueBeltRight.toString());

    URL blueBeltTop= getClass().getResource("ConveyorBelts/BlueBeltTop.png");
    Image imageBlueBeltTop = new Image(blueBeltTop.toString());

    URL blueRotating = getClass().getResource("ConveyorBelts/blueRotating.png");
    Image imageBlueRotating = new Image(blueRotating.toString());


    URL greenBeltBottom = getClass().getResource("ConveyorBelts/GreenBeltBottom.png");
    Image imageGreenBeltBottom = new Image(greenBeltBottom.toString());

    URL greenBeltLeft= getClass().getResource("ConveyorBelts/GreenBeltLeft.png");
    Image imageGreenBeltLeft = new Image(greenBeltLeft.toString());

    URL greenBeltRight= getClass().getResource("ConveyorBelts/GreenBeltRight.png");
    Image imageGreenBeltRight = new Image(greenBeltRight.toString());

    URL greenBeltTop= getClass().getResource("ConveyorBelts/GreenBeltTop.png");
    Image imageGreenBeltTop = new Image(greenBeltTop.toString());

    URL greenRotating = getClass().getResource("ConveyorBelts/GreenRotating.png");
    Image imageGreenRotating = new Image(greenRotating.toString());



}
