package chess.figures;

import javax.swing.JButton;

/**
 * Class representing a Queen chess piece.
 *  * @Autor Yevhenii Shatov
 */
public class Queen extends ChessPiece {
    /**
     * Constructs a Queen object with the specified color and associated button.
     *
     * @param color  The color of the Queen ("White" or "Black").
     * @param button The JButton associated with this Queen piece.
     */
    public Queen(String color, JButton button) {
        super(color, button, 9); // Assigning value to Queen for evaluation
    }

    /**
     * Determines the valid moves for the Queen from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the Queen.
     * @param startCol The starting column of the Queen.
     * @return A 2D boolean array indicating valid moves (true) for the Queen.
     */
    @Override
    public boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol) {
        // Diagonal moves from Bishop logic
        boolean[][] validMoves = new Bishop(this.getColor(), this.getButton()).getValidMoves(board, startRow, startCol);
        // Straight moves from Rook logic
        boolean[][] straightMoves = new Rook(this.getColor(), this.getButton()).getValidMoves(board, startRow, startCol);

        // Combine moves from Bishop and Rook
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                validMoves[i][j] = validMoves[i][j] || straightMoves[i][j];
            }
        }
        return validMoves;
    }

    /**
     * Returns the symbol representing the Queen.
     *
     * @return The symbol "Q" for Queen.
     */
    @Override
    public String getSymbol() {
        return "Q";
    }

    /**
     * Returns the value of the Queen piece.
     * The value is overridden to 0 because the Queen's movement and influence on the game are better evaluated in context.
     *
     * @return The value of the Queen, which is 0.
     */
    @Override
    public int getValue() {
        return 9;
    }
}
