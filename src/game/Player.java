package game;

public enum Player {
    BLACK, WHITE;

    public Player other() {
        if (this.equals(BLACK)) {
            return WHITE;
        }
        else {
            return BLACK;
        }
    }
}
