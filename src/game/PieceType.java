package game;

public enum PieceType {
    BLACK, WHITE, BLACKKING, WHITEKING;

    public Player getTeam() {
        if (this.equals(BLACK) || this.equals(BLACKKING)) {
            return Player.BLACK;
        }
        return Player.WHITE;
    }

    public boolean[] getMovementOptions() {
        boolean[] movementOptions=new boolean[2];
        if (this.equals(BLACKKING) || this.equals(WHITEKING) || this.equals(WHITE)) {
            movementOptions[0]=true;
        }
        if (this.equals(BLACKKING) || this.equals(WHITEKING) || this.equals(BLACK)) {
            movementOptions[1]=true;
        }
        return movementOptions;
    }
}