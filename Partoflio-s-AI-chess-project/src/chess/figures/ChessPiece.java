package chess.figures;

import javax.swing.JButton;

/**
 * Abstract class representing a chess piece.
 *  * @Autor Yevhenii Shatov
 */
public abstract class ChessPiece {
    private String color; // Color of the piece ("White" or "Black")
    private JButton button; // GUI component representing the piece on the board
    private boolean justMovedTwoSquares = false; // Specific for pawn movement tracking for en passant
    private int value; // Value of the piece on the board

    /**
     * Constructs a ChessPiece object with the specified color, associated button, and value.
     *
     * @param color  The color of the piece ("White" or "Black").
     * @param button The JButton associated with this piece.
     * @param value  The value of the piece.
     */
    public ChessPiece(String color, JButton button, int value) {
        this.color = color;
        this.button = button;
        this.value = value;
    }

    /**
     * Determines the valid moves for the piece from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the piece.
     * @param startCol The starting column of the piece.
     * @return A 2D boolean array indicating valid moves (true) for the piece.
     */
    public abstract boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol);

    /**
     * Returns the symbol representing the piece on the board.
     *
     * @return The symbol representing the piece.
     */
    public abstract String getSymbol();

    /**
     * Gets the color of the piece.
     *
     * @return The color of the piece.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Sets whether this piece has just moved two squares (used by pawns for en passant).
     *
     * @param moved A flag indicating if the piece has just moved two squares.
     */
    public void setJustMovedTwoSquares(boolean moved) {
        this.justMovedTwoSquares = moved;
    }

    /**
     * Checks if this piece has just moved two squares.
     *
     * @return True if the piece has just moved two squares, otherwise false.
     */
    public boolean getJustMovedTwoSquares() {
        return this.justMovedTwoSquares;
    }

    /**
     * Gets the value of the piece.
     *
     * @return The value of the piece.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the JButton associated with this piece.
     *
     * @return The JButton associated with this piece.
     */
    public JButton getButton() {
        return this.button;
    }
}
