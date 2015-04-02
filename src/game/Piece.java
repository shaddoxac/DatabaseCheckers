package game;

public class Piece {
    public PieceType type;
    public int location;
    public boolean down, up;

    public Piece(PieceType type, int location) {
        this.type=type;
        this.location= location;
        setMovementOptions();
    }

    private void setMovementOptions() {
        boolean[] movementOptions=type.getMovementOptions();
        up=movementOptions[0];
        down=movementOptions[1];
    }
}
