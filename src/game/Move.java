package game;

import java.util.ArrayList;

public class Move {
    private Piece piece;
    private int destination;
    private boolean isJump;
    private ArrayList<Piece> sequentialJumps=new ArrayList<>();//TODO


    public Move(PieceType type, int loc, int dest) {
        piece=new Piece(type,loc);
        destination=dest;
        isJump=false;
    }
    public Move(Piece piece, int dest) {
        this.piece=piece;
        destination=dest;
        isJump=false;
    }

    public void addJump(PieceType jumpedType, int loc) {
        Piece jumpedPiece=new Piece(jumpedType,loc);
        sequentialJumps.add(jumpedPiece);
    }

    public Piece getPiece() {return piece;}
    public PieceType getType() {
        return piece.type;
    }
    public int getLocation() {
        return piece.location;
    }
    public int getDestination() {return destination;}
    public ArrayList<Piece> getSequentialJumps() { return sequentialJumps;}
    public boolean isJump() {return isJump;}
    public boolean getUp() {return piece.up;}
    public boolean getDown() {return piece.down;}

    public void setDestination(int dest) {destination=dest;}
    public void setJump(boolean b) {isJump=b;}

}