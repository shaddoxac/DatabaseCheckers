package game;

//squares go left to right, top to bottom
public class Game {
    private int whitePos, blackPos, whiteKingPos, blackKingPos;
    private int whiteStartingPos=0xFFF00000;
    private int blackStartingPos=0x00000FFF;
    private Player currentTurn;

    public Game() {
        currentTurn=Player.WHITE;
        setBoard();
    }


    private void setBoard() {
        whitePos=whiteStartingPos;
        blackPos=blackStartingPos;
        whiteKingPos=0;
        blackKingPos=0;
    }

    private void changeMove(PieceType pieceType, int loc, int dest) {
        int bitBoard=getBitBoard(pieceType);
        bitBoard=bitBoard & ~loc;
        bitBoard=bitBoard | dest;
        setBitBoard(bitBoard,pieceType);
    }

    private void setBitBoard(int bitBoard, PieceType pieceType) {
        if (pieceType.equals(PieceType.BLACK)) {blackPos=bitBoard;}
        else if (pieceType.equals(PieceType.WHITE)) {whitePos=bitBoard;}
        else if (pieceType.equals(PieceType.BLACKKING)) {blackKingPos=bitBoard;}
        else {whiteKingPos=bitBoard;}
    }

    private int getBitBoard(PieceType pieceType) {
        if (pieceType.equals(PieceType.BLACK)) {return blackPos;}
        if (pieceType.equals(PieceType.WHITE)) {return whitePos;}
        if (pieceType.equals(PieceType.BLACKKING)) {return blackKingPos;}
        return whiteKingPos;
    }

    private boolean checkJump(Piece piece) {
        if (isNotEdge(piece.destination)) {

        }
        //TODO
        else {
            //check different criteria for different edges
        }
        return false;
    }

    private boolean isNotEdge(int space) {
        if (isHorizontalBorder(space) && isVerticalBorder(space)) {
            return true;
        }
        return false;
    }
    private boolean isHorizontalBorder(int space) {
        if (space >= 0xF0000000 && space <= 0xF) {return true;}
        return false;
    }
    private boolean isVerticalBorder(int space) {
        if (((space +4) % 8 == 0) && space % 8==5) {return true;}
        return false;
    }

    private boolean checkMove(Piece piece) {
        if (checkOccupied(whitePos, piece.destination) || checkOccupied(blackPos, piece.destination) || checkOccupied(blackKingPos, piece.destination) || checkOccupied(whiteKingPos, piece.destination)) {
            //TODO if there are no jumps
                return true;
            //}
        }
        else if (checkJump(piece)) {
            return true;
        }
        return false;
    }
    private boolean checkOccupied(int bucket, int dest) {
        if ((bucket & dest)!=0) {
            return false;
        }
        return true;
    }

    private void changeTurn() {
        currentTurn=currentTurn.change();
    }
}