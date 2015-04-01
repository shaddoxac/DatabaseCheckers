package game;

//squares go left to right, top to bottom
public class Board {
    int white, black, whiteKing, blackKing;

    public Board() {
        white=0xFFF00000;
        black=0x00000FFF;
        whiteKing=0;
        blackKing=0;
    }
}
