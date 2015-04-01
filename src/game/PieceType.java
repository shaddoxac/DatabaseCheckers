package game;

public enum PieceType {
    BLACK, WHITE, BLACKKING, WHITEKING;

    public Player getTeam() {
        if (this.equals(BLACK) || this.equals(BLACKKING)) {
            return Player.BLACK;
        }
        return Player.WHITE;
    }
}
