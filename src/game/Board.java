package game;

//squares go left to right, top to bottom
public class Board {
    int whitePos, blackPos, whiteKingPos, blackKingPos;
    private int whiteStartingPos=0xFFF00000;
    private int blackStartingPos=0x00000FFF;

    public Board() {
        whitePos =0xFFF00000;
        blackPos =0x00000FFF;
        whiteKingPos =0;
        blackKingPos =0;
    }
}
