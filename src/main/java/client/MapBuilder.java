package client;
import server.BoardElement.*;
import javafx.scene.image.Image;
import server.BoardTypes.Board;
import server.BoardTypes.DizzyHighway;

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

    URL checkPoint5 = getClass().getResource("Checkpoints/Checkpoint5.png");
    Image imageCheckPoint5 = new Image(checkPoint5.toString());

    URL checkPoint6 = getClass().getResource("Checkpoints/Checkpoint6.png");
    Image imageCheckPoint6 = new Image(checkPoint6.toString());

    URL blueBeltBottom = getClass().getResource("ConveyorBelts/BlueBeltBottom.png");
    Image imageBlueBeltBottom = new Image(blueBeltBottom.toString());

    URL blueBeltLeft = getClass().getResource("ConveyorBelts/BlueBeltLeft.png");
    Image imageBlueBeltLeft = new Image(blueBeltLeft.toString());

    URL blueBeltRight = getClass().getResource("ConveyorBelts/BlueBeltRight.png");
    Image imageBlueBeltRight = new Image(blueBeltRight.toString());

    URL blueBeltTop = getClass().getResource("ConveyorBelts/BlueBeltTop.png");
    Image imageBlueBeltTop = new Image(blueBeltTop.toString());

    public Board board=new DizzyHighway();

    public void printMap(){
         Image elmImage=null;
        for(int x=0; x<9 ; x++){
            for (int y=0;y<12 ;y++)
            {
                    String firstBoardElmName=board.getBoardElem(x,y,0).getName();
                    String secondBoardElmName=board.getBoardElem(x,y,1).getName();
                    switch(firstBoardElmName){
                        case"StartPoint":
                            elmImage=imageStartPoint;
                     break;
                }

            }
        }

    }
}
