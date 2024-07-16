package chess.figures;

import javax.swing.JButton;

/**
 * Class representing a Bishop chess piece.
 *  * @Autor Yevhenii Shatov
 */
public class Bishop extends ChessPiece {
    /**
     * Constructs a Bishop object with the specified color and associated button.
     *
     * @param color  The color of the Bishop ("White" or "Black").
     * @param button The JButton associated with this Bishop piece.
     */
    public Bishop(String color, JButton button) {
        super(color, button, 3);
    }

    /**
     * Determines the valid moves for the Bishop from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the Bishop.
     * @param startCol The starting column of the Bishop.
     * @return A 2D boolean array indicating valid moves (true) for the Bishop.
     */
    @Override
    public boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol) {
        boolean[][] validMoves = new boolean[8][8];
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // Diagonal directions

        // Iterate over each diagonal direction
        for (int[] dir : directions) {
            int x = startRow;
            int y = startCol;
            while (true) {
                x += dir[0];
                y += dir[1];
                if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                    break; // Check if out of bounds
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

    @Override
    public int getValue() {
        return 3;
    }

    /**
     * Returns the symbol representing the Bishop.
     *
     * @return The symbol "B" for Bishop.
     */
    @Override
    public String getSymbol() {
        return "B";
    }
}
