/**
 * Class providing a dialog to select the game mode at the start of the game.
 * This class displays a dialog box with three options: playing with a friend on the same computer,
 * playing remotely, or playing against an AI. The selected option is returned as an integer.
 *  * @Autor Yevhenii Shatov
 */

package chess.gui;


import chess.ai.MinimaxAI;
import chess.ai.Move;
import chess.figures.Bishop;
import chess.figures.King;
import chess.figures.Knight;
import chess.figures.Pawn;
import chess.figures.Queen;
import chess.figures.Rook;
import chess.figures.ChessPiece;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;


public class ChessGUI {
    private final JFrame mainFrame = new JFrame();
    private final int size = 8; // Size of the chess board
    private final JButton[][] boardButtons = new JButton[this.size][this.size]; // Buttons for each square on the board
    private final ChessPiece[][] chessPieces = new ChessPiece[this.size][this.size]; // Array to hold chess pieces
    private ChessPiece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isWhiteTurn = true;  // Start the game with White's turn
    private MinimaxAI ai;
    private MinimaxAI ai2;

    private final JLabel lastMoveLabel = new JLabel("Last Move: None");
    private JScrollPane moveHistoryScroll;
    private final JTextArea moveHistoryArea = new JTextArea(15, 30);  // Customize the size for your interface

    /**
     * Constructs the ChessGUI, sets up the main frame and initializes the board.
     */
    public ChessGUI() {
        this.moveHistoryScroll = new JScrollPane(this.moveHistoryArea);

        this.mainFrame.setTitle("Chess Game");
        this.mainFrame.setSize(1100, 800); // Set the main frame size
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initializeUI();

        int gameMode = GameStartDialog.showStartDialog();
        switch (gameMode) {
            case 0:
                // Playing on this PC
                break;
            case 1:
                // Game vs. AI
                this.ai = new MinimaxAI();
                break;
            case 2:
                // AI vs AI
                this.ai = new MinimaxAI();
                this.ai2 = new MinimaxAI();
                Timer aiTimer = new Timer(500, e -> this.aiVsAIMove());
                aiTimer.start();
                break;
        }

        this.initializeBoard();
        this.mainFrame.setVisible(true);
    }

    private void aiVsAIMove() {
        if (this.ai != null && this.ai2 != null) {
            MinimaxAI currentAI = this.isWhiteTurn ? this.ai : this.ai2;
            Move bestMove = currentAI.findBestMove(this.chessPieces, this.isWhiteTurn);
            this.executeMove(bestMove.getStartX(), bestMove.getStartY(), bestMove.getEndX(), bestMove.getEndY());
            this.isWhiteTurn = !this.isWhiteTurn; // Switch turns
            this.updateDisplay(); // Update display after AI move
            this.checkKingStatus(); // Check king status after each move
        }
    }

    /**
     * Sets up the remote multiplayer settings.
     */
    private void setupRemoteMultiplayer() {
        // Implementation for remote multiplayer
    }

    /**
     * Initializes the UI components.
     */
    private void initializeUI() {
        // Create and configure the text area for move history
        this.moveHistoryArea.setEditable(false);

        // Initialize JScrollPane
        this.moveHistoryScroll = new JScrollPane(this.moveHistoryArea);
        this.moveHistoryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create side panel and add JScrollPane
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(this.moveHistoryScroll, BorderLayout.CENTER);

        // Add side panel to the main frame
        this.mainFrame.add(sidePanel, BorderLayout.EAST);
    }

    /**
     * Initializes the chess board with buttons.
     */
    private void initializeBoard() {
        // Create panel for the chess board with GridLayout
        JPanel boardPanel = new JPanel(new GridLayout(this.size + 2, this.size + 2));

        for (int row = -1; row <= this.size; row++) {
            for (int col = -1; col <= this.size; col++) {
                if (row == -1 || col == -1 || row == this.size || col == this.size) {
                    JButton label = new JButton();
                    label.setOpaque(true);
                    label.setBackground(Color.LIGHT_GRAY);
                    if ((row == -1 || row == this.size) && col >= 0 && col < this.size) {
                        label.setText(String.valueOf((char)('A' + col)));
                    } else if ((col == -1 || col == this.size) && row >= 0 && row < this.size) {
                        label.setText(String.valueOf(this.size - row));
                    } else {
                        label.setVisible(false); // Hide unnecessary buttons
                    }
                    boardPanel.add(label);
                } else {
                    JButton button = new JButton();
                    button.setFont(new Font("Arial", Font.BOLD, 24));
                    button.setOpaque(true);
                    button.setBorderPainted(false);
                    button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                    final int finalRow = row;
                    final int finalCol = col;
                    button.addActionListener(e -> this.handleButtonClick(finalRow, finalCol));
                    this.boardButtons[row][col] = button;
                    boardPanel.add(button);
                }
            }
        }
        this.setupPieces();
        this.updateDisplay();

        this.mainFrame.add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Sets up the initial positions of the chess pieces on the board.
     */
    private void setupPieces() {
        // Setup black pieces
        this.chessPieces[0][0] = new Rook("Black", this.boardButtons[0][0]);
        this.chessPieces[0][1] = new Knight("Black", this.boardButtons[0][1]);
        this.chessPieces[0][2] = new Bishop("Black", this.boardButtons[0][2]);
        this.chessPieces[0][3] = new Queen("Black", this.boardButtons[0][3]);
        this.chessPieces[0][4] = new King("Black", this.boardButtons[0][4]);
        this.chessPieces[0][5] = new Bishop("Black", this.boardButtons[0][5]);
        this.chessPieces[0][6] = new Knight("Black", this.boardButtons[0][6]);
        this.chessPieces[0][7] = new Rook("Black", this.boardButtons[0][7]);

        // Setup white pieces
        this.chessPieces[7][0] = new Rook("White", this.boardButtons[7][0]);
        this.chessPieces[7][1] = new Knight("White", this.boardButtons[7][1]);
        this.chessPieces[7][2] = new Bishop("White", this.boardButtons[7][2]);
        this.chessPieces[7][3] = new Queen("White", this.boardButtons[7][3]);
        this.chessPieces[7][4] = new King("White", this.boardButtons[7][4]);
        this.chessPieces[7][5] = new Bishop("White", this.boardButtons[7][5]);
        this.chessPieces[7][6] = new Knight("White", this.boardButtons[7][6]);
        this.chessPieces[7][7] = new Rook("White", this.boardButtons[7][7]);

        // Setup pawns
        for (int i = 0; i < this.size; i++) {
            this.chessPieces[1][i] = new Pawn("Black", this.boardButtons[1][i]);
            this.chessPieces[6][i] = new Pawn("White", this.boardButtons[6][i]);
        }
    }

    /**
     * Checks if the King of the specified color is in check.
     *
     * @param color The color of the King to check.
     * @return True if the King is in check, false otherwise.
     */
    public boolean isKingInCheck(String color) {
        int kingRow = -1;
        int kingCol = -1;

        // Find the king
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.chessPieces[i][j] != null && this.chessPieces[i][j] instanceof King && this.chessPieces[i][j].getColor().equals(color)) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        if (kingRow == -1 || kingCol == -1) {
            JOptionPane.showMessageDialog(this.mainFrame, "Due to the situation the game ended prematurely.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            this.mainFrame.dispose();
            return false; // King not found, exceptional situation
        }

        // Check if any opposing pieces can attack the king's position
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.chessPieces[i][j] != null && !this.chessPieces[i][j].getColor().equals(color)) {
                    if (this.chessPieces[i][j].getValidMoves(this.chessPieces, i, j)[kingRow][kingCol]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Highlights the King in the specified color.
     *
     * @param kingRow The row of the King.
     * @param kingCol The column of the King.
     * @param color   The color to highlight the King.
     */
    private void highlightKing(int kingRow, int kingCol, Color color) {
        this.boardButtons[kingRow][kingCol].setBackground(color);
    }

    /**
     * Updates the display of the chess board.
     */
    private void updateDisplay() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (this.chessPieces[row][col] != null) {
                    this.boardButtons[row][col].setText(this.chessPieces[row][col].getSymbol());
                    this.boardButtons[row][col].setForeground(this.chessPieces[row][col].getColor().equals("Black") ? Color.PINK : Color.BLACK);
                } else {
                    this.boardButtons[row][col].setText("");
                }
            }
        }
    }

    /**
     * Handles button click events on the chess board.
     *
     * @param row The row of the clicked button.
     * @param col The column of the clicked button.
     */
    private void handleButtonClick(int row, int col) {
        if (this.selectedPiece != null) {
            if ((this.isWhiteTurn && this.selectedPiece.getColor().equals("White")) || (!this.isWhiteTurn && this.selectedPiece.getColor().equals("Black"))) {
                if (this.selectedPiece.getValidMoves(this.chessPieces, this.selectedRow, this.selectedCol)[row][col]) {
                    this.executeMove(this.selectedRow, this.selectedCol, row, col);
                    this.isWhiteTurn = !this.isWhiteTurn; // Switch turns
                    this.updateDisplay(); // Update display after all moves

                    if (!this.isWhiteTurn && this.ai != null) {
                        Move bestMove = this.ai.findBestMove(this.chessPieces, false);
                        this.executeMove(bestMove.getStartX(), bestMove.getStartY(), bestMove.getEndX(), bestMove.getEndY());
                        this.isWhiteTurn = !this.isWhiteTurn; // Switch turns back after AI move
                        this.updateDisplay(); // Update display after AI move
                    }
                }
                this.selectedPiece = null;
                this.clearHighlights();
            }
        } else if (this.chessPieces[row][col] != null && ((this.isWhiteTurn && this.chessPieces[row][col].getColor().equals("White")) || (!this.isWhiteTurn && this.chessPieces[row][col].getColor().equals("Black")))) {
            this.selectedPiece = this.chessPieces[row][col];
            this.selectedRow = row;
            this.selectedCol = col;
            this.clearHighlights();
            this.highlightMoves(row, col);
        }

        this.checkKingStatus();
    }

    /**
     * Checks the status of the Kings and highlights them if in check or checkmate.
     */
    private void checkKingStatus() {
        if (this.isKingInCheck("White")) {
            int[] kingPosition = this.findKing("White");
            if (this.isCheckmate("White")) {
                this.highlightKing(kingPosition[0], kingPosition[1], Color.RED);
                this.handleGameOver("Black"); // Black wins
            } else {
                this.highlightKing(kingPosition[0], kingPosition[1], Color.ORANGE);
            }
        }
        if (this.isKingInCheck("Black")) {
            int[] kingPosition = this.findKing("Black");
            if (this.isCheckmate("Black")) {
                this.highlightKing(kingPosition[0], kingPosition[1], Color.RED);
                this.handleGameOver("White"); // White wins
            } else {
                this.highlightKing(kingPosition[0], kingPosition[1], Color.ORANGE);
            }
        }
    }

    /**
     * Finds the King's position for the specified color.
     *
     * @param color The color of the King to find.
     * @return The position of the King as an array [row, col].
     */
    private int[] findKing(String color) {
        // Find the king's position for the specified color
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.chessPieces[i][j] instanceof King && this.chessPieces[i][j].getColor().equals(color)) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Return invalid position if king not found
    }

    /**
     * Checks if the specified color is in checkmate.
     *
     * @param color The color to check for checkmate.
     * @return True if in checkmate, false otherwise.
     */
    private boolean isCheckmate(String color) {
        // Check if there are any possible moves that can defend the king
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.chessPieces[i][j] != null && this.chessPieces[i][j].getColor().equals(color)) {
                    boolean[][] validMoves = this.chessPieces[i][j].getValidMoves(this.chessPieces, i, j);
                    for (int k = 0; k < this.size; k++) {
                        for (int l = 0; l < this.size; l++) {
                            if (validMoves[k][l]) {
                                ChessPiece capturedPiece = this.chessPieces[k][l];
                                this.chessPieces[k][l] = this.chessPieces[i][j];
                                this.chessPieces[i][j] = null;
                                if (!this.isKingInCheck(color)) {
                                    // Revert the piece to its original position
                                    this.chessPieces[i][j] = this.chessPieces[k][l];
                                    this.chessPieces[k][l] = capturedPiece;
                                    return false;
                                }
                                // Revert the piece to its original position
                                this.chessPieces[i][j] = this.chessPieces[k][l];
                                this.chessPieces[k][l] = capturedPiece;
                            }
                        }
                    }
                }
            }
        }
        return true; // If there are no moves to avoid check, it's checkmate
    }

    /**
     * Handles game over logic, displaying a message and disabling the board.
     *
     * @param winningColor The color of the player who won.
     */
    private void handleGameOver(String winningColor) {
        JOptionPane.showMessageDialog(this.mainFrame, "Checkmate! " + winningColor + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.boardButtons[row][col].setEnabled(false);
            }
        }
    }

    /**
     * Clears all highlighted squares on the board.
     */
    private void clearHighlights() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.boardButtons[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    /**
     * Highlights valid moves for the selected piece.
     *
     * @param row The starting row of the selected piece.
     * @param col The starting column of the selected piece.
     */
    private void highlightMoves(int row, int col) {
        if (this.selectedPiece != null) {
            boolean[][] validMoves = this.selectedPiece.getValidMoves(this.chessPieces, row, col);
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (validMoves[i][j]) {
                        this.boardButtons[i][j].setBackground(Color.YELLOW);
                    }
                }
            }
        }
    }

    /**
     * Executes a move from the start position to the end position.
     *
     * @param startRow The starting row of the move.
     * @param startCol The starting column of the move.
     * @param endRow   The ending row of the move.
     * @param endCol   The ending column of the move.
     */
    private void executeMove(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece movedPiece = this.chessPieces[startRow][startCol];
        String moveText = String.format("%s %s from %c%d to %c%d",
                movedPiece.getColor(), movedPiece.getClass().getSimpleName(),
                'A' + startCol, 8 - startRow, 'A' + endCol, 8 - endRow);

        this.lastMoveLabel.setText("Last Move: " + moveText);  // Update the last move label
        this.moveHistoryArea.append(moveText + "\n");  // Add move to history area

        this.chessPieces[endRow][endCol] = movedPiece;
        this.chessPieces[startRow][startCol] = null;
        this.boardButtons[endRow][endCol].setText(this.boardButtons[startRow][startCol].getText());
        this.boardButtons[startRow][startCol].setText("");

        // Check if a pawn reaches the last row for promotion
        if (this.chessPieces[endRow][endCol] instanceof Pawn) {
            if ((this.chessPieces[endRow][endCol].getColor().equals("White") && endRow == 0) ||
                    (this.chessPieces[endRow][endCol].getColor().equals("Black") && endRow == 7)) {
                this.promotePawn(endRow, endCol);
            }
        }

        this.updateDisplay();
        this.clearHighlights();
        this.moveHistoryArea.setCaretPosition(this.moveHistoryArea.getDocument().getLength());
    }

    /**
     * Promotes a pawn that has reached the end of the board.
     *
     * @param row The row of the pawn to be promoted.
     * @param col The column of the pawn to be promoted.
     */
    private void promotePawn(int row, int col) {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        String newPiece = (String)JOptionPane.showInputDialog(this.mainFrame, "Choose piece for pawn promotion:", "Pawn Promotion", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (newPiece) {
            case "Queen":
                this.chessPieces[row][col] = new Queen(this.chessPieces[row][col].getColor(), this.boardButtons[row][col]);
                break;
            case "Rook":
                this.chessPieces[row][col] = new Rook(this.chessPieces[row][col].getColor(), this.boardButtons[row][col]);
                break;
            case "Bishop":
                this.chessPieces[row][col] = new Bishop(this.chessPieces[row][col].getColor(), this.boardButtons[row][col]);
                break;
            case "Knight":
                this.chessPieces[row][col] = new Knight(this.chessPieces[row][col].getColor(), this.boardButtons[row][col]);
                break;
        }
        this.boardButtons[row][col].setText(this.chessPieces[row][col].getSymbol());  // Update the text on the button
    }
}
