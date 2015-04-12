package tests;

import game.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class GameTests {
    Game game;
    Random rand=new Random();


    @Before
    public void initialize() {
        game=new Game();
    }

    @Test
    public void testMove() {
        game.analyzeBoard();
        assertTrue(game.currentMoves.size()>0);
        assertFalse(game.hasJumps);
    }

    @Test
    public void testConversions() {
        int num1=2;
        int num2=0x2;
        compareNumAndBit(num1,num2);
        num1=5;
        num2=0x10;
        compareNumAndBit(num1,num2);
    }

    @Test
    public void testCommitting() {
        getValidMoves(PieceType.BLACK, 11);
        game.commitMove(getRandomMove());
        checkNotOccupied(11);
        checkOneIsOccupied(bits(15), bits(16));
        changeTurn();
        if (game.spaceOccupied(bits(15))) {
            getValidMoves(PieceType.BLACK, 10);
            assertEquals(game.pieceMoves.size(), 1);
            assertEquals(game.pieceMoves.get(0).getDestination(), bits(14));
        }
        else {
            getValidMoves(PieceType.BLACK, 12);
            assertEquals(game.pieceMoves.size(), 0);
        }
    }

    @Test
    public void testVictory() {
        setBitBoard(0, PieceType.WHITE);
        changeTurn();
        checkGameOver();

        game=new Game();
        setBitBoard(bits(5), PieceType.WHITE);
        setBitBoard(bits(1), PieceType.BLACK);
        changeTurn();
        checkGameOver();
    }

    @Test
    public void testStart() {
        for (int i=1; i<=12; i++) {
            checkInSpace(Player.BLACK, i);
        }
        for (int i=21; i<=32; i++) {
            checkInSpace(Player.WHITE, i);
        }
    }

    @Test
    public void testMoving() {
        setBitBoard(bits(1), PieceType.BLACK);
        setBitBoard(bits(32), PieceType.WHITE);
        commitMove(PieceType.BLACK, 1,5);
        checkNotOccupied(1);
        checkOccupied(5);
        commitMove(PieceType.BLACK, 5,9);
        checkNotOccupied(5);
        checkOccupied(9);

        setBitBoard(bits(4), PieceType.BLACK);
        commitMove(PieceType.BLACK, 4,8);
        checkNotOccupied(4);
        checkOccupied(8);
        commitMove(PieceType.BLACK, 8,12);
        checkNotOccupied(8);
        checkOccupied(12);
    }

    @Test
    public void testJump() {
        setBitBoard(bits(1), PieceType.BLACK);
        setBitBoard(bits(6), PieceType.WHITE);
        getValidMoves(PieceType.BLACK, 1);
        assertTrue(game.hasJumps);
        game.commitMove(getRandMove(game.pieceMoves));
        checkInSpace(Player.BLACK, 10);
        checkNotOccupied(6);
        checkNotOccupied(1);
        changeTurn();
        checkGameOver();

        game=new Game();
        setBitBoard(bits(27), PieceType.BLACK);
        setBitBoard(bits(31), PieceType.WHITE);
        changeTurn();
        getValidMoves(PieceType.WHITE, 31);
        assertTrue(game.hasJumps);
        game.commitMove(getRandMove(game.pieceMoves));
        checkInSpace(Player.WHITE, 24);
        checkNotOccupied(27);
        checkNotOccupied(31);
        changeTurn();
        checkGameOver();

        game=new Game();
        setBitBoard(bits(3) + bits(7), PieceType.BLACK);
        getValidMoves(PieceType.BLACK, 3);
        assertFalse(game.hasJumps);
        game.commitMove(getRandMove(game.pieceMoves));
        checkInSpace(Player.BLACK, 8);
        checkInSpace(Player.BLACK, 7);
        checkNotOccupied(3);
        changeTurn();
    }

    @Test
    public void testDoubleJumps() {
        Move tempMove;
        setBitBoard(bits(1), PieceType.BLACK);
        setBitBoard(bits(6) + bits(14), PieceType.WHITE);
        getValidMoves(PieceType.BLACK, 1);
        assertTrue(game.hasJumps);
        tempMove=getMoveWithDest(game.pieceMoves, 17);
        if (tempMove==null) {fail();}
        game.commitMove(tempMove);
        checkInSpace(Player.BLACK, 17);
        checkNotOccupied(6);
        checkNotOccupied(14);
        changeTurn();
        checkGameOver();

        game=new Game();
        setBitBoard(0, PieceType.BLACK);
        setBitBoard(bits(27)+bits(19)+bits(18), PieceType.WHITE);
        setBitBoard(bits(31), PieceType.BLACKKING);
        getValidMoves(PieceType.BLACKKING, 31);
        assertTrue(game.hasJumps);
        tempMove=getMoveWithDest(game.pieceMoves, 22);
        if (tempMove==null) {fail();}
        game.commitMove(tempMove);
        checkInSpace(Player.BLACK, 22);
        checkNotOccupied(27);
        checkNotOccupied(19);
        checkNotOccupied(18);
        changeTurn();
        checkGameOver();
    }

    @Test
    public void testMakingKings() {
        setBitBoard(bits(28), PieceType.BLACK);
        setBitBoard(bits(6), PieceType.WHITE);
        commitMove(PieceType.BLACK,28,32);
        commitMove(PieceType.WHITE,6,2);
        assertFalse(game.blackOccupied(bits(32)));
        assertFalse(game.whiteOccupied(bits(2)));
        assertTrue(game.blackKingOccupied(bits(32)));
        assertTrue(game.whiteKingOccupied(bits(2)));
    }

    @Test
    public void testIllegalMove() {

    }


    private int bits(int space) {return game.getBitRepresentation(space);}
    private int num(int space) {return game.getNumRepresentation(space);}

    private void changeTurn() {
        game.changeTurn();
    }

    private void getValidMoves(PieceType type, int location) {
        Piece piece=new Piece(type, bits(location));
        game.getMovesForPiece(piece);
    }

    private void commitMove(PieceType type, int loc, int dest) {
        game.commitMove(new Move(type, bits(loc), bits(dest)));
    }

    private Move getRandMove(ArrayList<Move> list) {
        int index=rand.nextInt(list.size());
        return list.get(index);
    }

    private Move getMoveWithDest(ArrayList<Move> list, int dest) {
        for (int idx=0; idx<list.size(); idx++) {
            Move move=list.get(idx);
            System.out.println(num(move.getDestination()));
            if (move.getDestination()==bits(dest)) {
                return move;
            }
        }
        return null;
    }


    private void setBitBoard(int bitBoard, PieceType type) {
        game.setBitBoard(bitBoard, type);
    }

    private Move getRandomMove() {
        int index=rand.nextInt(game.pieceMoves.size());
        return game.pieceMoves.get(index);
    }

    private void checkOneIsOccupied(int loc1, int loc2) {
        if (game.spaceNotOccupied(loc1) && game.spaceNotOccupied(loc2)) {
            fail();
        }
    }

    private void checkOccupied(int loc) {assertTrue(game.spaceOccupied(bits(loc)));}
    private void checkNotOccupied(int loc) {
        assertTrue(game.spaceNotOccupied(bits(loc)));
    }

    private void compareNumAndBit(int num1, int num2) {
        assertEquals(game.getBitRepresentation(num1), num2);
        assertEquals(game.getNumRepresentation(num2), num1);
    }

    private void checkInSpace(Player player, int space) {
        assertTrue(game.spaceOccupiedByTeam(player, bits(space)));
    }

    private void checkGameOver() {
        assertTrue(game.gameOver);
    }
}