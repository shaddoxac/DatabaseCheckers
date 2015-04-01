package game;


public class Game {
    private int white, black, whiteKing, blackKing;
    private int whiteStartingPos=0xFFF00000;
    private int blackStartingPos=0x00000FFF;

    public Game() {
        setBoard();
    }


    private void setBoard() {
        white=whiteStartingPos;
        black=blackStartingPos;
        whiteKing=0;
        blackKing=0;
    }

    private boolean checkMove() {
        return true; //TODO
    }
}
