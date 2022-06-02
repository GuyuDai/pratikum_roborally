package server.BoardElement;



public enum Direction {
    NORTH(0),EAST(90),SOUTH(180),WEST(270);

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


    public Direction turnRight() {
        return Direction.getDirection((angel+90)%360);
    }
    public Direction turnLeft() {
        return Direction.getDirection((angel-90)%360);
    }


}
