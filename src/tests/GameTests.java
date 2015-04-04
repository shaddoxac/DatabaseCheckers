package tests;

import game.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTests {
    Game game;


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
        assertEquals(game.getBitRepresentation(num1), num2);
        assertEquals(game.getNumRepresentation(num2), num1);
    }
}