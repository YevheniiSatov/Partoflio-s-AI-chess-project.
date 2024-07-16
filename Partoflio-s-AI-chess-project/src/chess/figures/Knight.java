package chess.figures;

import javax.swing.JButton;

/**
 * Class representing a Knight chess piece.
 *  * @Autor Yevhenii Shatov
 */
public class Knight extends ChessPiece {
    /**
     * Constructs a Knight object with the specified color and associated button.
     *
     * @param color  The color of the Knight ("White" or "Black").
     * @param button The JButton associated with this Knight piece.
     */
    public Knight(String color, JButton button) {
        super(color, button, 3); // Assigning value to Knight for evaluation
    }

    /**
     * Determines the valid moves for the Knight from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the Knight.
     * @param startCol The starting column of the Knight.
     * @return A 2D boolean array indicating valid moves (true) for the Knight.
     */
    @Override
    public boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol) {
        boolean[][] validMoves = new boolean[8][8];
        int[][] moveOffsets = {
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        }; // Possible L-shaped moves for the Knight

        // Check each possible move for validity
        for (int[] offset : moveOffsets) {
            int newRow = startRow + offset[0];
            int newCol = startCol + offset[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) { // Ensure move is within board bounds
                if (board[newRow][newCol] == null || !board[newRow][newCol].getColor().equals(this.getColor())) {
                    validMoves[newRow][newCol] = true; // Valid move if the square is empty or contains opponent's piece
                    // System.out.println("Valid knight move from (" + startRow + ", " + startCol + ") to (" + newRow + ", " + newCol + ")");
                }
            }
        }

        return validMoves;
    }

    /**
     * Returns the symbol representing the Knight.
     *
     * @return The symbol "N" for Knight.
     */
    @Override
    public String getSymbol() {
        return "N"; // The typical symbol for a knight in chess notation
    }

    /**
     * Returns the value of the Knight piece.
     * The value is overridden to 0 because the Knight's movement and influence on the game are better evaluated in context.
     *
     * @return The value of the Knight, which is 0.
     */
    @Override
    public int getValue() {
        return 3;
    }
}
