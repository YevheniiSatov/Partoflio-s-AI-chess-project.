package chess.figures;

import javax.swing.JButton;

/**
 * Class representing a King chess piece.
 *  * @Autor Yevhenii Shatov
 */
public class King extends ChessPiece {
    /**
     * Constructs a King object with the specified color and associated button.
     *
     * @param color  The color of the King ("White" or "Black").
     * @param button The JButton associated with this King piece.
     */
    public King(String color, JButton button) {
        super(color, button, 100); // Value assigned to King for evaluation
    }

    /**
     * Determines the valid moves for the King from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the King.
     * @param startCol The starting column of the King.
     * @return A 2D boolean array indicating valid moves (true) for the King.
     */
    @Override
    public boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol) {
        boolean[][] validMoves = new boolean[8][8];
        int[][] directions = {{1, 1}, {1, 0}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}}; // All possible directions for King

        // Check each possible direction for valid moves
        for (int[] dir : directions) {
            int x = startRow + dir[0];
            int y = startCol + dir[1];
            if (x >= 0 && x < 8 && y >= 0 && y < 8) { // Ensure move is within board bounds
                if (board[x][y] == null || !board[x][y].getColor().equals(this.getColor())) {
                    validMoves[x][y] = true; // Valid move if the square is empty or contains opponent's piece
                }
            }
        }
        return validMoves;
    }

    /**
     * Returns the symbol representing the King.
     *
     * @return The symbol "K" for King.
     */
    @Override
    public String getSymbol() {
        return "K";
    }

    /**
     * Returns the value of the King piece.
     * The value is overridden to 0 because the King is invaluable in terms of standard evaluation.
     *
     * @return The value of the King, which is 0.
     */
    @Override
    public int getValue() {
        return 100;
    }
}
