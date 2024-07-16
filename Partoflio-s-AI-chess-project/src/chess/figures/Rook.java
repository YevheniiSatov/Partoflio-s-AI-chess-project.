package chess.figures;

import javax.swing.JButton;

/**
 * Class representing a Rook chess piece.
 *  * @Autor Yevhenii Shatov
 */
public class Rook extends ChessPiece {
    /**
     * Constructs a Rook object with the specified color and associated button.
     *
     * @param color  The color of the Rook ("White" or "Black").
     * @param button The JButton associated with this Rook piece.
     */
    public Rook(String color, JButton button) {
        super(color, button, 5); // Assigning value to Rook for evaluation
    }

    /**
     * Determines the valid moves for the Rook from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the Rook.
     * @param startCol The starting column of the Rook.
     * @return A 2D boolean array indicating valid moves (true) for the Rook.
     */
    @Override
    public boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol) {
        boolean[][] validMoves = new boolean[8][8];
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Horizontal and vertical directions

        // Check each possible direction for valid moves
        for (int[] dir : directions) {
            int x = startRow;
            int y = startCol;
            while (true) {
                x += dir[0];
                y += dir[1];
                if (x < 0 || x >= 8 || y < 0 || y >= 8) { // Ensure move is within board bounds
                    break;
                }
                if (board[x][y] == null) {
                    validMoves[x][y] = true; // Empty square
                } else {
                    if (!board[x][y].getColor().equals(this.getColor())) {
                        validMoves[x][y] = true; // Capture opponent's piece
                    }
                    break; // Blocked by a piece
                }
            }
        }
        return validMoves;
    }

    /**
     * Returns the symbol representing the Rook.
     *
     * @return The symbol "R" for Rook.
     */
    @Override
    public String getSymbol() {
        return "R";
    }

    /**
     * Returns the value of the Rook piece.
     *
     * @return The value of the Rook, which is 5.
     */
    @Override
    public int getValue() {
        return 5;
    }
}
