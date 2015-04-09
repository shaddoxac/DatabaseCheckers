package game;

import expertsystem.InferenceEngine;

import java.sql.SQLException;
import java.util.ArrayList;


public class Game {
    private Player currentTurn;
    private InferenceEngine ai;
    public Board board;
    public boolean hasJumps;
    public boolean gameOver;
    public Player winner;
    public ArrayList<Move> currentMoves=new ArrayList<>();
    public ArrayList<Move> pieceMoves=new ArrayList<>();

    private int lastIndex=0x80000000;

    public Game() {
        currentTurn=Player.BLACK;
        board=new Board();
        gameOver=false;
    }

    public Game(InferenceEngine ai) {
        currentTurn=Player.BLACK;
        board=new Board();
        this.ai=ai;
        gameOver=false;
    }

    public Move commitMove(Move move) {
        int bitBoard=getBitBoard(move.getType());
        bitBoard=bitBoard & ~move.getLocation();
        bitBoard=bitBoard | move.getDestination();
        System.out.println(getNumRepresentation(move.getDestination()));
        setBitBoard(bitBoard, move.getType());
        if (hasJumps) {
            removePieces(move.getSequentialJumps());
        }
        if (isHorizontalBorder(move.getDestination())) {
            makeKings();
        }
        return move;
    }
    
    public Move commitAIMove() {
        try {return commitMove(ai.getMove(board, currentMoves));}
        catch (SQLException e) {return null;}
    }
    
    private void makeKings() {
    	int blackPawns = board.blackPos;
    	int whitePawns = board.whitePos;
    	int blackKingRow = 0xF0000000;
    	int whiteKingRow = 0xF;
    	int newBlackKings = blackPawns & blackKingRow;
    	int newWhiteKings = whitePawns & whiteKingRow;
    	board.blackKingPos = board.blackKingPos | newBlackKings;
    	board.whiteKingPos = board.whiteKingPos | newWhiteKings;
    	board.blackPos = blackPawns & (blackPawns ^ board.blackKingPos);
    	board.whitePos = whitePawns & (whitePawns ^ board.whiteKingPos);
    }
    
    public boolean isPlayerTurn() {
    	return currentTurn.equals(Player.BLACK);
    }

    public void changeTurn() {
        currentTurn=currentTurn.other();
        analyzeBoard();
    }

    public void analyzeBoard() {
        hasJumps=false;
        currentMoves.clear();
        if (isPlayerTurn()) {
            analyzeGroup(board.blackPos, PieceType.BLACK);
            analyzeGroup(board.blackKingPos, PieceType.BLACKKING);
        }
        else {
            analyzeGroup(board.whitePos,PieceType.WHITE);
            analyzeGroup(board.whiteKingPos,PieceType.WHITEKING);
        }
        if (currentMoves.size()==0) {gameOver(currentTurn.other());}
        else if (hasJumps) {
            System.out.println("hasjumps");
            eraseNonJumpMoves(currentMoves);
        }
    }
    public void getMovesForPiece(Piece piece) {
        getValidMoves(piece);
        if (hasJumps) {eraseNonJumpMoves(pieceMoves);}
    }

    public boolean spaceOccupied(int dest) {
        return spaceOccupiedByTeam(Player.WHITE,dest) || spaceOccupiedByTeam(Player.BLACK,dest);
    }

    public boolean spaceNotOccupied(int dest) {
        return !spaceOccupied(dest);
    }

    public boolean spaceOccupiedByTeam(Player team, int loc) {
        if (team.equals(Player.BLACK)) {
            return (isOccupied(board.blackPos, loc) || isOccupied(board.blackKingPos, loc));
        }
        return (isOccupied(board.whitePos, loc) || isOccupied(board.whiteKingPos, loc));
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
    public int getNumRepresentation(int bits) {
        if (bits==lastIndex) {return 32;}
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

    private void eraseNonJumpMoves(ArrayList<Move> list) {
        for (int idx=0; idx<list.size(); idx++) {
            if (!list.get(idx).isJump()) {
                list.remove(idx);
                idx--;
            }
        }
    }

    private void getValidMoves(Piece piece) {
        pieceMoves.clear();
        if (piece.down) {
            if (inOddRow(piece.location)) {
                if (!isLeftBorder(piece.location)) {
                    checkDestination(piece, -3);
                }
            }
            else {
                if (!isRightBorder(piece.location)) {
                    checkDestination(piece, -5);
                }
            }
            checkDestination(piece, -4);
        }
        if (piece.up) {
            if (inOddRow(piece.location)) {
                if (!isLeftBorder(piece.location)) {
                    checkDestination(piece, 5);
                }
            }
            else {
                if (!isRightBorder(piece.location)) {
                    checkDestination(piece, 3);
                }
            }
            checkDestination(piece, 4);
        }
    }

    private void analyzeGroup(int bitBoard, PieceType type) {
        boolean cond=true;
        int idx=1;
        int temp;
        while (cond) {
            if (idx==lastIndex) {
                cond=false;
            }
            temp=idx & bitBoard;
            if (temp!=0) {
                getValidMoves(new Piece(type, idx));
            }
            idx= idx << 1;
        }
    }

    private void checkDestination(Piece piece, int offset) {
        int tempDestination;
        if (offset > 0) {
            tempDestination=piece.location << offset;
        }
        else {
            tempDestination=piece.location >>> (-1*offset);
        }
        Move tempMove=new Move(piece.type, piece.location, tempDestination);
        checkMove(tempMove);
    }

    private void removePieces(ArrayList<Piece> jumps) {
        for (Piece jumped : jumps) {System.out.println("jumped loc= "+getNumRepresentation(jumped.location)); removePiece(jumped);}
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
        if (isNotEdge(move.getDestination()) && isEnemyOccupied(move)) {
            if (!spaceAfterJumpOccupied(move)) {
                addJump(move);
                return true;
            }
        }
        return false;
    }

    private void addJump(Move move) {
        hasJumps=true;
        move.addJump(getPieceType(move.getDestination()),move.getDestination());
        int newDest=getJumpDestination(move);
        System.out.println("newDest= "+getNumRepresentation(newDest));
        move.setDestination(newDest);
    }

    private boolean isEnemyOccupied(Move move) {
        Player otherTeam=move.getType().getTeam().other();
        return spaceOccupiedByTeam(otherTeam, move.getDestination());
    }
    private boolean spaceAfterJumpOccupied(Move move) {
        int newDest=getJumpDestination(move);
        return spaceOccupied(newDest);
    }

    private int getJumpDestination(Move move) {
        int loc=move.getLocation();
        int dest=move.getDestination();
        System.out.println("loc= "+getNumRepresentation(loc));
        System.out.println("dest= "+getNumRepresentation(dest));
        if (loc > dest) {
            if (inOddRow(dest)) {
                if ((loc >>> 5) == dest) {
                    return dest >>> 4;
                } else {
                    return dest >>> 3;
                }
            }
            else {
                if ((loc >>> 4) == dest) {
                    return dest >>> 5;
                } else {
                    return dest >>> 4;
                }
            }
        }
        else {
            if (inOddRow(dest)) {
                if ((loc << 4) == dest) {
                    return dest << 5;
                } else {
                    return dest << 4;
                }
            }
            else {
                if ((loc << 5) == dest) {
                    return dest << 4;
                } else {
                    return dest << 3;
                }
            }
        }
    }

    private void checkMove(Move move) {
        if (inBounds(move.getDestination())) {
        	if (checkJump(move)) {
                move.setJump(true);
                currentMoves.add(move);
                pieceMoves.add(move);
            }
        	else if (spaceNotOccupied(move.getDestination())) {
                currentMoves.add(move);
                pieceMoves.add(move);
            }
            
        }
    }


    private boolean isEdge(int space) {
        return isHorizontalBorder(space) || isVerticalBorder(space);
    }
    private boolean isNotEdge(int space) {return !isEdge(space);}
    private boolean isHorizontalBorder(int space) {
        return (space & 0xF000000F) !=0;
    }
    private boolean isVerticalBorder(int space) {return isLeftBorder(space) || isRightBorder(space);}
    private boolean isLeftBorder(int space) {return (space & 0x8080808)!=0;}
    private boolean isRightBorder(int space) {return (space & 0x10101010)!=0;}

    private boolean inOddRow(int space) {
        return (space & 0xF0F0F0F)!=0;
    }

    private boolean inBounds(int dest) {
        return (dest>0 || dest==lastIndex);
    }

    private boolean isOccupied(int bucket, int dest) {
        return (bucket & dest) != 0;
    }
    private boolean isNotOccupied(int bucket, int dest) {return !isOccupied(bucket,dest);}
}