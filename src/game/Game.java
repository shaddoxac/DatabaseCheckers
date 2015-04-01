package game;

//squares go left to right, top to bottom
public class Game {
    private int whitePos, blackPos, whiteKingPos, blackKingPos;
    private int whiteStartingPos=0xFFF00000;
    private int blackStartingPos=0x00000FFF;

    public Game() {
        setBoard();
    }


    private void setBoard() {
        whitePos=whiteStartingPos;
        blackPos=blackStartingPos;
        whiteKingPos=0;
        blackKingPos=0;
    }

    private void changeMove(Piece pieceType, int loc, int dest) {
        int bitBoard=getBitBoard(pieceType);
        bitBoard=bitBoard & ~loc;
        bitBoard=bitBoard | dest;
        setBitBoard(bitBoard,pieceType);
    }

    private void setBitBoard(int bitBoard, Piece pieceType) {
        if (pieceType.equals(Piece.BLACK)) {blackPos=bitBoard;}
        else if (pieceType.equals(Piece.WHITE)) {whitePos=bitBoard;}
        else if (pieceType.equals(Piece.BLACKKING)) {blackKingPos=bitBoard;}
        else {whiteKingPos=bitBoard;}
    }

    private int getBitBoard(Piece pieceType) {
        if (pieceType.equals(Piece.BLACK)) {return blackPos;}
        if (pieceType.equals(Piece.WHITE)) {return whitePos;}
        if (pieceType.equals(Piece.BLACKKING)) {return blackKingPos;}
        return whiteKingPos;
    }
    private boolean checkMove(int dest) {
        if (checkOccupied(whitePos, dest) || checkOccupied(blackPos, dest)) {
            if (checkOccupied(blackKingPos, dest) || checkOccupied(whiteKingPos, dest)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkOccupied(int bucket, int dest) {
        if ((bucket & dest)!=0) {
            return false;
        }
        return true;
    }
}