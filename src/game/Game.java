package game;

import java.util.ArrayList;

//squares go left to right, top to bottom
public class Game {
    private Player currentTurn;
    private Board board;

    private int upperBound=0xFFF00000;

    public Game() {
        currentTurn=Player.BLACK;
        board=new Board();
    }

    public Game(Board board) {
        currentTurn=Player.WHITE;
        this.board=board;
    }


    public void changeTurn() {
        currentTurn=currentTurn.change();
    }



    private ArrayList<Move> getPotentialMoves(Move move) {
        Move potentialMove=new Move(move.type,move.location);
        return getValidMoves(potentialMove);
    }

    private ArrayList<Move> getValidMoves(Move move) {
        ArrayList<Move> moveLocations=new ArrayList<Move>();
        Move tempMove;
        if (move.down) {
            tempMove=checkDestination(move, -5);
            if (tempMove!=null) {moveLocations.add(tempMove);}
            tempMove=checkDestination(move, -4);
            if (tempMove!=null) {moveLocations.add(tempMove);}
        }
        if (move.up) {
            tempMove=checkDestination(move, 5);
            if (tempMove!=null) {moveLocations.add(tempMove);}
            tempMove=checkDestination(move, 4);
            if (tempMove!=null) {moveLocations.add(tempMove);}
        }
        return moveLocations;
    }

    private Move checkDestination(Move move, int offset) {
        int tempDestination=move.location-offset;
        Move tempMove=new Move(move.type, move.location, tempDestination);
        if (checkMove(tempMove)) {return tempMove;}
        return null;
    }

    private void commitMove(PieceType pieceType, int loc, int dest) {
        int bitBoard=getBitBoard(pieceType);
        bitBoard=bitBoard & ~loc;
        bitBoard=bitBoard | dest;
        setBitBoard(bitBoard,pieceType);
    }

    private void setBitBoard(int bitBoard, PieceType pieceType) {
        if (pieceType.equals(PieceType.BLACK)) {board.blackPos=bitBoard;}
        else if (pieceType.equals(PieceType.WHITE)) {board.whitePos=bitBoard;}
        else if (pieceType.equals(PieceType.BLACKKING)) {board.blackKingPos=bitBoard;}
        else {board.whiteKingPos=bitBoard;}
    }

    private int getBitBoard(PieceType pieceType) {
        if (pieceType.equals(PieceType.BLACK)) {return board.blackPos;}
        if (pieceType.equals(PieceType.WHITE)) {return board.whitePos;}
        if (pieceType.equals(PieceType.BLACKKING)) {return board.blackKingPos;}
        return board.whiteKingPos;
    }

    private boolean checkJump(Move move) {
        if (isNotEdge(move.destination)) {
            if (!nextSpaceOccupied(move)) {
                return true;
            }
        }
        return false;
    }

    private boolean nextSpaceOccupied(Move move) {
        int newDest=getJumpDestination(move);
        return spaceNotOccupied(new Move(move.type, move.location, newDest));
    }

    private int getJumpDestination(Move move) {
        if (move.location > move.destination) {
            if (move.location-5==move.destination) {
                return move.destination-4;
            }
            else {
                return move.destination-3;
            }
        }
        else {
            if (move.location+5==move.destination) {
                return move.destination+4;
            }
            else {
                return move.destination+3;
            }
        }
    }

    private boolean isNotEdge(int space) {
        return isHorizontalBorder(space) && isVerticalBorder(space);
    }
    private boolean isHorizontalBorder(int space) {
        return space >= 0xF0000000 && space <= 0xF;
    }
    private boolean isVerticalBorder(int space) {
        return ((space + 4) % 8 == 0) && space % 8 == 5;
    }

    private boolean checkMove(Move move) {
        if (inBounds(move.destination)) {
            if (spaceNotOccupied(move)) {
                //TODO if there are no jumps
                return true;
                //}
            }
            else if (checkJump(move)) {
                return true;
            }
        }
        return false;
    }

    private boolean inBounds(int dest) {
        return !(dest < 0 || dest > upperBound);
    }

    private boolean spaceNotOccupied(Move move) {
        return isNotOccupied(board.whitePos, move.destination) || isNotOccupied(board.blackPos, move.destination) || isNotOccupied(board.blackKingPos, move.destination) || isNotOccupied(board.whiteKingPos, move.destination);
    }

    private boolean isNotOccupied(int bucket, int dest) {
        return (bucket & dest) == 0;
    }
}