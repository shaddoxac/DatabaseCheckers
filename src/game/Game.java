package game;

import java.util.ArrayList;


public class Game {
    private Player currentTurn;

    public Board board;
    public boolean hasJumps;
    public boolean gameOver;
    public Player winner;
    public ArrayList<Move> currentMoves=new ArrayList<>();
    public ArrayList<Move> pieceMoves=new ArrayList<>();

    private int lastIndex=-2147483648;

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


    public void commitMove(Move move) {
        int bitBoard=getBitBoard(move.getType());
        bitBoard=bitBoard & ~move.getLocation();
        bitBoard=bitBoard | move.getDestination();
        setBitBoard(bitBoard, move.getType());
        if (hasJumps) {
            removePieces(move.getSequentialJumps());
        }
    }

    public void changeTurn() {
        currentTurn=currentTurn.other();
    }

    public void analyzeBoard() {
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

    public void getValidMoves(Piece piece) {
        pieceMoves.clear();
        if (piece.down) {
            checkDestination(piece, -5);
            checkDestination(piece, -4);
        }
        if (piece.up) {
            checkDestination(piece, 5);
            checkDestination(piece, 4);
        }
    }

    public boolean spaceOccupied(int dest) {
        return isOccupied(board.whitePos, dest) || isOccupied(board.blackPos, dest) || isOccupied(board.blackKingPos, dest) || isOccupied(board.whiteKingPos, dest);
    }

    public boolean spaceNotOccupied(int dest) {
        return !spaceOccupied(dest);
    }

    public boolean spacePlayerOccupied(int loc) {
        return !(isNotOccupied(board.blackPos, loc) || isNotOccupied(board.blackKingPos, loc));
    }

    public PieceType getPieceType(int dest) {
        if (isOccupied(board.whitePos, dest)) {return PieceType.WHITE;}
        if (isOccupied(board.blackPos, dest)) {return PieceType.BLACK;}
        if (isOccupied(board.whiteKingPos, dest)) {return PieceType.WHITEKING;}
        return PieceType.BLACKKING;
    }
    public int getBitRepresentation(int num) {
        return 1 << (num-1);
    }
    public int getNumRepresentation(int bits) {//make sure this doesn't edit important information
        int counter=1;
        while (bits>1) {
            bits=bits >> 1;
            counter++;
        }
        return counter;
    }

    private void gameOver(Player winner) {
        gameOver=true;
        this.winner=winner;
    }

    private void eraseNonJumpMoves() {
        for (int idx=0; idx<currentMoves.size(); idx++) {
            if (!currentMoves.get(idx).isJump()) {
                currentMoves.remove(idx);
                idx--;
            }
        }
    }

    private void analyzeGroup(int bitBoard, PieceType type) {
        int idx=1;
        int temp;
        while (idx!=lastIndex) {
            temp=idx & bitBoard;
            if (temp==idx) {
                getValidMoves(new Piece(type, idx));
            }
            idx=idx << 1;
        }
    }

    private void checkDestination(Piece piece, int offset) {
        int tempDestination=piece.location << offset;
        Move tempMove=new Move(piece.type, piece.location, tempDestination);
        checkMove(tempMove);
    }

    private void removePieces(ArrayList<Piece> jumps) {
        for (Piece jumped : jumps) {removePiece(jumped);}
    }

    private void removePiece(Piece piece) {
        int changedBit= ~piece.location;
        if (piece.type.equals(PieceType.BLACK)) {board.blackPos=board.blackPos & changedBit;}
        else if (piece.type.equals(PieceType.WHITE)) {board.whitePos=board.whitePos & changedBit;}
        else if (piece.type.equals(PieceType.BLACKKING)) {board.blackKingPos=board.blackKingPos & changedBit;}
        else {board.whiteKingPos=board.whiteKingPos & changedBit;}
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
        if (isNotEdge(move.getDestination())) {
            if (!nextSpaceOccupied(move)) {
                hasJumps=true;
                return true;
            }
        }
        return false;
    }

    private boolean nextSpaceOccupied(Move move) {
        int newDest=getJumpDestination(move);
        return spaceNotOccupied(newDest);
    }

    private int getJumpDestination(Move move) {//TODO check this
        if (move.getLocation() > move.getDestination()) {
            if (move.getLocation()-5==move.getDestination()) {
                return move.getDestination() >> 4;
            }
            else {
                return move.getDestination() >> 3;
            }
        }
        else {
            if (move.getLocation()+5==move.getDestination()) {
                return move.getDestination() << 4;
            }
            else {
                return move.getDestination() << 3;
            }
        }
    }

    private void checkMove(Move move) {
        if (inBounds(move.getDestination())) {
            System.out.println("inBounds");
            if (spaceNotOccupied(move.getDestination())) {
                move.setDestination(getJumpDestination(move));
                currentMoves.add(move);
                pieceMoves.add(move);
            }
            else if (checkJump(move)) {
                move.setJump(true);
                currentMoves.add(move);
                pieceMoves.add(move);
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
        return (dest>0 || dest==lastIndex);
    }

    private boolean isOccupied(int bucket, int dest) {
        return (bucket & dest) != 0;
    }
    private boolean isNotOccupied(int bucket, int dest) {return !isOccupied(bucket,dest);}

    private boolean isWhiteTurn() {
        return currentTurn.equals(Player.WHITE);
    }
}