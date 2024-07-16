package chess.ai;

/**
 * Class representing a chess move from one position to another.
 *  * @Autor Yevhenii Shatov
 */
public class Move {
    private int startX; // The starting x-coordinate of the move
    private int startY; // The starting y-coordinate of the move
    private int endX;   // The ending x-coordinate of the move
    private int endY;   // The ending y-coordinate of the move

    /**
     * Constructs a Move object with the specified start and end coordinates.
     *
     * @param startX The starting x-coordinate of the move.
     * @param startY The starting y-coordinate of the move.
     * @param endX   The ending x-coordinate of the move.
     * @param endY   The ending y-coordinate of the move.
     */
    public Move(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * Gets the starting x-coordinate of the move.
     *
     * @return The starting x-coordinate.
     */
    public int getStartX() {
        return this.startX;
    }

    /**
     * Gets the starting y-coordinate of the move.
     *
     * @return The starting y-coordinate.
     */
    public int getStartY() {
        return this.startY;
    }

    /**
     * Gets the ending x-coordinate of the move.
     *
     * @return The ending x-coordinate.
     */
    public int getEndX() {
        return this.endX;
    }

    /**
     * Gets the ending y-coordinate of the move.
     *
     * @return The ending y-coordinate.
     */
    public int getEndY() {
        return this.endY;
    }
}
