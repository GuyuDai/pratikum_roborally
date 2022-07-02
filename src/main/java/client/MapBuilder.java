package client;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import server.BoardElement.*;
import javafx.scene.image.Image;
import server.BoardTypes.Board;
import server.BoardTypes.DizzyHighway;
import server.Control.Direction;

import java.net.URL;

public class MapBuilder {


    @FXML
    GridPane gameboard;

    /**
     * checkpoints
     */
    URL checkPoint1 = getClass().getResource("/Checkpoints/Checkpoint1.png");
    Image imageCheckPoint1 = new Image(checkPoint1.toString());

    URL checkPoint2 = getClass().getResource("/Checkpoints/Checkpoint2.png");
    Image imageCheckPoint2 = new Image(checkPoint2.toString());

    URL checkPoint3 = getClass().getResource("/Checkpoints/Checkpoint3.png");
    Image imageCheckPoint3 = new Image(checkPoint3.toString());

    URL checkPoint4 = getClass().getResource("/Checkpoints/Checkpoint4.png");
    Image imageCheckPoint4 = new Image(checkPoint4.toString());

    URL checkPoint5 = getClass().getResource("/Checkpoints/Checkpoint5.png");
    Image imageCheckPoint5 = new Image(checkPoint5.toString());

    URL checkPoint6 = getClass().getResource("/Checkpoints/Checkpoint6.png");
    Image imageCheckPoint6 = new Image(checkPoint6.toString());

    /**
     * Conveyor belts
     */
    URL blueBeltBottom = getClass().getResource("/ConveyorBelts/BlueBeltBottom.png");
    Image imageBlueBeltBottom = new Image(blueBeltBottom.toString());


    URL blueBeltLeft = getClass().getResource("/ConveyorBelts/BlueBeltLeft.png");
    Image imageBlueBeltLeft = new Image(blueBeltLeft.toString());

    URL blueBeltRight = getClass().getResource("/ConveyorBelts/BlueBeltRight.png");
    Image imageBlueBeltRight = new Image(blueBeltRight.toString());

    URL blueBeltTop = getClass().getResource("/ConveyorBelts/BlueBeltTop.png");
    Image imageBlueBeltTop = new Image(blueBeltTop.toString());

    URL blueRotating = getClass().getResource("/ConveyorBelts/blueRotating.png");
    Image imageBlueRotating = new Image(blueRotating.toString());

    URL greenBeltBottom = getClass().getResource("/ConveyorBelts/GreenBeltBottom.png");
    Image imageGreenBeltBottom = new Image(greenBeltBottom.toString());

    URL greenBeltLeft = getClass().getResource("/ConveyorBelts/GreenBeltLeft.png");
    Image imageGreenBeltLeft = new Image(greenBeltLeft.toString());

    URL greenBeltRight = getClass().getResource("/ConveyorBelts/GreenBeltRight.png");
    Image imageGreenBeltRight = new Image(greenBeltRight.toString());

    URL greenBeltTop = getClass().getResource("/ConveyorBelts/GreenBeltTop.png");
    Image imageGreenBeltTop = new Image(greenBeltTop.toString());

    URL greenRotating = getClass().getResource("/ConveyorBelts/GreenRotating.png");
    Image imageGreenRotating = new Image(greenRotating.toString());

    URL rbBottom = getClass().getResource("/ConveyorBelts/RB Bottom.png");
    Image imageRBDownLeft = new Image(rbBottom.toString());

    URL rbBottom2 = getClass().getResource("/ConveyorBelts/RB Bottom 2.png");
    Image imageRBDownRight = new Image(rbBottom2.toString());

    URL rbLeft = getClass().getResource("/ConveyorBelts/RB left.png");
    Image imageRBLeftUp = new Image(rbLeft.toString());

    URL rbLeft2 = getClass().getResource("/ConveyorBelts/RB left2.png");
    Image imageRBLeftDown = new Image(rbLeft2.toString());

    URL rbRight = getClass().getResource("ConveyorBelts/RB right.png");
    Image imageRBRightDown = new Image(rbRight.toString());

    URL rbRight2 = getClass().getResource("/ConveyorBelts/RB right2.png");
    Image imageRBRightUp = new Image(rbRight2.toString());

    URL rbTop = getClass().getResource("/ConveyorBelts/RB top.png");
    Image imageRBUpLeft = new Image(rbTop.toString());

    URL rbTop2 = getClass().getResource("/ConveyorBelts/RB top2.png");
    Image imageRBUpRight = new Image(rbTop2.toString());

    /**
     * Board Images
     */
    URL wallDown = getClass().getResource("/BoardImages/WallDown.png");
    Image imageWallDown = new Image(wallDown.toString());

    URL wallRight = getClass().getResource("/BoardImages/WallRight.png");
    Image imageWallRight = new Image(wallRight.toString());

    URL wallLeft = getClass().getResource("/BoardImages/WallLeft.png");
    Image imageWallLeft = new Image(wallLeft.toString());

    URL antenna = getClass().getResource("/BoardImages/antenna.png");
    Image imageAntenna = new Image(antenna.toString());

    URL pit = getClass().getResource("/BoardImages/Pit.png");
    Image imagePit = new Image(pit.toString());

    URL startPoint = getClass().getResource("/BoardImages/StartPoint.png");
    Image imageStartPoint = new Image(startPoint.toString());

    URL wallup = getClass().getResource("/BoardImages/Wall.png");
    Image imageWallUp = new Image(wallup.toString());

    URL wallEcke = getClass().getResource("/BoardImages/WallEcke.png");
    Image imageWallEcke = new Image(wallEcke.toString());


    /**
     * EnergyImg
     */
    URL energyOff = getClass().getResource("/EnergyImg/energyOff.png");
    Image imageenergyOff = new Image(energyOff.toString());

    URL energyOn = getClass().getResource("/EnergyImg/energyOn.png");
    Image imageenergyOn = new Image(energyOn.toString());


    /**
     * Laser
     */
    URL laser1 = getClass().getResource("/Laser/Laser1.png");
    Image imagehorilaser1 = new Image(laser1.toString());

    URL laser2 = getClass().getResource("/Laser/Laser2.png");
    Image imagehorilaser2 = new Image(laser2.toString());

    URL laser3 = getClass().getResource("/Laser/Laser3.png");
    Image imagehorilaser3 = new Image(laser3.toString());

    URL wallLaser1 = getClass().getResource("/Laser/WallLaser1.png");
    Image imagewallRightLaser1 = new Image(wallLaser1.toString());

    URL wallLaser2 = getClass().getResource("/Laser/WallLaser2.png");
    Image imagewallRightLaser2 = new Image(wallLaser2.toString());

    URL wallLaser3 = getClass().getResource("/Laser/WallLaser3.png");
    Image imagewallRightLaser3 = new Image(wallLaser3.toString());

    URL laser1Vertical = getClass().getResource("/Laser/Laser1Vertical.png");
    Image imagelaser1Vertical = new Image(laser1Vertical.toString());

    URL laser2Vertical = getClass().getResource("/Laser/Laser2Vertical.png");
    Image imagelaser2Vertical = new Image(laser2Vertical.toString());

    URL laser3Vertical = getClass().getResource("/Laser/Laser3Vertical.png");
    Image imagelaser3Vertical = new Image(laser3Vertical.toString());

    URL wallLaser1Vertical = getClass().getResource("/Laser/WallLaser1Vertical.png");
    Image imageWallLaser1Vertical = new Image(wallLaser1Vertical.toString());

    URL wallLaser2Vertical = getClass().getResource("/Laser/WallLaser2Vertical.png");
    Image imageWallLaser2Vertical = new Image(wallLaser2Vertical.toString());

    URL wallLaser3Vertical = getClass().getResource("/Laser/WallLaser3Vertical.png");
    Image imageWallLaser3Vertical = new Image(wallLaser3Vertical.toString());

    URL wallLaser1Vertical2 = getClass().getResource("/Laser/WallLaser1Vertical2.png");
    Image imageWallLaser1Vertical2 = new Image(wallLaser1Vertical2.toString());

    URL wallLaser2Vertical2 = getClass().getResource("/Laser/WallLaser2Vertical2.png");
    Image imageWallLaser2Vertical2 = new Image(wallLaser2Vertical2.toString());

    URL wallLaser3Vertical2 = getClass().getResource("/Laser/WallLaser3Vertical2.png");
    Image imageWallLaser3Vertical2 = new Image(wallLaser3Vertical2.toString());


    /**
     * others
     */
    URL gearLeft = getClass().getResource("/others/gearLeft.png");
    Image imageGearLeft = new Image(gearLeft.toString());

    URL gearRight = getClass().getResource("/others/gearRight.png");
    Image imageGearRight = new Image(gearRight.toString());

    URL PP24 = getClass().getResource("/others/PP24.png");
    Image imagePP24 = new Image(PP24.toString());

    URL PP135 = getClass().getResource("/others/PP135.png");
    Image imagePP135 = new Image(PP135.toString());

    URL reboot = getClass().getResource("/others/reboot.png");
    Image imageReboot = new Image(reboot.toString());





    public Board board = new DizzyHighway();

    public void printMap() {
        Image elmImage = null;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 12; y++) {
                BoardElem boardElem1 = board.getBoardElem(x, y, 0);
                BoardElem boardElem2 = board.getBoardElem(x, y, 1);
                String firstBoardElmName = board.getBoardElem(x, y, 0).getName();
                String secondBoardElmName = board.getBoardElem(x, y, 1).getName();
                switch (firstBoardElmName) {
                    case "StartPoint":
                        elmImage = imageStartPoint;
                        break;
                    case "CheckPoint":
                        int number = boardElem1.getCount();
                        switch (number) {
                            case 1:
                                elmImage = imageCheckPoint1;
                                break;
                            case 2:
                                elmImage = imageCheckPoint2;
                                break;
                            case 3:
                                elmImage = imageCheckPoint3;
                                break;
                            case 4:
                                elmImage = imageCheckPoint4;
                                break;
                            case 5:
                                elmImage = imageCheckPoint5;
                                break;
                            case 6:
                                elmImage = imageCheckPoint6;
                                break;
                        }
                        break;
                    case "ConveyBelt":
                        int speed = boardElem1.getSpeed();
                        Direction direction = boardElem1.getDirection();
                        if (speed == 1) {
                            switch (direction) {
                                case UP:
                                    elmImage = imageGreenBeltTop;
                                    break;
                                case DOWN:
                                    elmImage = imageGreenBeltBottom;
                                    break;
                                case LEFT:
                                    elmImage = imageGreenBeltLeft;
                                    break;
                                case RIGHT:
                                    elmImage = imageGreenBeltRight;
                                    break;
                            }
                        }
                        if(speed==2){
                            switch (direction){
                                case UP:
                                    elmImage=imageBlueBeltTop;
                                    break;
                                case DOWN:
                                    elmImage=imageGreenBeltBottom;
                                    break;
                                case LEFT:
                                    elmImage=imageGreenBeltLeft;
                                    break;
                                case RIGHT:
                                    elmImage=imageGreenBeltRight;
                                    break;
                            }
                        }
                        break;
                    case"EnergySpace":
                        elmImage=imageenergyOn;
                        break;
                    case"Laser":
                        int count=boardElem1.getCount();
                        Direction LaserDirection=boardElem1.getDirection();
                        String name=boardElem2.getName();
                        if(!name.equals("Wall")) {
                            switch (count) {
                                case 1:
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                    { elmImage = imagehorilaser1;}
                                    if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                        elmImage = imagelaser1Vertical;
                                    }
                                    break;
                                case 2:
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                    {  elmImage = imagehorilaser2; }
                                    if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                        elmImage = imagelaser2Vertical;
                                    }
                                    break;
                                case 3:
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)) {
                                        elmImage = imagehorilaser3;
                                    }
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                        elmImage = imagelaser3Vertical;
                                    }
                                    break;
                            }
                        }
                        else {
                            switch (count) {
                                case 1:
                                    if (LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)) {
                                        elmImage = imageWallLaser1Vertical;
                                    }
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                        elmImage = imagewallRightLaser1;
                                    }
                                    break;
                                case 2:
                                    if (LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)) {
                                        elmImage = imageWallLaser2Vertical;
                                    }
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                        elmImage = imagewallRightLaser2;
                                    }
                                    break;
                                case 3:
                                    if (LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)) {
                                        elmImage = imageWallLaser3Vertical;
                                    }
                                    if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                        elmImage = imagewallRightLaser3;
                                    }
                                    break;
                            }
                        }
                        break;
                    case"RotatingBelt":
                        Direction direction1=boardElem1.getDirection();
                        Direction direction2=boardElem1.getDirection2();
                        switch (direction1){
                            case RIGHT:
                                switch (direction2){
                                    case UP:
                                        elmImage=imageRBRightUp;
                                        break;
                                    case DOWN:
                                        elmImage=imageRBRightDown;
                                }
                                break;
                            case LEFT:
                                switch (direction2){
                                    case UP:
                                        elmImage=imageRBLeftUp;
                                        break;
                                    case DOWN:
                                        elmImage=imageRBLeftDown;
                                        break;
                                }
                                break;
                            case UP:
                                switch (direction2){
                                    case RIGHT:
                                        elmImage=imageRBUpRight;
                                        break;
                                    case LEFT:
                                        elmImage=imageRBUpLeft;
                                        break;
                                }
                                break;
                            case DOWN:
                                switch (direction2){
                                    case RIGHT:
                                        elmImage=imageRBDownRight;
                                        break;
                                    case LEFT:
                                        elmImage=imageRBDownLeft;
                                }
                                break;
                        }
                        break;
                    case"Antenna":
                        elmImage=imageAntenna;
                        break;
                    case"PushPanel":
                        int speedPushPanel=boardElem1.getSpeed();
                        switch (speedPushPanel){
                            case 135:
                                elmImage=imagePP135;
                                break;
                            case 24:
                                elmImage=imagePP24;
                                break;
                        }
                        break;
                    case"Gear":
                        String turnDirection=boardElem1.getTurnDirection();
                        switch (turnDirection){
                            case "TurnLeft":
                                elmImage=imageGearLeft;
                                break;
                            case"TurnRight":
                                elmImage=imageGearRight;
                                break;
                        }
                        break;
                    case"reboot":
                        elmImage=imageReboot;
                        break;
                    case"Pit":
                        elmImage=imagePit;
                        break;
                    default:
                        break;
                }
                switch(secondBoardElmName){
                    case"Laser":
                        int laserCount=boardElem2.getCount();
                        Direction LaserDirection=boardElem2.getDirection();
                        switch (laserCount){
                            case 1:
                                if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                { elmImage = imagehorilaser1;}
                                if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                    elmImage = imagelaser1Vertical;
                                }
                                break;
                            case 2:
                                if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT))
                                {  elmImage = imagehorilaser2; }
                                if(LaserDirection.equals(Direction.UP) || LaserDirection.equals(Direction.DOWN)){
                                    elmImage = imagelaser2Vertical;
                                }
                                break;
                            case 3:
                                if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)) {
                                    elmImage = imagehorilaser3;
                                }
                                if (LaserDirection.equals(Direction.RIGHT) || LaserDirection.equals(Direction.LEFT)){
                                    elmImage = imagelaser3Vertical;
                                }
                                break;
                        }
                        break;
                    case"Wall":
                        Direction wallDirection=boardElem2.getDirection2();
                        switch (wallDirection){
                            case UP:
                                elmImage=imageWallUp;
                                break;
                            case LEFT:
                                elmImage=imageWallLeft;
                                break;
                            case  DOWN:
                                elmImage=imageWallDown;
                                break;
                            case  RIGHT:
                                elmImage=imageWallRight;
                                break;
                        }
                }
                ImageView boardElem=new ImageView(elmImage);
                gameboard.add(boardElem,x,y);
            }
        }

    }

}

