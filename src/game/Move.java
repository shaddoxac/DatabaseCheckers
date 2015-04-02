package game;

public class Move {
    public int location, destination;
    public PieceType type;
    public boolean down, up;
    public int NEValue, NWValue, SEValue, SWValue;

    public Move(PieceType type, int loc, int dest) {
        this.type=type;
        location=loc;
        destination=dest;
        setMovementOptions();
    }

    private void setMovementOptions() {
        boolean[] movementOptions=type.getMovementOptions();
        up=movementOptions[0];
        down=movementOptions[1];
    }
}