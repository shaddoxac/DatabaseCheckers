package game;

public enum Piece {
    BLACK, WHITE, BLACKKING, WHITEKING;

    public Player getTeam() {
        if (this.equals(BLACK) || this.equals(BLACKKING)) {
            return Player.BLACK;
        }
        return Player.WHITE;
    }
}
