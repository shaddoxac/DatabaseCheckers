package game;

//squares go left to right, top to bottom
public class Board {
    public int whitePos, blackPos, whiteKingPos, blackKingPos;
    private int whiteStartingPos=0xFFF00000;
    private int blackStartingPos=0x00000FFF;
    private int numDigits=8;

    public Board() {
        whitePos =0xFFF00000;
        blackPos =0x00000FFF;
        whiteKingPos =0;
        blackKingPos =0;
    }

    public String toBinaryString(int bitBoard) {
        int num=Integer.MIN_VALUE;
        int count = 0;
        while ((num & bitBoard) != num) {
            num = num >>> 1;
            count++;
        }
        String zeroes="";
        for (int idx=0; idx<count; idx++) {
            zeroes += "0";
        }
        return zeroes+Integer.toBinaryString(bitBoard);
    }

    public String toWhiteString() {return toBinaryString(whitePos);}
    public String toWhiteKingString() {return toBinaryString(whiteKingPos);}
    public String toBlackString() {
        return toBinaryString(blackPos);
    }
    public String toBlackKingString() {
        return toBinaryString(blackKingPos);
    }
}
