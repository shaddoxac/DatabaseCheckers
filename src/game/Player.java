package game;

public enum Player {
    BLACK, WHITE;

    public Player change() {
        if (this.equals(BLACK)) {
            return WHITE;
        }
        else {
            return BLACK;
        }
    }
}
