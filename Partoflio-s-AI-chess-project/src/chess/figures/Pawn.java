package chess.figures;

import javax.swing.JButton;

/**
 * Class representing a Pawn chess piece.
 *  * @Autor Yevhenii Shatov
 */
public class Pawn extends ChessPiece {
    /**
     * Constructs a Pawn object with the specified color and associated button.
     *
     * @param color  The color of the Pawn ("White" or "Black").
     * @param button The JButton associated with this Pawn piece.
     */
    public Pawn(String color, JButton button) {
        super(color, button, 1); // Assigning value to Pawn for evaluation
    }

    /**
     * Determines the valid moves for the Pawn from the given starting position on the board.
     *
     * @param board    The current chess board.
     * @param startRow The starting row of the Pawn.
     * @param startCol The starting column of the Pawn.
     * @return A 2D boolean array indicating valid moves (true) for the Pawn.
     */
    @Override
    public boolean[][] getValidMoves(ChessPiece[][] board, int startRow, int startCol) {
        boolean[][] validMoves = new boolean[8][8];
        int direction = this.getColor().equals("White") ? -1 : 1; // Direction the pawn moves (up for white, down for black)
        int start = this.getColor().equals("White") ? 6 : 1; // Starting row for white and black pawns

        // Standard forward moves
        if (startRow + direction >= 0 && startRow + direction < 8 && board[startRow + direction][startCol] == null) {
            validMoves[startRow + direction][startCol] = true;

            // Two-square move from the starting position
            if (startRow == start && board[startRow + 2 * direction][startCol] == null) {
                validMoves[startRow + 2 * direction][startCol] = true;
            }
        }

        // Capturing diagonally
        int[] captureMoves = {-1, 1}; // Diagonal left and right
        for (int move : captureMoves) {
            int newRow = startRow + direction;
            int newCol = startCol + move;
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(this.getColor())) {
                    validMoves[newRow][newCol] = true; // Capture opponent's piece
                } else if (board[newRow][newCol] == null) {
                    // En passant capture
                    int sideRow = this.getColor().equals("White") ? newRow + 1 : newRow - 1;
                    if (newCol >= 0 && newCol < 8 && board[sideRow][newCol] instanceof Pawn) {
                        Pawn adjacentPawn = (Pawn)board[sideRow][newCol];
                        if (adjacentPawn.getColor() != this.getColor() && adjacentPawn.getJustMovedTwoSquares()) {
                            validMoves[newRow][newCol] = true; // En passant capture
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    @Override
    public int getValue() {
        return 1;
    }

    /**
     * Returns the symbol representing the Pawn.
     *
     * @return The symbol "P" for Pawn.
     */
    @Override
    public String getSymbol() {
        return "P";
    }
}
