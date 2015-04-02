package game;

import java.util.ArrayList;

public class Move {
    public int location, destination;
    public ArrayList<Integer> sequentialJumps=new ArrayList();//TODO
    public PieceType type;
    public boolean down, up;

    public Move(PieceType type, int loc) {
        this.type=type;
        location=loc;
        setMovementOptions();
    }

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