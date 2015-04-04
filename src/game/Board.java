package game;

//squares go left to right, top to bottom
public class Board {
    public int whitePos, blackPos, whiteKingPos, blackKingPos;
    private int whiteStartingPos=0xFFF00000;
    private int blackStartingPos=0x00000FFF;

    public Board() {
        whitePos =0xFFF00000;
        blackPos =0x00000FFF;
        whiteKingPos =0;
        blackKingPos =0;
    }

    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(getStringOf(whitePos));
        sb.append(" ");
        sb.append(getStringOf(blackPos));
        sb.append(" ");
        sb.append(getStringOf(whiteKingPos));
        sb.append(" ");
        sb.append(getStringOf(blackKingPos));
        return sb.toString();
    }

    private String getStringOf(int num) {
        StringBuilder sb=new StringBuilder();
        int tempNum=num;
        int value;
        while (0 < tempNum) {
            value=tempNum & 1;
            sb.append(value);
            tempNum=tempNum >> 1;
        }
        sb.reverse();
        return sb.toString();
    }
}
