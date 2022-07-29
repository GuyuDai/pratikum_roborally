package server.Control;


/**
 * @author Minghao Li
 * enum Direction for boardElem,Position,and Robot
 */
public enum Direction {
    UP(0), RIGHT(90), DOWN(180), LEFT(270);

    private final int angel;
    private Direction(final int angel){
        this.angel=angel;
    }

    public int getAngel(){
        return angel;
    }
    public static Direction getDirection(final int degrees) {
        int ordinal = ((degrees % 360) / 90) - ( 4 * (degrees % 90));
        return ordinal < 0 ?  null : values()[ordinal];
    }

    /**
     * @author Minghao Li
     * use angel increasing to make Direction change.
     */
    public Direction turnRight() {
        return Direction.getDirection((angel+90)%360);
    }
    public Direction turnLeft() {
        return Direction.getDirection((angel+270)%360);
    }

    public Direction turn180(){
        return Direction.getDirection((angel+180)%360);
    }


}
