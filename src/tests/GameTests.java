package tests;

import game.*;
import org.junit.Before;
import org.junit.Test;

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
        getValidMoves(PieceType.BLACK, bits(11));
        game.commitMove(getRandomMove());
        checkNotOccupied(bits(11));
        checkOccupied(bits(15), bits(16));
        changeTurn();
        if (game.spaceOccupied(bits(15))) {
            getValidMoves(PieceType.BLACK, bits(10));
            assertEquals(game.pieceMoves.size(), 1);
            assertEquals(game.pieceMoves.get(0).getDestination(), bits(14));
        }
        else {
            getValidMoves(PieceType.BLACK, bits(12));
            assertEquals(game.pieceMoves.size(),0);
        }
    }


    private int bits(int space) {
        return game.getBitRepresentation(space);
    }
    private void changeTurn() {
        game.changeTurn();
    }
    private void getValidMoves(PieceType type, int location) {
        Piece piece=new Piece(type, location);
        game.getValidMoves(piece);
    }
    private Move getRandomMove() {
        int index=rand.nextInt(game.pieceMoves.size());
        return game.pieceMoves.get(index);
    }

    private void checkOccupied(int loc1, int loc2) {
        if (game.spaceNotOccupied(loc1) && game.spaceNotOccupied(loc2)) {
            fail();
        }
    }
    private void checkNotOccupied(int loc) {
        assertTrue(game.spaceNotOccupied(loc));
    }
    private void compareNumAndBit(int num1, int num2) {
        assertEquals(game.getBitRepresentation(num1), num2);
        assertEquals(game.getNumRepresentation(num2), num1);
    }
}