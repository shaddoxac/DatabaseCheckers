package game;

import java.util.ArrayList;


public class Game {
    private Player currentTurn;
    private Board board;
    private boolean hasJumps;

    public boolean gameOver;
    public Player winner;
    public ArrayList<Move> currentMoves=new ArrayList<Move>();

    private int upperBound=0xFFF00000;

    public Game() {
        currentTurn=Player.BLACK;
        board=new Board();
        gameOver=false;
    }

    public Game(Board board) {
        currentTurn=Player.WHITE;
        gameOver=true;
        this.board=board;
    }


    public void changeTurn() {
        currentTurn=currentTurn.other();
    }


    private void analyzeBoard() {
        hasJumps=false;
        if (isWhiteTurn()) {
            analyzeGroup(board.whitePos,PieceType.WHITE);
            analyzeGroup(board.whiteKingPos,PieceType.WHITEKING);
        }
        else {
            analyzeGroup(board.blackPos, PieceType.BLACK);
            analyzeGroup(board.blackKingPos, PieceType.BLACKKING);
        }
        if (currentMoves.size()==0) {gameOver(currentTurn.other());}
        else if (hasJumps) {
            eraseNonJumpMoves();
        }
    }

    private void gameOver(Player winner) {
        gameOver=true;
        this.winner=winner;
    }

    private void eraseNonJumpMoves() {
        for (int idx=0; idx<currentMoves.size(); idx++) {
            if (!currentMoves.get(idx).isJump) {
                currentMoves.remove(idx);
                idx--;
            }
        }
    }

    private void analyzeGroup(int bitBoard, PieceType type) {
        int idx=1;
        int comparator;
        while (idx<bitBoard) {
            System.out.println("idx="+idx);
            comparator=idx & bitBoard;
            if (comparator!=0) {
                getValidMoves(new Piece(type,idx));
            }
            idx=idx>>1;
        }
    }

    private void getValidMoves(Piece piece) {
        if (piece.down) {
            checkDestination(piece, -5);
            checkDestination(piece, -4);
        }
        if (piece.up) {
            checkDestination(piece, 5);
            checkDestination(piece, 4);
        }
    }

    private void checkDestination(Piece piece, int offset) {
        int tempDestination=piece.location << offset;
        Move tempMove=new Move(piece.type, piece.location, tempDestination);
        checkMove(tempMove);
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
                hasJumps=true;
                return true;
            }
        }
        return false;
    }

    private boolean nextSpaceOccupied(Move move) {
        int newDest=getJumpDestination(move);
        return spaceNotOccupied(new Move(move.getType(), move.getLocation(), newDest));
    }

    private int getJumpDestination(Move move) {
        if (move.getLocation() > move.destination) {
            if (move.getLocation()-5==move.destination) {
                return move.destination-4;
            }
            else {
                return move.destination-3;
            }
        }
        else {
            if (move.getLocation()+5==move.destination) {
                return move.destination+4;
            }
            else {
                return move.destination+3;
            }
        }
    }

    private void checkMove(Move move) {
        if (inBounds(move.destination)) {
            if (spaceNotOccupied(move)) {
                currentMoves.add(move);
            }
            else if (checkJump(move)) {
                move.isJump=true;
                currentMoves.add(move);
            }
        }
    }


    private boolean isNotEdge(int space) {
        return isHorizontalBorder(space) && isVerticalBorder(space);
    }
    private boolean isHorizontalBorder(int space) {
        return space >= 0xF0000000 || space <= 0xF;
    }
    private boolean isVerticalBorder(int space) {
        return (space & 0x181800)!=0;
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

    private boolean isWhiteTurn() {
        return currentTurn.equals(Player.WHITE);
    }
}