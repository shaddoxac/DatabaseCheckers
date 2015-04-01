package game;

public class Piece {
    public int location, destination;
    public PieceType type;
    public int NEValue, NWValue, SEValue, SWValue;

    public Piece(PieceType type, int loc, int dest) {
        this.type=type;
        location=loc;
        destination=dest;
    }
}
