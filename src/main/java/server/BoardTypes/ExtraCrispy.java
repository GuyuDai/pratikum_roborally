package server.BoardTypes;

import server.BoardElement.*;
import server.Control.*;


public abstract class ExtraCrispy extends Board {
    public ExtraCrispy() {
        super("ExtraCrispy");
        Empty empty = new Empty(currentGame);

        BoardElem[][][] map = new BoardElem[9][12][1];//{{{},{}},{{},{}}};
        map[0][0][0] = empty;//Green Start
        map[0][1][0] = empty;
        map[0][2][0] = new ConveyBelt(currentGame,1, Direction.RIGHT);
        map[0][3][0] = empty;
        map[0][4][0] = new ConveyBelt(currentGame,2, Direction.DOWN);
        map[0][5][0] = empty;
        map[0][6][0] = empty;
        map[0][7][0] = empty;
        map[0][8][0] = new EnergySpace(currentGame, 2);
        map[0][9][0] = new Wall(currentGame, Direction.LEFT);
        map[0][9][1] = new Laser(currentGame, 1,Direction.LEFT);
        map[0][10][0] = new Laser(currentGame, 1,Direction.LEFT);
        map[0][11][0] = new Laser(currentGame, 1,Direction.LEFT);
        map[0][11][1]= new Wall(currentGame, Direction.RIGHT);
        map[0][12][0] = empty;

        map[1][0][0] = empty;
        map[1][1][0]= new StartingPoint(currentGame);
        map[1][2][0] = empty;
        map[1][3][0] = empty;
        map[1][4][0] = new ConveyBelt(currentGame,2, Direction.DOWN);
        map[1][5][0] = empty;
        map[1][6][0] = new RotatingBelt(currentGame,1, Direction.DOWN, Direction.LEFT);
        map[1][7][0] = new ConveyBelt(currentGame,1, Direction.LEFT);
        map[1][8][0] = empty;
        map[1][9][0] = empty;
        map[1][10][0] = empty;
        map[1][11][0] = new ConveyBelt(currentGame,2, Direction.DOWN);
        map[1][12][0] = empty;

        map[2][0][0] = empty;
        map[2][1][0]= new Wall(currentGame, Direction.UP);
        map[2][2][0] = empty;
        map[2][3][0] = empty;
        map[2][4][0] = new ConveyBelt(currentGame,2, Direction.DOWN);
        map[2][5][0] = new CheckPoint(currentGame,1);
        map[2][5][1] = new Wall(currentGame, Direction.UP);
        map[2][5][2] = new Laser(currentGame, 1,Direction.DOWN);
        map[2][6][0] = new Pit(currentGame);
        map[2][7][0] = empty;
        map[2][8][0] = empty;
        map[2][9][0] = new Pit(currentGame);
        map[2][10][0] = new CheckPoint(currentGame,1);
        map[2][11][0] = new ConveyBelt(currentGame,2, Direction.DOWN);
        map[2][12][0] = empty;

        map[3][0][0]= new StartingPoint(currentGame);
        map[3][1][0] = empty;
        map[3][2][0] = empty;
        map[3][3][0] = empty;
        map[3][4][0] = new RotatingBelt(currentGame,2, Direction.UP, Direction.LEFT);
        map[3][5][0] = new ConveyBelt(currentGame,2, Direction.RIGHT);
        map[3][5][1] = new Laser(currentGame, 1,Direction.DOWN);
        map[3][6][0] = new Pit(currentGame);
        map[3][7][0] = new Laser(currentGame, 1,Direction.LEFT);
        map[3][7][1] = new Wall(currentGame, Direction.LEFT);
        map[3][8][0]= new Laser(currentGame, 1,Direction.LEFT);
        map[3][8][1]= new Wall(currentGame, Direction.RIGHT);
        map[3][9][0]= new Pit(currentGame);
        map[3][10][0] = new ConveyBelt(currentGame,2, Direction.LEFT);
        map[3][10][1]= new Laser(currentGame, 1,Direction.DOWN);
        map[3][11][0] = new RotatingBelt(currentGame,2, Direction.DOWN, Direction.LEFT);
        map[3][12][0] = empty;

        map[4][0][0] = new Antenna(currentGame);
        map[4][1][0]= new StartingPoint(currentGame);
        map[4][2][0]= new Wall(currentGame, Direction.RIGHT);
        map[4][3][0] = new Wall(currentGame, Direction.UP);
        map[4][3][1] = new EnergySpace(currentGame, 1);
        map[4][4][0] = empty;
        map[4][5][0] = new Laser(currentGame, 1,Direction.DOWN);
        map[4][6][0]= new Gear(currentGame, "turnRight");
        map[4][7][0] = empty;
        map[4][8][0] = empty;
        map[4][9][0] = new Wall(currentGame, Direction.RIGHT);
        map[4][10][0] = new Laser(currentGame, 1,Direction.DOWN);
        map[4][11][0] = new EnergySpace(currentGame, 2);
        map[4][12][0] = new Wall(currentGame, Direction.UP);

        map[5][0][0] = empty;
        map[5][1][0] = new StartingPoint(currentGame);
        map[5][2][0]= new Wall(currentGame, Direction.RIGHT);
        map[5][3][0] = new Wall(currentGame, Direction.DOWN);
        map[5][4][0] = empty;
        map[5][5][0] = new Laser(currentGame, 1,Direction.DOWN);
        map[5][6][0] = new Wall(currentGame, Direction.LEFT);
        map[5][7][0] = new EnergySpace(currentGame, 2);
        map[5][8][0] = empty;
        map[5][9][0] = new Gear(currentGame, "turnLeft");
        map[5][10][0] = new Laser(currentGame, 1,Direction.DOWN);
        map[5][11][0] = empty;
        map[5][12][0] = new Wall(currentGame, Direction.DOWN);

        map[6][0][0] = new StartingPoint(currentGame);
        map[6][1][0] = empty;
        map[6][2][0] = empty;
        map[6][3][0] = empty;
        map[6][4][0] = new RotatingBelt(currentGame, 2, Direction.UP, Direction.RIGHT);
        map[6][5][0] = new ConveyBelt(currentGame,2, Direction.RIGHT);
        map[6][5][1] = new Laser(currentGame, 1,Direction.UP);
        map[6][6][0] = new Pit(currentGame);
        map[6][7][0] = new Laser(currentGame, 1,Direction.RIGHT);
        map[6][7][1] = new Wall(currentGame, Direction.LEFT);
        map[6][8][0] = new Wall(currentGame, Direction.RIGHT);
        map[6][8][1] = new Laser(currentGame, 1,Direction.RIGHT);
        map[6][9][0] = new Pit(currentGame);
        map[6][10][0] = new ConveyBelt(currentGame,2, Direction.LEFT);
        map[6][10][1] = new Laser(currentGame, 1,Direction.DOWN);
        map[6][11][0] = new RotatingBelt(currentGame, 2, Direction.UP, Direction.LEFT);
        map[6][12][0] = empty;

        map[7][0][0] = empty;
        map[7][1][0] = new Wall(currentGame, Direction.DOWN);
        map[7][2][0] = empty;
        map[7][3][0] = empty;
        map[7][4][0] = new ConveyBelt(currentGame,2, Direction.UP);
        map[7][5][0] = new CheckPoint(currentGame,1);
        map[7][5][1]= new Wall(currentGame,Direction.DOWN);
        map[7][5][2]= new Laser(currentGame, 1, Direction.DOWN);
        map[7][6][0] = new Pit(currentGame);
        map[7][7][0] = empty;
        map[7][8][0] = empty;
        map[7][9][0] = new Pit(currentGame);
        map[7][10][0] = new CheckPoint(currentGame,1);
        map[7][10][1]= new Wall(currentGame,Direction.DOWN);
        map[7][10][2]= new Laser(currentGame, 1, Direction.DOWN);
        map[7][11][0] = new ConveyBelt(currentGame,2, Direction.UP);
        map[7][12][0] = empty;

        map[8][0][0] = empty;
        map[8][1][0]= new StartingPoint(currentGame);
        map[8][2][0] = empty;
        map[8][3][0] = empty;
        map[8][4][0] = new ConveyBelt(currentGame,2, Direction.UP);
        map[8][5][0] = empty;
        map[8][6][0] = empty;
        map[8][7][0] = empty;
        map[8][8][0] = new ConveyBelt(currentGame,1, Direction.RIGHT);
        map[8][9][0] = new RotatingBelt(currentGame, 1, Direction.UP, Direction.LEFT);
        map[8][10][0] = empty;
        map[8][11][0] = new ConveyBelt(currentGame,2, Direction.UP);
        map[8][12][0] = empty;

        map[9][0][0] = empty;
        map[9][1][0] = empty;
        map[9][2][0] = new ConveyBelt(currentGame,1, Direction.RIGHT);
        map[9][3][0] = new EnergySpace(currentGame, 2);
        map[9][4][0] = new Wall(currentGame,Direction.LEFT);
        map[9][4][1] = new Laser(currentGame, 1, Direction.RIGHT);
        map[9][5][0] = new Laser(currentGame, 1, Direction.RIGHT);
        map[9][6][0] = new Laser(currentGame, 1, Direction.RIGHT);
        map[9][6][1] = new Wall(currentGame,Direction.LEFT);
        map[9][7][0] = empty;
        map[9][8][0] = empty;
        map[9][9][0] = empty;
        map[9][10][0] = empty;
        map[9][11][0] = new ConveyBelt(currentGame,2, Direction.UP);
        map[9][12][0] = empty;
    }
}


