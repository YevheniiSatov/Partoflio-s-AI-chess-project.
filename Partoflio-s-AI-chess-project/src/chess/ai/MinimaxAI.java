package chess.ai;

import chess.figures.ChessPiece;

/**
 * Class implementing a chess AI using the Minimax algorithm with alpha-beta pruning.
 * @Autor Yevhenii Shatov
 */
public class MinimaxAI {
    private static final int MAX_DEPTH = 3; // Maximum search depth

    /**
     * Finds the best move for the current player.
     *
     * @param board              The current chess board.
     * @param isMaximizingPlayer A flag indicating if the current player is the maximizing player.
     * @return The best move for the current player.
     */
    public static Move findBestMove(ChessPiece[][] board, boolean isMaximizingPlayer) {
        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = null;

        // Iterate over all pieces on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].getColor().equals(isMaximizingPlayer ? "White" : "Black")) {
                    boolean[][] validMoves = board[i][j].getValidMoves(board, i, j);
                    // Iterate over all valid moves for the current piece
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (validMoves[k][l]) {
                                ChessPiece capturedPiece = board[k][l];
                                board[k][l] = board[i][j];
                                board[i][j] = null;

                                // Recursive call to minimax to evaluate the move
                                int score = minimax(board, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, !isMaximizingPlayer);

                                // Undo the move
                                board[i][j] = board[k][l];
                                board[k][l] = capturedPiece;

                                // Update the best move if the current move is better
                                if ((isMaximizingPlayer && score > bestScore) || (!isMaximizingPlayer && score < bestScore)) {
                                    bestScore = score;
                                    bestMove = new Move(i, j, k, l);
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    /**
     * Implements the minimax algorithm with alpha-beta pruning to evaluate the board.
     *
     * @param board              The current chess board.
     * @param depth              The current search depth.
     * @param alpha              The alpha value for pruning.
     * @param beta               The beta value for pruning.
     * @param isMaximizingPlayer A flag indicating if the current player is the maximizing player.
     * @return The evaluation score of the board.
     */
    private static int minimax(ChessPiece[][] board, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        // Base case: if maximum depth is reached or game over
        if (depth == 0 /* || game over */) {
            return evaluate(board);
        }

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Iterate over all pieces on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].getColor().equals(isMaximizingPlayer ? "White" : "Black")) {
                    boolean[][] validMoves = board[i][j].getValidMoves(board, i, j);
                    // Iterate over all valid moves for the current piece
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (validMoves[k][l]) {
                                ChessPiece capturedPiece = board[k][l];
                                board[k][l] = board[i][j];
                                board[i][j] = null;

                                // Recursive call to minimax to evaluate the move
                                int score = minimax(board, depth - 1, alpha, beta, !isMaximizingPlayer);

                                // Undo the move
                                board[i][j] = board[k][l];
                                board[k][l] = capturedPiece;

                                if (isMaximizingPlayer) {
                                    bestScore = Math.max(bestScore, score);
                                    alpha = Math.max(alpha, bestScore);
                                } else {
                                    bestScore = Math.min(bestScore, score);
                                    beta = Math.min(beta, bestScore);
                                }

                                // Prune the search tree if alpha-beta range is invalid
                                if (beta <= alpha) {
                                    return bestScore;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestScore;
    }

    /**
     * Evaluates the current board, returning the evaluation score for the maximizing player.
     *
     * @param board The current chess board.
     * @return The evaluation score of the board.
     */
    private static int evaluate(ChessPiece[][] board) {
        int whiteScore = 0;
        int blackScore = 0;

        // Simple material evaluation
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor().equals("White")) {
                        whiteScore += board[i][j].getValue();
                    } else {
                        blackScore += board[i][j].getValue();
                    }
                }
            }
        }

        return whiteScore - blackScore;
    }
}
