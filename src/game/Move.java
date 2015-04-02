package game;

import java.util.ArrayList;

public class Move {
    public Piece piece;
    public int destination;
    public ArrayList<Integer> sequentialJumps=new ArrayList<Integer>();//TODO


    public Move(PieceType type, int loc, int dest) {
        piece=new Piece(type,loc);
        destination=dest;
    }


    public PieceType getType() {
        return piece.type;
    }
    public int getLocation() {
        return piece.location;
    }
}